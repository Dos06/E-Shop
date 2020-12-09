package springbootproject.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootproject.springboot.entities.Order;
import springbootproject.springboot.entities.ShopitemOrder;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ShopitemOrderRepository extends JpaRepository<ShopitemOrder, Integer> {
    List<ShopitemOrder> findAllByOrder(Order order);
}
