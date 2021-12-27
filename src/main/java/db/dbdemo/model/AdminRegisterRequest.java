package db.dbdemo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AdminRegisterRequest {

    @NotEmpty(message = "Username is empty")
    @Size(max = 13, message = "Name must be 13 characters max.")
    private String username;
    @NotEmpty(message = "Password must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;
    @NotEmpty(message = "Passwords must not be empty")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String repeatPassword;

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
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}