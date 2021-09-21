package db.dbdemo.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VehicleViolationKey implements Serializable {
    private long violationId;
    private String plugedNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleViolationKey)) return false;
        VehicleViolationKey that = (VehicleViolationKey) o;
        return violationId == that.violationId && plugedNumber.equals(that.plugedNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plugedNumber, violationId);
    }
}
