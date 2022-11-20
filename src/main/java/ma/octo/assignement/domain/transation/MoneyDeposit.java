package ma.octo.assignement.domain.transation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


//why using @DATA :@ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
@Data
//@NoArgsConstructor will generate a constructor with no parameters.
@NoArgsConstructor
//Generates an all-args constructor
@AllArgsConstructor
//is a helpful mechanism for using the Builder pattern without writing boilerplate code.
@Builder

//The entities are the persistence objects stores as a record in the database
@Entity

//a column to identify the type of the record
@DiscriminatorValue("deposit")
public class MoneyDeposit extends Transaction{

  // Adding the column the name in the table of a particular MySQL database
  @Column
  private String nomPrenomEmetteur;

  }
