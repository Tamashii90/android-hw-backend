package db.dbdemo.controller;

import db.dbdemo.model.Vehicle;
import db.dbdemo.repository.VehiclesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    VehiclesRepo vehiclesRepo;

    @GetMapping("/{plugedNumber}")
    public Vehicle getVehicle(@PathVariable("plugedNumber") String plugedNumber) {
        Vehicle vehicle = vehiclesRepo.findById(plugedNumber).orElse(null);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle doesn't exist");
        }
        return vehicle;
    }

    @PostMapping("/{plugedNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void crossOutAVehicle(@PathVariable String plugedNumber, @RequestBody Map<String, Boolean> map) {
        Vehicle vehicle = vehiclesRepo.findById(plugedNumber).orElse(null);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle doesn't exist");
        }
        if (map.containsKey("crossOut")) {
            vehicle.setCrossOut(map.get("crossOut"));
            vehiclesRepo.save(vehicle);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad format");
        }
    }
}
