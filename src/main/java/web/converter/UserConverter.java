package web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.dto.UserDto;
import web.dto.UserWithIdDto;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    @Autowired
    private RoleService roleService;

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

    public User toUser(UserWithIdDto userDto) {
       return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(convertRoles(userDto.getRoles()))
                .build();
    }

    public Set<Role> convertRoles(String[] roles) {
       return Arrays.stream(roles)
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet());
    }
}
