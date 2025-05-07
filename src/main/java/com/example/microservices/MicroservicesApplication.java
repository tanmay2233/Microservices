package com.example.microservices;

import com.example.microservices.services.order.OrderEvent;
import com.example.microservices.services.order.OrderItem;
import com.example.microservices.services.order.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// (other imports)

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class MicroservicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(OrderService orderService) {
        return args -> {
            List<OrderItem> items = Arrays.asList(
                    new OrderItem("item1", "Laptop", 2, 100),
                    new OrderItem("item2", "Mouse", 5, 200)
            );

            OrderEvent orderEvent = new OrderEvent("1", "PENDING", "Sample order data", items, UUID.randomUUID().toString());
            System.out.println("Initiating demo order placement for orderId: " + orderEvent.getOrderId());

            orderService.placeOrder(orderEvent);
        };
    }
}
