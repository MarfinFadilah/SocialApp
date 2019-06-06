package com.example.fancester.link;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.location.Location;
import android.os.Build;
import android.os.Parcel;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        View.OnClickListener{

    //Our Map
    private GoogleMap mMap;

    //Friend Location
    private Marker FirstFriend;
    private Marker SecondFriend;
    private Marker ThirdFriend;
    private Marker ThirdMark;

    //To store longitude and latitude from map
    private double longitude;
    private double latitude;

    //Buttons
    private ImageButton buttonSave;
    private ImageButton buttonCurrent;
    private ImageButton buttonView;

    mapView myView = mapView.SearchFriend;
    String str;

    private GoogleApiClient googleApiClient;

    //Google ApiClient
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button btn = (Button) findViewById(R.id.chatMapButton);
        AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar2);
        ListView lst = (ListView) findViewById(R.id.chat_map_list);
        EditText edt = (EditText) findViewById(R.id.chatMapBox);
        TextView txt1 = (TextView) findViewById(R.id.textView);
        TextView txt2 = (TextView) findViewById(R.id.textView2);
        //params.topMargin = 200;
        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView = mapView.Chat;
                TextView txt2 = (TextView) findViewById(R.id.textView2);
                txt2.setVisibility(VISIBLE);
                TextView txt1 = (TextView) findViewById(R.id.textView);
                txt1.setVisibility(INVISIBLE);
            }
        });
        toolbar.setVisibility(INVISIBLE);
        lst.setVisibility(INVISIBLE);
        btn.setVisibility(INVISIBLE);
        edt.setVisibility(INVISIBLE);
        txt1.setVisibility(INVISIBLE);
        txt2.setVisibility(INVISIBLE);

        //Initializing googleapi client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
        //Initializing views and adding onclick listeners
        /*buttonSave = (ImageButton) findViewById(R.id.buttonSave);
        buttonCurrent = (ImageButton) findViewById(R.id.buttonCurrent);
        buttonView = (ImageButton) findViewById(R.id.buttonView);
        buttonSave.setOnClickListener(this);
        buttonCurrent.setOnClickListener(this);
        buttonView.setOnClickListener(this);*/
    }

    public enum mapView {
        SearchFriend,
        StartChat,
        Chat
    }

    @Override
    public void onBackPressed() {
        Button btn = (Button) findViewById(R.id.chatMapButton);
        AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar2);
        ListView lst = (ListView) findViewById(R.id.chat_map_list);
        EditText edt = (EditText) findViewById(R.id.chatMapBox);
        TextView txt1 = (TextView) findViewById(R.id.textView);
        TextView txt2 = (TextView) findViewById(R.id.textView2);
        switch(myView) {
            case SearchFriend:
                super.onBackPressed();
                break;
            case StartChat:
                toolbar.setVisibility(INVISIBLE);
                lst.setVisibility(INVISIBLE);
                btn.setVisibility(INVISIBLE);
                edt.setVisibility(INVISIBLE);
                txt1.setVisibility(INVISIBLE);
                txt2.setVisibility(INVISIBLE);
                myView = mapView.SearchFriend;
                break;
            case Chat:
                myView = mapView.StartChat;
                txt1.setVisibility(VISIBLE);
                txt2.setVisibility(INVISIBLE);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        //googleApiClient.disconnect();
        super.onStop();
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        Location location;
        //Creating a location object
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        } else {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            //moving the map to location
            moveMap();
        }
    }

    //Function to move the map
    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;
        str="";
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);
        LatLng firstF = new LatLng(-6.2832917, 107.1704233);
        LatLng secondF = new LatLng(-6.2842928, 107.1704333);
        LatLng thirdF = new LatLng(-6.2832928, 107.1804333);
        str = getIntent().getStringExtra("EXTRA_SESSION_CHATUSR");
        //Create customize marker
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myuser);
        Bitmap firstBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user1);
        Bitmap secondBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user2);
        Bitmap thirdBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user3);
        myBitmap = onCreateMarker(myBitmap);
        firstBitmap = onCreateMarker(firstBitmap);
        secondBitmap = onCreateMarker(secondBitmap);
        thirdBitmap = onCreateMarker(thirdBitmap);
        //msg = myBitmap.getWidth() + ", "+ myBitmap.getHeight();
        //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        BitmapDescriptor myIcon = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(myBitmap, 140, 166, false));
        BitmapDescriptor firstIcon = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(firstBitmap, 140, 166, false));
        BitmapDescriptor secondIcon = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(secondBitmap, 140, 166, false));
        BitmapDescriptor thirdIcon = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(thirdBitmap, 140, 166, false));
        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("MyUser")
                .icon(myIcon)); //Adding a title
        FirstFriend = mMap.addMarker(new MarkerOptions().position(firstF).draggable(true).title("Ney").icon(firstIcon));
        SecondFriend = mMap.addMarker(new MarkerOptions().position(secondF).draggable(true).title("Axel").icon(secondIcon));
        ThirdFriend = mMap.addMarker(new MarkerOptions().position(thirdF).draggable(true).title("Rod").icon(thirdIcon));
        //Moving the camera
        if (str.equals(FirstFriend.getTitle())) {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(firstF));
        }

       else if (str.equals(SecondFriend.getTitle())) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(secondF));
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        }
        else if (str.equals(ThirdFriend.getTitle())) {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(thirdF));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(thirdF));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Button btn = (Button) findViewById(R.id.chatMapButton);
                AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar2);
                ListView lst = (ListView) findViewById(R.id.chat_map_list);
                EditText edt = (EditText) findViewById(R.id.chatMapBox);
                TextView txt1 = (TextView) findViewById(R.id.textView);
                TextView txt2 = (TextView) findViewById(R.id.textView2);
                toolbar.setVisibility(VISIBLE);
                lst.setVisibility(VISIBLE);
                btn.setVisibility(VISIBLE);
                edt.setVisibility(VISIBLE);
                txt1.setVisibility(VISIBLE);
                txt2.setVisibility(VISIBLE);
                txt1.setText(marker.getTitle());
                txt2.setText(marker.getTitle());
                myView = mapView.StartChat;
                return true;
            }
        });
        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //Displaying current coordinates in toast
    }

    public Bitmap onCreateMarker(Bitmap bitmap) {
        bitmap = getResizedBitmap(bitmap, 192, 192);
        Bitmap thirdBitmap = Bitmap.createBitmap(bitmap.getWidth()*2, bitmap.getHeight()*2, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(thirdBitmap);
        onDraw(c, bitmap);
        c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
        bitmap.recycle();
        return thirdBitmap;
    }

    protected void onDraw(Canvas canvas, Bitmap fillBMP) {
        BitmapShader fillBMPshader = new BitmapShader(fillBMP, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint fillPaint = new Paint();
        fillPaint.setColor(0xFFFFFFFF);
        fillPaint.setStyle(Paint.Style.FILL);
        //Assign the 'fillBMPshader' to this paint
        fillPaint.setShader(fillBMPshader);
        //fillPaint.setColor(android.graphics.Color.RED);
        //paint.setStyle(Paint.Style.FILL_AND_STROKE);
        Point a = new Point(50, 150);
        Point b = new Point(100, 220);
        Point c = new Point(150, 150);

        Path path = new Path();
        path.moveTo(100,0);
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(a.x, a.y);
        path.lineTo(c.x, c.y);
        path.lineTo(b.x, b.y);
        path.close();
        fillBMP.recycle();
        canvas.drawPath(path, fillPaint);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //Clearing all the markers
        mMap.clear();

        //Adding a new marker to the current pressed position
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Getting the coordinates
        latitude = marker.getPosition().latitude;
        longitude = marker.getPosition().longitude;

        //Moving the map
        moveMap();
    }

    @Override
    public void onClick(View v) {
        /*if(v == buttonCurrent){
            getCurrentLocation();
            moveMap();
        }*/
    }
}
