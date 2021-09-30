package web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "table_roles")
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String role;

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role.replaceAll("[\\[\\],]", "");
    }
}
