package gpx;


import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artz on 24/12/14.
 */

public class GPX {

    public List<Track> tracks;
    private LatLngBounds.Builder bounds;

    public GPX()
    {
        tracks = new ArrayList<Track>();
    }

    public void addTrack(Track track)
    {
        tracks.add(track);
    }


    public void removeTrack(Track track)
    {
        tracks.remove(track);
    }

    public List<Track> getTracks()
    {
        return tracks;
    }


}
