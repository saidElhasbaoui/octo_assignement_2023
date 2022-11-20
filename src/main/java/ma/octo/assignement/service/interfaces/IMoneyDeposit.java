package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface IMoneyDeposit {
    List<MoneyDepositDto> findAll();
    MoneyDepositDto save(MoneyDepositDto moneyDepositDto) throws
            CompteNonExistantException
            ,TransactionException;

}
