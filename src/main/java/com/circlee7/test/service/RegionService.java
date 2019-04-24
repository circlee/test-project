package com.circlee7.test.service;

import com.circlee7.test.domain.Region;
import com.circlee7.test.model.ProgramDTO;
import com.circlee7.test.model.RegionDTO;

import java.util.List;

public interface RegionService {

    List<Region> createOrGetRegionListByServiceRegionText(String serviceRegion);

    Region createOrGetRegionByRegionName(String regionName, String rootName);

    List<String> getRegionNameListByServiceRegion(String serviceRegion);

    List<RegionDTO> getRegionList();

    List<ProgramDTO> getRegionProgramList(String regionId);
}
