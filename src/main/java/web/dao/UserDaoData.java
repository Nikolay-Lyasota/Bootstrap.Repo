package web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.User;

@Repository
public interface UserDaoData extends JpaRepository<User, Long> {
    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.name = :username OR u.email = :username")
    User findByUsername(String username);
}
