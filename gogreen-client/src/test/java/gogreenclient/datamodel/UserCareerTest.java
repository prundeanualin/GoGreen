package gogreenclient.datamodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import gogreenclient.config.AppConfig;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserCareerTest {

    @Autowired
    private ObjectMapper objectMapper;
    private RestTemplate template = new RestTemplate();
    private UserCareerService service = new UserCareerService();
    private MockRestServiceServer server;
    private String uri = "https://localhost:8443/api/";


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        server = MockRestServiceServer.createServer(template);
        service.setRestTemplate(template);
        service.setUrl(uri);
    }

    @Test
    public void getCareer_UserExists() throws Exception {
        Records records = new Records();
        records.setUserName("zhao");
        service.setUsername("zhao");
        String userCareer_json = objectMapper.writeValueAsString(records);
        this.setUp_Get(server, "record/" + "zhao", userCareer_json);
        assertEquals(records.getUserName(), service.getCareer().getUserName());
    }


    @Test()
    public void getCareerBadRequest() throws Exception {
        String none = objectMapper.writeValueAsString(null);
        this.setUp_Get(server, "record/" + "zhao", none);
        service.setUsername("zhao");
        String message ="";
        try {
            service.getCareer();
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        assertEquals("User career doesn't exist.", message);
    }

    @Test
    public void getAchievementsNotFound() throws Exception {
        notFound("achievement");
        assertEquals(new ArrayList<>(), service.getAchievements());
    }

    @Test
    public void getAchievementsBadRequest() throws Exception {
        badRequest("achievement", null, HttpStatus.BAD_REQUEST);
        assertEquals(null, service.getAchievements());
    }

    @Test
    public void getAchievementsNull() throws Exception {
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        when(template.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<Achievements>>() {})))
                .thenReturn(null);
        assertEquals(null, service.getAchievements());
    }

    @Test
    public void getAchievementsNotOk() throws Exception {
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        when(template.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<Achievements>>() {})))
                .thenReturn(new ResponseEntity<List<Achievements>>(HttpStatus.ACCEPTED));
        assertEquals(null, service.getAchievements());
    }

    @Test
    public void getAchievementsGoodRequest() throws Exception {
        Achievements ach1 = new Achievements();
        ach1.setUserName("sparrow");
        ach1.setAchieveData(LocalDateTime.of(1, 1, 1, 1, 1, 1, 1));
        Achievements ach2 = new Achievements();
        ach2.setUserName("jack");
        ach2.setAchieveData(LocalDateTime.of(100, 1, 1, 1, 1, 1, 1));
        List<Achievements> list = new ArrayList<>();
        list.add(ach1);
        list.add(ach2);
        String resp = objectMapper.writeValueAsString(list);
        service.setUsername("johnny");
        server.reset();
        setUp_Get(server, "achievement/johnny", resp);
        assertEquals("jack", service.getAchievements().get(1).getUserName());
    }

    @Test
    public void getRecentTwoInsertHistoryNotFound () throws Exception {
        notFound("insertHistory");
        assertEquals(new ArrayList<>(), service.getRecentTwoInsertHistory());
    }

    @Test
    public void getRecentTwoInsertHistoryBadRequest() throws Exception{
        List<InsertHistoryCo2> list = new ArrayList<>();
        list.add(new InsertHistoryCo2());
        badRequest("insertHistory", list, HttpStatus.BAD_REQUEST);
        assertEquals(null, service.getRecentTwoInsertHistory());
    }

    @Test
    public void getRecentTwoInsertHistoryNotOk() throws Exception{
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        when(template.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<InsertHistoryCo2>>() {})))
                .thenReturn(new ResponseEntity<List<InsertHistoryCo2>>(HttpStatus.ACCEPTED));
        assertEquals(null, service.getRecentTwoInsertHistory());
    }

    @Test
    public void getRecentTwoInsertHistoryNull() throws Exception{
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        when(template.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<InsertHistoryCo2>>() {})))
                .thenReturn(null);
        assertEquals(null, service.getRecentTwoInsertHistory());
    }

    @Test
    public void getRecentTwoInsertHistoryGoodRequest() throws Exception {
        InsertHistoryCo2 hist1 = new InsertHistoryCo2();
        hist1.setActivityName("chilling");
        List<InsertHistoryCo2> list = new ArrayList<>();
        list.add(hist1);
        String resp = objectMapper.writeValueAsString(list);
        service.setUsername("jackie");
        setUp_Get(server, "insertHistory/jackie", resp);
        assertEquals("chilling", service.getRecentTwoInsertHistory().get(0).getActivityName());
    }

    @Test
    public void getActivityAmountNotFound() throws Exception {
        notFound("insertHistory/amount");
        assertEquals("0", service.getActivityAmount());
    }

    @Test
    public void getActivityAmountBadRequest() throws Exception {
        badRequest("insertHistory/amount", "69", HttpStatus.BAD_REQUEST);
        assertEquals(null, service.getActivityAmount());
    }

    @Test
    public void getActivityAmountNull() throws Exception {
        badRequest("insertHistory/amount", null, HttpStatus.ACCEPTED);
        assertEquals(null, service.getActivityAmount());
    }

    @Test
    public void getActivityAmountNotOk() throws Exception {
        badRequest("insertHistory/amount", "69", HttpStatus.ACCEPTED);
        assertEquals(null, service.getActivityAmount());
    }

    @Test
    public void getActivityAmountGoodRequest() throws Exception {
        service.setUsername("pablo");
        setUp_Get(server, "insertHistory/amount/pablo", "24");
        assertTrue(service.getActivityAmount().equals("24"));
    }

    @Test
    public void getActiveDaysNotFound() throws Exception {
        notFound("insertHistory/days");
        assertEquals("0", service.getActiveDays());
    }

    @Test
    public void getActiveDaysBadRequest() throws Exception {
        badRequest("insertHistory/days", "4", HttpStatus.BAD_REQUEST);
        assertEquals(null, service.getActiveDays());
    }

    @Test
    public void getActiveDaysNotOkRequest() throws Exception {
        badRequest("insertHistory/days", "4", HttpStatus.MULTI_STATUS);
        assertEquals(null, service.getActiveDays());
    }

    @Test
    public void getActiveDaysNull() throws Exception {
        badRequest("insertHistory/days", null, HttpStatus.MULTI_STATUS);
        assertEquals(null, service.getActiveDays());
    }

    @Test
    public void getActiveDaysGoodRequest() throws Exception {
        service.setUsername("pablo");
        setUp_Get(server, "insertHistory/days/pablo", "27");
        assertTrue(service.getActiveDays().equals("27"));
    }


    @Test
    public void showPhotoTestDefault() throws IOException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        service.setRestTemplate(restTemplate);
        when(restTemplate.getForEntity(
                anyString(),
                eq(File.class)
        )).thenReturn(new ResponseEntity<File>(HttpStatus.NO_CONTENT));
        File file = new ClassPathResource("static/green-hibiscus-md.png").getFile();
        BufferedImage imgBuffer = ImageIO.read(file);
        BufferedImage img2 = imgBuffer;
        SwingFXUtils.fromFXImage(service.showPhoto(), img2);
        assertEquals(imgBuffer.getData().getDataBuffer().getSize(), img2.getData().getDataBuffer().getSize());
    }

    @Test
    public void showPhotoTestNewPhoto() throws IOException {
        RestTemplate restTemplate = mock(RestTemplate.class);
        service.setRestTemplate(restTemplate);
        File file = new ClassPathResource("static/fish.jpg").getFile();
        BufferedImage imgBuffer = ImageIO.read(file);
        when(restTemplate.getForEntity(
                anyString(),
                eq(File.class)
        )).thenReturn(new ResponseEntity<File>(file, HttpStatus.OK));
        BufferedImage img2 = imgBuffer;
        SwingFXUtils.fromFXImage(service.showPhoto(), img2);
        assertEquals(imgBuffer.getData().getDataBuffer().getSize(), img2.getData().getDataBuffer().getSize());
    }

    public void badRequest(String urii, Object obj, HttpStatus status) throws Exception {
        String resp = objectMapper.writeValueAsString(obj);
        service.setUsername("johnny");
        server.reset();
        server.expect(requestTo(uri + urii + "/johnny"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(status).body(resp));
    }

    public void notFound(String urii) throws Exception {
        String resp = objectMapper.writeValueAsString(null);
        service.setUsername("johnny");
        server.reset();
        server.expect(requestTo(uri + urii + "/johnny"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
    }

    public void setUp_Post(MockRestServiceServer server, String uri, String content, String json) throws Exception {
        server.reset();
        server.expect(requestTo(uri))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().string(content))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
    }

    public void setUp_Get(MockRestServiceServer server, String urii, String json) throws Exception {
        server.reset();
        server.expect(requestTo(uri + urii))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
    }

}