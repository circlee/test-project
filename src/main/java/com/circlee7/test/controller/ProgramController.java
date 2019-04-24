package com.circlee7.test.controller;

import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Program API", description = "지역 프로그램 API")
@RestController
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @ApiOperation("지역 프로그램 추가")
    @PostMapping
    public ProgramDTO insertProgram(@RequestBody @Valid ProgramDTO programDTO){
        return programService.saveProgram(programDTO);
    }

    @ApiOperation("지역 프로그램 수정")
    @PutMapping("/{programId}")
    public ProgramDTO updateProgram(@PathVariable("programId") String programId, @RequestBody @Valid ProgramDTO programDTO){
        programDTO.setProgram(programId);
        return programService.saveProgram(programDTO);
    }

    @ApiOperation("지역 프로그램 삭제")
    @DeleteMapping("/{programId}")
    public void deleteProgram(@PathVariable("programId") String programId){
        programService.deleteProgram(programId);
    }


}
