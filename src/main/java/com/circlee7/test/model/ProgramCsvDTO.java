package com.circlee7.test.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@JsonPropertyOrder({"number", "prgmName", "theme", "serviceRegion", "prgmInfo", "prgmDescription"})
public class ProgramCsvDTO extends ProgramDTO {
    private String number;
}
