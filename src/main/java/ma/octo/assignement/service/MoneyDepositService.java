package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.transation.MoneyDeposit;
import ma.octo.assignement.dto.MoneyDepositDto;

import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.MoneyDepositMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.MoneyDepositRepository;
import ma.octo.assignement.service.interfaces.IMoneyDeposit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoneyDepositService implements IMoneyDeposit {

    public static final int MONTANT_MAXIMAL = 10000;

    Logger LOGGER = LoggerFactory.getLogger(MoneyDepositService.class);

    private final MoneyDepositRepository moneyDepositRepository;

    private final AuditService auditService;

    private final CompteRepository compteRepository;

    @Autowired
    public MoneyDepositService(MoneyDepositRepository moneyDepositRepository, AuditService auditService, CompteRepository compteRepository) {
        this.moneyDepositRepository = moneyDepositRepository;
        this.auditService = auditService;
        this.compteRepository = compteRepository;
    }


    @Override
    public List<MoneyDepositDto> findAll() {
        LOGGER.info("Lister des deposit");
        List<MoneyDeposit> all = moneyDepositRepository.findAll();
        List<MoneyDepositDto> moneyDepositDtos = all.stream().map(x-> MoneyDepositMapper.transforToDto(x)).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(moneyDepositDtos) ? moneyDepositDtos : null;
    }

    @Override
    public MoneyDepositDto save(MoneyDepositDto moneyDepositDto) throws CompteNonExistantException, TransactionException {
        Compte compteBeneficiaire = compteRepository.findByRib(moneyDepositDto.getRipBeneficiaire());

        if(compteBeneficiaire==null){
            System.out.println("compte non existant");
            throw new CompteNonExistantException("Compte Non Existant");
        }

        if(moneyDepositDto.getMontant().equals(null)){
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        }else if (moneyDepositDto.getMontant().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        }else if (moneyDepositDto.getMontant().intValue() < 10) {
            System.out.println("Montant minimal de transfer non atteint");
            throw new TransactionException("Montant minimal de transfer non atteint");
        } else if (moneyDepositDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de transfer dépassé");
            throw new TransactionException("Montant maximal de transfer dépassé");
        }
        if (moneyDepositDto.getMotif().length() == 0 || moneyDepositDto.getMotif().equals(null)) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        compteBeneficiaire.setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + moneyDepositDto.getMontant().intValue()));
        compteRepository.save(compteBeneficiaire);

        MoneyDeposit moneyDeposit = new MoneyDeposit();
        moneyDeposit.setDateExecution(moneyDepositDto.getDate());
        moneyDeposit.setMotifTransfer(moneyDepositDto.getMotif());
        moneyDeposit.setMontantTransfer(moneyDepositDto.getMontant());
        moneyDeposit.setNomPrenomEmetteur(moneyDepositDto.getNomPrenomEmetteur());
        moneyDeposit.setCompteBeneficiaire(compteBeneficiaire);

        moneyDepositRepository.save(moneyDeposit);

        auditService.auditDeposit("deposit depuis Monsieur : " +moneyDepositDto.getNomPrenomEmetteur() + " vers "+
                moneyDepositDto.getRipBeneficiaire() + " d'un montant de "+ moneyDepositDto.getMontant().toString());

        return moneyDepositDto;
    }
}
