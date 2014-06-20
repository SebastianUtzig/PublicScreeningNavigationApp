package publicscreeningnavigation.app;

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

    public GetData(String name){
        this.table_name = name;
    }


    @Override
    protected Void doInBackground(Void... params) {
        getData(this.table_name);

        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
    private void getData(String table_name) {

        /*List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("name", this.table_name));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");


        HttpGet httpget = new HttpGet("http://192.168.1.59/MIS/project/get_data.php/"+paramString);
        System.out.println("http://192.168.1.59/MIS/project/get_data.php?"+paramString);
        try {



            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpget);
            if(response != null) {
                String line = "";
                InputStream inputstream = response.getEntity().getContent();
                line = convertStreamToString(inputstream);

                System.out.println(line);
                //Toast.makeText(this, line, Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
            }
        } catch (ClientProtocolException e) {
            //Toast.makeText(this, "Caught ClientProtocolException", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Toast.makeText(this, "Caught IOException", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //Toast.makeText(this, "Caught Exception", Toast.LENGTH_SHORT).show();
        }*/


        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost("http://192.168.1.59/MIS/project/get_data.php");

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

                    System.out.println(line);
                    //Toast.makeText(this, line, Toast.LENGTH_SHORT).show();

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
                        locationStore.addLocation(new_location);


                        ///////tag post
                        /*HttpPost httppost2 = new HttpPost("http://192.168.1.59/MIS/project/get_data.php");

                        try {

                            // Add your data
                            List<NameValuePair> nameValuePairs2 = new ArrayList<NameValuePair>(2);
                            nameValuePairs2.add(new BasicNameValuePair("name","tags"));
                            httppost2.setEntity(new UrlEncodedFormEntity(nameValuePairs2));

                            // Execute HTTP Post Request
                            HttpResponse response2 = httpclient.execute(httppost2);
                            if(response2 != null) {
                                String line = "";
                                InputStream inputstream2 = response2.getEntity().getContent();
                                line = convertStreamToString(inputstream2);

                                if(line.length()>0) {}


                            } else {
                                //Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
                            }
                        } catch (ClientProtocolException e) {
                            //Toast.makeText(this, "Error", 5000).show();

                        }
                        catch (IOException e) {
                            //Toast.makeText(this, "Error", 5000).show();
                        }



                        */
                        ////////////////////////////////


                    }
                }







            } else {
                //Toast.makeText(this, "Unable to complete your request", Toast.LENGTH_LONG).show();
            }


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
