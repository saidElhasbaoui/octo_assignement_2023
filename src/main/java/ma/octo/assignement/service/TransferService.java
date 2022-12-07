package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.transation.Transfer;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.mapper.TransferMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.service.interfaces.ITransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService implements ITransfer {

    public static final int MONTANT_MAXIMAL = 10000;

    Logger LOGGER = LoggerFactory.getLogger(TransferService.class);

    private TransferRepository transferRepository;

    private AuditService auditService;

    private CompteRepository compteRepository;

    @Autowired
    public TransferService(AuditService auditService, CompteRepository compteRepository, TransferRepository transferRepository) {
        this.auditService = auditService;
        this.compteRepository = compteRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public List<TransferDto> findAll() {

        LOGGER.info("Lister des transferts");
        List<Transfer> all = transferRepository.findAll();
        List<TransferDto> transferMapper = all.stream().map(x-> TransferMapper.transforToDto(x)).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(transferMapper) ? transferMapper : null;

    }

    @Override
    @Transactional
    public TransferDto save(TransferDto transferDto)
            throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException {
        Compte compteEmetteur = compteRepository.findByNumeroCompte(transferDto.getNumeroCompteEmetteur());
        Compte compteBeneficiaire = compteRepository.findByNumeroCompte(transferDto.getNumeroCompteBeneficiaire());

        if (compteEmetteur == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (compteBeneficiaire == null) {
            System.out.println("Compte Non existant");
            throw new CompteNonExistantException("Compte Non existant");
        }

        if (transferDto.getMontant().equals(null)) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (transferDto.getMontant().intValue() == 0) {
            System.out.println("Montant vide");
            throw new TransactionException("Montant vide");
        } else if (transferDto.getMontant().intValue() < 10) {
            System.out.println("Montant minimal de transfer non atteint");
            throw new TransactionException("Montant minimal de transfer non atteint");
        } else if (transferDto.getMontant().intValue() > MONTANT_MAXIMAL) {
            System.out.println("Montant maximal de transfer dépassé");
            throw new TransactionException("Montant maximal de transfer dépassé");
        }

        if (transferDto.getMotif().length()==0 || transferDto.getMotif().equals(null)) {
            System.out.println("Motif vide");
            throw new TransactionException("Motif vide");
        }

        if (compteEmetteur.getSolde().intValue() - transferDto.getMontant().intValue() < 0) {
            LOGGER.error("Solde insuffisant pour l'utilisateur");
            throw new SoldeDisponibleInsuffisantException("Solde insuffisant pour l'utilisateur");
        }


        compteEmetteur.setSolde(compteEmetteur.getSolde().subtract(transferDto.getMontant()));
        compteRepository.save(compteEmetteur);

        compteBeneficiaire.setSolde(new BigDecimal(compteBeneficiaire.getSolde().intValue() + transferDto.getMontant().intValue()));
        compteRepository.save(compteBeneficiaire);

        Transfer transfer = new Transfer();
        transfer.setDateExecution(transferDto.getDate());
        transfer.setCompteBeneficiaire(compteBeneficiaire);
        transfer.setCompteEmetteur(compteEmetteur);
        transfer.setMontantTransfer(transferDto.getMontant());
        transfer.setMotifTransfer(transferDto.getMotif());

        transferRepository.save(transfer);

        auditService.auditTransfer("Transfer depuis " + transferDto.getNumeroCompteEmetteur() + " vers " + transferDto
                .getNumeroCompteBeneficiaire() + " d'un montant de " + transferDto.getMontant()
                .toString());

        return transferDto;
    }



}

