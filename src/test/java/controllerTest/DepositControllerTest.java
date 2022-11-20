package controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.transation.MoneyDeposit;
import ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.web.MoneyDepositController;
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

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MoneyDepositController.class)
@WebAppConfiguration
public class DepositControllerTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private MoneyDepositService moneyDepositService;

    String URI = "/api/deposit/";

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoadAllStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200,status);
    }


    @Test
    public void testCreateTransaction() throws Exception{
        String uri = URI+"executerDeposit";

        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("user1");
        utilisateur1.setLastname("last1");
        utilisateur1.setFirstname("first1");
        utilisateur1.setGender("Male");

        Compte compte1 = new Compte();
        compte1.setNumeroCompte("010000A000001000");
        compte1.setRib("RIB1");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setUtilisateur(utilisateur1);

        MoneyDeposit moneyDeposit = new MoneyDeposit();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontantTransfer(BigDecimal.valueOf(500));
        moneyDeposit.setCompteBeneficiaire(compte1);
        moneyDeposit.setDateExecution(new Date());
        moneyDeposit.setMotifTransfer("ecomm");

        String inputJson = mapToJson(moneyDeposit);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);


    }


}
