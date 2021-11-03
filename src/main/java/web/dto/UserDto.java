package web.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

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
