package publicscreeningnavigation.app;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


public class locationActivity extends ActionBarActivity {

    private screeningLocation hostedLocation;
    private TextView headerName;
    private TextView descriptionContent;
    private TextView tagContent;
    private GoogleMap map;
    private GPSTracker tracker;

    private void changeIntent(){
        Intent i = new Intent(getApplicationContext(), MapActivity.class);
        i.putExtra("centerId",hostedLocation.getID());
        startActivityForResult(i, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Bundle extras = getIntent().getExtras();
        int identifier = 1337;
        if (extras != null){
            identifier = extras.getInt("clickedId");
        }

        descriptionContent = (TextView)findViewById(R.id.descrContent);
        tagContent = (TextView)findViewById(R.id.tagsContent);


        hostedLocation = filter.getInstance().filterForId(identifier);

        descriptionContent.setText(hostedLocation.getDescription());
        tagContent.setText(hostedLocation.tagsToStr());

        ActionBar ab = getSupportActionBar();
        ab.setTitle(hostedLocation.getName());

        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        //interaction events with map preview lead to MapActivity
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                changeIntent();
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                changeIntent();
                return true;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                changeIntent();
            }
        });


        Marker marker = map.addMarker(new MarkerOptions()
                .position(hostedLocation.getPosition())
                .title(hostedLocation.getName())
                .snippet(hostedLocation.tagsToStr())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        // Move the camera instantly to shown location
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(hostedLocation.getPosition(), 13));

        // start image loading asynchronously here
        new GetImages((ImageView) findViewById(R.id.imageView),hostedLocation.getID()).execute();

        //interaction events with map preview lead to MapActivity
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback(){
            @Override
            public void onMapLoaded() {
                map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener(){
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        changeIntent();
                    }
                });
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.loation, menu);
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

    public void navigate(View view){

        double hosted_lat = hostedLocation.getPosition().latitude;
        double hosted_long = hostedLocation.getPosition().longitude;

        tracker = new GPSTracker(locationActivity.this);
        double lat, lon;
        if (tracker.canGetLocation()) {
            Location current = tracker.getLocation();
            lat = current.getLatitude();
            lon = current.getLongitude();

            // routing with google maps app
            Intent intent = new Intent( Intent.ACTION_VIEW,
                    Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d" +
                            "&saddr="+lat+", "+lon+"&daddr="+hosted_lat+", "+hosted_long+"&hl=zh&t=m&dirflg=w"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK&Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);

        }else{
            tracker.showSettingsAlert();
        }

    }

}
