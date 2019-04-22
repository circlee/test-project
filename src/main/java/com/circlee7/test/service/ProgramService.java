package com.circlee7.test.service;

import com.circlee7.test.model.ProgramDTO;

public interface ProgramService {


    ProgramDTO saveProgram(ProgramDTO programDTO);

    ProgramDTO selectProgram(Long programId);
}
