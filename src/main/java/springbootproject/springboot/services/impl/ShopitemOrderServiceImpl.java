package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Order;
import springbootproject.springboot.entities.ShopitemOrder;
import springbootproject.springboot.repositories.ShopitemOrderRepository;
import springbootproject.springboot.services.ShopitemOrderService;

import java.util.List;

@Service
public class ShopitemOrderServiceImpl implements ShopitemOrderService {
    @Autowired
    private ShopitemOrderRepository shopitemOrderRepository;

    @Override
    public ShopitemOrder addShopitemOrder(ShopitemOrder shopitemOrder) {
        return shopitemOrderRepository.save(shopitemOrder);
    }

    @Override
    public List<ShopitemOrder> getAllShopitemOrders() {
        return shopitemOrderRepository.findAll();
    }

    @Override
    public ShopitemOrder getShopitemOrder(int id) {
        return shopitemOrderRepository.getOne(id);
    }

    @Override
    public void deleteShopitemOrder(ShopitemOrder shopitemOrder) {
        shopitemOrderRepository.delete(shopitemOrder);
    }

    @Override
    public ShopitemOrder saveShopitemOrder(ShopitemOrder shopitemOrder) {
        return shopitemOrderRepository.save(shopitemOrder);
    }

    @Override
    public List<ShopitemOrder> getShopitemOrdersByOrder(Order order) {
        return shopitemOrderRepository.findAllByOrder(order);
    }
}
