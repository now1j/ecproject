package com.zerobase.ecproject.repository;

import com.zerobase.ecproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
