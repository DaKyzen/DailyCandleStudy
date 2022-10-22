import java.io.BufferedReader;
import java.io.FileReader;

public class App {

    private static String csvLocation = "C:\\Users\\kldep\\OneDrive\\Forex\\EURUSD_Daily_20_Rows.csv";

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader(csvLocation)) {
            BufferedReader csvReader = new BufferedReader(fileReader);
            csvReader.readLine();
            String previousRow = "";
            String currentRow = "";
            double previousDayHigh = 0;
            double previousDayLow = 0;
            double currentDayHigh = 0;
            double currentDayLow = 0;
            int numDaysPreviousHighLowPierced = 0;
            while ((previousRow = csvReader.readLine()) != null && (currentRow = csvReader.readLine()) != null) {
                System.out.println(previousRow);
                System.out.println(currentRow);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static float getHigh(String row) {
        String[] split = row.split(",");
        String high = split[2];
        return Float.parseFloat(high);
    }
    public static float getLow(String row) {
        String[] split = row.split(",");
        String high = split[3];
        return Float.parseFloat(high);
    }


}
