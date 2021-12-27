package db.dbdemo.model;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "Violations_Log")
public class ViolationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean paid;
    private String location;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "plate_number")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "violation_type")
    private Violation violation;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
