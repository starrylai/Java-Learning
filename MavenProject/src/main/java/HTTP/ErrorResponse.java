package HTTP;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private String path;
    private String code;
    private String message;
    private Object details;
}