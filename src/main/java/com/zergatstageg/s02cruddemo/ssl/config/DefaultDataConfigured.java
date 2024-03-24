package com.zergatstageg.s02cruddemo.ssl.config;

import com.zergatstageg.s02cruddemo.ssl.domain.Earthquake;
import com.zergatstageg.s02cruddemo.ssl.repository.WarehouseService;
import com.zergatstageg.s02cruddemo.ssl.services.ParseFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import processing.core.PApplet;

import java.util.Date;
import java.util.List;

@Configuration
public class DefaultDataConfigured {

    private final WarehouseService warehouseService;
    private final Logger logger = LoggerFactory.getLogger(DefaultDataConfigured.class);
    public DefaultDataConfigured(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    public static List<Earthquake> parseEarthquakes(String fileName){
        return ParseFeed.parseEarthquake(new PApplet(), fileName);
    }
    @Scheduled(fixedDelay = 10000)
    public void parseEarthQuakes(){
        String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
        List<Earthquake> earthquakes = DefaultDataConfigured.parseEarthquakes(earthquakesURL);
        for (Earthquake earthquake: earthquakes) {
            warehouseService.save(earthquake);
        }
        logger.info("Scheduled event: parseEarthQuakes finished at: " + new Date());
    }
}
