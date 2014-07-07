package publicscreeningnavigation.app;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


public class MapActivity extends FragmentActivity  {

    private GoogleMap map;

    private Marker vip_marker = null;
    private GPSTracker tracker;
    private HashMap<Marker,Integer> marker_locationId = new HashMap<Marker,Integer>();
    private HashMap<Integer,Marker> locationId_marker = new HashMap<Integer,Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getActionBar().hide();

        Bundle extras = getIntent().getExtras();
        int centerId = -1;
        if (extras != null){
            centerId = extras.getInt("centerId");
        }

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        // load all stored locations on map
        for (screeningLocation l : locationStore.sharedLocations()) {
            String tagString = "";
            for (String tag : l.getTags()){
                tagString+= tag+", ";
            }
           tagString = tagString.substring(0,tagString.length()-2);



            Marker m = map.addMarker(new MarkerOptions()
                 .position(l.getPosition())
                 .title(l.getName())
                 .snippet(tagString)
                 .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher)));

            marker_locationId.put(m,l.getID());
            locationId_marker.put(l.getID(),m);


            if(centerId>=0 && centerId == l.getID()){
                this.vip_marker = m;
            }

        }

        //to location on info window click
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                if(marker_locationId.get(marker) != null){
                    int id = marker_locationId.get(marker);
                    Intent i = new Intent(getApplicationContext(), locationActivity.class);
                    i.putExtra("clickedId",id);
                    startActivityForResult(i, 0);
                }
            }
        });

        //own location
        tracker = new GPSTracker(MapActivity.this);
        double lat, lon;
        LatLng own_location = null;
        if (tracker.canGetLocation()) {
            Location current = tracker.getLocation();
            lat = current.getLatitude();
            lon = current.getLongitude();

            own_location = new LatLng(lat,lon);
            Marker meMarker = map.addMarker(new MarkerOptions().position(own_location)
                    .title("Your Position"));

        }
        else{
            tracker.showSettingsAlert();
        }

        //coming from a locationActivity?
        if(centerId>=0) {

            screeningLocation location = filter.getInstance().filterForId(centerId);

            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location.getPosition(), 13));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            if(this.vip_marker != null){
                vip_marker.showInfoWindow();
            }
        }
        else {

            if (own_location != null){
                // Move the camera instantly to hamburg with a zoom of 15.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(own_location, 13));

                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
            }
        }

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

    public void nearestLocation(View view){

        double lat, lon;
        if (tracker.canGetLocation()) {
            Location current = tracker.getLocation();
            lat = current.getLatitude();
            lon = current.getLongitude();

            double min_dist = 99999999;
            int min_location_id = -1;
            LatLng min_latlng = null;
            for (screeningLocation l : locationStore.sharedLocations()) {
                double tmp_lat = l.getPosition().latitude;
                double tmp_lng = l.getPosition().longitude;

                double diffLat = Math.abs(lat - tmp_lat);
                double diffLng = Math.abs(lon - tmp_lng);

                double distance = Math.sqrt(diffLat * diffLat + diffLng * diffLng);
                if (distance < min_dist){
                    min_dist = distance;
                    min_location_id = l.getID();
                    min_latlng = l.getPosition();
                }
            }

            if(min_location_id >=0){
                Marker nearest_marker = locationId_marker.get(min_location_id);

                nearest_marker.showInfoWindow();

                // Move the camera instantly to nearest location
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(min_latlng, 13));

                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

            }



        }
        else{
            tracker.showSettingsAlert();
        }
    }
}
