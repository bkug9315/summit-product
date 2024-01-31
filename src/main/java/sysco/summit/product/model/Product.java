package sysco.summit.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private int supplierId;
    private String supplierName;
    private boolean isValid;
    @OneToOne
    private ProductImage productImage;

    public Product(String name, String description, int supplierId, String supplierName, ProductImage productImage) {
        this.name = name;
        this.description = description;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.productImage = productImage;
    }

    public void update(String name, String description, int supplierId, String supplierName) {
        this.name = name;
        this.description = description;
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }
}
