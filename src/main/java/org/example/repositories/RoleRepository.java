package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.entities.Role;

import java.util.List;

public class RoleRepository {
    EntityManager entityManager;

    public RoleRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }

    public List<Role> findAll() {
        return entityManager.createQuery("select r from Role r", Role.class)
                .getResultList();
    }

    public void delete(Long id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Role.class, id));
        entityManager.getTransaction().commit();
    }

    public void saveOrUpdate(Role role) {
        try {
            Role r = entityManager.createQuery("select r from Role r where r.roleName = :roleName", Role.class)
                    .setParameter("roleName", role.getRoleName())
                    .getSingleResult();
            role.setId(r.getId());
            entityManager.getTransaction().begin();
            entityManager.merge(role);
            entityManager.getTransaction().commit();
        } catch (NoResultException e) {
            entityManager.getTransaction().begin();
            entityManager.persist(role);
            entityManager.getTransaction().commit();
        }
    }
}
