package web.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserDto {
    private final Long id;
    private final String name;
    private final int age;
    private final String email;
    private final String password;
    private final String[] roles;
}
