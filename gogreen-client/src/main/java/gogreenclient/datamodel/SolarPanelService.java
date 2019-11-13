package gogreenclient.datamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class SolarPanelService {

    private List<AddSolarpanels> addSolarpanels;

    @Autowired
    private RestTemplate restTemplate;

    private String url;

    private String userName;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Read the current size of this user's solar panel.
     *
     * @return the current size of this user's solar panel. default value is 0.
     */
    public float getSolarPanelSize() {
        ResponseEntity<List<AddSolarpanels>> response = null;
        try {
            response = restTemplate.exchange(
                url + "/addSolarpanel/" + userName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AddSolarpanels>>() {
                }
            );

        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                return 0;
            }
        }
        float totalSize = 0;
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            addSolarpanels = response.getBody();
            for (AddSolarpanels a : addSolarpanels) {
                totalSize += a.getArea();
            }

        }
        return totalSize;
    }

    /**
     * This method will send a request to increment the size of user's solar panel.
     *
     * @param addSolarpanels solar panel object.
     * @return response entity.
     */
    public ResponseEntity<String> incrementSize(AddSolarpanels addSolarpanels) {
        ResponseEntity<String> response = restTemplate
            .postForEntity(url + "/addSolarpanel", addSolarpanels, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            restTemplate
                .postForEntity(url + "/insertHistory",
                    new InsertHistory(userName), String.class);
        }
        return response;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
