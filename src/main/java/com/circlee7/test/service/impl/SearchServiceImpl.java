package com.circlee7.test.service.impl;

import com.circlee7.test.Repository.ProgramRepository;
import com.circlee7.test.Repository.RegionRepository;
import com.circlee7.test.domain.Program;
import com.circlee7.test.domain.Region;
import com.circlee7.test.model.SearchKeywordDTO;
import com.circlee7.test.model.SearchKeywordProgramDTO;
import com.circlee7.test.model.SearchRegionDTO;
import com.circlee7.test.model.SearchRegionProgramDTO;
import com.circlee7.test.service.SearchService;
import com.circlee7.test.util.CustomException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final RegionRepository regionRepository;

    private final ProgramRepository programRepository;

    private final JPAQueryFactory queryFactory;







    @Override
    public SearchRegionDTO.SearchRegionDTO_result searchRegion(SearchRegionDTO searchRegionDTO) {

        String regionText = searchRegionDTO.getRegion();

        Region region = regionRepository.findTopByRegionNameContains(regionText);


        return Optional.ofNullable(region).map(r -> {

            SearchRegionDTO.SearchRegionDTO_result result =  new SearchRegionDTO.SearchRegionDTO_result();

            List<SearchRegionProgramDTO> programDTOList = Optional.ofNullable(r.getPrograms()).orElse(Collections.emptySet())
                    .stream()
                    .map( p -> {
                        return SearchRegionProgramDTO.builder()
                                .prgmName(p.getPrgmName())
                                .theme(p.getTheme())
                                .build();
                    })
                    .collect(Collectors.toList());


            result.setRegion(r.getId());
            result.setPrograms(programDTOList);

            return result;
        }).orElseThrow(() -> {
            return new CustomException(HttpStatus.NOT_FOUND);
        });


    }

    @Override
    public SearchKeywordDTO.SearchKeywordDTO_result searchKeyword(SearchKeywordDTO searchKeywordDTO) {


        List<Program> programs = programRepository.findProgramByPrgmInfoContains(searchKeywordDTO.getKeyword());

        List<SearchKeywordProgramDTO> programDTOList = programs.stream()
                .flatMap(p -> p.getRegionMapping().stream())
                .filter(region -> !StringUtils.isEmpty(region.getRootName()))
                .map(region -> region.getFullRegionName())
                .collect(Collectors.groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .map(entry -> {
                    SearchKeywordProgramDTO programDTO = new SearchKeywordProgramDTO();

                    programDTO.setRegion(entry.getKey());
                    programDTO.setCount(entry.getValue().size());
                    return programDTO;
                })
                .collect(Collectors.toList());

        SearchKeywordDTO.SearchKeywordDTO_result result = new SearchKeywordDTO.SearchKeywordDTO_result();

        result.setKeyword(searchKeywordDTO.getKeyword());
        result.setPrograms(programDTOList);


        return result;

    }

    @Override
    public SearchKeywordDTO.SearchKeywordDTO_countResult searchKeywordCount(SearchKeywordDTO searchKeywordDTO) {


        Pattern keywordPattern = Pattern.compile(searchKeywordDTO.getKeyword());


        long count = programRepository.findProgramByPrgmDescriptionContains(searchKeywordDTO.getKeyword())
                .stream()
                .map(p -> p.getPrgmDescription())
                .filter(s -> !StringUtils.isEmpty(s))
                .mapToLong( s -> {


                    Matcher m = keywordPattern.matcher(s);
                    long i = 0;
                    while(m.find()){
                        i++;
                    }
                    return i;
                })
                .sum();

        SearchKeywordDTO.SearchKeywordDTO_countResult result = new SearchKeywordDTO.SearchKeywordDTO_countResult();
        result.setKeyword(searchKeywordDTO.getKeyword());
        result.setCount(count);

        return result;

    }
}
