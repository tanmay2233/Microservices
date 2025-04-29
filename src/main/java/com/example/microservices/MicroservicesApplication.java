package com.example.microservices;

import com.example.microservices.services.order.OrderEvent;
import com.example.microservices.services.order.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(OrderService orderService) {
        return args -> {
            OrderEvent orderEvent = new OrderEvent("demo-order-1", "PENDING", "Sample order data");
            System.out.println("Initiating demo order placement for orderId: " + orderEvent.getOrderId());

            orderService.placeOrder(orderEvent);
        };
    }
}