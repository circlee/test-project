package com.circlee7.test.controller;

import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.service.ProgramService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ProgramDTO getProgram(@PathVariable("programId") Long id){
        return programService.selectProgram(id);
    }

    @ApiOperation("프로그램 생성")
    @PostMapping
    public ProgramDTO insertProgram(@RequestBody @Valid ProgramDTO programDTO){

        return programService.saveProgram(programDTO);
    }

    @ApiOperation("프로그램 수정")
    @PutMapping("/{programId}")
    public ProgramDTO insertProgram(@PathVariable("programId") Long id, @RequestBody @Valid ProgramDTO programDTO){
        return null;
    }

    @ApiOperation("프로그램 부분수정")
    @PatchMapping("/{programId}")
    public ProgramDTO updaterogram(@PathVariable("programId") Long id, @RequestBody ProgramDTO programDTO){
        return null;
    }


}
