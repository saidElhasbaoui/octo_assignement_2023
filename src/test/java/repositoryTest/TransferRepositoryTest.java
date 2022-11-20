package repositoryTest;



import ma.octo.assignement.NiceBankApplication;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.domain.transation.Transfer;
import ma.octo.assignement.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NiceBankApplication.class)
@Transactional
public class TransferRepositoryTest {


    @Autowired
    TransferRepository transferRepository;

    @Test
    public void findOne() {

        Transfer transfer = transferRepository.findById(5L).get();

        assertEquals(5,transfer.getId().intValue());


    }

    @Test
    public void findAll() {
        List<Transfer> transferList = transferRepository.findAll();

        assertThat(transferList).hasSize(1);
    }

    @Test
    public void save() {
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

        Transfer v = new Transfer();
        v.setMontantTransfer(BigDecimal.valueOf(50));
        v.setCompteBeneficiaire(compte2);
        v.setCompteEmetteur(compte1);
        v.setDateExecution(new Date());
        v.setMotifTransfer("Assignment 2021");

        Transfer transfer1 =transferRepository.save(v);

        Transfer transfer = transferRepository.findById(transfer1.getId()).get();


        assert(transfer.getId().equals(transfer1.getId()));


    }

    @Test
    public void delete() {
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

        Transfer v = new Transfer();
        v.setMontantTransfer(BigDecimal.valueOf(50));
        v.setCompteBeneficiaire(compte2);
        v.setCompteEmetteur(compte1);
        v.setDateExecution(new Date());
        v.setMotifTransfer("Assignment 2021");

        Transfer transfer = transferRepository.save(v);

        transferRepository.deleteById(transfer.getId());

        List<Transfer> transferList = transferRepository.findAll();

        assertThat(transferList).hasSize(1);


    }


}
