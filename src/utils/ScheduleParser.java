package utils;

import entity.Bus;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Aleksandr Fomin
 */
public class ScheduleParser {

    /**
     * Parses the schedule of buses from the given file
     * @param pathToFile path to file with schedule
     * @return List<Bus>
     */
    public static List<Bus> parse(String pathToFile) {
        Path path = Paths.get(pathToFile);
        List<Bus> list = new ArrayList<>();
        Set<String> stringList = new HashSet<>();

        try {
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> stringList.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringList.forEach(stringSchedule ->{
            if (stringSchedule.length()!=0) {
                String[] split = stringSchedule.split(" ");
                Bus bus = new Bus();
                bus.setCompanyName(split[0]);
                bus.setDeparture(LocalTime.parse(split[1]));
                bus.setArriving(LocalTime.parse(split[2]));
                list.add(bus);
            } else {
            }
        });
        return list;
    }
}
