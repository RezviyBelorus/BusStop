package utils;

import entity.Bus;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

/**
 * @author Aleksandr Fomin
 */
public class ScheduleModifier {
    private final static String POSH = "Posh";
    private final static String GROTTY = "Grotty";

    /**
     * Gets the timetable of buses from the file, sorts it and writes into the output file
     *
     * @param inputFile  path to input file
     * @param outPutFile path to output file
     */
    public static void getBestBuses(String inputFile, String outPutFile) {
        List<Bus> buses = ScheduleParser.parse(inputFile);

        Set<Bus> uniqueBuses = removeDublicates(buses);

        Set<Bus> result = getValidBuses(uniqueBuses);
        Set<Bus> sortedBuses = new TreeSet<>(new DepartureComparator());
        sortedBuses.addAll(result);

        int delimiter = findDelimiter(sortedBuses);

        writeScheduleToFile(sortedBuses, outPutFile, delimiter);
    }

    /**
     * Removes the dublicates from ﻿the schedule time of buses
     *
     * @param basicBuses list of buses after ScheduleParser
     * @return Set<Bus>
     */
    private static Set<Bus> removeDublicates(List<Bus> basicBuses) {
        Set<Bus> uniqueBuses = new HashSet<>();
        basicBuses.forEach(bus -> {
            if (uniqueBuses.contains(bus)) {
                if (bus.getCompanyName().equals(POSH)) {
                    uniqueBuses.remove(bus);
                    uniqueBuses.add(bus);
                }
            } else uniqueBuses.add(bus);

        });
        return uniqueBuses;
    }

    /**
     * Finds the point which is used to divide the schedule time into two parts
     *
     * @param sortedBuses Collection of buses sorted by departure and name of company
     * @return int
     */
    private static int findDelimiter(Set<Bus> sortedBuses) {
        Iterator<Bus> iterator = sortedBuses.iterator();
        int indexOfDelimiter = 0;
        while (iterator.hasNext()) {
            Bus next = iterator.next();
            if (next.getCompanyName().equals(GROTTY)) {
                return indexOfDelimiter;
            }
            indexOfDelimiter++;
        }
        return indexOfDelimiter;
    }

    /**
     * Returns the collection of buses which corresponds with the following requirements:
     * - Any service longer than an hour shall not be included
     * - If it starts at the same time and reaches earlier,
     * - or o If it starts later and reaches at the same time,
     * - or o If it starts later and reaches earlier.
     *
     * @param buses set of buses before validating
     * @return Set<Bus>
     */
    public static Set<Bus> getValidBuses(Set<Bus> buses) {
        Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            Bus next = iterator.next();
            Duration between = Duration.between(next.getDeparture(), next.getArriving());
            long routeDurationSec = between.getSeconds();
            boolean isLongRoute = false;
            if (routeDurationSec > 60 * 60) {
                iterator.remove();
                isLongRoute = true;
            }
            if (!isLongRoute) {
                List<Bus> busList = new ArrayList<>();
                busList.addAll(buses);
                for (int i = 0; i < busList.size(); i++) {
                    int departure = busList.get(i).getDeparture().compareTo(next.getDeparture());
                    int arriving = busList.get(i).getArriving().compareTo(next.getArriving());

                    if (departure > 0 && arriving <= 0) {
                        iterator.remove();
                        break;
                    }
                    if (departure == 0 && arriving < 0) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        return buses;
    }

    /**
     * Records the schedule of buses in *.txt file
     *
     * @param bestBuses        collection of buses which will be write to file
     * @param pathToOutPutFile path to out put file
     * @param indexOfDelimiter point which devides schedule in to two parts
     * @throws IOException if something was incorrect
     */
    private static void writeScheduleToFile(Set<Bus> bestBuses, String pathToOutPutFile, int indexOfDelimiter) {
        Path path = Paths.get(pathToOutPutFile);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            int pointForNewTimeTable = 1;
            for (Bus bus : bestBuses) {
                writer.write(bus.toString());
                writer.newLine();
                if (pointForNewTimeTable == indexOfDelimiter) {
                    writer.write(" ");
                    writer.newLine();
                }
                pointForNewTimeTable++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
