package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> getUsersList();

    User getUser(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

    User findByUsername(String username);

    void updateUser(User user);
}
