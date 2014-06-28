package publicscreeningnavigation.app;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PostData extends AsyncTask<Void, Void, Void> {

    private double lat;
    private double lon;
    private String name;
    private String description;
    private ArrayList<String> tags;

    public PostData(double lat, double lon, String name,String description,ArrayList<String> tags){
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.tags = tags;
        this.description = description;
    }


    @Override
    protected Void doInBackground(Void... params) {
        postData();

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
    private void postData() {

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        //HttpPost httppost = new HttpPost("http://192.168.1.59/MIS/project/post_data.php");
        HttpPost httppost = new HttpPost("http://192.168.1.59/PublicScreeningNavigation/post_data.php");
        //HttpPost httppost = new HttpPost("http://127.0.0.1/MIS/location_server/recieve_data.php");

        //HttpPost httppost = new HttpPost("http://192.168.1.59:6081/"+la+"/"+lo);

        //System.out.println("posted data!!!!!!!!!!!!!"+la+" "+lo);
        //serverIpText.setText("posted data!!!!!!!!!!!!!"+la+" "+lo);

        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(this.lat)));
            nameValuePairs.add(new BasicNameValuePair("lon", String.valueOf(this.lon)));
            nameValuePairs.add(new BasicNameValuePair("name", String.valueOf(this.name)));
            nameValuePairs.add(new BasicNameValuePair("description", String.valueOf(this.description)));

            //tags:
            String tags = "";
            for(String tag : this.tags){
                tags+=tag+" ";
            }
            tags = tags.substring(0,tags.length()-1);
            nameValuePairs.add(new BasicNameValuePair("tags",tags));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


            // Execute HTTP Post Request
            //HttpResponse response = httpclient.execute(htget);
            HttpResponse response = httpclient.execute(httppost);
            //String resp = response.getStatusLine().toStrinkatyg(); try this now
            //Toast.makeText(this, resp, 5000).show();

            //also add it to local locationStore this time
            String line = "";
            InputStream inputstream = response.getEntity().getContent();
            line = convertStreamToString(inputstream);
            int insert_index = Integer.parseInt(line);
            screeningLocation new_location = new screeningLocation(this.name, insert_index, new LatLng(this.lat, this.lon));
            new_location.setDescription(this.description);
            for(String tag : this.tags){
                new_location.addTag(tag);
            }
            locationStore.addLocation(new_location);




        } catch (ClientProtocolException e) {
            //Toast.makeText(this, "Error", 5000).show();

        }
        catch (IOException e) {
            //Toast.makeText(this, "Error", 5000).show();
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
            //Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }

}
