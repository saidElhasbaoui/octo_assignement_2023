package controllerTest;

import ma.octo.assignement.service.UtilisateurService;
import ma.octo.assignement.web.UtilisateurController;
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
@SpringBootTest(classes = UtilisateurController.class)
@WebAppConfiguration
public class UtilisateurControllerTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private UtilisateurService utilisateurService;

    String URI = "/api/utilisateurs/";


    @Before
    public void setUp(){

        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadAllStatus() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200 , status);

    }





}
