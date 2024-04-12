package com.zerobase.ecproject.controller;

import com.zerobase.ecproject.entity.Store;
import com.zerobase.ecproject.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stores")
public class StoreController {

  @Autowired
  private StoreService storeService;

  @PostMapping
  public ResponseEntity<Store> createStore(@RequestBody Store store) {
    Store createdStore = storeService.createStore(store);
    return ResponseEntity.ok(createdStore);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
    Store updatedStore = storeService.updateStore(id, store);
    return ResponseEntity.ok(updatedStore);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteStore(@PathVariable Long id) {
    storeService.deleteStore(id);
    return ResponseEntity.ok().build();
  }
}
