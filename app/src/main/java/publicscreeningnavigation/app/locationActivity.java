package publicscreeningnavigation.app;

import android.content.Intent;
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
        /*headerName = (TextView)findViewById(R.id.textView);
        headerName.setText(hostedLocation.getName());*/
        ActionBar ab = getSupportActionBar();
        ab.setTitle(hostedLocation.getName());


        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(getApplicationContext(), MapActivity.class);
                startActivityForResult(i, 0);
            }
        });

        Marker weimar_reservebank = map.addMarker(new MarkerOptions()
                .position(hostedLocation.getPosition())
                .title(hostedLocation.getName())
                .snippet(hostedLocation.tagsToStr())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

        // Move the camera instantly to hamburg with a zoom of 15.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(hostedLocation.getPosition(), 15));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);


        //load images here:
        // getimages
        new GetImages((ImageView) findViewById(R.id.imageView),hostedLocation.getID()).execute();

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

}
