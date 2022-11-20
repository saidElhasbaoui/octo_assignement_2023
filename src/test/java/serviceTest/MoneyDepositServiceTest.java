package serviceTest;

import ma.octo.assignement.NiceBankApplication;

import ma.octo.assignement.dto.MoneyDepositDto;

import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.repository.MoneyDepositRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.CompteService;
import ma.octo.assignement.service.MoneyDepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NiceBankApplication.class)
@Transactional
public class MoneyDepositServiceTest {

    @Autowired
    private MoneyDepositService moneyDepositService;

    @Autowired
    private CompteService compteService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private MoneyDepositRepository moneyDepositRepository;

    @Test
    public void findAllTest(){
        List<MoneyDepositDto> moneyDepositDtoList = moneyDepositService.findAll();

        assertEquals(moneyDepositDtoList.size(),1);
    }

    @Test
    public void saveTestIfAllGood() throws TransactionException, CompteNonExistantException {

        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(500));
        moneyDeposit.setRipBeneficiaire("RIB2");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("ecomm");

        MoneyDepositDto moneyDepositDto = moneyDepositService.save(moneyDeposit);


        assertEquals(moneyDepositDto.getMontant(),BigDecimal.valueOf(500));

        assertEquals(moneyDepositDto.getNomPrenomEmetteur(),"said elhasbaoui");

    }

    @Test
    public void throwExceptionIfCompteNonExistant() throws TransactionException, CompteNonExistantException {
        boolean exceptionThrown = false ;
        String message = "";
        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(500));
        moneyDeposit.setRipBeneficiaire("RIB3");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("ecomm");

        try {
            moneyDepositService.save(moneyDeposit);
        }catch (Exception e){
            exceptionThrown = true;
            message = e.getMessage();
        }

        assertEquals(exceptionThrown,true);
        assertEquals(message,"Compte Non Existant");

    }

    @Test
    public void throwExceptionIfMontantVide() throws TransactionException, CompteNonExistantException {
        boolean exceptionThrown = false ;
        String message = "";
        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(0));
        moneyDeposit.setRipBeneficiaire("RIB2");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("ecomm");

        try {
            moneyDepositService.save(moneyDeposit);
        }catch (Exception e){
            exceptionThrown = true;
            message = e.getMessage();
        }

        assertEquals(exceptionThrown,true);
        assertEquals(message,"Montant vide");

    }

    @Test
    public void throwExceptionIfMontantNonAtteint() throws TransactionException, CompteNonExistantException {
        boolean exceptionThrown = false ;
        String message = "";
        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(9));
        moneyDeposit.setRipBeneficiaire("RIB2");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("ecomm");

        try {
            moneyDepositService.save(moneyDeposit);
        }catch (Exception e){
            exceptionThrown = true;
            message = e.getMessage();
        }

        assertEquals(exceptionThrown,true);
        assertEquals(message,"Montant minimal de transfer non atteint");

    }

    @Test
    public void throwExceptionIfMontantDepassé() throws TransactionException, CompteNonExistantException {
        boolean exceptionThrown = false ;
        String message = "";
        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(10001));
        moneyDeposit.setRipBeneficiaire("RIB2");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("ecomm");

        try {
            moneyDepositService.save(moneyDeposit);
        }catch (Exception e){
            exceptionThrown = true;
            message = e.getMessage();
        }

        assertEquals(exceptionThrown,true);
        assertEquals(message,"Montant maximal de transfer dépassé");

    }

    @Test
    public void throwExceptionIfMotifVide() throws TransactionException, CompteNonExistantException {
        boolean exceptionThrown = false ;
        String message = "";
        MoneyDepositDto moneyDeposit = new MoneyDepositDto();
        moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
        moneyDeposit.setMontant(BigDecimal.valueOf(8000));
        moneyDeposit.setRipBeneficiaire("RIB2");
        moneyDeposit.setDate(new Date());
        moneyDeposit.setMotif("");

        try {
            moneyDepositService.save(moneyDeposit);
        }catch (Exception e){
            exceptionThrown = true;
            message = e.getMessage();
        }

        assertEquals(exceptionThrown,true);
        assertEquals(message,"Motif vide");

    }
}
