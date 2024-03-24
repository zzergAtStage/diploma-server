package com.zergatstageg.s02cruddemo;

import com.zergatstageg.s02cruddemo.ssl.config.DefaultDataConfigured;
import com.zergatstageg.s02cruddemo.ssl.domain.Earthquake;
import com.zergatstageg.s02cruddemo.ssl.repository.WarehouseService;
import de.fhpotsdam.unfolding.data.PointFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@PropertySource("classpath:application-defaults.properties")
public class HttpsEnabledApplication {


    public static void main(String... args) {
        SpringApplication application = new SpringApplication(HttpsEnabledApplication.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);


    }

}
