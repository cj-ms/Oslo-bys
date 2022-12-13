package com.example.oslobysykkel;

import com.example.oslobysykkel.domain.Station;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class OsloBysykkelServiceTest {

    @InjectMocks
    OsloBysykkelService osloBysykkelService;

    @Mock
    RestTemplateBuilder restTemplateBuilder;

    private List<Station> stationStatusList;
    private List<Station> stationInformationList;

    @Before
    public void setup() {
        stationStatusList = createStationStatusResponseList();
        stationInformationList = createStationInformationResponseList();
        osloBysykkelService.completeStationStatusList(stationStatusList, stationInformationList);
    }


    @Test
    @Order(1)
    public void testCompleteStationStatusListMethodMapNameCorrect() {
        assertTrue("Assert every station-object gets its name",stationStatusList.stream().allMatch(station -> station.getName() != null));
        boolean mappedCorrect = false;
        for (Station stationStatus: stationStatusList) {
            for (Station stationInformation: stationInformationList) {
                if (stationStatus.getStation_id().equals(stationInformation.getStation_id())) {
                    mappedCorrect = stationStatus.getName().equals(stationInformation.getName());
                    break;
                }
            }
        }
        assertTrue("Assert every station-object gets its correct name", mappedCorrect);
    }

    @Test
    @Order(2)
    public void testCompleteStationStatusListMethodSortOnName() {
        assertEquals("Assert A-Station to be sorted first", "A-Station", stationStatusList.get(0).getName());
        assertEquals("Assert C-Station to be sorted last", "C-Station", stationStatusList.get(2).getName());
    }


    private static List<Station> createStationInformationResponseList() {
        List<Station> stationInformationList = new ArrayList<>();
        Station stationInfoOne = new Station();
        stationInfoOne.setStation_id(1);
        stationInfoOne.setName("A-Station");

        Station stationInfoTwo = new Station();
        stationInfoTwo.setStation_id(2);
        stationInfoTwo.setName("B-Station");

        Station stationInfoThree = new Station();
        stationInfoThree.setStation_id(3);
        stationInfoThree.setName("C-Station");

        stationInformationList.add(stationInfoThree);
        stationInformationList.add(stationInfoTwo);
        stationInformationList.add(stationInfoOne);


        return stationInformationList;
    }

    private static List<Station> createStationStatusResponseList() {
        List<Station> stationStatusList = new ArrayList<>();
        Station stationOne = new Station();
        stationOne.setStation_id(1);
        stationOne.setNum_bikes_available(3);
        stationOne.setNum_docks_available(6);

        Station stationTwo = new Station();
        stationTwo.setStation_id(2);
        stationTwo.setNum_bikes_available(4);
        stationTwo.setNum_docks_available(8);

        Station stationThree = new Station();
        stationThree.setStation_id(3);
        stationThree.setNum_bikes_available(5);
        stationThree.setNum_docks_available(10);

        stationStatusList.add(stationThree);
        stationStatusList.add(stationTwo);
        stationStatusList.add(stationOne);

        return stationStatusList;
    }
}