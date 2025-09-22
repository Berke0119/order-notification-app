package com.serinsoft.order_notification_app.service;

import com.serinsoft.order_notification_app.dto.ProductCreateRequest;
import com.serinsoft.order_notification_app.dto.ProductResponse;
import com.serinsoft.order_notification_app.entity.Product;
import com.serinsoft.order_notification_app.exception.InsufficentStockException;
import com.serinsoft.order_notification_app.exception.NotFoundException;
import com.serinsoft.order_notification_app.mapper.ProductMapper;
import com.serinsoft.order_notification_app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request){
        if(productRepository.existsByName(request.name())){
            throw new IllegalArgumentException("Product with name "+request.name()+" already exists");
        }

        Product saved = productRepository.save(ProductMapper.toEntity(request));
        return ProductMapper.toResponse(saved);
    }



    public List<ProductResponse> listAll(){
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(ProductMapper::toResponse).toList();
    }


    @Cacheable(value = "products", key = "#id")
    public Product getEntityById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id "+ id +" not found"));
    }


    public ProductResponse getById(Long id){
        Product product = getEntityById(id);
        return ProductMapper.toResponse(product);
    }


    @CacheEvict(cacheNames = "products", key = "#productId")
    public void decreaseStock(Long productId, Integer amount){
        Product product = getEntityById(productId);
        int newStock = product.getStock() - amount;

        if(newStock < 0){
            throw new InsufficentStockException("Not enough stock for product with id "+ productId);
        }

        product.setStock(newStock);

        productRepository.save(product);
    }

}
