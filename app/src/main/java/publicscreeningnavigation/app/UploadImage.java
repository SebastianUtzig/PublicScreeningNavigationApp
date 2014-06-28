package publicscreeningnavigation.app;

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

public class UploadImage extends AsyncTask<Bitmap, Bitmap, String> {

    private String image_title;
    int location_id;

    InputStream inputStream;

    public UploadImage(String image_title,int location_id){
        this.image_title = image_title;
        this.location_id = location_id;
    }

    @Override
    protected String doInBackground(Bitmap... params) {
        //postData(params[0]);
        System.out.println(params[0]);
        //System.out.println(params[1]);
        //postData(params[0],params[1]);
        upload(params[0]);
        return "WTF";
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }
    public void upload(Bitmap bitmap){

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Log.d("What","thefuck");
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream); //compress to which format you want.//!!!!!!!!!!!
        Log.d("What","fuckfuckfuck");

        byte[] bitmapdata = stream.toByteArray();


        byte[] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr,Base64.DEFAULT);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("title",this.image_title));
        nameValuePairs.add(new BasicNameValuePair("location_id",String.valueOf(this.location_id)));

        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.1.100/PublicScreeningNavigation/recieve_image.php");
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