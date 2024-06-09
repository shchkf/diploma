package org.schemas.controllers;

import org.schemas.manager.DDLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ddl")
public class DDLController {

    @Autowired
    private DDLManager ddlService;

    @GetMapping("/schemas")
    public ResponseEntity<Iterable<Schema>> getAllSchemas() {
        Iterable<Schema> schemas = ddlService.getAllSchemas();
        return new ResponseEntity<>(schemas, HttpStatus.OK);
    }

    @PostMapping("/schemas")
    public ResponseEntity<Schema> createSchema(@RequestBody Schema schema) {
        Schema createdSchema = ddlService.createSchema(schema);
        return new ResponseEntity<>(createdSchema, HttpStatus.CREATED);
    }

    @GetMapping("/tables")
    public ResponseEntity<Iterable<Table>> getAllTables() {
        Iterable<Table> tables = ddlService.getAllTables();
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @PostMapping("/tables")
    public ResponseEntity<Table> createTable(@RequestBody Table table) {
        Table createdTable = ddlService.createTable(table);
        return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
    }

    @GetMapping("/views")
    public ResponseEntity<Iterable<View>> getAllViews() {
        Iterable<View> views = ddlService.getAllViews();
        return new ResponseEntity<>(views, HttpStatus.OK);
    }

    @PostMapping("/views")
    public ResponseEntity<View> createView(@RequestBody View view) {
        View createdView = ddlService.createView(view);
        return new ResponseEntity<>(createdView, HttpStatus.CREATED);
    }
}
