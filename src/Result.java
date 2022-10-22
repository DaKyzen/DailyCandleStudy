public class Result {
    int totalNumDays = 0;
    int numDaysPassedTest = 0;

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

}
