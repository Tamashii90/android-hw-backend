package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "Violations_Log")
public class ViolationLog {
    @EmbeddedId
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private VehicleViolationKey id;
    private boolean paid;
    private String location;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;

    @ManyToOne
    @MapsId("plugedNumber")
    @JoinColumn(name = "pluged_number")
    private Vehicle vehicle;

    @ManyToOne
    @MapsId("violationId")
    @JoinColumn(name = "violation_id")
    private Violation violation;

    @JsonCreator
    public ViolationLog(VehicleViolationKey id, boolean paid, String location) {
        this.id = id;
        this.paid = paid;
        this.location = location;
    }

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", paid=" + paid +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", vehicle=" + vehicle +
                ", violation=" + violation +
                '}';

    }
}
