package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface ViolationCard {
    @JsonProperty("plugedNumber")
    String getPluged_Number();

    String getType();

    String getId();

    String getDriver();

    String getLocation();

    LocalDate getDate();

    long getTax();

    boolean getPaid();
}
