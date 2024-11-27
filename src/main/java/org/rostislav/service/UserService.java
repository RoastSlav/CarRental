package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.User;
import org.rostislav.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public void addUser(String fullName, String email, String password, String phone, String licenseNumber, int roleId) {
        User user = new User(fullName, email, password, phone, licenseNumber, roleId);
        userRepository.addUser(user);
    }

    public void updateUser(int id, String fullName, String email, String phone, String licenseNumber, int roleId) {
        User user = new User(fullName, email, null, phone, licenseNumber, roleId);
        user.setId(id);
        userRepository.updateUser(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }
}