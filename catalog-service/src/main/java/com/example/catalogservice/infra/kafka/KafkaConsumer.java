package com.example.catalogservice.infra.kafka;

import com.example.catalogservice.domain.model.entity.CatalogEntity;
import com.example.catalogservice.domain.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @KafkaListener(topics = "catalog-topic")
    public void updateQuantity(String message) {
        // catalog topic message 출력
        log.info("kafka message: ->", message);

        // catalog topic message를 읽어 map에 저장
        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            map = mapper.readValue(message, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // map에 저장된 값들을 반영
        CatalogEntity catalog = catalogRepository.findByProductId((String) map.get("productId"));
        if (!Objects.isNull(catalog)) {
            catalog.setStock(catalog.getStock() - (Integer) map.get("quantity"));
            catalogRepository.save(catalog);
        }

    }


}
