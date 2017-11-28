
import utils.ScheduleModifier;

public class Main {
    public static void main(String[] args) {
        String inputFile = "resources/schedule.txt";
        String outPutFile = "resources/scheduleOutput.txt";
        ScheduleModifier.getBestBuses(inputFile, outPutFile);
    }
}
