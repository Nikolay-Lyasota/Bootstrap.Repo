package web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class UserDto {

    private final String name;

    private final int age;

    private final String email;

    private final String password;

    private final String[] roles;
}
