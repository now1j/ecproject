package com.zerobase.ecproject.service;

import com.zerobase.ecproject.entity.Cart;
import com.zerobase.ecproject.entity.CartItem;
import com.zerobase.ecproject.entity.Member;
import com.zerobase.ecproject.entity.Product;
import com.zerobase.ecproject.repository.CartItemRepository;
import com.zerobase.ecproject.repository.CartRepository;
import com.zerobase.ecproject.repository.MemberRepository;
import com.zerobase.ecproject.security.MemberRole;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MemberService memberService;

  private void validateCustomerRole(Member member) {
    if (member.getRole() != MemberRole.CUSTOMER) {
      throw new RuntimeException("장바구니 추가는 구매자만 가능합니다.");
    }
  }

  public Cart getOrCreateCart(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new RuntimeException("해당하는 회원을 찾을 수 없습니다."));

    return cartRepository.findByMember(member)
        .orElseGet(() -> {
          Cart newCart = new Cart();
          newCart.setMember(member);
          return cartRepository.save(newCart);
        });
  }

  public CartItem addProductToCart(Long memberId, Long productId, int quantity) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new RuntimeException("해당하는 회원을 찾을 수 없습니다."));
    validateCustomerRole(member);

    Cart cart = getOrCreateCart(memberId);
    return cartItemRepository.findByCartAndProductId(cart, productId)
        .map(item -> {
          item.setQuantity(item.getQuantity() + quantity);
          return cartItemRepository.save(item);
        }).orElseGet(() -> {
          CartItem newItem = new CartItem();
          newItem.setCart(cart);
          newItem.setProduct(new Product(productId));
          newItem.setQuantity(quantity);
          return cartItemRepository.save(newItem);
        });
  }

  public void removeProductFromCart(Long cartItemId) {
    cartItemRepository.deleteById(cartItemId);
  }

  public List<CartItem> listCartItems(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new RuntimeException("해당하는 회원을 찾을 수 없습니다."));
    return cartItemRepository.findAllByMember(member);
  }
}
