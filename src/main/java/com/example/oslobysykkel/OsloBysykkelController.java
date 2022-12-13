package com.example.oslobysykkel;

import com.example.oslobysykkel.domain.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OsloBysykkelController {
    @Autowired
    private OsloBysykkelService osloBySykkelService;

   @GetMapping(value = "api/stations", produces = "application/json")
    public List<Station> getStationList() {
       return osloBySykkelService.getStationsData();
    }
}
