package publicscreeningnavigation.app;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostData extends AsyncTask<Void, Void, Void> {

    private double lat;
    private double lon;
    private String name;
    private ArrayList<String> tags;

    public PostData(double lat, double lon, String name,ArrayList<String> tags){
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.tags = tags;
    }


    @Override
    protected Void doInBackground(Void... params) {
        postData(this.lat,this.lon,this.name);

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
    private void postData(double la, double lo, String name) {

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://192.168.1.59/MIS/project/post_data.php");
        //HttpPost httppost = new HttpPost("http://127.0.0.1/MIS/location_server/recieve_data.php");

        //HttpPost httppost = new HttpPost("http://192.168.1.59:6081/"+la+"/"+lo);

        //System.out.println("posted data!!!!!!!!!!!!!"+la+" "+lo);
        //serverIpText.setText("posted data!!!!!!!!!!!!!"+la+" "+lo);

        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("lat", String.valueOf(la)));
            nameValuePairs.add(new BasicNameValuePair("lon", String.valueOf(lo)));
            nameValuePairs.add(new BasicNameValuePair("name", String.valueOf(name)));

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



        } catch (ClientProtocolException e) {
            //Toast.makeText(this, "Error", 5000).show();

        }
        catch (IOException e) {
            //Toast.makeText(this, "Error", 5000).show();
        }
    }

}
