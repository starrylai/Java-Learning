package HTTP;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String code;
    private final String message;

    public ResourceNotFoundException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
