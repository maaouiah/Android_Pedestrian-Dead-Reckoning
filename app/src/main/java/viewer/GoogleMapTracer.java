package viewer;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

/**
 * Created by artz on 27/12/14.
 */
public class GoogleMapTracer {

    private GoogleMap gMap ;
    private Polyline  pl ;

    public GoogleMapTracer( GoogleMap map ){

        this.gMap = map ;
        pl = gMap.addPolyline(new PolylineOptions());
    }

    /**
     * Nouvel segment
     * @param point
     */
    public void newSegment( LatLng point  ){

        markerFin();
        PolylineOptions lineOptions = new PolylineOptions().width(5).color(Color.BLUE).add(point);
        pl = gMap.addPolyline(lineOptions);
        markerDebut(point);
    }

    /**
     * Nouveau point
     * @param point
     */
    public void newPoint( LatLng point ){

        List<LatLng> points = pl.getPoints();
        points.add(point);
        pl.setPoints(points);

    }

    /**
     * Marker Fin
     */
    public void markerFin(){

        List<LatLng> listePoints = pl.getPoints();
        int indice = 0 ;
        if( listePoints.size() > 0 ){

            indice = listePoints.size() - 1 ;
            LatLng dernierPoint = listePoints.get( indice );
            MarkerOptions marketfin = new MarkerOptions();
            marketfin.position(dernierPoint);
            marketfin.title(" Fin !");
            gMap.addMarker(marketfin);

        }
    }

    /**
     * Marker debut
     * @param point
     */
    public void markerDebut(LatLng point){

        MarkerOptions marketDebut = new MarkerOptions();
        marketDebut.position(point);
        marketDebut.title(" Début !");
        gMap.addMarker(marketDebut);

    }


}
