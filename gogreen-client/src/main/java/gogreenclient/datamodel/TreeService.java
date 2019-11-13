package gogreenclient.datamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TreeService {

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
     * Sending the newly planted tree to server. After server has saved it, a "login" request will
     * be sent to insertHistory table to refresh co2_saved data.
     *
     * @param tree object to be sent to server.
     * @return response entity.
     */
    public ResponseEntity<String> plantingTree(Tree tree) {
        ResponseEntity<String> response = restTemplate
            .postForEntity(url + "/addTree", tree, String.class);
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
