package sysco.summit.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import sysco.summit.product.enumeration.ErrorMessage;

@Getter
public class ProductServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ProductServiceException(ErrorMessage errorMessage, Throwable cause, HttpStatus httpStatus) {
        super(errorMessage.getMessage(), cause);
        this.httpStatus = httpStatus;
    }

    public ProductServiceException(ErrorMessage errorMessage, HttpStatus httpStatus) {
        super(errorMessage.getMessage());
        this.httpStatus = httpStatus;
    }

    public ProductServiceException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }
}
