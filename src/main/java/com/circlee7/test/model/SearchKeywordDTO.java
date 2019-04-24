package com.circlee7.test.model;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchKeywordDTO {

    @NotEmpty
    private String keyword;


    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class SearchKeywordDTO_result extends SearchKeywordDTO {

        List<SearchKeywordProgramDTO> programs;

    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    @NoArgsConstructor
    public static class SearchKeywordDTO_countResult extends SearchKeywordDTO {
        long count;
    }

}
