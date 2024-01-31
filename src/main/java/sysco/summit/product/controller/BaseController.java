package sysco.summit.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sysco.summit.product.dto.response.ResponseWrapper;
import sysco.summit.product.enumeration.ErrorMessage;
import sysco.summit.product.exception.ProductServiceException;

@Slf4j
public class BaseController {

    protected <T> ResponseEntity<ResponseWrapper> sendSuccessResponse(T response) {
        if (response == null)
            return new ResponseEntity<>(HttpStatus.OK);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

    protected <T> ResponseEntity<ResponseWrapper> sendCreatedResponse(T response) {
        if (response == null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        return new ResponseEntity<>(responseWrapper, HttpStatus.CREATED);
    }

    protected ResponseEntity<ResponseWrapper> sendErrorResponse(ErrorMessage errorMessage, HttpStatus httpStatus) {
        ResponseWrapper responseWrapper = new ResponseWrapper(errorMessage.getMessage());
        return new ResponseEntity<>(responseWrapper, httpStatus);
    }

    @ResponseBody
    @ExceptionHandler(value = ProductServiceException.class)
    protected ResponseEntity<ResponseWrapper> handleException(ProductServiceException exception) {
        log.error("Product service exception occurred. ", exception);
        ResponseWrapper responseWrapper = new ResponseWrapper(exception.getMessage());
        return new ResponseEntity<>(responseWrapper, exception.getHttpStatus());
    }
}
