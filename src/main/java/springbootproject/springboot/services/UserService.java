package springbootproject.springboot.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import springbootproject.springboot.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserByEmail(String email);

    List<User> getAllUsers();
    User getUser(int id);
    User addUser(User user);
    User saveUser(User user);
}
