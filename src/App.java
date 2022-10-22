import java.io.BufferedReader;
import java.io.FileReader;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD_Daily_20_Rows.csv";

    public static void main(String[] args) {

    }

    public static void analysePreviousDayHighLowPierced() {
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


        displayResult(numDaysPreviousHighLowPierced, numDays);
    }

    private static void displayResult(int numDaysPreviousHighLowPierced, int numDays) {
        System.out.println("Number of days where the previous day's high and low were pierced");
        System.out.printf("Raw: %d/%d\n", numDaysPreviousHighLowPierced, numDays);
        System.out.printf("Percentage %f %%", ((float) numDaysPreviousHighLowPierced / numDays) * 100);
    }


}
