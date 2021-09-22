package db.dbdemo.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VehicleRegisterRequest {
    @NotEmpty
    private String plugedNumber;
    @NotEmpty
    private String repeatPlugedNumber;
    @NotEmpty
    private String driver;
    @NotEmpty
    private String type;
    @NotEmpty
    private String category;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
