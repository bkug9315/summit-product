package sysco.summit.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sysco.summit.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findByNameContains(Pageable pageable, String searchTerm);
}
