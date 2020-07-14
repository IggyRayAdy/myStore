package com.example.myStore.Repository;

import com.example.myStore.Domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
}
