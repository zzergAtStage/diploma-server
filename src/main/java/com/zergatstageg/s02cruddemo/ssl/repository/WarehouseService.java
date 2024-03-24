package com.zergatstageg.s02cruddemo.ssl.repository;

import com.zergatstageg.s02cruddemo.ssl.domain.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseService extends JpaRepository<Earthquake, Long> {
    Earthquake getEarthquakesByDepth(int depth);
}
