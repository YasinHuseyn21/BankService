package az.orient.bank.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespAccount {

    private Long id;
    private String accountNo;
    private String iban;
    private String currency;
    private Double balance;
    private Date accountDate;
    private RespCustomer respCustomer;
}
