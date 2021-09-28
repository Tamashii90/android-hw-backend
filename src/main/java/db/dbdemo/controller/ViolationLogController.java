package db.dbdemo.controller;

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

import java.util.ArrayList;
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

    @GetMapping("/{plugedNumber}")
    public List<ViolationLog> getViolationLog(@PathVariable String plugedNumber) {
        return violationsLogRepo.findViolationLogByPlugedNum(plugedNumber);
    }

    @GetMapping
    public List<ViolationLog> getAllViolationLogs() {
        List<ViolationLog> result = new ArrayList<>();
        violationsLogRepo.findAll().forEach(log -> {
            if (!log.getVehicle().isCrossOut()) {
                result.add(log);
            }
        });
//        Map<String, Object> result = new HashMap<>();
//        List<ViolationLog> logs = new ArrayList<>();
//        long sum = 0L;
//        for (ViolationLog log : violationsLogRepo.findAll()) {
//            sum += log.getViolation().getTax();
//            logs.add(log);
//        }
//        result.put("logs", logs);
//        result.put("totalTax", sum);
        return result;
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
