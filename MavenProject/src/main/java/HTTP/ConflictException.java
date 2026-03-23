package HTTP;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    private final String code;
    private final String message;

    public ConflictException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
