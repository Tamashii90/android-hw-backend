package db.dbdemo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class VehicleRegisterRequest {
    @NotEmpty
    @Size(min = 6, max = 6, message = "Pluged Number must be 6 digits.")
    private String plugedNumber;
    @NotEmpty
    @Size(min = 6, max = 6, message = "Pluged Number must be 6 digits.")
    private String repeatPlugedNumber;
    @NotEmpty
    @Size(max = 13, message = "Name must be 13 characters max.")
    private String driver;
    @NotNull
    private VehicleTypes type;
    @NotNull
    private LocalDate productionDate;
    @NotNull
    private Boolean crossOut;

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
    }

    public String getRepeatPlugedNumber() {
        return repeatPlugedNumber;
    }

    public void setRepeatPlugedNumber(String repeatPlugedNumber) {
        this.repeatPlugedNumber = repeatPlugedNumber;
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

    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
    }
}
