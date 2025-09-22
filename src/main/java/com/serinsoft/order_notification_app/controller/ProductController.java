package com.serinsoft.order_notification_app.controller;

import com.serinsoft.order_notification_app.dto.ProductCreateRequest;
import com.serinsoft.order_notification_app.dto.ProductResponse;
import com.serinsoft.order_notification_app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductCreateRequest request){
        ProductResponse created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listAll(){
        List<ProductResponse> products = productService.listAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        ProductResponse product = productService.getById(id);
        return ResponseEntity.ok(product);
    }
}
