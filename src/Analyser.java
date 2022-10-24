import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Analyser {
    private String csvLocation;

    public Analyser(String csvLocation) {
        this.csvLocation = csvLocation;
    }

    public Result analyseBarType(BarType barType) {
        Predicate<Row> testBarType = (row) -> row.getBarType() == barType;
        return getResult(testBarType);
    }
    public Result analysePreviousCloseSameAsCurrentClose() {
        BiPredicate<Row, Row> previousAndCurrentCloseDirectionEqual = (previous, current) -> previous.getClosingDirection() == current.getClosingDirection();
        return getResult(previousAndCurrentCloseDirectionEqual);
    }

    public Result analysePierceAndCover(double percent) {
        BiPredicate<Row, Row> currentDayPiercesAndRetracesBy = (previous, current) ->  {
            double distanceToMove = previous.getPriceRange() * percent;
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

    public Result analyseInsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() <= previous.getHigh() && current.getLow() >= previous.getLow();
        return getResult(currentDayIsInsideYesterday);
    }

    public Result analyseOutsideBar() {
        BiPredicate<Row, Row> currentDayIsInsideYesterday = (previous, current) -> current.getHigh() >= previous.getHigh() && current.getLow() <= previous.getLow();
        return getResult(currentDayIsInsideYesterday);
    }


    private Result getResult(BiPredicate<Row, Row> condition) {
        String previousRowString = "";
        String currentRowString = "";
        Row previousRow = new Row();
        Row currentRow = new Row();
        int numDaysTestPassed = 0;
        int numDays = 0;

        try (BufferedReader csvReader = Files.newBufferedReader(Path.of(csvLocation))) {
            csvReader.readLine();
            previousRowString = csvReader.readLine();
            numDays = (int) (csvReader.lines().count() - 1);

            while ((currentRowString = csvReader.readLine()) != null) {
                previousRow.initialiseRow(previousRowString);
                currentRow.initialiseRow(currentRowString);

                if (condition.test(previousRow, currentRow))
                    numDaysTestPassed ++;

                previousRowString = currentRowString;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new Result(numDays, numDaysTestPassed);
    }
    private Result getResult(Predicate<Row> condition) {
        int numDaysTestPassed = 0;
        int numDays = 0;

        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(csvLocation))) {
            numDaysTestPassed = (int) bufferedReader.lines().map(Row::new).filter(condition).count();
            numDays = (int) bufferedReader.lines().count();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new Result(numDays, numDaysTestPassed);
    }
}
