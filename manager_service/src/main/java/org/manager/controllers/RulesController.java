package org.manager.controllers;

import org.manager.managers.ResgroupManager;
import org.manager.managers.RulesManager;
import org.manager.models.Notification;
import org.manager.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rules")
public class RulesController {

    @Autowired
    private RulesManager rulesService;

    @PostMapping
    public ResponseEntity<Rule> createRule(@RequestBody Rule rule) {
        Rule createdRule = rulesService.createRule(rule);
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = rulesService.getAllRules();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rule> getRuleById(@PathVariable Long id) {
        Rule rule = rulesService.getRuleById(id);
        return new ResponseEntity<>(rule, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rule> updateRule(@PathVariable Long id, @RequestBody Rule rule) {
        Rule updatedRule = rulesService.updateRule(id, rule);
        return new ResponseEntity<>(updatedRule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        rulesService.deleteRule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
