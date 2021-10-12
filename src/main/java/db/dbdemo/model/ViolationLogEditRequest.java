package db.dbdemo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViolationLogEditRequest {
    @NotBlank(message = "Violation type is required.")
    private String type;
    @NotBlank(message = "Location is required.")
    private String location;
    @NotNull(message = "Date is required.")
    private LocalDate date;
    @NotNull(message = "Paid field is required.")
    private boolean paid;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
