package sysco.summit.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sysco.summit.product.dto.request.CreateProductRequestDto;
import sysco.summit.product.dto.request.UpdateProductRequestDto;
import sysco.summit.product.dto.response.ProductImageResponseDto;
import sysco.summit.product.dto.response.ProductResponseDto;
import sysco.summit.product.exception.ProductServiceException;
import sysco.summit.product.model.Product;
import sysco.summit.product.model.ProductImage;
import sysco.summit.product.repository.ProductImageRepository;
import sysco.summit.product.repository.ProductRepository;
import sysco.summit.product.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sysco.summit.product.enumeration.ErrorMessage.INTERNAL_SERVER_ERROR;
import static sysco.summit.product.enumeration.ErrorMessage.INVALID_PRODUCT_ID;

@Service
@Slf4j
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImp(ProductRepository productRepository, ProductImageRepository productImageRepository,
                             ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Page<ProductResponseDto> getProducts(Pageable pageable, String searchTerm) {
        try {
            Page<Product> productPage = searchTerm == null ? productRepository.findAll(pageable) :
                    productRepository.findByNameContains(pageable, searchTerm);
            List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
            productPage.getContent().forEach(product -> {
                ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
                productResponseDto.setImage(modelMapper.map(product.getProductImage(), ProductImageResponseDto.class));
                productResponseDtoList.add(productResponseDto);
            });
            return new PageImpl<>(productResponseDtoList, productPage.getPageable(), productPage.getTotalElements());
        } catch (Exception e) {
            log.error("Failed to get products from database.", e);
            throw new ProductServiceException(INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void addProducts(CreateProductRequestDto createProductRequestDto) {
        try {
            ProductImage productImage = productImageRepository.save(new ProductImage(createProductRequestDto.getFile()));
            productRepository.save(new Product(createProductRequestDto.getName(),
                    createProductRequestDto.getDescription(), createProductRequestDto.getSupplierId(),
                    createProductRequestDto.getSupplierName(), productImage));
        } catch (Exception e) {
            log.error("Failed to add products.", e);
            throw new ProductServiceException(INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateProducts(UpdateProductRequestDto updateProductRequestDto) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(updateProductRequestDto.getId());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Optional<ProductImage> optionalProductImage =
                        productImageRepository.findById(product.getProductImage().getId());
                if (optionalProductImage.isPresent()) {
                    optionalProductImage.get().update(updateProductRequestDto.getFile());
                    productImageRepository.save(optionalProductImage.get());
                }
                product.update(updateProductRequestDto.getName(), updateProductRequestDto.getDescription(),
                        updateProductRequestDto.getSupplierId(), updateProductRequestDto.getSupplierName());
                productRepository.save(product);
            } else {
                log.error("Product not found for id: {}", updateProductRequestDto.getId());
                throw new ProductServiceException(INVALID_PRODUCT_ID, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException | IOException e) {
            log.error("Couldn't get product for id: {}", updateProductRequestDto.getId());
            throw new ProductServiceException(INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                productImageRepository.deleteById(optionalProduct.get().getProductImage().getId());
                productRepository.deleteById(id);
            } else {
                log.error("Product not found for id: {}", id);
                throw new ProductServiceException(INVALID_PRODUCT_ID, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            log.error("Couldn't delete product for id: {}", id);
            throw new ProductServiceException(INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void validateProduct(int id, boolean isValid) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                optionalProduct.get().setValid(isValid);
                productRepository.save(optionalProduct.get());
            } else {
                log.error("Product not found for id: {}", id);
                throw new ProductServiceException(INVALID_PRODUCT_ID, HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            log.error("Couldn't validate product for id: {}", id);
            throw new ProductServiceException(INTERNAL_SERVER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
