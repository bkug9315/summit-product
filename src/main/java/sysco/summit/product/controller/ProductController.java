package sysco.summit.product.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sysco.summit.product.dto.request.CreateProductRequestDto;
import sysco.summit.product.dto.request.UpdateProductRequestDto;
import sysco.summit.product.dto.response.ResponseWrapper;
import sysco.summit.product.service.ProductService;

import static sysco.summit.product.enumeration.ErrorMessage.PRODUCT_IMAGE_MISSING;
import static sysco.summit.product.enumeration.ErrorMessage.REQUIRED_FIELDS_MISSING;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController extends BaseController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProducts(@RequestParam int page, @RequestParam int size,
                                                       @RequestParam(required = false) String searchTerm) {
        Pageable pageable = PageRequest.of(page, size);
        return sendSuccessResponse(productService.getProducts(pageable, searchTerm));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper> addProducts(@Valid @ModelAttribute CreateProductRequestDto createProductRequestDto,
                                                       BindingResult bindingResult) {
        if (createProductRequestDto.getFile().getContentType() == null)
            return sendErrorResponse(PRODUCT_IMAGE_MISSING, HttpStatus.BAD_REQUEST);
        if (bindingResult.hasErrors())
            return sendErrorResponse(REQUIRED_FIELDS_MISSING, HttpStatus.BAD_REQUEST);
        productService.addProducts(createProductRequestDto);
        return sendCreatedResponse(null);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapper> updateProducts(@Valid @ModelAttribute
                                                          UpdateProductRequestDto updateProductRequestDto,
                                                          BindingResult bindingResult) {
        if (updateProductRequestDto.getFile().getContentType() == null)
            return sendErrorResponse(PRODUCT_IMAGE_MISSING, HttpStatus.BAD_REQUEST);
        if (bindingResult.hasErrors())
            return sendErrorResponse(REQUIRED_FIELDS_MISSING, HttpStatus.BAD_REQUEST);
        productService.updateProducts(updateProductRequestDto);
        return sendSuccessResponse(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper> deleteProducts(@PathVariable int id) {
        productService.deleteProduct(id);
        return sendSuccessResponse(null);
    }

    @PatchMapping("validate")
    public ResponseEntity<ResponseWrapper> validateProduct(@RequestParam int id, @RequestParam boolean isValid) {
        productService.validateProduct(id, isValid);
        return sendSuccessResponse(null);
    }
}
