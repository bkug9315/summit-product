package sysco.summit.product.enumeration;

import lombok.Getter;

/**
 * Error messages.
 */
@Getter
public enum ErrorMessage {

    INTERNAL_SERVER_ERROR("Something went wrong."),
    REQUIRED_FIELDS_MISSING("Required fields missing."),
    SUPPLIER_EXISTS("Supplier name already exists."),
    INVALID_PRODUCT_ID("Invalid product id."),
    PRODUCT_IMAGE_MISSING("Product image is missing.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
