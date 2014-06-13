package publicscreeningnavigation.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

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
        CharSequence searchString = searchView.getText();
        if (searchString.length() < 0){
            //do search
        }
        else {
            //toast: no search string!
        }
    }
}
