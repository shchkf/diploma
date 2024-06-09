package org.monitoring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.monitoring.models.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QueryDao {
    private final JdbcTemplate jdbcTemplate;

    public QueryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Query> getAllQueries() {
        String sql = "SELECT * FROM queries";
        List<Query> queries = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Query query = mapRowToQuery(resultSet);
                queries.add(query);
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return queries;
    }

    public Query getQueryById(Long id) {
        String sql = "SELECT * FROM queries WHERE id = ?";
        Query query = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    query = mapRowToQuery(resultSet);
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return query;
    }

    public void deleteQuery(Long id) {
        String sql = "DELETE FROM queries WHERE id = ?";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    private Query mapRowToQuery(ResultSet resultSet) throws SQLException {
        Query query = new Query();
        query.setId(resultSet.getLong("id"));
        query.setName(resultSet.getString("name"));
        query.setDescription(resultSet.getString("description"));
        query.setSql(resultSet.getString("sql"));
        return query;
    }
}