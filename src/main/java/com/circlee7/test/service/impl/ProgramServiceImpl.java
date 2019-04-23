package com.circlee7.test.service.impl;

import com.circlee7.test.Repository.ProgramRepository;
import com.circlee7.test.component.CsvParser;
import com.circlee7.test.domain.Program;
import com.circlee7.test.exception.InCaseException;
import com.circlee7.test.model.ProgramCsvDTO;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    private final CsvParser csvParser;

    @Override
    @Transactional
    public void bulkInsertCSV(MultipartFile file) {

        List<ProgramCsvDTO> csvDtoList = null;
        try {

            csvDtoList = csvParser.read(ProgramCsvDTO.class, file.getInputStream());

        } catch (IOException e) {
            throw new InCaseException(e.getCause());
        }

        Optional.ofNullable(csvDtoList)
                .ifPresent( dtoList -> {
                    dtoList.stream()
                            .forEach(dto -> {
                                System.out.println(dto);
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
                .build();


        // create/update info
        program.setCreatedAt(LocalDateTime.now());
        program.setCreatedBy(1L);
        program.setUpdatedAt(LocalDateTime.now());
        program.setUpdatedBy(1L);

        program = programRepository.save(program);

        programDTO.setProgram(program.getId());
        return programDTO;
    }

    @Override
    public ProgramDTO selectProgram(String programId) {

        return programRepository.findById(programId)
                .map( program -> {
                    return ProgramDTO.builder()
                            .program(program.getId())
                            .prgmName(program.getPrgmName())
                            .serviceRegion(program.getServiceRegion())
                            .prgmInfo(program.getPrgmInfo())
                            .prgmDescription(program.getPrgmDescription())
                            .theme(program.getTheme())
                            .build();
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
