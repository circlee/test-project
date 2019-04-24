package com.circlee7.test.controller;

import com.circlee7.test.model.SearchKeywordDTO;
import com.circlee7.test.model.SearchRegionDTO;
import com.circlee7.test.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@Api(value = "Search API", description = "검색 API")
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation("서비스 지역 문자열 지역 조회")
    @PostMapping("/region")
    public SearchRegionDTO.SearchRegionDTO_result searchRegion(@RequestBody @Valid SearchRegionDTO searchRegionDTO)  {
        return searchService.searchRegion(searchRegionDTO);
    }

    @ApiOperation("지역 프로그램 소개 서비스지역 갯수 조회")
    @PostMapping("/keyword")
    public SearchKeywordDTO.SearchKeywordDTO_result searchKeyword(@RequestBody @Valid SearchKeywordDTO searchKeywordDTO) throws IOException {
        return searchService.searchKeyword(searchKeywordDTO);
    }

    @ApiOperation("지역 프로그램 상세 키워드 노출 카운트")
    @PostMapping("/keywordCount")
    public SearchKeywordDTO.SearchKeywordDTO_countResult searchKeywordCount(@RequestBody @Valid SearchKeywordDTO searchKeywordDTO) throws IOException {
        return searchService.searchKeywordCount(searchKeywordDTO);
    }



}
