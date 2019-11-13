package gogreenclient.datamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExceptionHandler exceptionHandler;

    private String friendAmount = "0";

    private String username;
    private String url;


    /**
     * adding friend by userName. That user name will be checked on the server side, whether it's
     * a valid user name, whether it's already your friend. Then corresponding HttpStatus will be
     * returned.
     *
     * @param friendName friend's user name.
     * @return a int value, 1 for successfully adding friend, 0 for friend exists, -1 for adding
     *     failure.
     */
    public int addFriend(String friendName) {
        Friend friend = new Friend();
        friend.setAddTime(LocalDateTime.now());
        friend.setFriendName(friendName);
        friend.setUserName(username);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url + "/friend", friend, String.class);
        } catch (HttpServerErrorException e) {
            if (e instanceof HttpServerErrorException.InternalServerError) {
                exceptionHandler.internalServerErrorHandler(e);
            }
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                exceptionHandler.notFoundHandler(e, friendName);
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            return 1;
        } else if (response != null && response.getStatusCode() == HttpStatus.ALREADY_REPORTED) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * get a friend's record. If this friend is not a user of the app, then a pop up window will
     * show and remind the user. If user doesn't have any friend, then an empty list will be
     * returned, and the table in the show friends page will be empty too.
     *
     * @return list of Records.
     */
    public List<Records> getFriendRecords() {
        ResponseEntity<List<Records>> response = null;
        try {
            response = restTemplate.exchange(
                url + "/friend/record/" + username,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Records>>() {
                }
            );
        } catch (HttpServerErrorException e) {
            exceptionHandler.internalServerErrorHandler(e);
        } catch (HttpClientErrorException e) {
            exceptionHandler.notFoundHandler(e, username + "'s friends");
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders headers = response.getHeaders();
            friendAmount = headers.getFirst("friendAmount");
            return response.getBody();
        } else {
            return new ArrayList<>();
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFriendAmount() {
        return friendAmount;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
