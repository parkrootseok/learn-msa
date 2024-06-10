package com.example.catalogservice.service;

import com.example.catalogservice.model.entity.Catalog;

public interface CatalogService {

    Iterable<Catalog>  getAllCatalogs();

}
