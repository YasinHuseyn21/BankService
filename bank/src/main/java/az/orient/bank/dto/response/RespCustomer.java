package az.orient.bank.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespCustomer {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String dob;
    private String cif;
    private String seria;
    private String pin;
}
