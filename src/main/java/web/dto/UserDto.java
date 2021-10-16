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

//    @NotBlank(message = "empty")
    private final String name;

//    @NotBlank(message = "empty")
    private final int age;

//    @NotBlank(message = "empty")
//    @Email
    private final String email;

//    @NotBlank(message = "empty")
    private final String password;

//    @NotBlank
    private final String[] roles;
}
