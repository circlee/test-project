package com.circlee7.test.controller;

import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.model.RegionDTO;
import com.circlee7.test.service.ProgramService;
import com.circlee7.test.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Api(value = "Region API", description = "서비스 지역 API")
@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final ProgramService programService;
    private final RegionService regionService;

    @ApiOperation("지역프로그램 csv 업로드")
    @PostMapping("/bulkCSV")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCSVProgram(@RequestParam("file") @NotNull MultipartFile file) throws IOException {
        programService.bulkInsertCSV(file.getInputStream());
    }

    @ApiOperation("서비스지역코드 목록 조회")
    @GetMapping
    public List<RegionDTO> getList(){
        return regionService.getRegionList();
    }

    @ApiOperation("서비스지역 프로그램 조회")
    @GetMapping("/{regionId}")
    public List<ProgramDTO> getProgram(@PathVariable("regionId") String regionId){
        return regionService.getRegionProgramList(regionId);
    }

}
