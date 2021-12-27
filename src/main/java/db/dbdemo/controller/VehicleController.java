package db.dbdemo.controller;

import db.dbdemo.model.Vehicle;
import db.dbdemo.model.VehicleTypes;
import db.dbdemo.repository.VehiclesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    VehiclesRepo vehiclesRepo;

    @GetMapping("/{plateNumber}")
    public Vehicle getVehicle(@PathVariable("plateNumber") String plateNumber) {
        Vehicle vehicle = vehiclesRepo.findById(plateNumber).orElse(null);
        if (vehicle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle doesn't exist");
        }
        return vehicle;
    }

    @GetMapping("/types")
    public List<String> getTypes() {
        return Arrays.stream(VehicleTypes.values())
                .map(VehicleTypes::getDescription)
                .collect(Collectors.toList());
    }

    @PostMapping("/{plateNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void crossOutAVehicle(@PathVariable String plateNumber, @RequestBody Map<String, Boolean> map) {
        Vehicle vehicle = vehiclesRepo.findById(plateNumber).orElse(null);
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
