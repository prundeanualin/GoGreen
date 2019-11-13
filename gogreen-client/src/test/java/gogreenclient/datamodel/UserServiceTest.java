package gogreenclient.datamodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import gogreenclient.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest {

    private RestTemplate template = new RestTemplate();
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private MockRestServiceServer server;
    private User userz;
    private String user;

    @Before
    public void setUp() throws Exception {
        userService.setRestTemplate(template);
        userz = new User();
        userz.setUsername("Alin");
        userz.setPassword("alin");
        userz.setDateCreated(LocalDate.now());
        user = objectMapper.writeValueAsString(userz);
        server = MockRestServiceServer.createServer(template);
        server.expect(requestTo(new URI("https://localhost:8443/api/createUser")))
            .andExpect(method(HttpMethod.POST))
//            .andExpect(content().string(user))
            .andRespond(withSuccess(user, MediaType.APPLICATION_JSON));
    }

    @Test
    public void addUser() throws Exception {
        assertEquals(userz.getUsername(), userService
            .addUser(userz.getUsername(), userz.getPassword(), null, null).getBody().getUsername());
    }

    @Test
    public void findUser() throws Exception {
        server.reset();
        server.expect(requestTo(new URI("https://localhost:8443/api/user/findUser/Alin")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess("success", MediaType.APPLICATION_JSON));
        assertTrue(userService.findUser("Alin"));
    }

    @Test
    public void findUserFail() throws Exception {
        server.reset();
        server.expect(requestTo(new URI("https://localhost:8443/api/user/findUser/Alin")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertFalse(userService.findUser("Alin"));
    }

    @Test
    public void findUserError() throws Exception {
        server.reset();
        server.expect(requestTo(new URI("https://localhost:8443/api/user/findUser/Alin")))
                .andExpect(method(HttpMethod.GET))
                .andRespond((response) -> {throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
                });
        assertTrue(userService.findUser("Alin"));
    }
}
