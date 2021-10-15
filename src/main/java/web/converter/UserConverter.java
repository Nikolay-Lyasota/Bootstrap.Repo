package web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.dto.UserDto;
import web.model.Role;
import web.model.User;
import web.service.RoleService;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserConverter {

    @Autowired
    RoleService roleService;

    public User convertDtoToUser(UserDto dto) {
        User user = new User();
        Set<Role> roleSet = new HashSet<>();

        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        if(dto.getRoles() != null) {
            for (String role : dto.getRoles()) {
                Role roleFromDb = roleService.getRoleByName(role);
                if (roleFromDb != null) {
                    roleSet.add(roleFromDb);
                }
                user.setRoles(roleSet);
            }
        }

        return user;
    }
}
