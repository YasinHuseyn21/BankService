package az.orient.bank.parser.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespRates {

    private Double AZN;
    private Double USD;
    private Double TRY;
}
