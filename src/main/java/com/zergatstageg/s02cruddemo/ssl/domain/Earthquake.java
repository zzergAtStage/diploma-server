package com.zergatstageg.s02cruddemo.ssl.domain;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.Feature.FeatureType;
import de.fhpotsdam.unfolding.geo.Location;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Setter
public class Earthquake extends Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long earthquakeId;
    Location location;
    int depth;
    public Earthquake() {
        super(FeatureType.POINT);
    }
    public Earthquake(Location var1) {
        super(FeatureType.POINT);
        this.location = var1;
    }

}
