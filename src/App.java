import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD 2004-2022 No Weekends.csv";

    public static void main(String[] args) {
        analyseOutsideBar();

        System.out.println();
        System.out.println();

        analyseInsideBar();
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

            while ((previousRowString = csvReader.readLine()) != null && (currentRowString = csvReader.readLine()) != null) {
                previousRow.initialiseRow(previousRowString);
                currentRow.initialiseRow(currentRowString);

                if (condition.test(previousRow, currentRow))
                    numDaysTestPassed ++;

                numDays += 2;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new Result(numDays, numDaysTestPassed);
    }

    public static float getTargetCoverPriceByPercent(float percent, float startingPrice, float endingPrice) {
        float range = Math.abs(endingPrice - startingPrice);
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
