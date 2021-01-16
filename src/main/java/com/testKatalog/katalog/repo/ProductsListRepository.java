package com.testKatalog.katalog.repo;

import com.testKatalog.katalog.model.ProductsList;
import org.springframework.data.repository.CrudRepository;

public interface ProductsListRepository extends CrudRepository<ProductsList, Long> {
}
