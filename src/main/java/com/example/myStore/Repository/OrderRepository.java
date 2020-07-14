package com.example.myStore.Repository;

import com.example.myStore.Domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
