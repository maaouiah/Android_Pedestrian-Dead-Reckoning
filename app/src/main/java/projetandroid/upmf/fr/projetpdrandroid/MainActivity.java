package projetandroid.upmf.fr.projetpdrandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import gpx.Parser;
import gpx.Track;
import gpx.TrackPoint;
import gpx.TrackSeg;
import gpx.GPX;

public class MainActivity extends Activity implements View.OnClickListener {


    private GoogleMap gMap;
    private LatLngBounds.Builder llbBuilder =  LatLngBounds.builder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button button_GPX = (Button) findViewById(R.id.button);
        Button button_PDR = (Button) findViewById(R.id.button2);

        /* on crée un listner sur les buttons */
        button_GPX.setOnClickListener(this);
        button_PDR.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View v) {
        Intent intent;
        int id = v.getId();

        if (id == R.id.button) {
            intent = new Intent(MainActivity.this, GpxActivity.class);
            startActivity(intent);
        }

        if (id == R.id.button2) {
            intent = new Intent(MainActivity.this, PdrActivity.class);
            startActivity(intent);
        }

    }
}
