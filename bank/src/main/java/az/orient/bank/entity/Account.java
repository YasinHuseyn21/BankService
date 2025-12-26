package az.orient.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;

import java.util.Date;

@Entity
@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNo;
    private String iban;
    private String currency;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",insertable = false)
    private Date createdDate;
    @Column(columnDefinition = "INT DEFAULT 1", insertable = false)
    private Integer active;


}
