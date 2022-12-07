package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.transation.Transfer;
import ma.octo.assignement.dto.TransferDto;
import org.springframework.stereotype.Component;


@Component
public class TransferMapper {

    private static TransferDto transferDto;


    public static TransferDto transforToDto(Transfer transfer) {
        transferDto = new TransferDto();
        transferDto.setNumeroCompteEmetteur(transfer.getCompteEmetteur().getNumeroCompte());
        transferDto.setNumeroCompteBeneficiaire(transfer.getCompteBeneficiaire().getNumeroCompte());
        transferDto.setMontant(transfer.getMontantTransfer());
        transferDto.setDate(transfer.getDateExecution());
        transferDto.setMotif(transfer.getMotifTransfer());

        return transferDto;
    }



}
