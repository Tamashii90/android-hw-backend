package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.LocalDate;

public interface ViolationCard {
    @JsonProperty("plugedNumber")
    String getPluged_Number();
    String getDriver();
    String getLocation();
    LocalDate getDate();
    long getTax();
    boolean getPaid();
}
