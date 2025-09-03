package co.com.crediya.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final String code;
    private final LocalDateTime timestamp;
    private final HttpStatus status;
    private final List<String> details;

    public static ErrorResponse from(String message, String code, HttpStatus status) {
        return new ErrorResponse(message, code, LocalDateTime.now(), status, null);
    }

    public static ErrorResponse from(String message, String code, HttpStatus status, List<String> details) {
        return new ErrorResponse(message, code, LocalDateTime.now(), status, details);
    }
}