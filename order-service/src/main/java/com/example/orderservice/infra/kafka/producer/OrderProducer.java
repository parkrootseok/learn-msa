package com.example.orderservice.infra.kafka.producer;

import com.example.orderservice.infra.kafka.model.vo.FieldVO;
import com.example.orderservice.infra.kafka.model.dto.KafkaOrderDto;
import com.example.orderservice.domain.model.dto.OrderDto;
import com.example.orderservice.infra.kafka.model.vo.PayloadVO;
import com.example.orderservice.infra.kafka.model.vo.SchemaVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderProducer {

    public static final List<FieldVO> FIELDS = List.of(
            FieldVO.builder().type("string").optional(true).field("order_id").build(),
            FieldVO.builder().type("string").optional(true).field("user_id").build(),
            FieldVO.builder().type("string").optional(true).field("product_id").build(),
            FieldVO.builder().type("int32").optional(true).field("quantity").build(),
            FieldVO.builder().type("int32").optional(true).field("price").build(),
            FieldVO.builder().type("int32").optional(true).field("total_price").build()
    );

    public static final SchemaVO SCHEMA = SchemaVO.builder()
            .type("struct")
            .fields(FIELDS)
            .optional(false)
            .name("orders")
            .build();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderDto sendCreateOrderMessage(String topic, OrderDto orderDto) {

        PayloadVO payloadVO = PayloadVO.builder()
                .orderId(orderDto.getOrderId())
                .userId(orderDto.getUserId())
                .productId(orderDto.getProductId())
                .quantity(orderDto.getQuantity())
                .price(orderDto.getPrice())
                .totalPrice(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = KafkaOrderDto.builder()
                .schema(SCHEMA)
                .payload(payloadVO)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        /* 전송할 Entity에 대한 Dto를 JSON 형태로 직렬화 */
        try {
            jsonString = mapper.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        /* 명시한 topic으로 전달 */
        kafkaTemplate.send(topic, jsonString);
        log.info("Kafka Producer sent data from order Microservice: {}", kafkaOrderDto);

        return orderDto;

    }

}