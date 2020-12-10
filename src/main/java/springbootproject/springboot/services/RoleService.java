package springbootproject.springboot.services;

import springbootproject.springboot.entities.Role;

import java.util.List;

public interface RoleService {

    Role getRoleByName(String name);

    List<Role> getAllRoles();
    Role getRole(int id);
    Role addRole(Role role);
    Role saveRole(Role role);
}
