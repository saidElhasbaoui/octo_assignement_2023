package controllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.transation.MoneyDeposit;
import ma.octo.assignement.domain.transation.Transfer;
import ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.service.TransferService;
import ma.octo.assignement.web.TransferController;
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
@SpringBootTest(classes = TransferController.class)
@WebAppConfiguration
public class TransferControllerTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    private TransferService transferService;

    String URI = "/api/transfers/";

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
        String uri = URI+"executerTransfers";

        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("user1");
        utilisateur1.setLastname("last1");
        utilisateur1.setFirstname("first1");
        utilisateur1.setGender("Male");

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setUsername("user2");
        utilisateur2.setLastname("last2");
        utilisateur2.setFirstname("first2");
        utilisateur2.setGender("Female");

        Compte compte1 = new Compte();
        compte1.setNumeroCompte("010000A000001000");
        compte1.setRib("RIB1");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setUtilisateur(utilisateur1);

        Compte compte2 = new Compte();
        compte2.setNumeroCompte("010000B025001000");
        compte2.setRib("RIB2");
        compte2.setSolde(BigDecimal.valueOf(140000L));
        compte2.setUtilisateur(utilisateur2);

        Transfer transfer = new Transfer();
        transfer.setMontantTransfer(BigDecimal.TEN);
        transfer.setCompteBeneficiaire(compte2);
        transfer.setCompteEmetteur(compte1);
        transfer.setDateExecution(new Date());
        transfer.setMotifTransfer("Assignment 2021");

        String inputJson = mapToJson(transfer);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);


    }

}
