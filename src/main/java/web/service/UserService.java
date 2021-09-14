package web.service;


import org.springframework.stereotype.Service;
import web.model.User;

import java.util.List;

@Service
public interface UserService {

    List<User> getUsersList();

    User getUser(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

    User findByUsername(String username);

    void updateUser(User user);

    void updateUser(User user, String role, Long id);
}
