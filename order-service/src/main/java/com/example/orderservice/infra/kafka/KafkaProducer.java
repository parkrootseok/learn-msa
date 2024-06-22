package com.example.orderservice.infra.kafka;

import com.example.orderservice.domain.model.dto.OrderDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto send (String topic, OrderDto orderDto) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        /* 전송할 Entity에 대한 Dto를 JSON 형태로 직렬화 */
        try {
            jsonString = mapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /* 명시한 topic으로 전달 */
        kafkaTemplate.send(topic, jsonString);
        log.info("Kafka Producer sent data from order Microservice: {}", orderDto);

        return orderDto;

    }

}