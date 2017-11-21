package entity;

import java.time.LocalTime;

public class Bus {

    private String companyName;
    private LocalTime departure;
    private LocalTime arriving;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public LocalTime getArriving() {
        return arriving;
    }

    public void setArriving(LocalTime arriving) {
        this.arriving = arriving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bus schedule = (Bus) o;

        if (departure != null ? !departure.equals(schedule.departure) : schedule.departure != null) return false;
        return arriving != null ? arriving.equals(schedule.arriving) : schedule.arriving == null;
    }

    @Override
    public int hashCode() {
        int result = departure != null ? departure.hashCode() : 0;
        result = 31 * result + (arriving != null ? arriving.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return companyName + " " + departure + " " + arriving;
    }
}
