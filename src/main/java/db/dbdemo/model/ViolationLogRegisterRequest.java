package db.dbdemo.model;


public class ViolationLogRegisterRequest {
    private String violationType;
    private String plugedNumber;
    private String location;
    private boolean paid;

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "{" +
                "violationType='" + violationType + '\'' +
                ", plugedNumber='" + plugedNumber + '\'' +
                ", location='" + location + '\'' +
                ", paid=" + paid +
                '}';
    }
}
