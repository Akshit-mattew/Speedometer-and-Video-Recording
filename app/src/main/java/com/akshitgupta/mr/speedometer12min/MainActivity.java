package com.akshitgupta.mr.speedometer12min;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView txt,lon,lan,time,floa;
    double plat,plon,ptim;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    VideoView result_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        txt = (TextView) findViewById(R.id.speed);


        Button click= (Button)findViewById(R.id.button);
        result_video  =(VideoView)findViewById(R.id.videoView);

        lon = (TextView) findViewById(R.id.lat);
        lan = (TextView) findViewById(R.id.lon);
        floa=(TextView)findViewById(R.id.textView);
time=(TextView)findViewById(R.id.textView3);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);





    }




    public void dispatchTakeVideoIntent(View v) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            result_video.setVideoURI(videoUri);
        }
    }

    @Override
    public void onLocationChanged(Location location) {



        double mCurrent= location.getLatitude();
        double mlon=location.getLongitude();
        double mti=location.getElapsedRealtimeNanos();
        float f4=(float)mti;
        double landis= mCurrent-plat;
     float f1=(float)mlon;
        float f2=(float)mCurrent;
        double londis= mlon-plon;

        float timer= (float) mti-(float)ptim;
timer=timer*10;
        landis=landis*landis;
        londis=londis*londis;
        double add= londis+landis;
        float    dis=(float)Math.sqrt(add);
        float sp =  dis/timer;
        float f3=(float)sp;
        int a= (int)sp;
        String format=String.format("%.02f",sp);


        float myFloat =(float)sp ;
        String s= String.format("%2f",sp);
      //  floa.setText(myFloat +"");
    txt.setText(sp + "");
        lon.setText(f1 +"");
        lan.setText(f2+"");

        plat=mCurrent;
        plon=mlon;
        ptim=mti;

    }
/*
    public static  double round( double value,int places)
    {

        BigDecimal bd= new BigDecimal(value);
        bd= bd.setScale(places, RoundingMode.HALF_UP);
        return  bd.doubleValue();
    }
*/
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
