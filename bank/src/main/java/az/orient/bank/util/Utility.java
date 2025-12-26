package az.orient.bank.util;

import az.orient.bank.dto.request.ReqTransaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Utility {


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

}
