package com.circlee7.test.service;

import com.circlee7.test.model.ProgramDTO;

import java.io.InputStream;

public interface ProgramService {

    void bulkInsertCSV(InputStream is);

    ProgramDTO saveProgram(ProgramDTO programDTO);

    ProgramDTO updateProgram(ProgramDTO programDTO);

    ProgramDTO selectProgram(String programId);

    void deleteProgram(String programId);
}
