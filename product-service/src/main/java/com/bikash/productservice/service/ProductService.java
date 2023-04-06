package com.bikash.productservice.service;

import com.bikash.productservice.dto.ProductRequest;
import com.bikash.productservice.dto.ProductResponse;
import com.bikash.productservice.model.Product;
import com.bikash.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository repo;
    public void createProduct(ProductRequest productRequest){

        Product product = Product.builder()
                .patent(productRequest.getPatent())
                .productName(productRequest.getProductName())
                .productInfo(productRequest.getProductInfo())
                .price(productRequest.getPrice())
                .build();

        repo.save(product);
        log.info("Product {} is saved", product.getProductName());
    }

    public List<ProductResponse> getProducts(){
        log.info("Fetching all the Products.......");
        return repo.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productInfo(product.getProductInfo())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
    }
}
