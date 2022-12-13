package com.example.oslobysykkel;

import com.example.oslobysykkel.domain.Station;
import com.example.oslobysykkel.domain.StationInformation;
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
    private String stationInformationUrl;

    @Value("${endpoint.status.url}")
    private String stationStatusUrl;

    @Autowired
    public OsloBysykkelService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .build();
    }
    public List<Station> getStationsData() {
        List<Station> stationStatusList = getStationStatus();
        List<Station> stationInformationList = getStationInformation();
        completeStationStatusList(stationStatusList, stationInformationList);
        return stationStatusList;
    }

    public void completeStationStatusList(List<Station> stationStatusList, List<Station> stationInformationList) {
        for (Station stationInformation: stationInformationList) {
            for (Station stationStatus: stationStatusList) {
                if (Objects.equals(stationInformation.getStation_id(), stationStatus.getStation_id())) {
                    stationStatus.setName(stationInformation.getName());
                    break;
                }
            }
        }
        Collections.sort(stationStatusList);
    }

    public List<Station> getStationInformation() {
        return executeBysykkelRequest(stationInformationUrl);
    }
    public List<Station> getStationStatus() {
        return executeBysykkelRequest(stationStatusUrl);
    }

    private List<Station> executeBysykkelRequest(String url) {
        HttpEntity<String> request = getHttpEntity();
        try {
            ResponseEntity<StationInformation> stationNameObject = restTemplate.exchange(
                    url,
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
