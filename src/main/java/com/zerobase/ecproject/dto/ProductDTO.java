package com.zerobase.ecproject.dto;

import lombok.Data;

@Data
public class ProductDTO {

  private Long id;
  private String name;
  private String description;
  private Integer price;
  private String category;
  private Integer stockQuantity;
  private Long storeId;
}
