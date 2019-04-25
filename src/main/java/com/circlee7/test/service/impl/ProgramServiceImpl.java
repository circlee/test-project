package com.circlee7.test.service.impl;

import com.circlee7.test.Repository.ProgramRepository;
import com.circlee7.test.component.CsvParser;
import com.circlee7.test.domain.Program;
import com.circlee7.test.domain.Region;
import com.circlee7.test.exception.InCaseException;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import com.circlee7.test.service.RegionService;
import com.circlee7.test.util.DtoTranformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.circlee7.test.util.CustomException;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final RegionService regionService;

    private final ProgramRepository programRepository;

    private final CsvParser csvParser;

    @Override
    @Transactional
    public void bulkInsertCSV(InputStream is) {

        List<ProgramDTO.ProgramCsvDTO> csvDtoList = null;
        try {

            csvDtoList = csvParser.read(ProgramDTO.ProgramCsvDTO.class, is);

        } catch (IOException e) {
            throw new InCaseException(e.getCause());
        }

        Optional.ofNullable(csvDtoList)
                .ifPresent( dtoList -> {
                    dtoList.stream()
                            .forEach(dto -> {
                                saveProgram(dto);
                            });
                });
    }

    @Override
    @Transactional
    public ProgramDTO saveProgram(ProgramDTO programDTO) {

        Program program = Program.builder()
                .id(programDTO.getProgram())
                .prgmName(programDTO.getPrgmName())
                .serviceRegion(programDTO.getServiceRegion())
                .prgmInfo(programDTO.getPrgmInfo())
                .prgmDescription(programDTO.getPrgmDescription())
                .theme(programDTO.getTheme())
                .regionMapping(new HashSet<Region>(regionService.createOrGetRegionListByServiceRegionText(programDTO.getServiceRegion())))
                .build();


        program = programRepository.save(program);



        programDTO.setProgram(program.getId());
        return programDTO;
    }

    @Override
    @Transactional
    public ProgramDTO updateProgram(ProgramDTO programDTO) {
        Program program = programRepository.getOne(programDTO.getProgram());

        return saveProgram(programDTO);
    }

    @Override
    public ProgramDTO selectProgram(String programId) {

        return programRepository.findById(programId)
                .map(DtoTranformer.PROGRAM_TO_DTO)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteProgram(String programId) {

        Optional<Program> programOpt = programRepository.findById(programId);
        Program program = programOpt.orElse(null);
        if(program != null) {
            programRepository.delete(program);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND);
        }

    }
}
