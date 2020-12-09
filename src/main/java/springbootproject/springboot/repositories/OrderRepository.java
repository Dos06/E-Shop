package springbootproject.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootproject.springboot.entities.Order;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
