package org.monitoring.managers;

import org.monitoring.dao.ClusterHealthDao;
import org.monitoring.model.ClusterHealthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterHealthManager {
    @Autowired
    private ClusterHealthDao dao;

    public ClusterHealthStatus getClusterHealth() {
        try {
            return dao.fetchClusterHealthStatus();
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of cluster health status
            e.printStackTrace();
            return new ClusterHealthStatus("Unknown", "Error retrieving cluster health status");
        }
    }
}
