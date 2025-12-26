package az.orient.bank.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespStatus {

     private Integer code;
     private String message;

     private static final Integer SUCCESS_CODE = 1;
     private static final String SUCCESS_MESSAGE = "success";

     public static  RespStatus success() {
         return new RespStatus(SUCCESS_CODE, SUCCESS_MESSAGE);
     }



}
