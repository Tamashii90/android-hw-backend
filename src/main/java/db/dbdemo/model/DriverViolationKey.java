package db.dbdemo.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DriverViolationKey implements Serializable {
    private long violationId;
    private String driver;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverViolationKey)) return false;
        DriverViolationKey that = (DriverViolationKey) o;
        return violationId == that.violationId && driver.equals(that.driver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driver, violationId);
    }
}
