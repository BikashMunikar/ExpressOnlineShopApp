package com.bikash.orderservice.controller;

import com.bikash.orderservice.dto.OrderRequest;
import com.bikash.orderservice.dto.OrderResponse;
import com.bikash.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<String> saveOrder(@RequestBody OrderRequest orderRequest){
        return CompletableFuture.supplyAsync(()->orderService.createOrder(orderRequest));
    }

    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Something went wrong!");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByStatus(){
         return orderService.getAllOrders();
    }
}
