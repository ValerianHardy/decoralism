package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //только чтение методами по умолчанию (03:05:00 22/11)
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    //отображение всех пользователей
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    //возвращения товара по id, т.к. опциальный объект может быть пустым, обязательно указать orElse null
    public Product getProductId (int id){
         Optional<Product> optionalProduct = productRepository.findById(id);
         return optionalProduct.orElse(null);
    }
    // сохранение товара
    @Transactional
    public void saveProduct(Product product){
        productRepository.save(product);
    }
    // обновление товара
    @Transactional
    public void updateProduct(int id, Product product){
        product.setId(id);
        productRepository.save(product);
    }
    // удаление товара
    @Transactional
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }
}
