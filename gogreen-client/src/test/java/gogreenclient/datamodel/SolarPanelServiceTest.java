package gogreenclient.datamodel;

import gogreenclient.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringRunner.class)
public class SolarPanelServiceTest {

    private SolarPanelService service;
    private String url = "https://localhost:8443/api/";

    @Before
    public void init() {
        service = new SolarPanelService();
        MockitoAnnotations.initMocks(this);
        service.setUrl(url);
        service.setUserName("Gru");
    }

    @Test
    public void getSolarPanelSizeNull() {
        setUpGetSizeNoError(0, HttpStatus.OK);
        assertTrue(service.getSolarPanelSize() == 0.0F);
    }

    @Test
    public void getSolarPanelSizeNotOk() {
        setUpGetSizeNoError(1, HttpStatus.CONTINUE);
        assertTrue(service.getSolarPanelSize() == 0.0F);
    }

    @Test
    public void getSolarPanelSizeOk() {
        setUpGetSizeNoError(1, HttpStatus.OK);
        assertTrue(service.getSolarPanelSize() == 1.0F);
    }

    @Test
    public void getSolarPanelSizeNotFound() {
        setUpGetSizeError(HttpStatus.NOT_FOUND);
        assertTrue(service.getSolarPanelSize() == 0);
    }

    @Test
    public void getSolarPanelSizeUnauthorized() {
        setUpGetSizeError(HttpStatus.UNAUTHORIZED);
        assertTrue(service.getSolarPanelSize() == 0);
    }

    @Test
    public void incrementSizNotOke() {
        AddSolarpanels solarpanels = new AddSolarpanels();
        solarpanels.setArea(2.7F);
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("w", HttpStatus.NOT_FOUND);
        when(template.postForEntity(
                anyString(),
                eq(solarpanels),
                eq(String.class)
        )).thenReturn(responseEntity);
        assertEquals(service.incrementSize(solarpanels).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void incrementSizeOke() {
        AddSolarpanels solarpanels = new AddSolarpanels();
        solarpanels.setArea(2.7F);
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("w", HttpStatus.OK);
        when(template.postForEntity(
                anyString(),
                eq(solarpanels),
                eq(String.class)
        )).thenReturn(responseEntity);
        when(template.postForEntity(
                anyString(),
                eq(new InsertHistory("Gru")),
                eq(String.class)
        )).thenReturn(responseEntity);
        assertTrue(service.incrementSize(solarpanels).getBody().equals("w"));
    }

    public void setUpGetSizeNoError(int t, HttpStatus status) {
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        ResponseEntity<List<AddSolarpanels>> response;
        if (t == 0) {
            response = null;
        } else {
            AddSolarpanels solarpanels = new AddSolarpanels();
            solarpanels.setUserName("Gru");
            solarpanels.setArea(1.0F);
            List<AddSolarpanels> list = new ArrayList<>();
            list.add(solarpanels);
            response = new ResponseEntity<List<AddSolarpanels>>(list, status);
        }
        when(template.exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<AddSolarpanels>>() {})
        )).thenReturn(response);
    }

    public void setUpGetSizeError(HttpStatus status) {
        RestTemplate template = new RestTemplate();
        MockRestServiceServer serviceServer = MockRestServiceServer.createServer(template);
        service.setRestTemplate(template);
        serviceServer.expect(requestTo(url + "addSolarpanel/Gru"))
                .andExpect(method(HttpMethod.GET))
                .andRespond((response) -> {throw new HttpClientErrorException(status);
                });
    }
}