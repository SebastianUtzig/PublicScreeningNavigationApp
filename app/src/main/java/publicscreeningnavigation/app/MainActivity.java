package publicscreeningnavigation.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        new GetData("locations",this).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void openMapView(View view){
        Intent i = new Intent(getApplicationContext(), MapActivity.class);
        startActivityForResult(i, 0);
    }

    public void openSearchView(View view){
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        startActivityForResult(i, 0);
    }

    public void openUploadView(View view) {
        Intent i = new Intent(getApplicationContext(), uploadActivity.class);
        startActivityForResult(i,0);
    }
}
