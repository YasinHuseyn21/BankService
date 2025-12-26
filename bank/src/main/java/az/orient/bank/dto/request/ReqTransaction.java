package az.orient.bank.dto.request;

import lombok.Data;

@Data
public class ReqTransaction {
    private Long id;
    private Double amount;
    private String crAccount;
    private String currency;
    private Integer status;
    private Long customerId;
    private Long dtAccountId;


}
