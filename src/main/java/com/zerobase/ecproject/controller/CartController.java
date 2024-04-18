package com.zerobase.ecproject.controller;

import com.zerobase.ecproject.dto.CartItemDTO;
import com.zerobase.ecproject.entity.Cart;
import com.zerobase.ecproject.entity.CartItem;
import com.zerobase.ecproject.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

  @Autowired
  private CartService cartService;

  @PostMapping("/{memberId}")
  public ResponseEntity<Cart> createOrGetCart(@PathVariable Long memberId) {
    Cart cart = cartService.getOrCreateCart(memberId);
    return ResponseEntity.ok(cart);
  }

  @PostMapping("/add/{memberId}")
  public ResponseEntity<CartItem> addCartItem(@PathVariable Long memberId, @RequestBody CartItemDTO cartItemDto) {
    CartItem cartItem = cartService.addProductToCart(memberId, cartItemDto.getProductId(), cartItemDto.getQuantity());
    return ResponseEntity.ok(cartItem);
  }

  @DeleteMapping("/remove/{itemId}")
  public ResponseEntity<Void> removeCartItem(@PathVariable Long itemId) {
    cartService.removeProductFromCart(itemId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/items/{memberId}")
  public ResponseEntity<List<CartItem>> listCartItems(@PathVariable Long memberId) {
    List<CartItem> items = cartService.listCartItems(memberId);
    return ResponseEntity.ok(items);
  }
}
