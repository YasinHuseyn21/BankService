package az.orient.bank.util;

import az.orient.bank.dto.response.RespStatus;
import az.orient.bank.dto.response.Response;
import az.orient.bank.entity.Auth;
import az.orient.bank.enums.EnumStatus;
import az.orient.bank.exception.BankException;
import az.orient.bank.exception.ExceptionConstants;
import az.orient.bank.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class Utility {

    public final AuthRepository authRepository;

    public String sendGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String sendPost(String url, String body) {
        return null;
    }


    public String sendPut(String url, String body) {
        return null;
    }

    public String sendDelete(String url) {
        return null;
    }


    public Auth checkToken(String token, String username) {
        Auth auth = null;

        if (username == null) {
            throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data");
        }
        if (token == null) {
            throw new BankException(ExceptionConstants.SESSION_NOT_FOUND, "Session not found");
        }
        auth = authRepository.findAuthByTokenAndUsernameAndActive(token, username, EnumStatus.ACTIVE.getValue());
        if(auth == null){
            throw new BankException(ExceptionConstants.AUTH_NOT_FOUND, "Auth not found");
        }

        return auth;
    }

    ;

}
