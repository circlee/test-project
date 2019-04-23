package com.circlee7.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDTO {


    private String program;

    @NotEmpty
    private String prgmName;

    @NotEmpty
    private String theme;

    @NotEmpty
    private String serviceRegion;

    @NotEmpty
    private String prgmInfo;

    @NotEmpty
    private String prgmDescription;
}
