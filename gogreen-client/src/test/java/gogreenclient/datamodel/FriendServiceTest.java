package gogreenclient.datamodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import gogreenclient.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringRunner.class)
public class FriendServiceTest {

    @Autowired
    private ObjectMapper mapper;

    ExceptionHandler handler = new ExceptionHandler();
    private MockRestServiceServer server;
    private FriendService friendService = new FriendService();
    private RestTemplate template = new RestTemplate();
    private String url = "https://localhost:8443/api/";
    private Messenger msg;

    @Before
    public void setUp() {
        msg = spy(Messenger.class);
        MockitoAnnotations.initMocks(this);
        server = MockRestServiceServer.createServer(template);
        friendService.setRestTemplate(template);
        friendService.setUrl("https://localhost:8443/api/");
        handler.setMessenger(msg);
        friendService.setExceptionHandler(handler);
        friendService.setUsername("gru");
    }

    @Test
    public void addFriendBadRequestTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.CONTINUE);
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendNullTest() throws Exception {
        setUpAddFriend(null, HttpStatus.CONTINUE);
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendInternalServerErrorTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.INTERNAL_SERVER_ERROR);
        Mockito.doNothing().when(msg).showMessage("Server error, please try again");
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendOtherServerErrorTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.BAD_GATEWAY);
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendNotFoundTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.NOT_FOUND);
        Mockito.doNothing().when(msg).showMessage("mike not found. Please try again.");
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendOtherClientErrorTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.FORBIDDEN);
        assertEquals(-1, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendAlreadyReportedTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.ALREADY_REPORTED);
        assertEquals(0, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendAlreadyReportedNullTest() throws Exception {
        setUpAddFriend(null, HttpStatus.ALREADY_REPORTED);
        assertEquals(0, friendService.addFriend("mike"));
    }

    @Test
    public void addFriendOkTest() throws Exception {
        setUpAddFriend(new Friend(), HttpStatus.OK);
        assertEquals(1, friendService.addFriend("mike"));
    }

    @Test
    public void getFriendRecordsOk() throws Exception {
        setUpGetRecordsOk(HttpStatus.OK, 1);
        assertEquals(null, friendService.getFriendRecords());
    }

    @Test
    public void getFriendRecordsBadRequest() throws Exception {
        setUpGetRecordsOk(HttpStatus.BAD_REQUEST, 1);
        assertEquals(new ArrayList<>(), friendService.getFriendRecords());
    }

    @Test
    public void getFriendRecordsNull() throws Exception {
        setUpGetRecordsOk(HttpStatus.OK, 0);
        assertEquals(new ArrayList<>(), friendService.getFriendRecords());
    }

    @Test
    public void getFriendRecordsInternalServerError() {
        server.reset();
        server.expect(requestTo(url + "friend/record/gru"))
                .andExpect(method(HttpMethod.GET))
                .andRespond((response) -> {throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);});
        Mockito.doNothing().when(msg).showMessage("Server error, please try again");
        assertEquals(new ArrayList<>(), friendService.getFriendRecords());
    }

    @Test
    public void getFriendRecordsNotFound() throws Exception {
        server.reset();
        server.expect(requestTo(url + "friend/record/gru"))
                .andExpect(method(HttpMethod.GET))
                .andRespond((response) -> {throw new HttpClientErrorException(HttpStatus.NOT_FOUND);});
        Mockito.doNothing().when(msg).showMessage("gru's friends" + " not found. Please try again.");;
        assertEquals(new ArrayList<>(), friendService.getFriendRecords());
    }

    public void setUpAddFriend(Friend friend, HttpStatus status) throws Exception {
        server.reset();
        String resp = mapper.writeValueAsString(friend);
        server.expect(requestTo(url + "friend"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(status).body(resp));
    }

    public void setUpGetRecordsOk(HttpStatus status, int i) throws Exception {
        List<Records> body= new ArrayList<>();
        Records r = new Records();
        r.setSavedCo2Total((float) 4);
        body.add(r);
        RestTemplate mockrest = mock(RestTemplate.class);
        friendService.setRestTemplate(mockrest);
        ResponseEntity<List<Records>> response;
        if (i == 1) {
            response = new ResponseEntity<List<Records>>(status);
        }
        else {
            response = null;
        }
        when(mockrest.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(new ParameterizedTypeReference<List<Records>>() {})))
                .thenReturn(response);
//        server.reset();
//        server.expect(requestTo(url + "friend/record/gru"))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(status).body(mapper.writeValueAsString(new ParameterizedTypeReference<List<Records>>() {
//                })));
    }
}