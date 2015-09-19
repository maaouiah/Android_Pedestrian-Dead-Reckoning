package projetandroid.upmf.fr.projetpdrandroid;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import gpx.GPX;
import gpx.Parser;
import gpx.Track;
import gpx.TrackPoint;
import gpx.TrackSeg;


public class GpxActivity extends Activity {

    /* le google map */
    private GoogleMap gMap;
    /* le bound */
    private LatLngBounds.Builder llBuilder =  LatLngBounds.builder();
    /* Le GPX */
    private GPX gpx = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpx);

        /*On charge la map depuis l'activity_GPX */
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        /* On initie le google map */
        gMap = mapFragment.getMap();

        /* activé les options de la google map */
        gMap.setMyLocationEnabled(true);

        /*

        // lattitude langitude grenoble
        LatLng grenoble = new LatLng(45.188529,5.724523);

        // Zoom sur la ville
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(grenoble, 14));

        // Ajout d'un marker
        gMap.addMarker(new MarkerOptions().title("Grenoble").snippet("Centre ville grenoble") .position(grenoble));

        */

        try {

            InputStream data = getAssets().open("bikeandrun.gpx");

            try {
                gpx = Parser.parse(data);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            /* On boucle sur tous les tracks du fichier XML */
            for (Track t : gpx.getTracks())
            {

                /* On cherche tous les TrackSeg contenus dans un track */
                for (TrackSeg ts : t.getTrackSegs())
                {
                    //Polyline d'initiation
                    PolylineOptions chemin = new PolylineOptions();
                                    chemin.color(Color.BLUE);
                                    chemin.width(3);

                    /* On cherche tous les point dans un trackseg */
                    for (TrackPoint tp : ts.getTrackPoints())
                    {

                        /* À partir des données récupérés ont crée  un nouveau point */
                        LatLng point = new LatLng(tp.getLatitude(), tp.getLongitude());

                        /* On l'ajoute au polyline */
                        chemin.add(point);
                        llBuilder.include(point);
                    }

                    /* on ajoute le polyline récemment crée à google map */
                    gMap.addPolyline(chemin);

                    // On zoom sur la map si le builder est différent de null
                    if (this.llBuilder != null) {

                        /* On ajoute un lister sur la map*/
                        gMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                                /* On implémente onCaméraChange pour zoomer et recadrer la vue */
                                @Override
                                public void onCameraChange(CameraPosition arg0) {
                                    gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(llBuilder.build(), 20));
                                    gMap.setOnCameraChangeListener(null);
                                }
                        });

                    }
                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gpx, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
