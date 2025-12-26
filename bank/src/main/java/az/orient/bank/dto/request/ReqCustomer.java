package az.orient.bank.dto.request;


import lombok.Data;

import java.util.Date;

@Data
public class ReqCustomer {

    private Long id;
    private String name;
    private String surname;
    private Date dob;
    private String email;
    private String pin;
    private String seria;
    private String cif;
}
