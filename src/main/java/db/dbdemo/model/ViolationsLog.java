package db.dbdemo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Violations_Log")
public class ViolationsLog {
    @EmbeddedId
    private VehicleViolationKey id;
    private boolean paid;
    private String location;
    private LocalDate date = LocalDate.now();


    @ManyToOne
    @MapsId("plugedNumber")
    @JoinColumn(name = "pluged_number")
    private Vehicle plugedNumber;

    @ManyToOne
    @MapsId("violationId")
    @JoinColumn(name = "violation_id")
    private Violation violation;

    public VehicleViolationKey getId() {
        return id;
    }

    public void setId(VehicleViolationKey id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Vehicle getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(Vehicle plugedNumber) {
        this.plugedNumber = plugedNumber;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }
}
