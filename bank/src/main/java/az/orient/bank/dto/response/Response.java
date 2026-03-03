package az.orient.bank.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Response<T> {

    @JsonProperty("response")
    private T t;
    private RespStatus status;
}
