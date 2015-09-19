package gpx;

/**
 * Created by artz on 24/12/14.
 */
public class TrackPoint {

    private double latitude;
    private double longitude;

    public TrackPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
