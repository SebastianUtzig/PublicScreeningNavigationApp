package publicscreeningnavigation.app;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by Jo on 06.05.14.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context context;
    boolean GPSEnabled = false;
    boolean networkEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;
    private static final int DISTANCE_CHANGE_FOR_UPDATE = 10;
    private static final int TIME_BETWEEN_UPDATES = 1000*60;
    private boolean sourceIsNetwork = false;
    private boolean sourceIsGPS = false;


    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation(){
        try {
            sourceIsGPS = false;
            sourceIsNetwork = false;
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!GPSEnabled && !networkEnabled){}
            else{
                canGetLocation = true;
                if (networkEnabled){
                    sourceIsNetwork = true;
                    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,TIME_BETWEEN_UPDATES,DISTANCE_CHANGE_FOR_UPDATE, this);
                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (GPSEnabled) {
                    sourceIsGPS = true;
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BETWEEN_UPDATES, DISTANCE_CHANGE_FOR_UPDATE, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

    } catch (Exception e){
            e.printStackTrace();
        }

    return location;
    }

    public double getLatitude(){
        if (location!=null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if (location!=null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation(){
        return this.canGetLocation;
    }

    public boolean sourceIsBoth(){
        if (sourceIsNetwork && sourceIsGPS){
            return true;
        }
        return false;
    }

    public boolean isSourceIsNetwork(){
        return sourceIsNetwork;
    }

    public boolean isSourceIsGPS(){
        return sourceIsGPS;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("GPS is off!");
        alert.setMessage("GPS is not enabled. Go to settings menu?");

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    public void stopUsingGPS(){
        if (locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
