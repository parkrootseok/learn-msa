package com.example.catalogservice.domain.repository;

import com.example.catalogservice.domain.model.entity.Catalog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, Long> {

    Catalog findByProductId(String productId);

}


