package com.circlee7.test;

import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {


    @Autowired
    ProgramService programService;

    @Test
    public void contextLoads() {
    }



    @Test(expected = EntityNotFoundException.class)
    public void test00_selectProgram_notFound(){
        programService.selectProgram(11231L);
    }

    @Test
    public void test01_saveProgram(){

        ProgramDTO programDTO = ProgramDTO.builder()
                .prgmNumber(1L)
                .prgmDescription("정보 상세")
                .prgmInfo("정보")
                .serviceRegion("경기도 파주시")
                .prgmName("프로그램명")
                .theme("theme - 테마")
                .build();

        programDTO = programService.saveProgram(programDTO);

        ProgramDTO savedProgramDTO = programService.selectProgram(programDTO.getPrgmNumber());

        Assert.assertEquals(programDTO, savedProgramDTO);

    }



}
