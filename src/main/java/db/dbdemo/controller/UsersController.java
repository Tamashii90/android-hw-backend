package db.dbdemo.controller;

import db.dbdemo.model.*;
import db.dbdemo.repository.AdminsRepo;
import db.dbdemo.repository.VehiclesRepo;
import db.dbdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsersController {
    @Autowired
    VehiclesRepo vehiclesRepo;

    @Autowired
    AdminsRepo adminsRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Map<String, String> getToken(@RequestBody AuthRequest authRequest) {
        String requestDriver = authRequest.getUsername();
        String plateNumber = authRequest.getPassword();
        String token;
        GrantedAuthority authority;
        String dbDriver;

        var authReq = new UsernamePasswordAuthenticationToken(requestDriver, plateNumber);
        try {
            var authenticatedUser = authenticationManager.authenticate(authReq);
            authority = authenticatedUser.getAuthorities().stream().findFirst().get();
            dbDriver = authenticatedUser.getName();
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect Credentials");
        }
        token = jwtUtil.generateToken(dbDriver, authority.toString());
        return Map.of("jwt", token, "authority", authority.toString());
    }

    @PostMapping("/register")
    public Map<String, String> register(@Validated @RequestBody VehicleRegisterRequest vehicleRegisterRequest) {
        String driver = vehicleRegisterRequest.getDriver();
        String plateNumber = vehicleRegisterRequest.getPlateNumber();
        String repeatPlateNumber = vehicleRegisterRequest.getRepeatPlateNumber();
        String token;

        if (!plateNumber.equals(repeatPlateNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Plate numbers don't match");
        }
        if (vehiclesRepo.existsById(plateNumber)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle already exists");
        }

        vehiclesRepo.save(new Vehicle(vehicleRegisterRequest));
        token = jwtUtil.generateToken(driver, "USER");
        return Map.of("jwt", token, "authority", "USER");
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAdmin(@Validated @RequestBody AdminRegisterRequest adminRegisterRequest) {
        String username = adminRegisterRequest.getUsername();
        String password = adminRegisterRequest.getPassword();
        String repeatPassword = adminRegisterRequest.getRepeatPassword();

        if (!password.equals(repeatPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords don't match.");
        }
        if (adminsRepo.existsById(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }

        adminsRepo.save(new Admin(adminRegisterRequest));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        // only display first error
        ObjectError error = ex.getBindingResult().getAllErrors().get(0);
        String errorMessage = error.getDefaultMessage();
        errors.put("message", errorMessage);
        errors.put("status", 400);
        errors.put("error", "Bad Request");
        return errors;
    }

}
