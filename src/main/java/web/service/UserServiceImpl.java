package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public void saveUser(User user) {
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userDao.saveUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        User fromDb = userDao.getUser(user.getId());
        if(user.getPassword().length() != 0) {
            String password = user.getPassword();
            String encodedPass = passwordEncoder.encode(password);
            user.setPassword(encodedPass);
        } else {
            String fromDbPassword = fromDb.getPassword();
            user.setPassword(fromDbPassword);
        }

        if (user.getRoles().isEmpty()) {
            user.setRoles(fromDb.getRoles());
        }

        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user, String role, Long id) {
        User hiberUser = getUser(id);
        hiberUser.setName(user.getName());
        hiberUser.setAge(user.getAge());
        hiberUser.setEmail(user.getEmail());

        if (role != null) {
            hiberUser.getRoles().add(roleService.getRoleByName(role));
        }
        userDao.updateUser(hiberUser);
    }
}
