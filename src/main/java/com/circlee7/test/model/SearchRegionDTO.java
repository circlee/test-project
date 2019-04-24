package com.circlee7.test.model;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRegionDTO {

    @NotEmpty
    private String region;


    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class SearchRegionDTO_result extends SearchRegionDTO {

        List<SearchRegionProgramDTO> programs;

    }

}
