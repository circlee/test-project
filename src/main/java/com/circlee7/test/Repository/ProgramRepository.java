package com.circlee7.test.Repository;

import com.circlee7.test.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, String> {

}
