package org.monitoring.controllers;

import org.monitoring.managers.QueryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/queries")
public class QueryController {

    @Autowired
    private QueryManager queryService;

    @GetMapping
    public ResponseEntity<List<Query>> getAllQueries() {
        List<Query> queries = queryService.getAllQueries();
        return new ResponseEntity<>(queries, HttpStatus.OK);
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<Query> moveQuery(@PathVariable Long id, @RequestBody Query query) {
        Query movedQuery = queryService.moveQuery(id, query);
        return new ResponseEntity<>(movedQuery, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        queryService.deleteQuery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
