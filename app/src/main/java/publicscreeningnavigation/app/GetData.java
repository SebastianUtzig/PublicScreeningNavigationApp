package publicscreeningnavigation.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GetData extends AsyncTask<Void, Void, Void> {

    private String table_name;
    private Activity activity;

    public GetData(String name,Activity activity){
        this.table_name = name;
        this.activity = activity;
    }


    @Override
    protected Void doInBackground(Void... params) {
        getData();
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Long result) {
    }
    private void getData() {

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://141.54.50.201/PublicScreeningNavigation/get_data.php");
        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("name", String.valueOf(this.table_name)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if(response != null) {
                String line = "";
                InputStream inputstream = response.getEntity().getContent();
                line = convertStreamToString(inputstream);

                if(line.length()>0) {

                    //add locations to store:
                    String[] locations = line.split(";");

                    System.out.println(Arrays.deepToString(locations));

                    for (String s : locations) {
                        String[] location = s.split("%");
                        System.out.println(Arrays.deepToString(location));
                        String name = location[3];
                        int id = Integer.parseInt(location[0]);
                        double lat = Double.parseDouble(location[1]);
                        double lon = Double.parseDouble(location[2]);
                        String description = location[4];
                        screeningLocation new_location = new screeningLocation(name, id, new LatLng(lat, lon));
                        new_location.setDescription(description);

                        ///////tags
                        if(location.length>5){
                            for(int tag_index = 5;tag_index<location.length;++tag_index){
                                new_location.addTag(location[tag_index]);
                            }
                        }
                        // add local copy
                        locationStore.addLocation(new_location);

                    }
                }

            } else {
                Toast.makeText(this.activity.getApplicationContext(), "Unable to get data from server!", Toast.LENGTH_LONG).show();
            }

        } catch (ClientProtocolException e) {
            Toast.makeText(this.activity.getApplicationContext(), "Unable to get data from server!", Toast.LENGTH_LONG).show();

        }
        catch (IOException e) {
            Toast.makeText(this.activity.getApplicationContext(), "Unable to get data from server!", Toast.LENGTH_LONG).show();
        }

    }

    private String convertStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            Toast.makeText(this.activity, "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }

}
