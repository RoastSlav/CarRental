package org.rostislav.repository;

import org.apache.ibatis.annotations.*;
import org.rostislav.models.Role;

import java.util.Collection;

@Mapper
public interface RoleRepository {
    @Select("SELECT * FROM roles WHERE id = #{id}")
    Role getRoleById(int id);

    @Select("SELECT * FROM roles")
    Collection<Role> getAllRoles();

    @Insert("INSERT INTO roles (role_name) VALUES (#{roleName})")
    void addRole(Role role);

    @Update("UPDATE roles SET role_name=#{roleName} WHERE id=#{id}")
    void updateRole(Role role);

    @Delete("DELETE FROM roles WHERE id = #{id}")
    void deleteRole(int id);
}