public enum BarType {
    DOJI(0.25), MEDIUM(0.5), STRONG(0.75);

    private final double ratio;
    BarType(double ratioOfBodyToCandle) {
        this.ratio = ratioOfBodyToCandle;
    }

    public double getRatio() {
        return ratio;
    }
}
