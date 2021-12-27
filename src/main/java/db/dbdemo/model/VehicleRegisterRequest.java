package db.dbdemo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class VehicleRegisterRequest {

    @Pattern(regexp = "\\d{6}", message = "Plate number must be 6 digits.")
    private String plateNumber;
    @Pattern(regexp = "\\d{6}", message = "Plate number must be 6 digits.")
    private String repeatPlateNumber;
    @NotEmpty
    @Size(max = 13, message = "Name must be 13 characters max.")
    private String driver;
    @NotNull
    private VehicleTypes type;
    @NotNull
    private LocalDate productionDate;
    @NotNull
    private Boolean crossOut;

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setplateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getRepeatPlateNumber() {
        return repeatPlateNumber;
    }

    public void setRepeatPlateNumber(String repeatPlateNumber) {
        this.repeatPlateNumber = repeatPlateNumber;
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
