package springbootproject.springboot.services;

import springbootproject.springboot.entities.Order;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order);
    List<Order> getAllOrders();
    Order getOrder(int id);
    void deleteOrder(Order order);
    Order saveOrder(Order order);

}
