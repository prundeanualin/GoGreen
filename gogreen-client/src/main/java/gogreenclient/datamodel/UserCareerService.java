package gogreenclient.datamodel;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * A service class which will provide the service of retrieving user career from
 * database, updating user career in the database.
 */

public class UserCareerService {

    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private String username;

    /**
     * Get userCareer from database. If there is no usrCareer existing in the
     * database, then a new userCareer tuple will be created with the username
     * logged in.
     *
     * @return userCareer of the current user who has logged in.
     * @throws Exception threw by restTemplate.
     */
    public Records getCareer() {
        Records records = restTemplate.getForObject(url + "/record/" + username, Records.class);
        if (records == null) {
            throw new RuntimeException("User career doesn't exist.");
        } else {
            return records;
        }
    }

    /**
     * Get achievements of this user.
     *
     * @return list of achievements.
     */
    public List<Achievements> getAchievements() {
        ResponseEntity<List<Achievements>> response = null;
        List<Achievements> achievements = null;
        try {
            response = restTemplate.exchange(url + "/achievement/" + username, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Achievements>>() {
                    });
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                achievements = new ArrayList<>();
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            achievements = response.getBody();
            achievements.sort(Comparator.comparing(Achievements::getAchieveData));
        }
        return achievements;
    }

    /**
     * Get the most recent two insert history of this user.
     *
     * @return list of insert history.
     */
    public List<InsertHistoryCo2> getRecentTwoInsertHistory() {
        ResponseEntity<List<InsertHistoryCo2>> response = null;
        List<InsertHistoryCo2> insertHistories = null;
        try {
            response = restTemplate.exchange(url + "/insertHistory/" + username, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<InsertHistoryCo2>>() {
                    });
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                insertHistories = new ArrayList<>();
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            insertHistories = response.getBody();
        }
        return insertHistories;
    }

    /**
     * Get the amount of this user's activities.
     *
     * @return the string representation of the number of activities.
     */
    public String getActivityAmount() {
        ResponseEntity<String> response = null;
        String amount = null;
        try {
            response = restTemplate.getForEntity(url + "/insertHistory/amount/" + username,
                    String.class);
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                amount = "0";
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            amount = response.getBody();
        }
        return amount;
    }

    /**
     * Get the active days of this user.
     *
     * @return the string representation of the number of days.
     */
    public String getActiveDays() {
        ResponseEntity<String> response = null;
        String days = null;
        try {
            response = restTemplate.getForEntity(url + "/insertHistory/days/" + username,
                    String.class);
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                days = "0";
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            days = response.getBody();
        }
        return days;
    }

    /**
     * . Method to send the request to server in order to get user profile pic
     * 
     * @return the profile pic as byte array
     * @throws IOException in case it fails
     */
    public Image showPhoto() {

        ResponseEntity<String> remoteUrl = restTemplate
                .getForEntity(url + "/user/photourl/" + username, String.class);

        try {
            return SwingFXUtils.toFXImage(ImageIO.read(new URL(remoteUrl.getBody())), null);
        } catch (IOException | NullPointerException e) {
            ResponseEntity<File> response = restTemplate
                    .getForEntity(url + "/user/photo/" + username, File.class);
            BufferedImage imgBuffer;
            try {
                if (response.getStatusCode() == HttpStatus.OK) {
                    imgBuffer = ImageIO.read(response.getBody());
                } else {
                    File file = new ClassPathResource("static/green-hibiscus-md.png").getFile();
                    imgBuffer = ImageIO.read(file);
                }
                return SwingFXUtils.toFXImage(imgBuffer, null);
            } catch (IOException fatal) {
                fatal.printStackTrace();
                return null;
            }
        }

    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
