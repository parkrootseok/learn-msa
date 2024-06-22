package com.example.catalogservice.domain.service;

import com.example.catalogservice.domain.model.entity.Catalog;

public interface CatalogService {

    Iterable<Catalog>  getAllCatalogs();

}
