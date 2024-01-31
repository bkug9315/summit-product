package sysco.summit.product.dto.response;

import lombok.Data;

@Data
public class ProductResponseDto {

    private String name;
    private String description;
    private int supplierId;
    private String supplierName;
    private ProductImageResponseDto image;
}
