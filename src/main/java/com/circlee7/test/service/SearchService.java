package com.circlee7.test.service;

import com.circlee7.test.model.SearchKeywordDTO;
import com.circlee7.test.model.SearchRegionDTO;

public interface SearchService {

    SearchRegionDTO.SearchRegionDTO_result searchRegion(SearchRegionDTO searchRegionDTO);

    SearchKeywordDTO.SearchKeywordDTO_result searchKeyword(SearchKeywordDTO searchKeywordDTO);

    SearchKeywordDTO.SearchKeywordDTO_countResult searchKeywordCount(SearchKeywordDTO searchKeywordDTO);


}
