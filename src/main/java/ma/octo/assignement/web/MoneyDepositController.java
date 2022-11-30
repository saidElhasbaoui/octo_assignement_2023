package ma.octo.assignement.web;

import ma.octo.assignement.dto.MoneyDepositDto;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.TransactionException;
import ma.octo.assignement.service.MoneyDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping( "/api/deposit")
public class MoneyDepositController{

    private final MoneyDepositService moneyDepositService;

    @Autowired
    public MoneyDepositController(MoneyDepositService moneyDepositService) {
        this.moneyDepositService = moneyDepositService;
    }

    @GetMapping("")
    List<MoneyDepositDto> loadAll(){

        return moneyDepositService.findAll();

    }

    @PostMapping("/executerDeposit")
    @ResponseStatus(HttpStatus.CREATED)
    public MoneyDepositDto createTransaction(@RequestBody MoneyDepositDto moneyDepositDto)
            throws CompteNonExistantException, TransactionException {
        MoneyDepositDto moneyDepositDto1 = moneyDepositService.save(moneyDepositDto);
        return moneyDepositDto1;
    }


}
