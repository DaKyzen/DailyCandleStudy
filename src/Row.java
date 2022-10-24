public class Row {
    private String gmtTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private ClosingDirection closingDirection;
    private BarType barType;

    public Row(String stringRow) {
        initialiseRow(stringRow);
    }

    public Row() {
        initialiseRow("0,0,0,0,0,0");
    }

    public void initialiseRow(String stringRow) {
        String[] split = stringRow.split(",");
        this.gmtTime = split[Column.GMT_TIME.getIndex()];
        this.open = Double.parseDouble(split[Column.OPEN.getIndex()]);
        this.high = Double.parseDouble(split[Column.HIGH.getIndex()]);
        this.low = Double.parseDouble(split[Column.LOW.getIndex()]);
        this.close = Double.parseDouble(split[Column.CLOSE.getIndex()]);
        this.volume = Double.parseDouble(split[Column.VOLUME.getIndex()]);
        this.closingDirection = getClosingDirection();
        this.barType = getBarType();
    }

    public String getGmtTime() {
        return gmtTime;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public ClosingDirection getClosingDirection() {
        return this.open > this.close ? ClosingDirection.BULLISH : this.open == this.close ? ClosingDirection.UNDECIDED : ClosingDirection.BEARISH;
    }

    public BarType getBarType() {
        double range = this.getPriceRange();
        double bodyRange = Math.abs(this.open - this.close);
        double ratio = bodyRange / range;
        BarType barType = BarType.STRONG;
        if (ratio < BarType.STRONG.getRatio() && ratio > BarType.DOJI.getRatio())
            barType = BarType.MEDIUM;
        else if (ratio <= BarType.DOJI.getRatio())
            barType = BarType.DOJI;
        return barType;
    }

    public double getPriceRange() {
        return Math.abs(this.high - this.low);
    }
}
