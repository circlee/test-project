package com.circlee7.test.service.impl;

import com.circlee7.test.Repository.RegionRepository;
import com.circlee7.test.domain.Program;
import com.circlee7.test.domain.Region;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.model.RegionDTO;
import com.circlee7.test.service.RegionService;
import com.circlee7.test.util.DtoTranformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {


    private final RegionRepository regionRepository;

    private final Pattern REMOVE_AREA_OVER_ALL = Pattern.compile("(\\s+)?(일대|일원)(\\s+)$");
    private final Pattern TRIM_SAN_BUNJI = Pattern.compile("(.+?)\\s*(산)?\\s*(\\d+(-\\d+)?)\\s*(번지)?(.*?)");
    private final Pattern SPLIT_AREA_WITH = Pattern.compile("(\\s+)?[,~및](\\s+)?");


    @Override
    @Transactional
    public List<Region> createOrGetRegionListByServiceRegionText(String serviceRegion) {

        List<String> regionNameList = getRegionNameListByServiceRegion(serviceRegion);

        String rootName = Optional.ofNullable(regionNameList)
                .map( names -> names.stream().findFirst().orElse(""))
                .orElse("");

        return regionNameList
                .stream()
                .map(regionName -> {
                    if(regionName.equals(rootName)) {
                        return createOrGetRegionByRegionName(regionName, null);
                    }

                    return createOrGetRegionByRegionName(regionName, rootName);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Region createOrGetRegionByRegionName(String regionName, String rootName) {

        Region equalRegion =
                regionRepository.findTopByRegionNameEqualsAndRootNameEquals(regionName, rootName);
        if(equalRegion != null) {
             return equalRegion;
        }


        if(regionName.matches(".*[시구군읍면동]")) {
            Region candidateRegion =
                    regionRepository.findTopByRegionNameEqualsAndRootNameEquals(regionName.substring(0,regionName.length() -1), rootName);

            if(candidateRegion != null) {
                candidateRegion.setRegionName(regionName);
                return regionRepository.save(candidateRegion);
            }
        }

        Region newRegion = Region.builder().regionName(regionName).rootName(rootName).build();
        return regionRepository.save(newRegion);
    }

    // 일단 시도/ 시구군 까지만..
    @Override
    public List<String> getRegionNameListByServiceRegion(String serviceRegion) {


        String regionText = serviceRegion;

        // 1 일대 / 일원 제거
        Matcher removeAreaOverAllMatcher = REMOVE_AREA_OVER_ALL.matcher(regionText);
        regionText = removeAreaOverAllMatcher.replaceAll("");


        // 산 번지 공백제거
        Matcher trimSanBunjiMatcher = TRIM_SAN_BUNJI.matcher(regionText);
        if(trimSanBunjiMatcher.find()){
            regionText = trimSanBunjiMatcher.replaceFirst("$1 $2$3$5$6").trim();
        }


        // ,~및 파이프 처리
        Matcher splitAreaWithMatcher = SPLIT_AREA_WITH.matcher(regionText);
        if(splitAreaWithMatcher.find()) {
            regionText = splitAreaWithMatcher.replaceAll("|");
        }

        String[] splitRegion = regionText.split(" ");


        return Arrays.stream(splitRegion)
                .limit(2)
                .flatMap(s -> {
                    return Arrays.stream(s.split("\\|"));
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<RegionDTO> getRegionList() {
       return regionRepository.findAll()
                .stream()
                .map(region -> {

                    return RegionDTO.builder()
                            .region(region.getId())
                            .regionName(region.getFullRegionName()
                            ).build();

                }).collect(Collectors.toList());
    }

    @Override
    public List<ProgramDTO> getRegionProgramList(String regionId) {

        Set<Program> programs = regionRepository.findById(regionId)
                .map( region -> {
                return region.getPrograms();
                }).orElse(Collections.emptySet());


        return programs.stream()
                .map(DtoTranformer.PROGRAM_TO_DTO)
                .collect(Collectors.toList());
    }


}
