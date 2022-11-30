package repositoryTest;


import ma.octo.assignement.NiceBankApplication;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.repository.CompteRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = NiceBankApplication.class)
public class CompteRepositoryTest {

    @Autowired
    private CompteRepository compteRepository;

    @Test
    public void findByNumeroCompteTest(){

        Compte compte = compteRepository.findByNumeroCompte("010000A000001000");

        assertThat(compte.getUtilisateur().getUsername()).isEqualTo("user1");

        assertThat(compte.getNumeroCompte()).isEqualTo("010000A000001000");

    }

    @Test
    public void findByRibTest(){
        Compte compte = compteRepository.findByRib("RIB2");

        assertThat(compte.getUtilisateur().getUsername()).isEqualTo("user2");

        assertThat(compte.getRib()).isEqualTo("RIB2");
    }

}
