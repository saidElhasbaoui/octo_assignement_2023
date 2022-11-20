package controllerTest;

import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.web.CompteController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CompteController.class)
@WebAppConfiguration
public class CompteControllerTest {

    private MockMvc mvc;

    @MockBean
    CompteService compteService;

    @Autowired
    WebApplicationContext webApplicationContext;

    String URI = "/api/comptes/";

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testLoadAllCompteStatus() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200 , status);


    }

}
