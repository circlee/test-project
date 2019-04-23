package com.circlee7.test.service;

import com.circlee7.test.model.ProgramDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProgramService {

    void bulkInsertCSV(MultipartFile file);

    ProgramDTO saveProgram(ProgramDTO programDTO);

    ProgramDTO selectProgram(String programId);
}
