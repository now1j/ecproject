package com.zerobase.ecproject.repository;

import com.zerobase.ecproject.entity.Cart;
import com.zerobase.ecproject.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByMember(Member member);
}
