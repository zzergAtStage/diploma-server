package com.zergatstageg.s02cruddemo.repository;

import com.zergatstageg.s02cruddemo.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<User> findAll(){
        String sql = "SELECT * FROM users";


        return jdbc.query(sql, new UserRowMapper());
    }
    public User findById(int id){
        String sql = "SELECT * FROM users where id=?";
        return jdbc.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),id);
    }


    public User save(User user){
        String sql = "INSERT INTO users (firstName, lastName) VALUES (?,?)";
        jdbc.update(sql,user.getFirstName(), user.getLastName());
        return user;
    }

    public User update(User user){
        String sql = "UPDATE users SET firstName=?, lastName=? WHERE id=?";
        jdbc.update(sql,user.getFirstName(), user.getLastName(), user.getId());
        return user;
    }

    public void deleteById(int id){
        String sql = "DELETE FROM users WHERE id=?";
        jdbc.update(sql,id);
    }


    static class UserRowMapper implements RowMapper<User>{
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User rowObject = User.createUser();
            rowObject.setId(rs.getInt("id"));
            rowObject.setFirstName(rs.getString("firstName"));
            rowObject.setLastName(rs.getString("lastName"));
            return rowObject;
        }
    }


}
