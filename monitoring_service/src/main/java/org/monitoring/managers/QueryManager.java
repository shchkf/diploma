package org.monitoring.managers;

import org.monitoring.dao.QueryDao;
import org.monitoring.models.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryManager {
    @Autowired
    private QueryDao dao;

    public List<Query> getAllQueries() {
        try {
            return dao.getAllQueries();
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of queries
            e.printStackTrace();
            return null;
        }
    }

    public Query moveQuery(Long id, Query updatedQuery) {
        try {
            Query existingQuery = dao.getQueryById(id);
            if (existingQuery != null) {
                // Update the existing query with the new information
                existingQuery.setStatus(updatedQuery.getStatus());
                existingQuery.setPriority(updatedQuery.getPriority());
                existingQuery.setLocation(updatedQuery.getLocation());
                return dao.updateQuery(existingQuery);
            } else {
                // Handle the case where the query with the given ID doesn't exist
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the query
            e.printStackTrace();
            return null;
        }
    }

    public void deleteQuery(Long id) {
        try {
            dao.deleteQuery(id);
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion of the query
            e.printStackTrace();
        }
    }
}
