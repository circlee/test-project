package com.circlee7.test.service.impl;

import com.circlee7.test.Repository.ProgramRepository;
import com.circlee7.test.domain.Program;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;


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
