import java.io.BufferedReader;
import java.io.FileReader;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD 2004-2022 No Weekends.csv";

    public static void main(String[] args) {
        analyseOutsideBar();
        System.out.println();
        analyseInsideBar();
    }

    public static void analyseInsideBar() {
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



                if (currentRow.getHigh() <= previousRow.getHigh() && currentRow.getLow() >= previousRow.getLow())
                    numDaysTestPassed ++;

                numDays += 2;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Number of days where the current day is an inside bar");
        displayResult(numDaysTestPassed, numDays);
    }



    public static void analyseOutsideBar() {
        String previousRowString = "";
        String currentRowString = "";
        Row previousRow = new Row();
        Row currentRow = new Row();
        int numDaysPreviousHighLowPierced = 0;
        int numDays = 0;

        try (FileReader fileReader = new FileReader(csvLocation)) {
            BufferedReader csvReader = new BufferedReader(fileReader);
            csvReader.readLine();

            while ((previousRowString = csvReader.readLine()) != null && (currentRowString = csvReader.readLine()) != null) {
                previousRow.initialiseRow(previousRowString);
                currentRow.initialiseRow(currentRowString);

                if (currentRow.getHigh() >= previousRow.getHigh() && currentRow.getLow() <= previousRow.getLow())
                    numDaysPreviousHighLowPierced ++;
                numDays += 2;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Number of days the current day is an outside bar");
        displayResult(numDaysPreviousHighLowPierced, numDays);
    }

    public static float getTargetCoverPriceByPercent(float percent, float startingPrice, float endingPrice) {
        float range = Math.abs(endingPrice - startingPrice);
        return range * percent;
    }
    private static void displayResult(int numDaysPreviousHighLowPierced, int numDays) {
        System.out.printf("Raw: %d/%d\n", numDaysPreviousHighLowPierced, numDays);
        System.out.printf("Percentage %f %%", ((float) numDaysPreviousHighLowPierced / numDays) * 100);
    }

}
