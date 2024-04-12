package com.zerobase.ecproject.service;

import com.zerobase.ecproject.entity.Store;
import com.zerobase.ecproject.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreService {

  @Autowired
  private StoreRepository storeRepository;

  public Store createStore(Store store) {
    return storeRepository.save(store);
  }

  public Store updateStore(Long id, Store updatedStore) {
    return storeRepository.findById(id)
        .map(store -> {
          store.setName(updatedStore.getName());
          store.setDescription(updatedStore.getDescription());
          return storeRepository.save(store);
        }).orElseThrow(() -> new RuntimeException("스토어를 찾을 수 없습니다!"));
  }

  public void deleteStore(Long id) {
    storeRepository.deleteById(id);
  }
}
