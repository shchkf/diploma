package org.manager.dao;

import org.manager.models.Profile;
import org.manager.models.Resgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ResgroupDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Resgroup create(Resgroup resGroup) {
        try {
            String sql = "INSERT INTO resgroups (name, description) VALUES (?, ?)";
            int rowsAffected = jdbcTemplate.update(sql, resGroup.getName(), resGroup.getDescription());
            if (rowsAffected > 0) {
                return resGroup;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation of the resource group
            e.printStackTrace();
            return null;
        }
    }

    public Resgroup findById(Long id) {
        try {
            String sql = "SELECT * FROM resgroups WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ResgroupRowMapper());
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of the resource group
            e.printStackTrace();
            return null;
        }
    }

    public Resgroup update(Resgroup existingResGroup) {
        try {
            String sql = "UPDATE resgroups SET name = ?, description = ? WHERE id = ?";
            int rowsAffected = jdbcTemplate.update(sql, existingResGroup.getName(), existingResGroup.getDescription(), existingResGroup.getId());
            if (rowsAffected > 0) {
                return existingResGroup;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the resource group
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Long id) {
        try {
            String sql = "DELETE FROM resgroups WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion of the resource group
            e.printStackTrace();
        }
    }

    public Profile createProfile(Profile profile) {
        try {
            String sql = "INSERT INTO profiles (name, email, resgroup_id) VALUES (?, ?, ?)";
            int rowsAffected = jdbcTemplate.update(sql, profile.getName(), profile.getEmail(), profile.getResgroupId());
            if (rowsAffected > 0) {
                return profile;
            } else {
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation of the profile
            e.printStackTrace();
            return null;
        }
    }

    public Profile findProfileById(Long profileId) {
        try {
            String sql = "SELECT * FROM profiles WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{profileId}, new ProfileRowMapper());
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of the profile
            e.printStackTrace();
            return null;
        }
    }

    private static class ResgroupRowMapper implements RowMapper<Resgroup> {
        @Override
        public Resgroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            Resgroup resGroup = new Resgroup();
            resGroup.setId(rs.getLong("id"));
            resGroup.setName(rs.getString("name"));
            resGroup.setDescription(rs.getString("description"));
            return resGroup;
        }
    }

    private static class ProfileRowMapper implements RowMapper<Profile> {
        @Override
        public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
            Profile profile = new Profile();
            profile.setId(rs.getLong("id"));
            profile.setName(rs.getString("name"));
            profile.setEmail(rs.getString("email"));
            profile.setResgroupId(rs.getLong("resgroup_id"));
            return profile;
        }
    }
}