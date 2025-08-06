package sapphire.co.kafkaproducer.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle all unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception ex) {
        ResponseDto response = new ResponseDto();
        response.setCode(1); // Failure
        response.setMessage("Validation error: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Handle all unhandled exceptions
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ResponseDto> handleException(Exception ex) {
        ResponseDto response = new ResponseDto();
        response.setCode(1); // Failure
        response.setMessage("An error occurred: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Example: Handle specific exception
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgument(IllegalArgumentException ex) {
        ResponseDto response = new ResponseDto();
        response.setCode(1);
        response.setMessage("Invalid input: " + ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
