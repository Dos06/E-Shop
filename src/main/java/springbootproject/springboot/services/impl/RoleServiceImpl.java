package springbootproject.springboot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootproject.springboot.entities.Role;
import springbootproject.springboot.entities.User;
import springbootproject.springboot.repositories.RoleRepository;
import springbootproject.springboot.services.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRole(int id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
