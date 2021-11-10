package web.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.List;

@Repository
public interface RoleDaoData extends JpaRepository<Role, Long> {

    @Query(value= "SELECT r FROM Role r WHERE r.role = :role")
    Role findByRole(String role);

    @Query(value = "SELECT r FROM Role r")
    List<Role> getAllByRole();
}

