package gogreenclient.datamodel;

import gogreenclient.config.AppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class UserService {

    @Autowired
    private RestTemplate loginRestTemplate;

    @Autowired
    private AppConfig config;

    private String url;

    /**
     * Method that deals with the communication between server and client.
     *
     * @param username    the username retrived from the GUI, should be a string
     * @param password    the password retrived from the GUI, should be a string
     * @param bdate       the birthday of the user, should be of type localdate
     * @param nationality the nationality of the user, retrived from the GUI, should
     *                    be a string
     * @return this method will return a responseEntity
     * @throws Exception throw IOException
     */
    @SuppressWarnings("unchecked")
    public ResponseEntity<User> addUser(String username, String password, LocalDate bdate,
            String nationality) throws URISyntaxException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBdate(bdate);
        user.setEmail(nationality);
        return loginRestTemplate.postForEntity(url + "/createUser", user, User.class);
    }

    /**
     * Find if a user exists in the DB. None of user's detail will be received, so
     * this method is save to use
     *
     * @param username username entered by user while creating account.
     * @return true - the username is already used, false if it is valid.
     */
    public boolean findUser(String username) {
        try {
            ResponseEntity<String> response = loginRestTemplate
                    .getForEntity(url + "/user/findUser/" + username, String.class);
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                return false;
            }
        }
        return true;
    }

    /**
     * . * Method for uploading the photo from the user system and send it to server
     * on /createUser/upload endpoint
     * 
     * @param file     the image to save
     * @param userName the name entered by the user inside the textfield
     * @return the response entity of the connection which transfers the
     *
     */
    public ResponseEntity<String> uploadPhoto(File file, String userName) {
        loginRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        LinkedMultiValueMap body = new LinkedMultiValueMap();
        body.add("profile_pic", new FileSystemResource(file));
        body.add("username", userName);
        HttpEntity request = new HttpEntity<>(body, headers);
        return loginRestTemplate.postForEntity("https://localhost:8443/api/createUser/upload",
                request, String.class);
    }

    public void setRestTemplate(RestTemplate loginRestTemplate) {
        this.loginRestTemplate = loginRestTemplate;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     * Change a single user value.
     * @param attr A {@linkplain UserAttribute} to select what to change.
     * @param value The value to set it to.
     */
    public void changeDetail(UserAttribute attr, String value) {
        loginRestTemplate.postForEntity(
                url + "/updateUser/" + config.getUsername() + "/" + attr.value, value,
                String.class);
    }

    public enum UserAttribute {
        EMAIL("email"), PASS("password"), PURL("photo"), BDAY("birthday");

        private String value;

        private UserAttribute(String value) {
            this.value = value;
        }
    }
}
