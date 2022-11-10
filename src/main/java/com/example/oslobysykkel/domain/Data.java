package com.example.oslobysykkel.domain;

import java.util.List;

public class Data {
    private List<Station> stations = null;

    public Data() {
    }
    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }
}