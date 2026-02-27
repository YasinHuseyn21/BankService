package az.orient.bank.dto.request;

import lombok.Data;

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
