package publicscreeningnavigation.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity  {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    static final LatLng WEIMAR = new LatLng(50.9794934, 11.323543900000004);
    static final LatLng WEIMAR_HALL = new LatLng(50.9837403, 11.325099000000023);
    static final LatLng WEIMAR_RESERVEBANK = new LatLng(50.97411899999999, 11.32747919999997);

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        /*Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));*/
        /*Marker kiel = map.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));*/
        Marker weimar = map.addMarker(new MarkerOptions().position(WEIMAR)
                .title("Weimar"));
        Marker weimar_hall = map.addMarker(new MarkerOptions()
                .position(WEIMAR_HALL)
                .title("Weimar Hall")
                .snippet("indoor, no smoking")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));
        Marker weimar_reservebank = map.addMarker(new MarkerOptions()
                .position(WEIMAR_RESERVEBANK)
                .title("Reservebank")
                .snippet("indoor, sports bar")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(WEIMAR, 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
