package org.manager.dao;

import org.manager.models.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RulesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Rule create(Rule rule) {
        try {
            String sql = "INSERT INTO rules (name, description, priority) VALUES (?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(sql, rule.getName(), rule.getDescription(), rule.getPriority());
            if (rowsAffected > 0) {
                return rule;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation of the rule
            e.printStackTrace();
            return null;
        }
    }

    public List<Rule> findAll(Specification<Rule> specification) {
        try {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM rules");
            if (specification != null) {
                sqlBuilder.append(" WHERE ").append(specification.toSqlClause());
            }
            return jdbcTemplate.query(sqlBuilder.toString(), new Object[]{}, new RuleRowMapper());
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of rules
            e.printStackTrace();
            return null;
        }
    }

    public Rule findById(Long id) {
        try {
            String sql = "SELECT * FROM rules WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RuleRowMapper());
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of the rule
            e.printStackTrace();
            return null;
        }
    }

    public Rule update(Rule existingRule) {
        try {
            String sql = "UPDATE rules SET name = ?, description = ?, priority = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, existingRule.getName(), existingRule.getDescription(), existingRule.getPriority(), existingRule.getId());
            if (rowsAffected > 0) {
                return existingRule;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the rule
            e.printStackTrace();
            return null;
        }
    }

    private static class RuleRowMapper implements RowMapper<Rule> {
        @Override
        public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rule rule = new Rule();
            rule.setId(rs.getLong("id"));
            rule.setName(rs.getString("name"));
            rule.setDescription(rs.getString("description"));
            rule.setPriority(rs.getInt("priority"));
            return rule;
        }
    }
}