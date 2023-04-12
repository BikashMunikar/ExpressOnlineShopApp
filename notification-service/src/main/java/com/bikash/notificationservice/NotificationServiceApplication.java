package com.bikash.notificationservice;

import com.bikash.notificationservice.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic.v1", autoStartup = "true")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        //send an email
        log.info("Notification received for the Order Name {} ", orderPlacedEvent.getOrderName());
    }
}