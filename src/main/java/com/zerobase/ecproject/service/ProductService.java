package com.zerobase.ecproject.service;

import com.zerobase.ecproject.entity.Product;
import com.zerobase.ecproject.repository.ProductRepository;
import com.zerobase.ecproject.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private StoreRepository storeRepository;

  public Product createProduct(Product product, Long storeId) {
    return storeRepository.findById(storeId).map(store -> {
      product.setStore(store);
      return productRepository.save(product);
    }).orElseThrow(() -> new RuntimeException("Store not found with id " + storeId));
  }

  public Product updateProduct(Long productId, Product updatedProduct) {
    return productRepository.findById(productId).map(product -> {
      product.setName(updatedProduct.getName());
      product.setDescription(updatedProduct.getDescription());
      product.setPrice(updatedProduct.getPrice());
      product.setCategory(updatedProduct.getCategory());
      product.setStockQuantity(updatedProduct.getStockQuantity());
      return productRepository.save(product);
    }).orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
  }


  public void deleteProduct(Long productId) {
    productRepository.deleteById(productId);
  }

  @Transactional
  public void updateStock(Long productId, int quantity) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found!"));
    product.setStockQuantity(product.getStockQuantity() + quantity);
    productRepository.save(product);
  }

  @Transactional
  public void decreaseStock(Long productId, int quantity) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("Product not found!"));
    if (product.getStockQuantity() < quantity) {
      throw new RuntimeException("Insufficient stock!");
    }
    product.setStockQuantity(product.getStockQuantity() - quantity);
    productRepository.save(product);
  }
}
