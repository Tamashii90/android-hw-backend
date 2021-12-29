package db.dbdemo.controller;

import db.dbdemo.model.ViolationCard;
import db.dbdemo.model.ViolationLogEditRequest;
import db.dbdemo.model.ViolationLogRegisterRequest;
import db.dbdemo.repository.ViolationsLogRepo;
import db.dbdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/violations-log")
public class ViolationLogController {
    @Autowired
    ViolationsLogRepo violationsLogRepo;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping
    public List<ViolationCard> getViolationCards(
            @RequestParam(name = "plateNumber", defaultValue = "") String plateNumber,
            @RequestParam(name = "driver", defaultValue = "") String driver,
            @RequestParam(name = "location", defaultValue = "") String location,
            @RequestParam(name = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(name = "toDate", defaultValue = "") String toDate) {
        String sanitizedPlateNumber = plateNumber.isBlank() ? null : plateNumber;
        String sanitizedDriver = driver.isBlank() ? null : driver;
        String sanitizedLocation = location.isBlank() ? null : location;
        LocalDate sanitizedFromDate = fromDate.isBlank() ? null : LocalDate.parse(fromDate);
        LocalDate sanitizedToDate = toDate.isBlank() ? null : LocalDate.parse(toDate);
        return violationsLogRepo.getViolationCards(
                sanitizedPlateNumber, sanitizedDriver, sanitizedLocation, sanitizedFromDate, sanitizedToDate
        );
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addViolationOccurred(@Validated @RequestBody ViolationLogRegisterRequest violationLog) {
        String plateNumber = violationLog.getPlateNumber();
        String violationType = violationLog.getViolationType();
        String location = violationLog.getLocation();
        boolean paid = violationLog.isPaid();
        try {
            violationsLogRepo.insertLog(plateNumber, violationType, location, paid);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid plateNumber/violationType");
        }
    }

    @GetMapping("/{id}")
    public ViolationCard getViolationCard(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        String authority = jwtUtil.extractAuthority(token);
        String driver = jwtUtil.extractUsername(token);
        ViolationCard violationCard = violationsLogRepo.findViolationCard(id);
        if (violationCard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Violation doesn't exist");
        }
        if (!authority.equals("ADMIN") && !violationCard.getDriver().equals(driver)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return violationCard;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateViolationLog(@PathVariable Long id,
                                   @Validated @RequestBody ViolationLogEditRequest request) {

        try {
            violationsLogRepo.updateViolationLog(
                    id, request.getDate(), request.getType(),
                    request.getLocation(), request.isPaid()
            );
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid violation type");
        }
    }

    @GetMapping("/user/{plateNumber}")
    public List<ViolationCard> getUsersViolationCards(
            @PathVariable("plateNumber") String plateNumber,
            @RequestHeader("Authorization") String authorization,
            @RequestParam(name = "location", defaultValue = "") String location,
            @RequestParam(name = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(name = "toDate", defaultValue = "") String toDate) {
        String token = authorization.substring(7);
        String authority = jwtUtil.extractAuthority(token);
        String driver = jwtUtil.extractUsername(token);
        String sanitizedLocation = location.isBlank() ? null : location;
        LocalDate sanitizedFromDate = fromDate.isBlank() ? null : LocalDate.parse(fromDate);
        LocalDate sanitizedToDate = toDate.isBlank() ? null : LocalDate.parse(toDate);
        List<ViolationCard> cards = violationsLogRepo.getUsersViolationCards(
                plateNumber, sanitizedLocation, sanitizedFromDate, sanitizedToDate
        );
        if (!authority.equals("ADMIN")) {
            if (!cards.isEmpty() && !cards.get(0).getDriver().equals(driver)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
        return cards;
    }

    @PostMapping("/pay/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void payForViolation(@RequestHeader("Authorization") String authorization,
                                @PathVariable("id") Long id) {
        String token = authorization.substring(7);
        String driver = jwtUtil.extractUsername(token);
        ViolationCard violationCard = violationsLogRepo.findViolationCard(id);
        if (violationCard == null || !violationCard.getDriver().equals(driver)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        violationsLogRepo.payForViolation(id);
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
