package org.schemas.manager;

import org.schemas.dao.SchemasDao;
import org.schemas.models.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemasManager {

    @Autowired
    private SchemasDao schemasDao;

    public List<Schema> getAllSchemas() {
        try {
            return schemasDao.getAllSchemas();
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of schemas
            e.printStackTrace();
            return null;
        }
    }

    public Schema getSchemaById(Long id) {
        try {
            return schemasDao.getSchemaById(id);
        } catch (Exception e) {
            // Handle any exceptions that occur during the retrieval of a specific schema
            e.printStackTrace();
            return null;
        }
    }

    public Schema createSchema(Schema schema) {
        try {
            return schemasDao.createSchema(schema);
        } catch (Exception e) {
            // Handle any exceptions that occur during the creation of a new schema
            e.printStackTrace();
            return null;
        }
    }

    public Schema updateSchema(Long id, Schema updatedSchema) {
        try {
            Schema existingSchema = schemasDao.getSchemaById(id);
            if (existingSchema != null) {
                // Update the existing schema with the new information
                existingSchema.setName(updatedSchema.getName());
                existingSchema.setDescription(updatedSchema.getDescription());
                existingSchema.setVersion(updatedSchema.getVersion());
                return schemasDao.updateSchema(existingSchema);
            } else {
                // Handle the case where the schema with the given ID doesn't exist
                return null;
            }
        } catch (Exception e) {
            // Handle any exceptions that occur during the update of the schema
            e.printStackTrace();
            return null;
        }
    }

    public void deleteSchema(Long id) {
        try {
            schemasDao.deleteSchema(id);
        } catch (Exception e) {
            // Handle any exceptions that occur during the deletion of the schema
            e.printStackTrace();
        }
    }
}