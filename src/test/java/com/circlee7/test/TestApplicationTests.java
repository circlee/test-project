package com.circlee7.test;

import com.circlee7.test.component.CsvParser;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestApplicationTests {


    @Autowired
    CsvParser csvParser;

    @Autowired
    ProgramService programService;

    @Test
    public void contextLoads() {
    }



    @Test(expected = EntityNotFoundException.class)
    public void test00_selectProgram_notFound(){
        programService.selectProgram("prg0012");
    }

    @Test
    public void test01_saveProgram(){

        ProgramDTO programDTO = ProgramDTO.builder()
                .prgmDescription("정보 상세")
                .prgmInfo("정보")
                .serviceRegion("경기도 파주시")
                .prgmName("프로그램명")
                .theme("theme - 테마")
                .build();

        programDTO = programService.saveProgram(programDTO);

        ProgramDTO savedProgramDTO = programService.selectProgram(programDTO.getProgram());

        log.debug("savedProgramInfo >> {}", savedProgramDTO);
        Assert.assertEquals(programDTO, savedProgramDTO);

    }

    @Test
    public void test02_csvParseTest() throws IOException {

        InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

        csvParser.read(HashMap.class, fileStream);

    }

    @Test
    public void test03_csvParseTestToDTO() throws IOException {

        InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

        csvParser.read(ProgramDTO.ProgramCsvDTO.class, fileStream);
    }

    @Test
    public void test04_csvToBulkInsert() throws IOException {

        InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

        programService.bulkInsertCSV(fileStream);
    }


}
