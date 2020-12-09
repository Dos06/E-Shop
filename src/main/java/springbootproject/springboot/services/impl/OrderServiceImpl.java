package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Order;
import springbootproject.springboot.repositories.OrderRepository;
import springbootproject.springboot.services.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(int id) {
        return orderRepository.getOne(id);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
