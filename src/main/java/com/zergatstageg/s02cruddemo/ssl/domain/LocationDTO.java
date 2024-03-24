package com.zergatstageg.s02cruddemo.ssl.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class LocationDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id")
    private Long locationId;
    private double latitude;
    private double longitude;

    public LocationDTO( float lat, float lon) {
        latitude = lat;
        longitude = lon;
    }
}
