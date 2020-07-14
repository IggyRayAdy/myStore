package com.example.myStore.Repository;

import com.example.myStore.Domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProdRepository extends CrudRepository<Product, Long> {

//    Iterable<Product> findAllByLabel(String string);

    List<Product> findAllByLabel(String string);
}
