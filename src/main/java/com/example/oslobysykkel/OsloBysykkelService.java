package com.example.oslobysykkel;

import com.example.oslobysykkel.domain.Station;
import com.example.oslobysykkel.domain.StationInformation;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class OsloBysykkelService {
    private final RestTemplate restTemplate;

    @Value("${endpoint.information.url}")
    private String informationUrl;

    @Value("${endpoint.status.url}")
    private String statusUrl;

    @Autowired
    public OsloBysykkelService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .build();
    }
    public List<Station> getStationsInfo() {
        return getStations();
    }

    private List<Station> getStations() {
            ResponseEntity<String> stationInformation = getStationInformation(restTemplate);
            List<Station> stationStatus = getStationStatus(restTemplate);

            JsonArray jsonStationArray = JsonParser.parseString(
                            Objects.requireNonNull(stationInformation.getBody())).getAsJsonObject()
                    .getAsJsonObject("data")
                    .getAsJsonArray("stations");


            for (JsonElement jsonElement:jsonStationArray) {
                for (Station station:stationStatus) {
                    if (jsonElement.getAsJsonObject().get("station_id").getAsInt() == station.getStation_id()) {
                        station.setName(jsonElement.getAsJsonObject().get("name").getAsString());
                        break;
                    }
                }
            }
        Collections.sort(stationStatus);
        return stationStatus;
    }

    private ResponseEntity<String> getStationInformation(RestTemplate restTemplate) {
        HttpEntity<String> request = getHttpEntity();

       try {
           return restTemplate.exchange(
                   informationUrl,
                   HttpMethod.GET,
                   request,
                   String.class
           );
       }
       catch (HttpStatusCodeException e) {
           System.out.println(e.getResponseBodyAsString());
           throw e;
       }
    }

    private List<Station> getStationStatus(RestTemplate restTemplate) {
        HttpEntity<String> request = getHttpEntity();
       try {
           ResponseEntity<StationInformation> stationNameObject = restTemplate.exchange(
                   statusUrl,
                   HttpMethod.GET,
                   request,
                   StationInformation.class
           );

           return Objects.requireNonNull(stationNameObject.getBody()).getData().getStations();
       }
       catch (HttpStatusCodeException e) {
           System.out.println(e.getResponseBodyAsString());
           throw e;
       }

    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Client-Identifier", "Origo_Case_CJ");
        return new HttpEntity<>(headers);
    }
}
