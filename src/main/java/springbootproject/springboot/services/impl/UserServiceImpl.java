package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.User;
import springbootproject.springboot.repositories.RoleRepository;
import springbootproject.springboot.repositories.UserRepository;
import springbootproject.springboot.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User myUser = userRepository.findByEmail(s);
        if (myUser != null) {
            org.springframework.security.core.userdetails.User secUser = new org.springframework.security.core.userdetails.User(
                    myUser.getEmail(), myUser.getPassword(), myUser.getRoles()
            );
            return secUser;
        }
        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        String password = user.getPassword();
        if (password.length() < 25) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }
}
