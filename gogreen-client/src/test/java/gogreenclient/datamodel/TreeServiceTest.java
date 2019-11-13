package gogreenclient.datamodel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TreeServiceTest {

    private TreeService service = new TreeService();
    private Tree tree = new Tree();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void plantingTreeNotOk() {
        tree.setTree("oak");
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("wazzap", HttpStatus.ACCEPTED);
        when(template.postForEntity(
                anyString(),
                eq(tree),
                eq(String.class)
        )).thenReturn(responseEntity);
        assertTrue(service.plantingTree(tree).getBody().equals("wazzap"));
    }

    @Test
    public void plantingTreeOk() {
        tree.setTree("oak");
        RestTemplate template = mock(RestTemplate.class);
        service.setRestTemplate(template);
        service.setUserName("Ka");
        ResponseEntity<String> responseEntity = new ResponseEntity<String>("wazzap", HttpStatus.OK);
        when(template.postForEntity(
                anyString(),
                eq(new InsertHistory("Ka")),
                eq(String.class)
        )).thenReturn(null);
        when(template.postForEntity(
                anyString(),
                eq(tree),
                eq(String.class)
        )).thenReturn(responseEntity);
        assertTrue(service.plantingTree(tree).getBody().equals("wazzap"));
    }
}