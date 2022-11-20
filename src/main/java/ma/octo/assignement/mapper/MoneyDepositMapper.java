package ma.octo.assignement.mapper;

import ma.octo.assignement.domain.transation.MoneyDeposit;
import ma.octo.assignement.domain.transation.Transfer;
import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.dto.TransferDto;
import org.springframework.stereotype.Component;

@Component
public class MoneyDepositMapper {

    private static MoneyDepositDto moneyDepositDto;

    public static MoneyDepositDto transforToDto(MoneyDeposit moneyDeposit) {
        moneyDepositDto = new MoneyDepositDto();
        moneyDepositDto.setNomPrenomEmetteur(moneyDeposit.getNomPrenomEmetteur());
        moneyDepositDto.setRipBeneficiaire(moneyDeposit.getCompteBeneficiaire().getRib());
        moneyDepositDto.setMontant(moneyDeposit.getMontantTransfer());
        moneyDepositDto.setDate(moneyDeposit.getDateExecution());
        moneyDepositDto.setMotif(moneyDeposit.getMotifTransfer());

        return moneyDepositDto;
    }


}
