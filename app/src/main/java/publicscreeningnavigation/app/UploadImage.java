package publicscreeningnavigation.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

public class UploadImage extends AsyncTask<Bitmap, Bitmap, Integer> {

    private Activity activity;
    private String image_title;
    int location_id;

    InputStream inputStream;

    public UploadImage(Activity activity,String image_title,int location_id){
        this.activity = activity;
        this.image_title = image_title;
        this.location_id = location_id;
    }

    @Override
    protected Integer doInBackground(Bitmap... params) {
        //postData(params[0]);
        System.out.println(params[0]);
        //System.out.println(params[1]);
        //postData(params[0],params[1]);
        upload(params[0]);
        return 0;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Integer return_value) {
        //showDialog("Downloaded " + result + " bytes");
        Intent i = new Intent(this.activity, locationActivity.class);
        i.putExtra("clickedId",this.location_id);
        this.activity.startActivity(i);
        this.activity.finish();
    }
    public void upload(Bitmap bitmap){

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //compress to which format you want.//!!!!!!!!!!!

        byte[] bitmapdata = stream.toByteArray();

        byte[] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("title",this.image_title));
        nameValuePairs.add(new BasicNameValuePair("location_id",String.valueOf(this.location_id)));

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://141.54.50.201/PublicScreeningNavigation/recieve_image.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String the_string_response = convertResponseToString(response);

            System.out.println("uploaded photo!!!!!!!!!!!"+the_string_response);
            //Toast.makeText(UploadImage.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();
        }catch(Exception e){
            //Toast.makeText(UploadImage.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println("Error in http connection "+e.toString());
        }

    }

    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
        if (contentLength < 0){
        }
        else{
            byte[] data = new byte[512];
            int len = 0;
            try
            {
                while (-1 != (len = inputStream.read(data)) )
                {
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

        }
        return res;
    }

}