package org.schemas.controllers;

import org.schemas.manager.SchemasManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schemas")
public class SchemasController {

    @Autowired
    private SchemasManager schemaService;

    @GetMapping
    public ResponseEntity<List<Schema>> getAllSchemas() {
        List<Schema> schemas = schemaService.getAllSchemas();
        return new ResponseEntity<>(schemas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schema> getSchemaById(@PathVariable Long id) {
        Schema schema = schemaService.getSchemaById(id);
        return new ResponseEntity<>(schema, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Schema> createSchema(@RequestBody Schema schema) {
        Schema createdSchema = schemaService.createSchema(schema);
        return new ResponseEntity<>(createdSchema, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schema> updateSchema(@PathVariable Long id, @RequestBody Schema schema) {
        Schema updatedSchema = schemaService.updateSchema(id, schema);
        return new ResponseEntity<>(updatedSchema, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchema(@PathVariable Long id) {
        schemaService.deleteSchema(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
