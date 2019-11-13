package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.InsertHistory;
import gogreenserver.entity.InsertHistoryCo2;
import gogreenserver.repositories.InsertHistoryRepository;

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

import java.time.LocalDateTime;
import java.util.List;
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
public class InsertHistoryTests {

    // for debugging purposes
    private static final Logger LOGGER = LogManager.getLogger("Tests");

    // the ObjectMapper that Spring uses for its object->json conversions
    @Autowired
    private ObjectMapper mapper;

    // because testEntityManager cannot find by non-primary key or all.
    @Autowired
    private InsertHistoryRepository historyRepo;

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

    @WithMockUser
    @Test
    public void checkNonexistentHistories() throws Exception {

        LOGGER.debug("=== checkNonexistentHistories() ===");

        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/nobody")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(req).andExpect(status().is(404)).andReturn();
    }

    @WithMockUser("Attila")
    @Test
    public void addHistory() throws Exception {

        LOGGER.debug("=== addHistory() ===");

        InsertHistory dummy = createDummyInsertHistory("Attila");
        RequestBuilder req = MockMvcRequestBuilders.post("/api/insertHistory")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy))
                .header("userName", dummy.getUserName());
        mockMvc.perform(req).andExpect(status().is(200));

        // This is not how the real db works, but we don't have the real db, so...
        List<InsertHistory> found = historyRepo.findAll();
        assertThat(found).isNotNull();
        assertThat(found.size()).isEqualTo(1);

        manager.clear();
    }

    @WithMockUser("Hackerman")
    @Test
    public void addHistoryWithoutPermission() throws Exception {
        InsertHistory dummy = createDummyInsertHistory("Attila");
        RequestBuilder req = MockMvcRequestBuilders.post("/api/insertHistory")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy))
                .header("userName", dummy.getUserName());
        mockMvc.perform(req).andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void retrieveRecentHistory() throws Exception {

        LOGGER.debug("=== retrieveRecentHistory() ===");

        InsertHistoryCo2 first = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 second = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 third = createDummyInsertHistoryCo2("Theta");

        first.setInsertDate(LocalDateTime.of(2019, 1, 1, 1, 1));
        second.setInsertDate(LocalDateTime.of(2019, 1, 1, 2, 1));
        third.setInsertDate(LocalDateTime.of(2019, 1, 1, 3, 1));

        manager.persist(second);
        manager.persist(third);
        manager.persist(first);

        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/Theta")
                .accept(MediaType.APPLICATION_JSON);
        JsonNode list = mapper.readTree(mockMvc.perform(req).andExpect(status().is(200)).andReturn()
                .getResponse().getContentAsString());

        JsonNode alpha = list.get(0);
        JsonNode beta = list.get(1);
        assertThat(list.get(2)).isNull();
        LOGGER.debug("First response: " + alpha.get("insertDate").asText());
        LOGGER.debug("Second response: " + beta.get("insertDate").asText());

        assertThat(alpha.get("insertDate").asText()).isEqualTo("2019-01-01T03:01:00");
        assertThat(beta.get("insertDate").asText()).isEqualTo("2019-01-01T02:01:00");

        manager.clear();
    }

    @WithMockUser
    @Test
    public void checkNoLimitHeader() throws Exception {
        int historyAmount = new Random().nextInt(45) + 20;
        InsertHistoryCo2[] histories = new InsertHistoryCo2[historyAmount];
        for (int i = 0; i < historyAmount; i++) {
            histories[i] = manager.persist(createDummyInsertHistoryCo2("Dio"));
        }
        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/Dio")
                .accept(MediaType.APPLICATION_JSON).header("limit", -1);
        JsonNode list = mapper.readTree(mockMvc.perform(req).andExpect(status().is(200)).andReturn()
                .getResponse().getContentAsString());

        assertThat(list.size()).isEqualTo(historyAmount);

        RequestBuilder twentyreq = MockMvcRequestBuilders.get("/api/insertHistory/Dio")
                .accept(MediaType.APPLICATION_JSON).header("limit", 20);
        JsonNode twentylist = mapper.readTree(mockMvc.perform(twentyreq).andExpect(status().is(200))
                .andReturn().getResponse().getContentAsString());

        assertThat(twentylist.size()).isEqualTo(20);
    }

    @WithMockUser
    @Test
    public void checkHistoryRange() throws Exception {

        LOGGER.debug("=== checkHistoryRange() ===");

        InsertHistoryCo2 first = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 second = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 third = createDummyInsertHistoryCo2("Theta");

        first.setInsertDate(LocalDateTime.of(2019, 1, 1, 0, 0));
        second.setInsertDate(LocalDateTime.of(2019, 1, 2, 0, 0));
        third.setInsertDate(LocalDateTime.of(2019, 1, 4, 0, 0));

        manager.persist(second);
        manager.persist(third);
        manager.persist(first);

        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/days/Theta")
                .accept(MediaType.APPLICATION_JSON);

        String res = mockMvc.perform(req).andExpect(status().is(200)).andReturn().getResponse()
                .getContentAsString();
        LOGGER.debug(res);

        assertThat(res).isEqualTo("3");

        manager.clear();
    }

    @WithMockUser
    @Test
    public void checkHistoryAmount() throws Exception {

        LOGGER.debug("=== checkHistoryAmount() ===");

        InsertHistoryCo2 first = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 second = createDummyInsertHistoryCo2("Theta");
        InsertHistoryCo2 third = createDummyInsertHistoryCo2("Theta");

        first.setInsertDate(LocalDateTime.of(2019, 1, 1, 0, 0));
        second.setInsertDate(LocalDateTime.of(2019, 1, 2, 0, 0));
        third.setInsertDate(LocalDateTime.of(2019, 1, 4, 0, 0));

        manager.persist(second);
        manager.persist(third);
        manager.persist(first);

        manager.flush();

        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/amount/Theta")
                .accept(MediaType.APPLICATION_JSON);

        String res = mockMvc.perform(req).andExpect(status().is(200)).andReturn().getResponse()
                .getContentAsString();
        LOGGER.debug(res);

        assertThat(res).isEqualTo("3");

        manager.clear();
    }

    @WithMockUser
    @Test
    public void testEmptyHistory() throws Exception {
        RequestBuilder req = MockMvcRequestBuilders.get("/api/insertHistory/days/me")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(req).andExpect(status().is(404)).andReturn();
    }

    private InsertHistory createDummyInsertHistory(String name) {
        InsertHistory history = new InsertHistory();
        history.setUserName(name);
        return history;
    }

    private InsertHistoryCo2 createDummyInsertHistoryCo2(String name) {
        InsertHistoryCo2 history = new InsertHistoryCo2();
        history.setUserName(name);
        return history;
    }
}
