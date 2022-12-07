package ma.octo.assignement.domain.transation;

import lombok.Data;
import ma.octo.assignement.domain.Compte;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

//why using @DATA :@ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
@Data

//The entities are the persistence objects stores as a record in the database
@Entity
//allows you to specify the details of the table that will be used to persist the entity in the database
@Table(name="transaction")

//The single table strategy maps all entities of the inheritance structure to the same database table
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )

//Specifies the discriminator column for the SINGLE_TABLE and JOINED Inheritance mapping strategies
@DiscriminatorColumn( name="transaction_type")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal montantTransfer;

    // Adding the column the name in the table of a particular MySQL database
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecution;

    //one parent record can have multiple child records
    @ManyToOne
    private Compte compteBeneficiaire;

    @Column(length = 200)
    private String motifTransfer;
}
