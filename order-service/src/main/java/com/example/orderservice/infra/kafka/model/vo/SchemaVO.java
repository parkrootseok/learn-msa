package com.example.orderservice.infra.kafka.model.vo;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SchemaVO {

    private String type;
    private List<FieldVO> fields;
    private boolean optional;
    private String name;

    @Builder
    public SchemaVO(String type, List<FieldVO> fields, boolean optional, String name) {
        this.type = type;
        this.fields = fields;
        this.optional = optional;
        this.name = name;
    }

}
