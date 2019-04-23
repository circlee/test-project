package com.circlee7.test.controller;

import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(value = "Program API", description = "프로그램 API")
@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @ApiOperation("프로그램 목록 조회")
    @GetMapping
    public List<ProgramDTO> getList(){
        return null;
    }

    @ApiOperation("프로그램 조회")
    @GetMapping("/{programId}")
    public ProgramDTO getProgram(@PathVariable("programId") String id){
        return programService.selectProgram(id);
    }

    @ApiOperation("프로그램 생성")
    @PostMapping("/bulkCSV")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCSVProgram(@RequestParam("file") MultipartFile file) {
        programService.bulkInsertCSV(file);
    }

    @ApiOperation("프로그램 수정")
    @PutMapping("/{programId}")
    public ProgramDTO updateProgram(@PathVariable("programId") Long id, @RequestBody @Valid ProgramDTO programDTO){
        return null;
    }

    @ApiOperation("프로그램 부분수정")
    @DeleteMapping("/{programId}")
    public ProgramDTO deleteProgram(@PathVariable("programId") Long id){
        return null;
    }


}
