package db.dbdemo.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum VehicleTypes {
    PRIV_VEHICLE("Private Vehicle"), GOV_VEHICLE("Governmental Vehicle"),
    TAXI("Taxi"), COMM_VEHICLE("Commercial Vehicle"), PUB_TRANS("Public Transportation"),
    HEAVY("Heavy Duty");

    private String description;

    VehicleTypes(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
