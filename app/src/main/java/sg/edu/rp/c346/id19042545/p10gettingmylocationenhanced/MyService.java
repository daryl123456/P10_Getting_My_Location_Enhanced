package sg.edu.rp.c346.id19042545.p10gettingmylocationenhanced;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileWriter;

public class MyService extends Service {
    FusedLocationProviderClient mFusedLocationClient;
    Double lat;
    Double lng;
    LocationRequest mLocationRequest;
//    String folderLocation = getFilesDir().getAbsolutePath() + "/P09Location";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    boolean started;

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setSmallestDisplacement(500);
        checkPermission();

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("MyService", "Service exited");
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        super.onDestroy();

    }

//    private DownloadBinder mBinder = new DownloadBinder();
//
//    class DownloadBinder extends Binder {
//        public void startDownload() {
//            Log.d("MyService", "startDownload executed");
//        }
//        public int getProgress() {
//            Log.d("MyService", "getProgress executed");
//            return 0;
//        }
//    }
    LocationCallback mLocationCallback= new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult!= null) {

                Location data = locationResult.getLastLocation();
                lat= data.getLatitude();
                lng= data.getLongitude();

                Log.d("Test",lat+" "+lng);
//                try{
//                    File targetFile = new File(folderLocation, "location.txt");
//                    FileWriter writer_I= new FileWriter(targetFile, true);
//                    writer_I.write(data.getLatitude()+ " "+ data.getLatitude() + "\n");
//                    writer_I.flush();
//                    writer_I.close();

//                }catch (Exception e){
//
//                }


            }
        };
    };
    private void checkPermission(){

        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (
                permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {

        } else {


            ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
    }
}