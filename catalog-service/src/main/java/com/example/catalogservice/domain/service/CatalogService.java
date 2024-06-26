package com.example.catalogservice.domain.service;

import com.example.catalogservice.domain.model.entity.CatalogEntity;

public interface CatalogService {

    Iterable<CatalogEntity> getAllCatalogs();

}
