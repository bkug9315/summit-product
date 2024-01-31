package sysco.summit.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseWrapper {

    private String message;
    private Object data;

    public ResponseWrapper(Object data) {
        this.data = data;
    }

    public ResponseWrapper(String message) {
        this.message = message;
    }
}
