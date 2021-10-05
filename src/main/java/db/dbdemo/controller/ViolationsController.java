package db.dbdemo.controller;

import db.dbdemo.repository.ViolationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/violations")
public class ViolationsController {
    @Autowired
    ViolationsRepo violationsRepo;

    @GetMapping
    public List<String> getViolationTypes() {
        return violationsRepo.findAllTypes();
    }
}
