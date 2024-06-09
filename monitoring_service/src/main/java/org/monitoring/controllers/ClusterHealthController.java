package org.monitoring.controllers;

import org.monitoring.managers.ClusterHealthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cluster-health")
public class ClusterHealthController {

    @Autowired
    private ClusterHealthManager clusterHealthService;

    @GetMapping
    public ResponseEntity<ClusterHealthStatus> getClusterHealth() {
        ClusterHealthStatus clusterHealthStatus = clusterHealthService.getClusterHealth();
        return new ResponseEntity<>(clusterHealthStatus, HttpStatus.OK);
    }
}
