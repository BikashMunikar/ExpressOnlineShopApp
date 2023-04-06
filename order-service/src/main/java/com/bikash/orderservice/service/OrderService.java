package com.bikash.orderservice.service;

import com.bikash.orderservice.dto.OrderRequest;
import com.bikash.orderservice.dto.OrderResponse;
import com.bikash.orderservice.model.Order;
import com.bikash.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository repo;

    public void createOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .name(orderRequest.getName())
                .status("Order Placed")
                .build();

        repo.save(order);
        log.info("Order Number {} has been placed successfully. Thank you for Ordering {}", order.getId(), order.getName());
    }

    public List<OrderResponse> getAllOrdersByStatus(String status){
        log.info("Fetching all the placed Orders!");
        return repo.findAllByStatus(status)
                .stream()
                .map(order -> mapOrderToOrderResponse(order))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders(){
        log.info("Fetching all the placed Orders!");
        return repo.findAll()
                .stream()
                .map(order -> mapOrderToOrderResponse(order))
                .collect(Collectors.toList());
    }

    public OrderResponse mapOrderToOrderResponse(Order order) {
        return OrderResponse.builder()
                .name(order.getName())
                .status(order.getStatus())
                .build();
    }
}
