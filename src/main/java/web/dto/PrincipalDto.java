package web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import web.model.User;

@Data
@RequiredArgsConstructor
@Builder
public class PrincipalDto {

    private final String email;
    private final String roles;

    public PrincipalDto(User user) {
        email = user.getEmail();
        roles = user.getRoles().toString().replaceAll("[\\[\\],]", "");
    }
}
