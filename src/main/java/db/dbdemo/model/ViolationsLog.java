package db.dbdemo.model;

import javax.persistence.*;

@Entity
@Table(name = "Violations_Log")
public class ViolationsLog {
    @EmbeddedId
    private DriverViolationKey id;
    private boolean isPaid;
    @ManyToOne
    @MapsId("driver")
    @JoinColumn(name = "driver")
    private MyUser driver;

    @ManyToOne
    @MapsId("violationId")
    @JoinColumn(name = "Violation_Id")
    private Violation violation;

    public DriverViolationKey getId() {
        return id;
    }

    public void setId(DriverViolationKey id) {
        this.id = id;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public MyUser getDriver() {
        return driver;
    }

    public void setDriver(MyUser driver) {
        this.driver = driver;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }
}
