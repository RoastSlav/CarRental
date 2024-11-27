package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.Role;

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