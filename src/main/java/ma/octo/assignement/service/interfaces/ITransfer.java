package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.dto.TransferDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.SoldeDisponibleInsuffisantException;
import ma.octo.assignement.exceptions.TransactionException;

import java.util.List;

public interface ITransfer {

    List<TransferDto> findAll();
    TransferDto save(TransferDto transferDto) throws SoldeDisponibleInsuffisantException, CompteNonExistantException, TransactionException;


}
