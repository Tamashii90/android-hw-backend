package db.dbdemo.model;

import javax.validation.constraints.NotEmpty;

public class AuthRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public void setPassword(String password) {
        this.password = password;
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
}
