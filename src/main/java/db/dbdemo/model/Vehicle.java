package db.dbdemo.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


// TODO Add field validations
@Entity
@Table(name = "vehicles")
@NoArgsConstructor
public class Vehicle {
    @Id
    private String plugedNumber;
    private String driver;
    private String type;
    private String category;
    private LocalDate productionDate;
    private LocalDate registrationDate;
    private boolean crossOut;

    @OneToMany(mappedBy = "plugedNumber")
    private Set<ViolationsLog> violations;

    public Vehicle(RegisterRequest registerRequest) {
        this.plugedNumber = registerRequest.getPlugedNumber();
        this.driver = registerRequest.getDriver();
        this.type = registerRequest.getType();
        this.category = registerRequest.getCategory();
        this.productionDate = registerRequest.getProductionDate();
        this.crossOut = registerRequest.isCrossOut();
        this.registrationDate = LocalDate.now();
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
    }

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<ViolationsLog> getViolations() {
        return violations;
    }

    public void setViolations(Set<ViolationsLog> violations) {
        this.violations = violations;
    }

    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
    }
}
