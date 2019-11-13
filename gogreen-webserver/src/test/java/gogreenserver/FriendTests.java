package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.Friend;
import gogreenserver.entity.User;
import gogreenserver.services.FriendService;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class FriendTests {

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
    private FriendService friendService;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser("Buddy")
    @Test
    public void addFriend() throws Exception {

        LOGGER.debug("=== addFriend() ===");

        Friend dummy = createDummyFriend("Buddy", "Bruh");
        manager.persistAndFlush(UserTests.createDummyUser(dummy.getFriendName()));

        RequestBuilder req = MockMvcRequestBuilders.post("/api/friend")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(200)).andReturn();

        assertThat(friendService.friendshipExists(dummy.getUserName(), dummy.getFriendName()))
                .isTrue();
    }

    @WithMockUser("Buddy")
    @Test
    public void addNonExistentFriend() throws Exception {

        LOGGER.debug("=== addNonExistentFriend() ===");

        Friend dummy = createDummyFriend("Buddy", "Bruh");
        // don't add User 'Bruh'

        RequestBuilder req = MockMvcRequestBuilders.post("/api/friend")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(404)).andReturn();

        assertThat(friendService.friendshipExists(dummy.getUserName(), dummy.getFriendName()))
                .isFalse();

        manager.clear();
    }

    @WithMockUser("Buddy")
    @Test
    public void addAlreadyExistentFriend() throws Exception {

        LOGGER.debug("=== addAlreadyExistentFriend() ===");

        Friend dummy = createDummyFriend("Alice", "Bob");
        manager.persist(dummy);
        manager.persist(UserTests.createDummyUser(dummy.getFriendName()));
        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.post("/api/friend")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(208)).andReturn();

        assertThat(friendService.friendshipExists(dummy.getUserName(), dummy.getFriendName()))
                .isTrue();
    }

    @WithMockUser("me_irl")
    @Test
    public void listFriends() throws Exception {

        LOGGER.debug("=== listFriends() ===");

        RequestBuilder emptyreq = MockMvcRequestBuilders.get("/api/friend/me_irl");
        mockMvc.perform(emptyreq).andExpect(status().is(404)).andReturn();

        manager.persistAndFlush(createDummyFriend("me_irl", "meirl"));

        RequestBuilder existingreq = MockMvcRequestBuilders.get("/api/friend/me_irl");
        mockMvc.perform(existingreq).andExpect(status().is(200)).andReturn();
    }

    @WithMockUser
    @Test
    public void getFriendRecords() throws Exception {

        LOGGER.debug("=== getFriendRecords() ===");

        User donald = manager.persist(UserTests.createDummyUser("Donald"));

        User huey = manager.persist(UserTests.createDummyUser("Huey"));
        User dewey = manager.persist(UserTests.createDummyUser("Dewey"));
        User louie = manager.persist(UserTests.createDummyUser("Louie"));

        manager.persist(RecordsTests.createDummyRecords(huey.getUsername()));
        manager.persist(RecordsTests.createDummyRecords(dewey.getUsername()));
        manager.persist(RecordsTests.createDummyRecords(louie.getUsername()));

        manager.flush();

        RequestBuilder req404 = MockMvcRequestBuilders
                .get("/api/friend/record/" + donald.getUsername());
        mockMvc.perform(req404).andExpect(status().is(404)).andReturn();

        manager.persist(createDummyFriend(donald.getUsername(), huey.getUsername()));
        manager.persist(createDummyFriend(donald.getUsername(), dewey.getUsername()));
        manager.persist(createDummyFriend(donald.getUsername(), louie.getUsername()));

        manager.flush();

        RequestBuilder req200 = MockMvcRequestBuilders
                .get("/api/friend/record/" + donald.getUsername());
        mockMvc.perform(req200).andExpect(status().is(200)).andReturn();

        manager.clear();

    }

    @WithMockUser
    @Test
    public void getFriends500() throws Exception {

        LOGGER.debug("=== getFriends500() ===");

        LOGGER.debug("=== getFriendRecords() ===");

        User donald = manager.persist(UserTests.createDummyUser("Donald"));

        User huey = manager.persist(UserTests.createDummyUser("Huey"));
        User dewey = manager.persist(UserTests.createDummyUser("Dewey"));
        User louie = manager.persist(UserTests.createDummyUser("Louie"));

        manager.persist(createDummyFriend(donald.getUsername(), huey.getUsername()));
        manager.persist(createDummyFriend(donald.getUsername(), dewey.getUsername()));
        manager.persist(createDummyFriend(donald.getUsername(), louie.getUsername()));

        manager.flush();

        RequestBuilder req500 = MockMvcRequestBuilders
                .get("/api/friend/record/" + donald.getUsername());
        mockMvc.perform(req500).andExpect(status().is(500)).andReturn();
    }

    private Friend createDummyFriend(String name, String friendname) {
        Friend friend = new Friend();
        friend.setUserName(name);
        friend.setFriendName(friendname);
        return friend;
    }

}
