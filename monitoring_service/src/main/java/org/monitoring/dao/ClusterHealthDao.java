package org.monitoring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.monitoring.models.ClusterHealthStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClusterHealthDao {
    private final JdbcTemplate jdbcTemplate;

    public ClusterHealthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ClusterHealthStatus fetchClusterHealthStatus() {
        String sql = "SELECT status, active_nodes, total_nodes FROM cluster_health";
        ClusterHealthStatus clusterHealthStatus = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                clusterHealthStatus = new ClusterHealthStatus();
                clusterHealthStatus.setStatus(resultSet.getString("status"));
                clusterHealthStatus.setActiveNodes(resultSet.getInt("active_nodes"));
                clusterHealthStatus.setTotalNodes(resultSet.getInt("total_nodes"));
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return clusterHealthStatus;
    }
}