package sysco.summit.product.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateProductRequestDto extends CreateProductRequestDto {

    @Min(1)
    private int id;
}
