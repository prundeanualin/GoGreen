package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.AddSolarpanels;
import gogreenserver.repositories.AddSolarPanelsRepository;
import gogreenserver.services.AddSolarpanelsService;

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
public class AddSolarpanelsTests {

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
    private AddSolarpanelsService service;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    /**
     * Check if /api/addSolarpanels/ and /api/addSolarpanel/{username} works.
     */
    @WithMockUser
    @Test
    public void checkAddSolarpanels() throws Exception {

        LOGGER.debug("=== checkAddSolarpanels() ===");

        AddSolarpanels[] dummies = new AddSolarpanels[3];
        dummies[0] = manager.persist(createDummyAddSolarpanels("Silica"));
        dummies[1] = manager.persist(createDummyAddSolarpanels("Boron"));
        dummies[2] = manager.persist(createDummyAddSolarpanels("Electon"));
        manager.flush();

        RequestBuilder ereq = MockMvcRequestBuilders.get("/api/addSolarpanels")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult eres = mockMvc.perform(ereq).andExpect(status().is(200)).andReturn();

        JsonNode list = mapper.readTree(eres.getResponse().getContentAsString());

        LOGGER.debug("Returned Json: " + list);

        int solarcount = 0;
        for (JsonNode solar : list) {
            LOGGER.debug("Solar panel " + solarcount + ": " + solar);
            RequestBuilder achreq = MockMvcRequestBuilders
                    .get("/api/addSolarpanel/" + solar.get("userName").asText())
                    .accept(MediaType.APPLICATION_JSON);
            MvcResult ures = mockMvc.perform(achreq).andExpect(status().is(200)).andReturn();

            assertThat(ures.getResponse().getContentAsString())
                    .isEqualTo("[" + mapper.writeValueAsString(dummies[solarcount]) + "]");

            solarcount++;
        }
        LOGGER.debug("Solar panel installations amount: " + solarcount);
        assertThat(solarcount).isEqualTo(3);

        manager.clear();

    }

    @WithMockUser("Sunny")
    @Test
    public void addSolar() throws Exception {

        LOGGER.debug("=== addSolar() ===");

        AddSolarpanels dummy = createDummyAddSolarpanels("Sunny");

        RequestBuilder req = MockMvcRequestBuilders.post("/api/addSolarpanel")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(200)).andReturn();

        assertThat(service.findAllByUserName(dummy.getUserName())).isNotEmpty();

        manager.clear();
    }

    @WithMockUser("Hackerman")
    @Test
    public void addSolarWitoutPermission() throws Exception {

        LOGGER.debug("=== addSolarWitoutPermission() ===");

        AddSolarpanels dummy = createDummyAddSolarpanels("Moony");

        RequestBuilder req = MockMvcRequestBuilders.post("/api/addSolarpanel")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));
        mockMvc.perform(req).andExpect(status().is(401)).andReturn();

        assertThat(service.findAllByUserName(dummy.getUserName())).isNullOrEmpty();

        manager.clear();
    }

    @WithMockUser
    @Test
    public void checkNonexistentSolar() throws Exception {

        LOGGER.debug("=== checkNonexistentSolar() ===");

        RequestBuilder req = MockMvcRequestBuilders.get("/api/addSolarpanel/nobody")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(req).andExpect(status().is(404)).andReturn();
    }

    private AddSolarpanels createDummyAddSolarpanels(String name) {
        AddSolarpanels solar = new AddSolarpanels();
        solar.setUserName(name);
        return solar;
    }
}
