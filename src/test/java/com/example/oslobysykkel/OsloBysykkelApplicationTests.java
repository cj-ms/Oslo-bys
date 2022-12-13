package com.example.oslobysykkel;

import com.example.oslobysykkel.domain.Station;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class OsloBysykkelApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    private  OsloBysykkelController osloBysykkelController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    OsloBysykkelService osloBysykkelService;

    @Test
    void contextGetsLoaded() {
        Assert.assertNotNull(osloBysykkelController);


    }
    @Test
    void runApplicationAndPOJOFromEndpoint() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/api/stations";
        URI uri = new URI(baseUrl);

        ResponseEntity<Station[]> result = restTemplate.getForEntity(uri, Station[].class);
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertTrue(result.hasBody());
    }

    private boolean checkContainsValues(Station station) {
        return true;
    }

}
