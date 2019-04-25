package com.circlee7.test;

import com.circlee7.test.advice.ExceptionAdvice;
import com.circlee7.test.component.CsvParser;
import com.circlee7.test.controller.ProgramController;
import com.circlee7.test.controller.RegionController;
import com.circlee7.test.controller.SearchController;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import com.circlee7.test.model.SearchRegionDTO;
import com.circlee7.test.util.CustomException;
import org.springframework.test.annotation.Rollback;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class TestApplicationTests {

    private MockMvc mockProgramControllerMvc;
    private MockMvc mockRegionControllerMvc;
    private MockMvc mockSearchControllerMvc;

    private ObjectMapper om = new ObjectMapper();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CsvParser csvParser;

    @Autowired
    ProgramService programService;

    @Autowired
    ProgramController programController;

    @Autowired
    RegionController regionController;

    @Autowired
    SearchController searchController;

    @Autowired
    ExceptionAdvice exHandler;

    @Test
    public void contextLoads() {
    }


    @Before
    public void setUp() throws Exception {
        mockProgramControllerMvc = MockMvcBuilders.standaloneSetup(programController).setControllerAdvice(exHandler).build();
        mockRegionControllerMvc = MockMvcBuilders.standaloneSetup(regionController).setControllerAdvice(exHandler).build();
        mockSearchControllerMvc = MockMvcBuilders.standaloneSetup(searchController).setControllerAdvice(exHandler).build();
    }


    @Test(expected = CustomException.class)
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

    @Test
    public void test05_regionApi_01insertCSVProgram() throws Exception {

        InputStream fileStream = getClass().getResourceAsStream("/sample/test.csv");

        MockMultipartFile file = new MockMultipartFile("file", fileStream);
        mockRegionControllerMvc.perform(multipart("/regions/bulkCSV").file(file))
                .andExpect(status().isCreated());

    }

    @Test
    public void test05_regionApi_02getList() throws Exception {

        mockRegionControllerMvc.perform(get("/regions"))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_regionApi_03getProgram() throws Exception {

        mockRegionControllerMvc.perform(get("/regions/{regionId}","reg0001"))
                .andExpect(status().isOk());
    }

    @Test
    public void test06_programApi_01insertProgram() throws Exception {


        ProgramDTO dto = ProgramDTO.builder().prgmName("TEST")
                .theme("소풍")
                .serviceRegion("강원도 속초")
                .prgmInfo("설악산")
                .prgmDescription("설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요?")
                .build();

        mockProgramControllerMvc.perform(post("/programs")
                .content(om.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    @Test
    public void test06_programApi_02updateProgram() throws Exception {

        //insert
        test06_programApi_01insertProgram();

        ProgramDTO dto = ProgramDTO.builder().prgmName("TEST")
                .theme("소풍")
                .serviceRegion("강원도 속초")
                .prgmInfo("설악산")
                .prgmDescription("설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요?")
                .build();

        mockProgramControllerMvc.perform(put("/programs/{programId}","prg0001")
                .content(om.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    
    @Test
    public void test06_programApi_03deleteProgram() throws Exception {

        ProgramDTO dto = ProgramDTO.builder().prgmName("TEST")
                .theme("소풍")
                .serviceRegion("강원도 속초")
                .prgmInfo("설악산")
                .prgmDescription("설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요?")
                .build();

        //insert
        dto = programService.saveProgram(dto);

        mockProgramControllerMvc.perform(delete("/programs/{programId}", dto.getProgram()))
                .andExpect(status().isOk());

    }

    @Test
    public void test07_searchApi_01searchRegion() throws Exception {

        ProgramDTO dto = ProgramDTO.builder().prgmName("TEST")
        .theme("소풍")
        .serviceRegion("강원도 속초")
        .prgmInfo("설악산")
        .prgmDescription("설악산은 왜 설악산이고, 신흥사는 왜 신흥사일까요?")
        .build();

        //insert
        dto = programService.saveProgram(dto);

        mockSearchControllerMvc.perform(post("/search/region")
        .content(om.writeValueAsString(SearchRegionDTO.builder().region("강원도").build()))
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }






}
