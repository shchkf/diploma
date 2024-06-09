package org.schemas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.schemas.models.Schema;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SchemasDao {
    private final JdbcTemplate jdbcTemplate;

    public SchemasDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Schema> getAllSchemas() {
        String sql = "SELECT id, name, description FROM schemas";
        List<Schema> schemas = new ArrayList<>();

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Schema schema = new Schema();
                schema.setId(resultSet.getLong("id"));
                schema.setName(resultSet.getString("name"));
                schema.setDescription(resultSet.getString("description"));
                schemas.add(schema);
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return schemas;
    }

    public Schema getSchemaById(Long id) {
        String sql = "SELECT id, name, description FROM schemas WHERE id = ?";
        Schema schema = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    schema = new Schema();
                    schema.setId(resultSet.getLong("id"));
                    schema.setName(resultSet.getString("name"));
                    schema.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return schema;
    }

    public Schema createSchema(Schema schema) {
        String sql = "INSERT INTO schemas (name, description) VALUES (?, ?)";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, schema.getName());
            statement.setString(2, schema.getDescription());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    schema.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }

        return schema;
    }

    public void deleteSchema(Long id) {
        String sql = "DELETE FROM schemas WHERE id = ?";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}