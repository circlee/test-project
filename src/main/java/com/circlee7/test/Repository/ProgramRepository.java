package com.circlee7.test.Repository;

import com.circlee7.test.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, String> {

    List<Program> findProgramByPrgmInfoContains(String keyword);

    List<Program> findProgramByPrgmDescriptionContains(String keyword);
}
