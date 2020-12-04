package springbootproject.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootproject.springboot.entities.Picture;
import springbootproject.springboot.entities.ShopItem;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    List<Picture> findAllByShopItem(ShopItem shopItem);
}
