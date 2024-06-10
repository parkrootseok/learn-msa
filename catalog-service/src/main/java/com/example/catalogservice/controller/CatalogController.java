package com.example.catalogservice.controller;

import com.example.catalogservice.model.entity.Catalog;
import com.example.catalogservice.model.response.GetCatalogResponse;
import com.example.catalogservice.service.CatalogService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health-check")
    public String status() {

        return String.format("It's working in catalog service on PORT %s", env.getProperty("local.server.port"));

    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<GetCatalogResponse>> getCatalogs() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Iterable<Catalog> catalogs = catalogService.getAllCatalogs();

        List<GetCatalogResponse> response = new ArrayList<>();
        catalogs.forEach(
                catalog -> response.add(mapper.map(catalog, GetCatalogResponse.class))
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

}
