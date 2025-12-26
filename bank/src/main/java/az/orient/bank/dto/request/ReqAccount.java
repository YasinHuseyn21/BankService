package az.orient.bank.dto.request;

import az.orient.bank.dto.response.RespCustomer;
import az.orient.bank.entity.Account;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
@Data
public class ReqAccount {
    private Long id;
    private Long customerId;
    private String accountNo;
    private String iban;
    private String currency;
    private Double balance;
  //  private Date accountDate;
}
