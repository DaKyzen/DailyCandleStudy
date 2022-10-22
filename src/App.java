import java.io.BufferedReader;
import java.io.FileReader;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD_Daily_20_Rows.csv";

    public static void main(String[] args) {

    }

    public static void analysePreviousDayHighLowPierced() {
        String previousRow = "";
        String currentRow = "";
        double previousDayHigh = 0;
        double previousDayLow = 0;
        double currentDayHigh = 0;
        double currentDayLow = 0;
        int numDaysPreviousHighLowPierced = 0;
        int numDays = 0;
        try (FileReader fileReader = new FileReader(csvLocation)) {
            BufferedReader csvReader = new BufferedReader(fileReader);
            csvReader.readLine();

            while ((previousRow = csvReader.readLine()) != null && (currentRow = csvReader.readLine()) != null) {
                previousDayHigh = getHigh(previousRow);
                previousDayLow = getLow(previousRow);
                currentDayHigh = getHigh(currentRow);
                currentDayLow = getLow(currentRow);

                if (currentDayHigh >= previousDayHigh && currentDayLow <= previousDayLow)
                    numDaysPreviousHighLowPierced ++;
                numDays += 2;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Number of days where the previous day's high and low were pierced");
        System.out.printf("Raw: %d/%d\n", numDaysPreviousHighLowPierced, numDays);
        System.out.printf("Percentage %f %%", ((float) numDaysPreviousHighLowPierced/numDays) * 100);
    }

    public static float getHigh(String row) {
        String[] split = row.split(",");
        String high = split[Column.HIGH.getIndex()];
        return Float.parseFloat(high);
    }
    public static float getLow(String row) {
        String[] split = row.split(",");
        String high = split[Column.LOW.getIndex()];
        return Float.parseFloat(high);
    }


}
