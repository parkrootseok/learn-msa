package com.example.orderservice.infra.kafka.model.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldVO {

    private String type;
    private boolean optional;
    private String field;

    @Builder
    public FieldVO(String type, boolean optional, String field) {
        this.type = type;
        this.optional = optional;
        this.field = field;
    }

}

