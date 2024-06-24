package com.example.orderservice.infra.kafka.model.dto;

import com.example.orderservice.infra.kafka.model.vo.PayloadVO;
import com.example.orderservice.infra.kafka.model.vo.SchemaVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaOrderDto {

    private SchemaVO schema;
    private PayloadVO payload;

    @Builder
    public KafkaOrderDto(SchemaVO schema, PayloadVO payload) {
        this.schema = schema;
        this.payload = payload;
    }

}
