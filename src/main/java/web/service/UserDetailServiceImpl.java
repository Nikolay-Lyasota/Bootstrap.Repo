package web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.Dao.UserDao;
import web.model.User;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byUsername = userDao.findByUsername(username);
        return whenNull(byUsername, () -> {
            throw new UsernameNotFoundException("user not found");
        });
    }

    private <T extends UserDetails, E extends Supplier<T>> T whenNull(T arg, E action) {
        if (arg == null) {
            return action.get();
        }
        return arg;
    }
}
