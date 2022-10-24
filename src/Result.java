public class Result {
    private int totalNumDays = 0;
    private int numDaysPassedTest = 0;

    public Result(int totalNumDays, int numDaysPassedTest) {
        this.totalNumDays = totalNumDays;
        this.numDaysPassedTest = numDaysPassedTest;
    }

    public int getTotalNumDays() {
        return totalNumDays;
    }

    public int getNumDaysPassedTest() {
        return numDaysPassedTest;
    }

    public float getPercentage() {
        return (float) numDaysPassedTest / totalNumDays * 100;
    }

    private void display(String message) {
        System.out.println(message);
        message.chars().forEach(c -> System.out.print("-"));
        System.out.println();
        System.out.printf("Raw: %d/%d\n", this.numDaysPassedTest, this.totalNumDays);
        System.out.printf("Percentage %f %%", this.getPercentage());
    }

}
