package db.dbdemo.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    private String username;
    private String password;
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Admin() {
    }

    public Admin(AdminRegisterRequest adminRegisterRequest) {
        this.username = adminRegisterRequest.getUsername();
        setPassword(adminRegisterRequest.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
}
