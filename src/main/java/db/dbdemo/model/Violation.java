package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "violations")
public class Violation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private long tax;
    @OneToMany(mappedBy = "violation")
    @JsonIgnore
    private Set<ViolationLog> violatingVehicles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public Set<ViolationLog> getViolatingVehicles() {
        return violatingVehicles;
    }

    public void setViolatingVehicles(Set<ViolationLog> violatingVehicles) {
        this.violatingVehicles = violatingVehicles;
    }
}
