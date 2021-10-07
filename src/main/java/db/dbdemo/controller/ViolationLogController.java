package db.dbdemo.controller;

import db.dbdemo.model.ViolationCard;
import db.dbdemo.model.ViolationLogEditRequest;
import db.dbdemo.model.ViolationLogRegisterRequest;
import db.dbdemo.repository.ViolationsLogRepo;
import db.dbdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/violations-log")
public class ViolationLogController {
    @Autowired
    ViolationsLogRepo violationsLogRepo;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping
    public List<ViolationCard> getViolationCards(
            @RequestParam(name = "plugedNumber", defaultValue = "") String plugedNumber,
            @RequestParam(name = "driver", defaultValue = "") String driver,
            @RequestParam(name = "location", defaultValue = "") String location,
            @RequestParam(name = "fromDate", defaultValue = "") String fromDate,
            @RequestParam(name = "toDate", defaultValue = "") String toDate) {
        String sanitizedPlugedNumber = plugedNumber.isBlank() ? null : plugedNumber;
        String sanitizedDriver = driver.isBlank() ? null : driver;
        String sanitizedLocation = location.isBlank() ? null : location;
        LocalDate sanitizedFromDate = fromDate.isBlank() ? null : LocalDate.parse(fromDate);
        LocalDate sanitizedToDate = toDate.isBlank() ? null : LocalDate.parse(toDate);
        return violationsLogRepo.getViolationCards(
                sanitizedPlugedNumber, sanitizedDriver, sanitizedLocation, sanitizedFromDate, sanitizedToDate
        );
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addViolationOccurred(@Validated @RequestBody ViolationLogRegisterRequest violationLog) {
        String plugedNumber = violationLog.getPlugedNumber();
        String violationType = violationLog.getViolationType();
        String location = violationLog.getLocation();
        boolean paid = violationLog.isPaid();
        try {
            violationsLogRepo.insertLog(plugedNumber, violationType, location, paid);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid plugedNumber/violationType");
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
                                   @RequestBody ViolationLogEditRequest request) {
        violationsLogRepo.updateViolationLog(
                id, request.getDate(), request.getType(),
                request.getLocation(), request.isPaid()
        );
    }

    @GetMapping("/user/{plugedNumber}")
    public List<ViolationCard> getUsersViolationCards(
            @PathVariable("plugedNumber") String plugedNumber,
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
                plugedNumber, sanitizedLocation, sanitizedFromDate, sanitizedToDate
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
        if (!violationCard.getDriver().equals(driver)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        violationsLogRepo.payForViolation(id);
    }
}
