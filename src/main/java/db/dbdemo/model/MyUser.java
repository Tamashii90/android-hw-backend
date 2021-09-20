package db.dbdemo.model;

import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Entity
@Table(name = "USERS")
@NoArgsConstructor
public class MyUser {
    @Id
    private String email;
    private String password;
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @OneToMany(mappedBy = "driver")
    private Set<ViolationsLog> violations;

    public MyUser(String email, String password) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Set<ViolationsLog> getViolations() {
        return violations;
    }

    public void setViolations(Set<ViolationsLog> violations) {
        this.violations = violations;
    }
}
