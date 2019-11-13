package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Random;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)

//set up test mvc
@AutoConfigureWebMvc
@AutoConfigureMockMvc

//set up test db
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class UserTests {

    // for debugging purposes
    private static final Logger LOGGER = LogManager.getLogger("Tests");

    // the ObjectMapper that Spring uses for its object->json conversions
    @Autowired
    private ObjectMapper mapper;

    // DON'T AUTOWIRE, IT IS SET UP MANUALLY
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserDetailsService userfinder;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    /**
     * Check if /api/user/{username} works.
     */
    @WithMockUser
    @Test
    public void checkUsers() throws Exception {

        LOGGER.debug("=== checkUsers() ===");

        User dummy = manager.persistAndFlush(createDummyUser("Alice"));
        RequestBuilder ereq = MockMvcRequestBuilders
                .get("/api/user/findUser/" + dummy.getUsername())
                .accept(MediaType.APPLICATION_JSON);

        MvcResult eres = mockMvc.perform(ereq).andExpect(status().is(200)).andReturn();

        // if user exist.
        assertThat(eres.getResponse().getContentAsString()).isEqualTo("success");

        RequestBuilder nreq = MockMvcRequestBuilders.get("/api/user/findUser/Bob")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult nres = mockMvc.perform(nreq).andExpect(status().is(404)).andReturn();

        // if user does not exist.
        assertThat(nres.getResponse().getContentAsString()).isEqualTo("fail");

        manager.clear();
    }

    @Test
    public void addUser() throws Exception {

        LOGGER.debug("=== addUser() ===");

        User dummy = createDummyUser("Danny");
        RequestBuilder req = MockMvcRequestBuilders.post("/api/createUser")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(200));

        User found = manager.find(User.class, dummy.getUsername());

        // /api/createUser properly salts and hashes the user password, and therefore it
        // is (or should be) impossible to recreate it manually.
        dummy.setPassword(found.getPassword());

        assertThat(found).isEqualToComparingFieldByField(dummy);

        manager.clear();
    }

    @Test
    public void addUsernameTaken() throws Exception {

        LOGGER.debug("=== addUsernameTaken() ===");

        User dummy = manager.persistAndFlush(createDummyUser("BoatyMcBoatFace"));

        RequestBuilder req = MockMvcRequestBuilders.post("/api/createUser")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));

        mockMvc.perform(req).andExpect(status().is(400));

        manager.clear();
    }

    @WithMockUser("Hackerman")
    @Test
    public void updateWitoutPermission() throws Exception {

        LOGGER.debug("=== updateWitoutPermission() ===");

        User dummy = manager.persistAndFlush(createDummyUser("TrainMcTrainface"));

        RequestBuilder req = MockMvcRequestBuilders
                .post("/api/updateUser/" + dummy.getUsername() + "/email")
                .contentType(MediaType.TEXT_PLAIN).content("hackerman@example.org");

        mockMvc.perform(req).andExpect(status().is(401));

        manager.clear();
    }

    @WithMockUser("hunter")
    @Test
    public void updateInvalidProp() throws Exception {

        LOGGER.debug("=== updateInvalidProp() ===");

        User dummy = manager.persistAndFlush(createDummyUser("hunter"));

        RequestBuilder req = MockMvcRequestBuilders
                .post("/api/updateUser/" + dummy.getUsername() + "/username")
                .contentType(MediaType.TEXT_PLAIN).content("hunter2");

        mockMvc.perform(req).andExpect(status().is(404));

        manager.clear();
    }

    @WithMockUser("Merica")
    @Test
    public void updateInvalidDate() throws Exception {

        LOGGER.debug("=== updateInvalidDate() ===");

        User dummy = manager.persistAndFlush(createDummyUser("Merica"));

        RequestBuilder req = MockMvcRequestBuilders
                .post("/api/updateUser/" + dummy.getUsername() + "/birthday")
                .contentType(MediaType.TEXT_PLAIN).content("July 6th, 1776");

        mockMvc.perform(req).andExpect(status().is(400));

        manager.clear();
    }

    @WithMockUser("Emma")
    @Test
    public void deleteUser() throws Exception {

        LOGGER.debug("=== deleteUser() ===");

        User dummy = manager.persistAndFlush(createDummyUser("Emma"));
        RequestBuilder req = MockMvcRequestBuilders.delete("/api/deleteUser/" + dummy.getUsername())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(200));

        assertThat(manager.find(User.class, dummy.getUsername())).isNull();

        manager.clear();
    }

    @WithMockUser("Hackerman")
    @Test
    public void deleteWithoutPermission() throws Exception {

        LOGGER.debug("=== deleteWithoutPermission() ===");

        User dummy = manager.persistAndFlush(createDummyUser("Emma"));
        RequestBuilder req = MockMvcRequestBuilders.delete("/api/deleteUser/" + dummy.getUsername())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(401));

        assertThat(manager.find(User.class, dummy.getUsername())).isNotNull();

        manager.clear();
    }

    @WithMockUser
    @Test
    public void login() throws Exception {
        RequestBuilder req = MockMvcRequestBuilders.get("/api/login/");
        mockMvc.perform(req).andExpect(status().is(200));
    }

    @WithMockUser("V")
    @Test
    public void checkChanges() throws Exception {

        LOGGER.debug("=== checkChanges() ===");

        User dummy = manager.persistAndFlush(createDummyUser("V"));
        // because somehow (I am not sure), the manager auto-syncs dummy to all changes.
        String password = dummy.getPassword();

        LocalDate newtime = LocalDate.now().plusDays(1);
        String[] propertynames = { "email", "password", "birthday", "photo" };
        String[] propertyvalues = { "yeet", "wololo", mapper.writeValueAsString(newtime),
            "http://example.org" };

        for (int i = 0; i < propertynames.length; i++) {
            RequestBuilder req = MockMvcRequestBuilders
                    .post("/api/updateUser/" + dummy.getUsername() + "/" + propertynames[i])
                    .contentType(MediaType.APPLICATION_JSON).content(propertyvalues[i]);

            LOGGER.debug("Sent " + propertynames[i]);

            mockMvc.perform(req).andExpect(status().is(200));
        }
        manager.flush();
        User result = manager.find(User.class, dummy.getUsername());

        // because we cannot recreate the salt.
        assertThat(result.getPassword()).isNotEqualTo(password);
        assertThat(result.getEmail()).isEqualTo(propertyvalues[0]);
        assertThat(result.getBdate()).isEqualTo(newtime);
        assertThat(result.getPfpUrl()).isEqualTo(propertyvalues[3]);

    }

    @WithMockUser
    @Test
    public void getPic() throws Exception {

        LOGGER.debug("=== getPic() ===");

        User dummy = createDummyUser("Fab");

        RequestBuilder req = MockMvcRequestBuilders.get("/api/user/photourl/" + dummy.getUsername())
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(req).andExpect(status().is(404));

        manager.persistAndFlush(dummy);

        RequestBuilder req2 = MockMvcRequestBuilders
                .get("/api/user/photourl/" + dummy.getUsername())
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        MvcResult res = mockMvc.perform(req2).andExpect(status().is(200)).andReturn();
        assertThat(res.getResponse().getContentAsString()).isEqualTo(dummy.getPfpUrl());

        manager.clear();
    }
       
    @Test
    public void checkAuthChecker() {

        LOGGER.debug("=== checkAuthChecker() ===");

        User dummy = createDummyUser("Yeet");

        assertThatThrownBy(() -> {
            userfinder.loadUserByUsername(dummy.getUsername());
        }).isInstanceOf(UsernameNotFoundException.class);

        manager.persistAndFlush(dummy);

        assertThat(userfinder.loadUserByUsername(dummy.getUsername())).isNotNull();
    }

    /**
     * Creates a mock User instance.
     * 
     * @param name The username.
     */
    public static User createDummyUser(String name) {
        Random rgn = new Random(name.hashCode());
        return new User(name, "pass" + name, name + "@example.com",
                LocalDate.of(1950 + rgn.nextInt(60), rgn.nextInt(12) + 1, rgn.nextInt(29)),
                LocalDate.now(), "http://example.com/" + name + ".png");
    }
}
