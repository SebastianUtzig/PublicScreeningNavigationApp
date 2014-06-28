package publicscreeningnavigation.app;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class uploadActivity extends FragmentActivity implements addTagDialogFragment.addTagDialogListener{

    TextView collectedTags;
    String collectedTagsAsString = "None";
    boolean hasOverwrittenTags = false;
    EditText name;
    EditText description;
    GPSTracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        collectedTags = (TextView) findViewById(R.id.tagsText);
        collectedTags.setText(collectedTagsAsString);
        name = (EditText) findViewById(R.id.nameField);
        description = (EditText) findViewById(R.id.descriptionField);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewTag(View view){
        DialogFragment newFragment = new addTagDialogFragment();
        String tag = "tagPopUp";
        Bundle args = new Bundle();
        args.putString("tags",collectedTagsAsString);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(),tag);
    }

    public void onDialogPositiveClick(DialogFragment dialog){
        addTagDialogFragment adder = (addTagDialogFragment)dialog;
        String newTags = adder.getCurrentInputText();

        if (collectedTagsAsString.contains(newTags)){
            Context context = getApplicationContext();
            CharSequence text = "The entered tag already exists!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (hasOverwrittenTags)
            collectedTagsAsString= collectedTagsAsString+", "+newTags;
        else
            collectedTagsAsString = newTags;

        collectedTags.setText(collectedTagsAsString);
        hasOverwrittenTags = true;

    }

    public void onDialogNegativeClick(DialogFragment dialog){
        Context context = getApplicationContext();
        CharSequence text = "Tag adding canceled";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return;
    }

    public void startUpload(View view){
        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();
        if (nameString.equals("Location Name")){
            Context context = getApplicationContext();
            CharSequence text = "Please give a name to your location!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        if (descriptionString == "Description"){
            descriptionString = "No description available!";
        }



        tracker = new GPSTracker(uploadActivity.this);
        double lat, lon;
        if (tracker.canGetLocation()){
            Location current = tracker.getLocation();
            lat = current.getLatitude();
            lon = current.getLongitude();

         ArrayList<String> tags = new ArrayList<String>();
         if (collectedTagsAsString.contains(",")) {

             String[] parts = collectedTagsAsString.split(", ");
             for (String tag : parts) {
                 tags.add(tag);
             }
         }
         else {
             tags.add(collectedTagsAsString);
         }


         new PostData(lat,lon,nameString,descriptionString,tags).execute();
         int id = filter.getInstance().findHighestActiveId()+1;
         screeningLocation location = new screeningLocation(nameString, id, new LatLng(lat, lon));
         location.addTags(tags);
         location.setDescription(descriptionString);

         locationStore.addLocation(location);
        }
        else {
            tracker.showSettingsAlert();
        }



    }




}
