package ma.octo.assignement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data

public class TransferDto {
  private Long id;
  private String numeroCompteEmetteur;
  private String numeroCompteBeneficiaire;
  private String motif;
  private BigDecimal montant;
  private Date date;

}
