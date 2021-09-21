package db.dbdemo.controller;

import db.dbdemo.model.*;
import db.dbdemo.repository.VehiclesRepo;
import db.dbdemo.repository.ViolationsLogRepo;
import db.dbdemo.repository.ViolationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addViolationOccured(@Validated @RequestBody ViolationLog violationLog) {
        Vehicle vehicle;
        Violation violation;
        String plugedNumber = violationLog.getId().getPlugedNumber();
        long violationId = violationLog.getId().getViolationId();
        VehicleViolationKey vehicleViolationKey = new VehicleViolationKey(plugedNumber, violationId);

        if (violationsLogRepo.existsById(vehicleViolationKey)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Violation Log already exists.");
        }
        try {
            vehicle = vehiclesRepo.findById(plugedNumber).get();
            violation = violationsRepo.findById(violationId).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such vehicle/violation");
        }
        violationLog.setViolation(violation);
        violationLog.setVehicle(vehicle);
        violationsLogRepo.save(violationLog);
    }

    @GetMapping()
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
}
