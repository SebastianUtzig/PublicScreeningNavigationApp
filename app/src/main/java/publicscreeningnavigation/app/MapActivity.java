package publicscreeningnavigation.app;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


public class MapActivity extends FragmentActivity  {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    static final LatLng WEIMAR = new LatLng(50.9794934, 11.323543900000004);
    static final LatLng WEIMAR_HALL = new LatLng(50.9837403, 11.325099000000023);
    static final LatLng WEIMAR_RESERVEBANK = new LatLng(50.97411899999999, 11.32747919999997);

    private GoogleMap map;

    private Marker vip_marker = null;

    private HashMap<Marker,Integer> marker_locationId = new HashMap<Marker,Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();
        int centerId = -1;
        if (extras != null){
            centerId = extras.getInt("centerId");
        }

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
        /*Marker weimar_hall = map.addMarker(new MarkerOptions()
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
                        .fromResource(R.drawable.ic_launcher)));*/

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
        else{
            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(WEIMAR, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
        }


        //working route with gmaps app
        /*Intent intent = new Intent( Intent.ACTION_VIEW,
                Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d" +
                        "&saddr=50.9837403, 11.325099000000023&daddr=50.97411899999999, 11.32747919999997&hl=zh&t=m&dirflg=w"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK&Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);*/


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
        System.out.println("bla");
    }
}
