package web.Dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public
class RoleDaoImpl {

    @PersistenceContext
    private EntityManager entityManager;


    public Role getSingleRoleByName(String role) {
        return entityManager
                .createQuery("SELECT r FROM Role as r WHERE r.role =: role", Role.class)
                .setParameter("role", role)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
