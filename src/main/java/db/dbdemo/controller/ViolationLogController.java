package db.dbdemo.controller;

import db.dbdemo.model.Vehicle;
import db.dbdemo.model.VehicleViolationKey;
import db.dbdemo.model.Violation;
import db.dbdemo.model.ViolationLog;
import db.dbdemo.repository.VehiclesRepo;
import db.dbdemo.repository.ViolationsLogRepo;
import db.dbdemo.repository.ViolationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/violations-log")
public class ViolationLogController {
    @Autowired
    ViolationsLogRepo violationsLogRepo;

    @Autowired
    VehiclesRepo vehiclesRepo;

    @Autowired
    ViolationsRepo violationsRepo;

    @GetMapping
    public ViolationLog test(@RequestParam String plugedNumber, @RequestParam long violationId) {
        VehicleViolationKey vehicleViolationKey = new VehicleViolationKey(plugedNumber, violationId);
        ViolationLog violationLog = null;
        try {
            violationLog = violationsLogRepo.findById(vehicleViolationKey).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such vehicle/violation");
        }
        return violationLog;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addViolationOccurred(@Validated @RequestBody ViolationLog violationLog) {
        String plugedNumber = violationLog.getId().getPlugedNumber();
        long violationId = violationLog.getId().getViolationId();
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
