import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.BiPredicate;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD 2004-2022 No Weekends.csv";
//    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD_Daily_20_Rows.csv";

    public static void main(String[] args) {

    }

    public static Result analyseBarType(BarType barType) {
        BiPredicate<Row, Row> testBarType = (previous, current) -> previous.getBarType() == barType;
        return getResult(testBarType);
    }
    public static Result analysePreviousCloseSameAsCurrentClose() {
        BiPredicate<Row, Row> previousAndCurrentCloseDirectionEqual = (previous, current) -> previous.getClosingDirection() == current.getClosingDirection();
        return getResult(previousAndCurrentCloseDirectionEqual);
    }

    public static Result analysePierceAndCover(double percent) {
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

        return getResult(currentDayPiercesAndRetracesBy);
    }

    public static Result analyseInsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() <= previous.getHigh() && current.getLow() >= previous.getLow();
        return getResult(currentDayIsInsideYesterday);
    }

    public static Result analyseOutsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() >= previous.getHigh() && current.getLow() <= previous.getLow();
        return getResult(currentDayIsInsideYesterday);
    }

    public static Result getResult(BiPredicate<Row, Row> condition) {
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



}
