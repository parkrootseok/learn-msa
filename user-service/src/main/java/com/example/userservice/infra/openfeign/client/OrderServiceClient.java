package com.example.userservice.infra.openfeign.client;

import com.example.userservice.domain.model.response.GetOrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders-ng")
    List<GetOrderResponse> getOrders(@PathVariable("userId") String userId);

}
