package publicscreeningnavigation.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class resultActivity extends ActionBarActivity {

    boolean forName;
    boolean forLocation;
    boolean forTag;
    String searchWord = new String();
    ArrayList<screeningLocation> filteredResults = new ArrayList<screeningLocation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ListView listview = (ListView) findViewById(R.id.list);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            forName = extras.getBoolean("forName");
            forLocation = extras.getBoolean("forLocation");
            forTag = extras.getBoolean("forTag");
            searchWord = extras.getString("searchWord");
        }

        if (forName){
            filterForNames();
        } else if (forLocation){
            filterForLocation();
        } else {
            filterForTag();
        }

        ArrayList<String> names = getNamesFromResults();
        Log.d("Size of the names", String.valueOf(names.size()));
        final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.styled_list_element, names);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                screeningLocation clicked = filteredResults.get(position);

                Intent i = new Intent(getApplicationContext(), locationActivity.class);
                i.putExtra("clickedId",clicked.getID());
                startActivityForResult(i, 0);
            }

        });
    }

    private void filterForNames() {
        filteredResults = filter.getInstance().filterLocationsForName(searchWord);
    }

    private void filterForLocation() {
        Toast.makeText(getApplicationContext(),
                "Filtering for location is not yet implemented. Displaying all locations.", Toast.LENGTH_LONG).show();
        copyLocations();
    }

    private void filterForTag() {
        filteredResults = filter.getInstance().filterLocationsForTag(searchWord);
    }

    private void copyLocations() {
      filteredResults = filter.getInstance().filterLocationsForNoPattern();
    }

    private ArrayList<String> getNamesFromResults() {
        ArrayList<String> names = new ArrayList<String>();
        for (screeningLocation l : filteredResults){
            names.add(l.getName());
        }

        return names;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
