package sapphire.co.kafkaproducer.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private int code;       // 0 = success, 1 = failure
    private String message;
    private Object data;  // OK or FAILURE
}
