package org.manager.dao;

import org.manager.models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class NotificationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Notification create(Notification notification) {
        try {
            String sql = "INSERT INTO notifications (title, message, user_id, created_at) VALUES (?, ?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(sql, notification.getTitle(), notification.getMessage(), notification.getUserId(), notification.getCreatedAt());
            if (rowsAffected > 0) {
                return notification;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation of the notification
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Long id) {
        try {
            String sql = "DELETE FROM notifications WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion of the notification
            e.printStackTrace();
        }
    }

    public Notification update(Notification existingNotification) {
        try {
            String sql = "UPDATE notifications SET title = ?, message = ?, user_id = ?, created_at = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, existingNotification.getTitle(), existingNotification.getMessage(), existingNotification.getUserId(), existingNotification.getCreatedAt(), existingNotification.getId());
            if (rowsAffected > 0) {
                return existingNotification;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the notification
            e.printStackTrace();
            return null;
        }
    }

    public Notification findById(Long id) {
        try {
            String sql = "SELECT * FROM notifications WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new NotificationRowMapper());
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of the notification
            e.printStackTrace();
            return null;
        }
    }

    private static class NotificationRowMapper implements RowMapper<Notification> {
        @Override
        public Notification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Notification notification = new Notification();
            notification.setId(rs.getLong("id"));
            notification.setTitle(rs.getString("title"));
            notification.setMessage(rs.getString("message"));
            notification.setUserId(rs.getLong("user_id"));
            notification.setCreatedAt(rs.getTimestamp("created_at"));
            return notification;
        }
    }
}