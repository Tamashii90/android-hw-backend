package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Table(name = "USERS")
@NoArgsConstructor
public class MyUser {
    @Id
    private String email;
    private String password;
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MyUser(String email, String password) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

    @JsonCreator
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
}
