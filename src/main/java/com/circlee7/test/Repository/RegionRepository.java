package com.circlee7.test.Repository;

import com.circlee7.test.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, String> {

    Region findTopByRegionNameEqualsAndRootNameEquals(String regionName, String rootName);

    Region findTopByRegionNameContains(String regionName);
}
