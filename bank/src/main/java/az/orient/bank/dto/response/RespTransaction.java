package az.orient.bank.dto.response;

import az.orient.bank.entity.Account;
import az.orient.bank.entity.Customer;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class RespTransaction {
    private Long id;
    private Double amount;
    private String crAccount;
    private String currency;
    private Integer status;
    private String createDate;
    private Customer customer;
    private Account dtAccount;

}
