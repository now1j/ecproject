package com.zerobase.ecproject.controller;

import com.zerobase.ecproject.dto.ProductDTO;
import com.zerobase.ecproject.entity.Product;
import com.zerobase.ecproject.service.OrderService;
import com.zerobase.ecproject.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService productService;
  @Autowired
  private OrderService orderService;

  @PostMapping("/{storeId}")
  public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO,
      @PathVariable Long storeId) {
    Product product = convertToEntity(productDTO);
    Product createdProduct = productService.createProduct(product, storeId);
    return ResponseEntity.ok(convertToDTO(createdProduct));
  }

  private Product convertToEntity(ProductDTO dto) {
    Product product = new Product();
    product.setName(dto.getName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setStockQuantity(dto.getStockQuantity());
    return product;
  }

  private ProductDTO convertToDTO(Product product) {
    ProductDTO dto = new ProductDTO();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setDescription(product.getDescription());
    dto.setPrice(product.getPrice());
    dto.setCategory(product.getCategory());
    dto.setStockQuantity(product.getStockQuantity());
    dto.setStoreId(product.getStore() != null ? product.getStore().getId() : null);
    return dto;
  }


  @PutMapping("/{productId}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
      @RequestBody Product product) {
    Product updatedProduct = productService.updateProduct(productId, product);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{productId}/stock")
  public ResponseEntity<?> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
    try {
      productService.updateStock(productId, quantity);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("재고 업데이트 중 에러 발생: " + e.getMessage());
    }
  }

  // 주문 처리
  @PostMapping("/{productId}/order")
  public ResponseEntity<?> processOrder(@PathVariable Long productId, @RequestParam int quantity) {
    try {
      orderService.processOrder(productId, quantity);
      return ResponseEntity.ok().build();
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body("주문 처리 실패: " + e.getMessage());
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
    List<Product> products = productService.searchProductsByName(name);
    return ResponseEntity.ok(products);
  }

}