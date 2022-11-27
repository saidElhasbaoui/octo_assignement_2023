package ma.octo.assignement;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.MoneyDepositService;
import ma.octo.assignement.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class NiceBankApplication implements CommandLineRunner {

	private final CompteRepository compteRepository;
	private final UtilisateurRepository utilisateurRepository;
	private final TransferService transferService;
	private final MoneyDepositService moneyDepositService;

	public NiceBankApplication(CompteRepository compteRepository, UtilisateurRepository utilisateurRepository, TransferService transferService, MoneyDepositService moneyDepositService) {
		this.compteRepository = compteRepository;
		this.utilisateurRepository = utilisateurRepository;
		this.transferService = transferService;
		this.moneyDepositService = moneyDepositService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NiceBankApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Utilisateur utilisateur1 = new Utilisateur();
		utilisateur1.setUsername("user1");
		utilisateur1.setLastname("last1");
		utilisateur1.setFirstname("first1");
		utilisateur1.setGender("Male");

		utilisateurRepository.save(utilisateur1);

		Utilisateur utilisateur2 = new Utilisateur();
		utilisateur2.setUsername("user2");
		utilisateur2.setLastname("last2");
		utilisateur2.setFirstname("first2");
		utilisateur2.setGender("Female");

		utilisateurRepository.save(utilisateur2);

		Compte compte1 = new Compte();
		compte1.setNumeroCompte("010000A000001000");
		compte1.setRib("RIB1");
		compte1.setSolde(BigDecimal.valueOf(200000L));
		compte1.setUtilisateur(utilisateur1);

		compteRepository.save(compte1);

		Compte compte2 = new Compte();
		compte2.setNumeroCompte("010000B025001000");
		compte2.setRib("RIB2");
		compte2.setSolde(BigDecimal.valueOf(140000L));
		compte2.setUtilisateur(utilisateur2);

		compteRepository.save(compte2);

		TransferDto v = new TransferDto();
		v.setMontant(BigDecimal.valueOf(20));
		v.setNumeroCompteBeneficiaire(compte2.getNumeroCompte());
		v.setNumeroCompteEmetteur(compte1.getNumeroCompte());
		v.setDate(new Date());
		v.setMotif("Assignment 2021");

		transferService.save(v);

		MoneyDepositDto moneyDeposit = new MoneyDepositDto();
		moneyDeposit.setNomPrenomEmetteur("said elhasbaoui");
		moneyDeposit.setMontant(BigDecimal.valueOf(500));
		moneyDeposit.setRipBeneficiaire(compte2.getRib());
		moneyDeposit.setDate(new Date());
		moneyDeposit.setMotif("ecomm");

		moneyDepositService.save(moneyDeposit);

	}
}
