package springbootproject.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootproject.springboot.entities.Comment;
import springbootproject.springboot.entities.ShopItem;
import springbootproject.springboot.entities.User;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByShopItem(ShopItem shopItem);
    List<Comment> findAllByAuthor(User author);
}
