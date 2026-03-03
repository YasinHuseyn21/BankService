package az.orient.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class RespAuth {


    private String token;
    private Long expiresAt;



}
