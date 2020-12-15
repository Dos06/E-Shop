package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Comment;
import springbootproject.springboot.entities.ShopItem;
import springbootproject.springboot.entities.User;
import springbootproject.springboot.repositories.CommentRepository;
import springbootproject.springboot.services.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getComment(int id) {
        return commentRepository.getOne(id);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllByShopItem(ShopItem shopItem) {
        return commentRepository.findAllByShopItemOrderByAddedDateDesc(shopItem);
    }

    @Override
    public List<Comment> getAllByAuthor(User author) {
        return commentRepository.findAllByAuthorOrderByAddedDate(author);
    }
}
