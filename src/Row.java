public class Row {
    private String gmtTime;
    private float open;
    private float high;
    private float low;
    private float close;
    private float volume;

    public Row(String stringRow) {
        initialiseRow(stringRow);
    }

    public Row() {
        initialiseRow("0,0,0,0,0,0");
    }

    public void initialiseRow(String stringRow) {
        String[] split = stringRow.split(",");
        this.gmtTime = split[Column.GMT_TIME.getIndex()];
        this.open = Float.parseFloat(split[Column.OPEN.getIndex()]);
        this.high = Float.parseFloat(split[Column.HIGH.getIndex()]);
        this.low = Float.parseFloat(split[Column.LOW.getIndex()]);
        this.close = Float.parseFloat(split[Column.CLOSE.getIndex()]);
        this.volume = Float.parseFloat(split[Column.VOLUME.getIndex()]);
    }

    public String getGmtTime() {
        return gmtTime;
    }

    public float getOpen() {
        return open;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getClose() {
        return close;
    }

    public float getVolume() {
        return volume;
    }
}
