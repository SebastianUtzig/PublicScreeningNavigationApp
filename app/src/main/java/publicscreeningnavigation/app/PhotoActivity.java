package publicscreeningnavigation.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class PhotoActivity extends Activity {

    private Activity activity;
    private int location_id = -1;

    private Camera cameraObject;
    private Preview showCamera;
    private ImageView pic;
    public static Camera isCameraAvailiable(){
        Camera object = null;
        try {
            object = Camera.open();
        }
        catch (Exception e){
        }
        return object;
    }

    private Camera.PictureCallback capturedIt = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if(bitmap==null){
                Toast.makeText(getApplicationContext(), "not taken", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "taken", Toast.LENGTH_SHORT).show();
                System.out.println(bitmap);
                new UploadImage(activity,"photo_of_"+location_id,location_id).execute(bitmap);
            }
            cameraObject.release();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        this.activity = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            this.location_id = extras.getInt("location_id");
        }


        //pic = (ImageView)findViewById(R.id.imageView1);
        cameraObject = isCameraAvailiable();
        showCamera = new Preview(this, cameraObject);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);

        cameraObject.setDisplayOrientation(90);
        Camera.Parameters params= cameraObject.getParameters();
        preview.getLayoutParams().width=params.getPreviewSize().height;
        preview.getLayoutParams().height=params.getPreviewSize().width;
    }
    public void snapIt(View view){
        cameraObject.takePicture(null, null, capturedIt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
