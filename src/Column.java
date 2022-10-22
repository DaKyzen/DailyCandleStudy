public enum Column {
    GMT_TIME(0), OPEN(1), HIGH(2), LOW(3), CLOSE(4), VOLUME(5);

    private final int index;
    Column(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
