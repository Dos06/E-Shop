package springbootproject.springboot.services;

import springbootproject.springboot.entities.Order;
import springbootproject.springboot.entities.ShopitemOrder;

import java.util.List;

public interface ShopitemOrderService {
    ShopitemOrder addShopitemOrder(ShopitemOrder shopitemOrder);
    List<ShopitemOrder> getAllShopitemOrders();
    ShopitemOrder getShopitemOrder(int id);
    void deleteShopitemOrder(ShopitemOrder shopitemOrder);
    ShopitemOrder saveShopitemOrder(ShopitemOrder shopitemOrder);

    List<ShopitemOrder> getShopitemOrdersByOrder(Order order);
}
