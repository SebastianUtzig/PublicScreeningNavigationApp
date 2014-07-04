package publicscreeningnavigation.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GetImages extends AsyncTask<Void, Void, Integer> {

    private ImageView image_view = null;
    private Bitmap bitmap = null;
    private int location_id;

    public GetImages(ImageView iv,int location_id){
        this.location_id = location_id;
        this.image_view = iv;
    }


    @Override
    protected Integer doInBackground(Void... params) {
        getImages();
        return 1;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Integer result) {
        //showDialog("Downloaded " + result + " bytes");
        if(this.bitmap != null) {
            int nh = (int) ( this.bitmap.getHeight() * (512.0 / this.bitmap.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(this.bitmap, 512, nh, true);
            this.image_view.setImageBitmap(scaled);
            //this.image_view.setImageBitmap(bitmap);
        }
    }

    private void getImages() {

        String path = getImagePath();

        if(path != null) {
           /* this.bitmap = DownloadImage(
                    "http://192.168.1.59/PublicScreeningNavigation/img/new_photo.jpg");*/
           System.out.println(path);

           this.bitmap = DownloadImage(path);
        }
        else{
            System.out.println("There is no image for this location!!!!");
        }
    }

    private String getImagePath(){
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();

        //HttpPost httppost = new HttpPost("http://192.168.1.59/MIS/project/get_data.php");
        HttpPost httppost = new HttpPost("http://141.54.50.201/PublicScreeningNavigation/get_data.php");
        try {

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("name", "images"));
            nameValuePairs.add(new BasicNameValuePair("location_id", String.valueOf(this.location_id)));
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

                    return "http://141.54.50.201/PublicScreeningNavigation/"+locations[0];
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
        //String path = "http://192.168.1.59/PublicScreeningNavigation/";
        return null;
    }


    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");
        }
        return in;
    }

    private Bitmap DownloadImage(String URL)
    {
        Bitmap bitmap = null;
        InputStream in = null;
        InputStream in2 = null;
        try {
            in = OpenHttpConnection(URL);

            //new:
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 400, 400);

            in2 = OpenHttpConnection(URL);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(in2, null, options);


            //bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            //TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //////////////////////////////////////////////////////////post response
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
