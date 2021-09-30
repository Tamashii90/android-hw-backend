package db.dbdemo.controller;

import db.dbdemo.model.ViolationCard;
import db.dbdemo.model.ViolationLog;
import db.dbdemo.model.ViolationLogRegisterRequest;
import db.dbdemo.repository.VehiclesRepo;
import db.dbdemo.repository.ViolationsLogRepo;
import db.dbdemo.repository.ViolationsRepo;
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
    VehiclesRepo vehiclesRepo;

    @Autowired
    ViolationsRepo violationsRepo;

    @GetMapping("/{id}")
    public ViolationCard getViolationCard(@PathVariable("id") Long id) {
        ViolationCard violationCard = violationsLogRepo.findViolationCard(id);
        if (violationCard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Violation doesn't exist");
        }
        return violationCard;
    }

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
        long violationId = violationLog.getViolationId();
        String location = violationLog.getLocation();
        boolean paid = violationLog.isPaid();
        try {
            violationsLogRepo.insertLog(plugedNumber, violationId, location, paid);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid plugedNumber/violationId");
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateViolationLog(@RequestBody ViolationLog violationLog) {
        try {
            violationsLogRepo.save(violationLog);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such vehicle/violation");
        }
    }
}
