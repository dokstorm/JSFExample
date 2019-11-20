package ru.neoflex.dao;

import ru.neoflex.model.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserDao {
    private final static String UNIT_NAME = "CrudPU";

    @PersistenceContext(unitName = UNIT_NAME)
    private EntityManager entityManager;

    public void save(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }

    public void delete(UserEntity entity) {
        UserEntity entityToBeRemoved = entityManager.merge(entity);

        entityManager.remove(entityToBeRemoved);
    }

    public EntityManager update(UserEntity entity) {
        return entityManager.merge(entity);
    }

    public T find(int entityID) {
        return em.find(entityClass, entityID);
    }

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({ 'unchecked', 'rawtypes' })
    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    // Using the unchecked because JPA does not have a
    // ery.getSingleResult()<T> method
    @SuppressWarnings('unchecked')
    protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
        T result = null;

        try {
            Query query = em.createNamedQuery(namedQuery);

            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }

            result = (T) query.getSingleResult();

        } catch (Exception e) {
            System.out.println('Error while running query: ' + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {

        for (Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}
