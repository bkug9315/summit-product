package sysco.summit.product.dto.response;

import lombok.Data;

@Data
public class ProductImageResponseDto {

    private String fileName;
    private String contentType;
    private long fileSize;
    private byte[] file;
}
