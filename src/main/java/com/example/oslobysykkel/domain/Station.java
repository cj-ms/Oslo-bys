package com.example.oslobysykkel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station implements Comparable<Station> {
    public Station() {
    }
    private Integer station_id;
    private String name;
    private Integer num_bikes_available;
    private Integer num_docks_available;


    public Integer getNum_bikes_available() {
        return num_bikes_available;
    }

    public void setNum_bikes_available(Integer num_bikes_available) {
        this.num_bikes_available = num_bikes_available;
    }

    public Integer getNum_docks_available() {
        return num_docks_available;
    }

    public void setNum_docks_available(Integer num_docks_available) {
        this.num_docks_available = num_docks_available;
    }

    public Integer getStation_id() {
        return station_id;
    }

    public void setStation_id(Integer station_id) {
        this.station_id = station_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Station s) {
        if (getName() == null || s.getName() == null) {
            return 0;
        }
        return getName().compareTo(s.getName());
    }
}
