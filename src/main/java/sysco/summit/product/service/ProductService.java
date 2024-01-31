package sysco.summit.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sysco.summit.product.dto.request.CreateProductRequestDto;
import sysco.summit.product.dto.request.UpdateProductRequestDto;
import sysco.summit.product.dto.response.ProductResponseDto;

public interface ProductService {

    Page<ProductResponseDto> getProducts(Pageable pageable, String searchTerm);

    void addProducts(CreateProductRequestDto createProductRequestDto);

    void updateProducts(UpdateProductRequestDto updateProductRequestDto);

    void deleteProduct(int id);

    void validateProduct(int id, boolean isValid);

}
