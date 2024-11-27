package org.rostislav.service;

import java.util.Collection;

import org.rostislav.models.Role;
import org.rostislav.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }

    public String getRoleNameById(int id) {
        Role role = roleRepository.getRoleById(id);
        return role != null ? role.getRoleName() : "Unknown";
    }

    public void addRole(String roleName) {
        Role role = new Role(roleName);
        roleRepository.addRole(role);
    }

    public void updateRole(int id, String roleName) {
        Role role = new Role(roleName);
        role.setId(id);
        roleRepository.updateRole(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteRole(id);
    }
}