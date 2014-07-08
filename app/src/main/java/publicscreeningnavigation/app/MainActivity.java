package publicscreeningnavigation.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements changeIPDialogFragment.changeIPListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
        new GetData("locations").execute();
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

    public void optionsPopup(View view){
        DialogFragment newFragment = new changeIPDialogFragment();
        String tag = "tagPopUp";
        newFragment.show(getSupportFragmentManager(),tag);
    }

    public void onDialogPositiveClick(DialogFragment dialog){
        changeIPDialogFragment frag = (changeIPDialogFragment) dialog;
        String ip = frag.getCurrentInputText();
        locationStore.getInstance().setServerAddress(ip);
    }

    public void onDialogNegativeClick(DialogFragment dialog){
        Context context = getApplicationContext();
        CharSequence text = "IP Change cancelled";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        return;
    }

}
