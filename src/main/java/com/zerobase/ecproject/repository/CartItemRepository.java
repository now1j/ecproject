package com.zerobase.ecproject.repository;

import com.zerobase.ecproject.entity.Cart;
import com.zerobase.ecproject.entity.CartItem;
import com.zerobase.ecproject.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
  Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);
  List<CartItem> findAllByMember(Member member);

}
