package publicscreeningnavigation.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {

    private RadioButton nameRadio;
    private RadioButton tagRadio;
    private RadioButton locationRadio;
    private TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);

        nameRadio = (RadioButton)findViewById(R.id.nameRadio);
        tagRadio = (RadioButton)findViewById(R.id.tagRadio);
        locationRadio = (RadioButton)findViewById(R.id.locationRadio);
        searchView = (TextView)findViewById(R.id.editText);
        locationRadio.setChecked(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seach, menu);
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

    public void locationRadioHit(View view){
        locationRadio.setChecked(true);
        tagRadio.setChecked(false);
        nameRadio.setChecked(false);
    }

    public void tagRadioHit(View view){
        Log.d("Button hit","tag radio");
        locationRadio.setChecked(false);
        tagRadio.setChecked(true);
        nameRadio.setChecked(false);

    }

    public void nameRadioHit(View view){
        Log.d("Button hit","name radio");
        locationRadio.setChecked(false);
        tagRadio.setChecked(false);
        nameRadio.setChecked(true);
    }

    public void initSearch(View view){
        CharSequence searchSequence = searchView.getText();
        String searchString = searchSequence.toString();
        boolean forTag = false;
        boolean forLocation = false;
        boolean forName = false;

        if (searchString.length() > 0){

            if (locationRadio.isChecked()){
               forLocation = true;
            }
            else if (tagRadio.isChecked()){
                forTag = true;
            }
            else {
                forName = true;
            }

            Intent i = new Intent(getApplicationContext(), resultActivity.class);
            i.putExtra("searchWord",searchString);
            i.putExtra("forTag",forTag);
            i.putExtra("forName",forName);
            i.putExtra("forLocation",forLocation);
            startActivityForResult(i, 0);

        }
        else {
            //toast: no search string!
        }
    }
}
