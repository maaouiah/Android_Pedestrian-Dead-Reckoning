package gpx;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artz on 24/12/14.
 */
public class TrackSeg {

    public List<TrackPoint> trackPoints;
    private LatLngBounds.Builder bounds;

    public TrackSeg()
    {
        trackPoints = new ArrayList<TrackPoint>();
    }

    public void addTrackPoint(TrackPoint trackPoint)
    {
        trackPoints.add(trackPoint);
    }

    public void removeTrackPoint(TrackPoint trackPoint)
    {
        trackPoints.remove(trackPoint);
    }

    public List<TrackPoint> getTrackPoints()
    {
        return trackPoints;
    }
}
