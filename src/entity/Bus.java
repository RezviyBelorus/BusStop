package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bus {

    private String companyName;
    private LocalDateTime departure;
    private LocalDateTime arriving;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArriving() {
        return arriving;
    }

    public void setArriving(LocalDateTime arriving) {
        this.arriving = arriving;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return Objects.equals(departure, bus.departure) &&
                Objects.equals(arriving, bus.arriving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departure, arriving);
    }

    @Override
    public String toString() {
        return companyName + " " + departure.toLocalTime() + " " + arriving.toLocalTime();
    }
}
