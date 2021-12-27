package db.dbdemo.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ViolationLogRegisterRequest {
    @NotBlank(message = "Violation type is required.")
    private String violationType;
    @NotBlank(message = "Plate number is required.")
    private String plateNumber;
    @NotBlank(message = "Location is required.")
    private String location;
    @NotNull(message = "Paid field is required.")
    private Boolean paid;

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setplateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "{" +
                "violationType='" + violationType + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", location='" + location + '\'' +
                ", paid=" + paid +
                '}';
    }
}
