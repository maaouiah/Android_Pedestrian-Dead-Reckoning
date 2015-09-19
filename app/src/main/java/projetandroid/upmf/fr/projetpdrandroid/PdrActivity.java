package projetandroid.upmf.fr.projetpdrandroid;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;

import pdr.DeviceAttitudeHandle;
import pdr.StepDetectionHandler;
import pdr.StepPositioningHandler;
import viewer.GoogleMapTracer;


public class PdrActivity extends Activity implements StepDetectionHandler.StepDetectionListener, OnMapClickListener {

    private SensorManager sm            ;
    private GoogleMap gMap              ;
    private GoogleMapTracer gmt         ;
    private MapFragment mf              ;
    private DeviceAttitudeHandle dah    ;
    private StepDetectionHandler sdh    ;
    private StepPositioningHandler sph  ;
    private float step = 0.8F           ;
    private boolean ison = false        ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdr);

        // Création du fragement map
        mf      = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        // Création de la google map
        gMap    = mf.getMap();
                  // Ajout du listner
                  gMap.setOnMapClickListener(this);

        // instantiation du sensorManager
        sm      = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sdh     = new StepDetectionHandler(sm);
                  sdh.setSterpDetectionListner(this);

        dah     = new DeviceAttitudeHandle(sm);
        sph     = new StepPositioningHandler();

        //----

        gMap.setMyLocationEnabled(true);

        /* création d'une position du début */
        Location maLocation = new Location("");
        maLocation.setLatitude(45.188529);
        maLocation.setLongitude(5.724523);


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(maLocation.getLatitude(), maLocation.getLongitude()),14));
        gmt = new GoogleMapTracer(gMap);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdr, menu);
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


    @Override
    public void onMapClick(LatLng latLng) {

            // Création du location à partir du point cliqué sur la map
            Location loc = new Location("");
            loc.setLatitude( latLng.latitude );
            loc.setLongitude( latLng.longitude );
            //zoom sur la nouvelle position
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()),19));
            // on instantie un nouveau segment
            gmt.newSegment(latLng);
            sph.setMCurrentLocation(loc);

            // Variable boolean utilisée pour déclacher le programme
            ison = true ;
    }

    @Override
    public void onNewStep(float stepSize) {
            if( ison) {
                Location loc;
                float pas = dah.getOrientationYaw();
                loc = sph.computeNextStep((float)0.8, pas);
                sph.setMCurrentLocation(loc);
                LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, gMap.getCameraPosition().zoom));
                gmt.newPoint(ll);

            }

    }

    @Override
    protected void onPause() {
        sdh.stop();
        dah.stop();
        super.onPause();
    }
    @Override
    protected void onResume() {
        sdh.start();
        dah.start();
        super.onResume();
    }


}
