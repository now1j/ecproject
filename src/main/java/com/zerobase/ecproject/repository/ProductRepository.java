package com.zerobase.ecproject.repository;

import com.zerobase.ecproject.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :name, '%'))")
  List<Product> findByNameContainingIgnoreCase(String name);
}
