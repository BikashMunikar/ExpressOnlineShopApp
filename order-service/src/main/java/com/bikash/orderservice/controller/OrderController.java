package com.bikash.orderservice.controller;

import com.bikash.orderservice.dto.OrderRequest;
import com.bikash.orderservice.dto.OrderResponse;
import com.bikash.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOrder(@RequestBody OrderRequest orderRequest){
        orderService.createOrder(orderRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByStatus(){
         return orderService.getAllOrders();
    }
}
