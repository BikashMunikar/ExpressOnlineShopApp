package com.bikash.orderservice.service;

import com.bikash.orderservice.dto.OrderRequest;
import com.bikash.orderservice.dto.OrderResponse;
import com.bikash.orderservice.event.OrderPlacedEvent;
import com.bikash.orderservice.model.Order;
import com.bikash.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private WebClient.Builder webClient;

    private final Tracer tracer;

    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String createOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .name(orderRequest.getName())
                .status("Order Placed")
                .build();
        /* System needs to check if the order is available in the inventory*/
        List<String> orderNames = new ArrayList<>();
        orderNames.add(order.getName());

        Span inventoryServiceLookUp = tracer.nextSpan().name("InventoryServiceLookUp");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookUp.start()))
        {
            Boolean isPresent = webClient.build().get()
                    .uri("http://inventory-service/api/inventory/" + orderRequest.getName())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (isPresent)
            {
                repo.save(order);
                log.info("Order has been saved");
                kafkaTemplate.send("notificationTopic.v1", new OrderPlacedEvent(order.getName()));
                return "Order has been placed successfully. Thank you for Ordering!";
            }else
                throw new IllegalArgumentException("Order is not present");
        }finally {
            inventoryServiceLookUp.end();
        }


    }

    public List<OrderResponse> getAllOrdersByStatus(String status){
        log.info("Fetching all the placed Orders!");
        return repo.findAllByStatus(status)
                .stream()
                .map(order -> mapOrderToOrderResponse(order))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders(){
        log.info("Fetching all the Orders!");
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
