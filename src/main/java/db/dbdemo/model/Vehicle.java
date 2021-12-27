package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "vehicles")
@NoArgsConstructor
public class Vehicle implements Serializable {
    @Id
    private String plateNumber;
    private String driver;
    @Enumerated(EnumType.STRING)
    private VehicleTypes type;
    private LocalDate productionDate;
    private LocalDate registrationDate;
    private boolean crossOut;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private Set<ViolationLog> violations;

    public Vehicle(VehicleRegisterRequest vehicleRegisterRequest) {
        this.plateNumber = vehicleRegisterRequest.getPlateNumber();
        this.driver = vehicleRegisterRequest.getDriver();
        this.type = vehicleRegisterRequest.getType();
        this.productionDate = vehicleRegisterRequest.getProductionDate();
        this.crossOut = vehicleRegisterRequest.isCrossOut();
        this.registrationDate = LocalDate.now();
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public VehicleTypes getType() {
        return type;
    }

    public void setType(VehicleTypes type) {
        this.type = type;
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

    public Set<ViolationLog> getViolations() {
        return violations;
    }

    public void setViolations(Set<ViolationLog> violations) {
        this.violations = violations;
    }

    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
    }
}
