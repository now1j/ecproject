package com.zerobase.ecproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired
  private ProductService productService;

  public void processOrder(Long productId, int quantity) {
    productService.decreaseStock(productId, quantity);
  }
}
