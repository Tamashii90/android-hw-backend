package db.dbdemo.model;


public class ViolationLogRegisterRequest {
    private long violationId;
    private String plugedNumber;
    private String location;
    private boolean paid;

    public long getViolationId() {
        return violationId;
    }

    public void setViolationId(long violationId) {
        this.violationId = violationId;
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
                "violationId=" + violationId +
                ", plugedNumber='" + plugedNumber + '\'' +
                ", location='" + location + '\'' +
                ", paid=" + paid +
                '}';
    }
}
