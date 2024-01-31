package sysco.summit.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fileName;
    private String contentType;
    private long fileSize;
    @Lob
    @Column(length = 1000)
    private byte[] file;

    public ProductImage(MultipartFile multipartFile) throws IOException {
        this.fileName = multipartFile.getOriginalFilename();
        this.contentType = multipartFile.getContentType();
        this.fileSize = multipartFile.getSize();
        this.file = multipartFile.getBytes();
    }

    public void update(MultipartFile multipartFile) throws IOException {
        this.fileName = multipartFile.getOriginalFilename();
        this.contentType = multipartFile.getContentType();
        this.fileSize = multipartFile.getSize();
        this.file = multipartFile.getBytes();
    }
}
