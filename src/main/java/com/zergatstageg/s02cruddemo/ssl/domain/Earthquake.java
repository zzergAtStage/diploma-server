package com.zergatstageg.s02cruddemo.ssl.domain;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.Feature.FeatureType;
import de.fhpotsdam.unfolding.geo.Location;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
public class Earthquake extends Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long earthquakeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationDTO location;
    int depth;
    public Earthquake() {
        super(FeatureType.POINT);
    }
    public Earthquake(Location location) {
        super(FeatureType.POINT);
        this.location = new LocationDTO(location.getLat(), location.getLon());
    }
    public Earthquake(LocationDTO  location) {
        super(FeatureType.POINT);
        this.location = location;
    }

}
