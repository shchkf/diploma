package org.schemas.dao;

import org.schemas.models.Schema;
import org.schemas.models.Table;
import org.schemas.models.View;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Repository
public class DDLManager {

    @PersistenceContext
    private DDLDao entityManager;

    public Iterable<Schema> getAllSchemas(String nameFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Schema> cq = cb.createQuery(Schema.class);
        Root<Schema> root = cq.from(Schema.class);

        Predicate namePredicate = cb.like(root.get("name"), "%" + nameFilter + "%");
        cq.where(namePredicate);

        return entityManager.createQuery(cq).getResultList();
    }

    public Schema createSchema(Schema schema) {
        entityManager.persist(schema);
        return schema;
    }

    public Iterable<Table> getAllTables(String nameFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Table> cq = cb.createQuery(Table.class);
        Root<Table> root = cq.from(Table.class);

        Predicate namePredicate = cb.like(root.get("name"), "%" + nameFilter + "%");
        cq.where(namePredicate);

        return entityManager.createQuery(cq).getResultList();
    }

    public Table createTable(Table table) {
        entityManager.persist(table);
        return table;
    }

    public Iterable<View> getAllViews(String nameFilter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<View> cq = cb.createQuery(View.class);
        Root<View> root = cq.from(View.class);

        Predicate namePredicate = cb.like(root.get("name"), "%" + nameFilter + "%");
        cq.where(namePredicate);

        return entityManager.createQuery(cq).getResultList();
    }

    public View createView(View view) {
        entityManager.persist(view);
        return view;
    }
}