package utils;

import entity.Bus;

import java.util.Comparator;

public class CompanyNameAndDepartureComparator implements Comparator<Bus> {
    @Override
    public int compare(Bus o1, Bus o2) {
        int i = o2.getCompanyName().compareTo(o1.getCompanyName());
        if (i != 0) {
            return i;
        } else {
            i = o1.getDeparture().compareTo(o2.getDeparture());
            if (i != 0) {
                return i;
            } else return o1.getArriving().compareTo(o2.getArriving());

        }
    }
}
