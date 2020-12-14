package springbootproject.springboot.services;

import springbootproject.springboot.entities.Comment;
import springbootproject.springboot.entities.ShopItem;
import springbootproject.springboot.entities.User;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment);
    List<Comment> getAllComments();
    Comment getComment(int id);
    void deleteComment(Comment comment);
    Comment saveComment(Comment comment);

    List<Comment> getAllByShopItem(ShopItem shopItem);
    List<Comment> getAllByAuthor(User author);
}
