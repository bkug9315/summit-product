package sysco.summit.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductRequestDto {

    @NotNull
    private MultipartFile file;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    @NotNull
    @NotEmpty
    @NotBlank
    private String description;
    @Min(1)
    private int supplierId;
    @NotNull
    @NotEmpty
    @NotBlank
    private String supplierName;
}
