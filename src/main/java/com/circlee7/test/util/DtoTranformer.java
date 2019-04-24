package com.circlee7.test.util;

import com.circlee7.test.domain.Program;
import com.circlee7.test.domain.Region;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.model.RegionDTO;

import java.util.function.Function;

public class DtoTranformer {


    public final static Function<Program, ProgramDTO> PROGRAM_TO_DTO = (program) -> {
        return ProgramDTO.builder()
                .program(program.getId())
                .prgmName(program.getPrgmName())
                .serviceRegion(program.getServiceRegion())
                .prgmInfo(program.getPrgmInfo())
                .prgmDescription(program.getPrgmDescription())
                .theme(program.getTheme())
                .build();
    } ;

    public final static Function<Region, RegionDTO> REGION_TO_DTO = (region) -> {
        return RegionDTO.builder().region(region.getId())
                .regionName(region.getFullRegionName())
                .build();
    } ;


}
