package lib.kalu.czxing.jni;

import androidx.annotation.Keep;

@Keep
public class BarcodeResult {

    private BarcodeFormat format;
    private String text;
    private float[] points;
    private int scanType;

    BarcodeResult(BarcodeFormat format, String text) {
        this.format = format;
        this.text = text;
    }

    public BarcodeResult(String content, int formatIndex, float[] points, int scanType) {
        this.text = content;
        if (formatIndex < 0) {
            this.format = BarcodeFormat.QRCode;
        } else {
            this.format = BarcodeFormat.values()[formatIndex];
        }
        this.points = points;
        this.scanType = scanType;
    }

    public void setPoint(float[] lists) {
        points = lists;
    }

    public BarcodeFormat getFormat() {
        return format;
    }

    public String getText() {
        return text;
    }

    public float[] getPoints() {
        return points;
    }

    public int getScanType() {
        return scanType;
    }

    @Override
    public String toString() {
        return "text: " + text +
                "\nformat: " + getFormat() +
                "\nscanType: " + getScanType() +
                "\npoints: " + getPointsString();
    }

    private String getPointsString() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (float list : points) {
            i++;
            stringBuilder.append(list).append("  ");
            if (i % 2 == 0) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
