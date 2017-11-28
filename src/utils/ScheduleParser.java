package utils;

import entity.Bus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.time.LocalDateTime.parse;

/**
 * @author Aleksandr Fomin
 */
public class ScheduleParser {
    private static final String BEFORE_NIGHT = "2017-01-01T";
    private static final String AFTER_NIGHT = "2017-01-02T";
    private static final String DELIMITER = " ";

    /**
     * Parses the schedule of buses from the given file
     *
     * @param pathToFile path to file with schedule
     * @return List<Bus>
     */
    public static List<Bus> parseFile(String pathToFile) {
        Path path = Paths.get(pathToFile);
        List<Bus> list = new ArrayList<>();
        Set<String> stringList = new HashSet<>();

        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach(stringList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringList.forEach(stringSchedule -> {
            if (stringSchedule.length() != 0) {
                String[] split = stringSchedule.split(DELIMITER);
                Bus bus = new Bus();
                bus.setCompanyName(split[0]);
                LocalTime departureTime = LocalTime.parse(split[1]);
                LocalTime arrivingTime = LocalTime.parse(split[2]);

                int hours = arrivingTime.getHour() - departureTime.getHour();
                int minutes = arrivingTime.getMinute() - departureTime.getMinute();

                bus.setDeparture(LocalDateTime.parse(BEFORE_NIGHT + departureTime));
                bus.setArriving(getArrivingDate(arrivingTime, hours, minutes));
                list.add(bus);
            }
        });
        return list;
    }

    private static LocalDateTime getArrivingDate(LocalTime arrivingTime, int hours, int minutes) {
        return isArrivingOnNextDay(hours, minutes)
                ? parse(AFTER_NIGHT + arrivingTime)
                : parse(BEFORE_NIGHT + arrivingTime);
    }

    private static boolean isArrivingOnNextDay(int hours, int minutes) {
        return hours < 0 || (hours == 0 && minutes <= 0);
    }
}
