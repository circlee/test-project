package com.circlee7.test.Repository;

import com.circlee7.test.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, String> {

}
