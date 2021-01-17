package com.testKatalog.katalog.repo;

import com.testKatalog.katalog.model.ProductsList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsListRepository extends CrudRepository<ProductsList, Long> {
    List<ProductsList> findByName(String name);
}
