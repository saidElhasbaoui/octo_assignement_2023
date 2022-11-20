package ma.octo.assignement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MoneyDepositDto {
    private String nomPrenomEmetteur;
    private String ripBeneficiaire;
    private String motif;
    private BigDecimal montant;
    private Date date;

}
