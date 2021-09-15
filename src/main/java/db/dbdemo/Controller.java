package db.dbdemo;

import db.dbdemo.model.AuthRequest;
import db.dbdemo.model.MyUser;
import db.dbdemo.repository.UserRepository;
import db.dbdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/public")
    public String testPublic() {
        return "/public is accessed";
    }

    @GetMapping("/hello")
    public String hello() {
        return "<h1>" + "DD" + "</h1>";
    }

    @GetMapping("/authenticated")
    public String testAuth() {
        return "/authenticated is accessed";
    }

    @GetMapping("/vip")
    public String testUser() {
        return "/vip is accessed";
    }

    @GetMapping("/admin")
    public String currentUserName(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/login")
    public Map<String, String> getToken(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getEmail();
        String password = authRequest.getPassword();
        String token;

        var authReq = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(authReq);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Email or Password");
        }
        token = jwtUtil.generateToken(username);
        return Map.of("jwt", token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody MyUser myUser) {
        if (userRepository.existsByEmail(myUser.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userRepository.save(myUser);
        return new ResponseEntity(HttpStatus.OK);
    }

}
