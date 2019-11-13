package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gogreenserver.entity.Tree;
import gogreenserver.repositories.TreeRepository;

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

import java.time.LocalDateTime;
import java.util.List;

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
public class AddTreeTests {

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
    private TreeRepository repo;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser("ProfOak")
    @Test
    public void checkAddTree() throws Exception {

        LOGGER.debug("=== checkAddTree() ===");

        Tree dummy = createDummyTree("ProfOak");

        RequestBuilder ereq = MockMvcRequestBuilders.post("/api/addTree")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));

        mockMvc.perform(ereq).andExpect(status().is(200));

        List<Tree> res = repo.findAllByUserName(dummy.getUserName());

        assertThat(res.size()).isEqualTo(1);
                
        assertThat(res.get(0)).isEqualToIgnoringGivenFields(dummy, "id");

        manager.clear();
    }

    @WithMockUser("Hackerman")
    @Test
    public void checkAddTreeNoPermission() throws Exception {

        LOGGER.debug("=== checkAddTreeNoPermission() ===");

        Tree dummy = createDummyTree("ProfOak");

        RequestBuilder ereq = MockMvcRequestBuilders.post("/api/addTree")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dummy));

        mockMvc.perform(ereq).andExpect(status().is(401));

        List<Tree> res = repo.findAllByUserName(dummy.getUserName());

        assertThat(res.size()).isEqualTo(0);

        manager.clear();
    }

    @WithMockUser
    @Test
    public void checkGetAllTrees() throws Exception {
        
        LOGGER.debug("=== checkGetAllTrees() ===");

        Tree[] dummies = new Tree[3];
        dummies[0] = manager.persist(createDummyTree("Sylvia"));
        dummies[1] = manager.persist(createDummyTree("Bos"));
        dummies[2] = manager.persist(createDummyTree("Woud"));
        manager.flush();

        RequestBuilder ereq = MockMvcRequestBuilders.get("/api/getAllTrees")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult eres = mockMvc.perform(ereq).andExpect(status().is(200)).andReturn();

        JsonNode list = mapper.readTree(eres.getResponse().getContentAsString());

        LOGGER.debug("Returned Json: " + list);

        int count = 0;
        for (JsonNode tree : list) {
            LOGGER.debug("Tree " + count + ": " + tree);

            assertThat(mapper.writeValueAsString(tree))
                    .isEqualTo(mapper.writeValueAsString(dummies[count]));

            count++;
        }
        LOGGER.debug("Solar panel installations amount: " + count);
        assertThat(count).isEqualTo(3);

        manager.clear();
    }

    private Tree createDummyTree(String name) {
        Tree tree = new Tree();
        tree.setUserName(name);
        tree.setAddingDate(LocalDateTime.now());
        tree.setTree(RandomString.make());
        tree.setCo2Saved(name.hashCode());
        return tree;
    }
}
