import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.BiPredicate;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD 2004-2022 No Weekends.csv";
//    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD_Daily_20_Rows.csv";

    public static void main(String[] args) {
        analyseOutsideBar();
        System.out.println();
        System.out.println();
        analyseInsideBar();
        System.out.println();
        System.out.println();
        analysePierceAndCover(0.5);
    }


    public static void analysePierceAndCover(double percent) {
        BiPredicate<Row, Row> currentDayPiercesAndRetracesBy = (previous, current) ->  {
            double distanceToMove = getPercentageOfPriceRange(percent, previous.getLow(), previous.getHigh());
            double targetFromLow = previous.getLow() + distanceToMove;
            double targetFromHigh = previous.getHigh() - distanceToMove;

            boolean isPreviousHighPierced = current.getHigh() >= previous.getHigh();
            boolean isPreviousLowPierced = current.getLow() <= previous.getLow();
            boolean isTargetFromLowCovered = current.getHigh() >= targetFromLow;
            boolean isTargetFromHighCovered = current.getLow() <= targetFromHigh;

            return (isPreviousHighPierced && isTargetFromHighCovered) || (isPreviousLowPierced && isTargetFromLowCovered);
        };
        Result result = testCurrentDayComparedToPrevious(currentDayPiercesAndRetracesBy);

        displayResult(result, String.format("Number of days where current day moves at least %.0f%% of the previous day", percent * 100));
    }

    public static void analyseInsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() <= previous.getHigh() && current.getLow() >= previous.getLow();
        Result result = testCurrentDayComparedToPrevious(currentDayIsInsideYesterday);

        displayResult(result, "Number of days where the current day is an inside bar");
    }

    public static void analyseOutsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() >= previous.getHigh() && current.getLow() <= previous.getLow();
        Result result = testCurrentDayComparedToPrevious(currentDayIsInsideYesterday);

        displayResult(result, "Number of days the current day is an outside bar");
    }

    public static Result testCurrentDayComparedToPrevious(BiPredicate<Row, Row> condition) {
        String previousRowString = "";
        String currentRowString = "";
        Row previousRow = new Row();
        Row currentRow = new Row();
        int numDaysTestPassed = 0;
        int numDays = 0;

        try (FileReader fileReader = new FileReader(csvLocation)) {
            BufferedReader csvReader = new BufferedReader(fileReader);
            csvReader.readLine();

            previousRowString = csvReader.readLine();
            numDays++;

            while ((currentRowString = csvReader.readLine()) != null) {
                previousRow.initialiseRow(previousRowString);
                currentRow.initialiseRow(currentRowString);

                if (condition.test(previousRow, currentRow))
                    numDaysTestPassed ++;

                numDays ++;
                previousRowString = currentRowString;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new Result(numDays, numDaysTestPassed);
    }

    public static double getPercentageOfPriceRange(double percent, double startingPrice, double endingPrice) {
        double range = Math.abs(endingPrice - startingPrice);
        return range * percent;
    }
    private static void displayResult(Result result, String message) {
        System.out.println(message);
        message.chars().forEach(c -> System.out.print("-"));
        System.out.println();
        System.out.printf("Raw: %d/%d\n", result.getNumDaysPassedTest(), result.getTotalNumDays());
        System.out.printf("Percentage %f %%", result.getPercentage());
    }

}
