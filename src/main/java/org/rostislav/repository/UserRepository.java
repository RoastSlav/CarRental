package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.User;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users WHERE id = #{id}")
    User getUserById(int id);

    @Select("SELECT * FROM users")
    Collection<User> getAllUsers();

    @Insert("INSERT INTO users (full_name, email, password, phone, license_number, role_id) VALUES (#{fullName}, #{email}, #{password}, #{phone}, #{licenseNumber}, #{roleId})")
    void addUser(User user);

    @Update("UPDATE users SET full_name=#{fullName}, email=#{email}, password=#{password}, phone=#{phone}, license_number=#{licenseNumber}, role_id=#{roleId} WHERE id=#{id}")
    void updateUser(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(int id);
}