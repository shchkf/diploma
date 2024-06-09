package org.manager.dao;

import org.manager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<User> findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
            return Optional.of(user);
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of the user
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public User save(User existingUser) {
        try {
            String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, existingUser.getName(), existingUser.getEmail(), existingUser.getPassword(), existingUser.getId());
            if (rowsAffected > 0) {
                return existingUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the user
            e.printStackTrace();
            return null;
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}