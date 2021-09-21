package db.dbdemo.controller;

import db.dbdemo.model.AuthRequest;
import db.dbdemo.model.Vehicle;
import db.dbdemo.model.RegisterRequest;
import db.dbdemo.repository.VehiclesRepo;
import db.dbdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    VehiclesRepo vehiclesRepo;

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
        String driver = authRequest.getUsername();
        String plugedNumber = authRequest.getPassword();
        String token;
        GrantedAuthority authority;

        var authReq = new UsernamePasswordAuthenticationToken(driver, plugedNumber);
        try {
            var authenticatedUser = authenticationManager.authenticate(authReq);
            authority = authenticatedUser.getAuthorities().stream().findFirst().get();
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Credentials");
        }
        token = jwtUtil.generateToken(driver, authority.toString());
        return Map.of("jwt", token, "authority", authority.toString());
    }

    @PostMapping("/register")
    public Map<String, String> register(@Validated @RequestBody RegisterRequest registerRequest) {
        String driver = registerRequest.getDriver();
        String plugedNumber = registerRequest.getPlugedNumber();
        String repeatPlugedNumber = registerRequest.getRepeatPlugedNumber();
        String token;

        if (!plugedNumber.equals(repeatPlugedNumber) || vehiclesRepo.existsById(plugedNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        vehiclesRepo.save(new Vehicle(registerRequest));
        token = jwtUtil.generateToken(driver, "USER");
        return Map.of("jwt", token, "authority", "USER");
    }

}
