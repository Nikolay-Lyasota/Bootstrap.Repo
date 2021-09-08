package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("select u from User u ", User.class).getResultList();
    }

    @Transactional
    @Override
    public User getUser(Long id) {
        return (User) entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
//        entityManager.unwrap(Session.class).save(user);
        entityManager.merge(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        entityManager.createQuery("DELETE FROM User u WHERE u.id = :id").setParameter("id", id).executeUpdate();
    }


    @Override
    public User findByUsername(String username) {
        return (User) entityManager
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.name = :param OR u.email = :param")
                .setParameter("param", username)
                .getSingleResult();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }
}
