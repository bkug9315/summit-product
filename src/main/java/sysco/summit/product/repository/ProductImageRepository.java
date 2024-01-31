package sysco.summit.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sysco.summit.product.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

}
