package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.Achievements;

import net.bytebuddy.utility.RandomString;

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
import org.springframework.test.web.servlet.MvcResult;
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
public class AchievementsTests {

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

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    /**
     * Check if /api/achievements/ and /api/achievement/{username} works. Limited to
     * 1 achievement per user.
     */
    @WithMockUser
    @Test
    public void checkAchievements() throws Exception {

        LOGGER.debug("=== checkAchievements() ===");
        
        Achievements[] dummies = new Achievements[3];
        dummies[0] = manager.persist(createDummyAchievement("Ricardo"));
        dummies[1] = manager.persist(createDummyAchievement("Stan"));
        dummies[2] = manager.persist(createDummyAchievement("Tina"));
        manager.flush();

        RequestBuilder ereq = MockMvcRequestBuilders.get("/api/achievements")
            .accept(MediaType.APPLICATION_JSON);

        MvcResult eres = mockMvc.perform(ereq).andExpect(status().is(200)).andReturn();

        JsonNode list = mapper.readTree(eres.getResponse().getContentAsString());

        LOGGER.debug("Returned Json: " + list);

        int achcount = 0;
        for (JsonNode ach : list) {
            RequestBuilder achreq = MockMvcRequestBuilders
                .get("/api/achievement/" + ach.get("userName").asText())
                .accept(MediaType.APPLICATION_JSON);
            MvcResult ures = mockMvc.perform(achreq).andExpect(status().is(200)).andReturn();

            String content = ures.getResponse().getContentAsString();

            LOGGER.debug("Achievement record " + achcount + ": " + ach);
            LOGGER.debug("Returned content " + achcount + ": " + content);
            LOGGER.debug("Expected record " + achcount + ": "
                + mapper.writeValueAsString(dummies[achcount]));

            assertThat(ures.getResponse().getContentAsString())
                .isEqualTo('[' + mapper.writeValueAsString(dummies[achcount]) + ']');

            achcount++;
        }
        LOGGER.debug("Achievement record amount: " + achcount);
        assertThat(achcount).isEqualTo(3);

        manager.clear();

    }

    /**
     * Checks for the case if there are multiple achievements per user.
     */
    @WithMockUser
    @Test
    public void checkmultiAchievements() throws Exception {

        LOGGER.debug("=== checkmultiAchievements() ===");

        Achievements[] dummies = new Achievements[3];

        for (int i = 0; i < 3; i++) {
            dummies[i] = manager.persist(createDummyAchievement("Zhao"));
        }
        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.get("/api/achievement/Zhao")
            .accept(MediaType.APPLICATION_JSON);
        MvcResult res = mockMvc.perform(req).andExpect(status().is(200)).andReturn();

        JsonNode list = mapper.readTree(res.getResponse().getContentAsString());

        int count = 0;
        for (JsonNode node : list) {
            assertThat(node.get("userName").asText()).isEqualTo("Zhao");
            count++;
        }
        assertThat(count).isEqualTo(3);
    }

    @WithMockUser
    @Test
    public void checkNonexistentAch() throws Exception {

        LOGGER.debug("=== checkNonexistentAch() ===");

        RequestBuilder req = MockMvcRequestBuilders.get("/api/achievement/nobody")
            .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(req).andExpect(status().is(404)).andReturn();
    }

    private Achievements createDummyAchievement(String name) {
        Achievements ach = new Achievements();
        ach.setUserName(name);
        ach.setAchievement(RandomString.make());
        return ach;
    }
}
