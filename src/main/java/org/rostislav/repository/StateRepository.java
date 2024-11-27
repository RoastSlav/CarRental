package org.rostislav.repository;

import java.util.Collection;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.rostislav.models.State;

@Mapper
public interface StateRepository {
    @Select("SELECT * FROM states WHERE id = #{id}")
    State getStateById(int id);

    @Select("SELECT * FROM states")
    Collection<State> getAllStates();

    @Insert("INSERT INTO states (state_name) VALUES (#{stateName})")
    void addState(State state);

    @Update("UPDATE states SET state_name=#{stateName} WHERE id=#{id}")
    void updateState(State state);

    @Delete("DELETE FROM states WHERE id = #{id}")
    void deleteState(int id);
}
