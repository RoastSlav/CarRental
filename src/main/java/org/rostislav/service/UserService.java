package org.rostislav.service;

import org.rostislav.models.Role;
import org.rostislav.models.User;
import org.rostislav.repository.RoleRepository;
import org.rostislav.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public void addUser(String username, String password, String phone, String licenseNumber, int roleId) {
        userRepository.addUser(new User(username, password, phone, licenseNumber, roleId));
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void removeUser(int id) {
        userRepository.deleteUser(id);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }


    public Role getRoleById(int id) {
        return roleRepository.getRoleById(id);
    }

    public Collection<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    public void addRole(String roleName) {
        roleRepository.addRole(new Role(roleName));
    }

    public void removeRole(int id) {
        roleRepository.deleteRole(id);
    }

    public void updateRole(Role role) {
        roleRepository.updateRole(role);
    }

    public void addRole(Role role) {
        roleRepository.addRole(role);
    }
}
