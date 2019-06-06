package com.example.fancester.link;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.location.Location;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */


    private static final String TAG = "tabappMessage";


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private int[] tabIcons = {
            R.drawable.ic_contact,
            R.drawable.ic_callout,
            R.drawable.ic_location
    };
    private TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();
        Log.i(TAG, "onCreate");
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements
            AdapterView.OnItemClickListener,
            OnMapReadyCallback,
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            GoogleMap.OnMarkerDragListener,
            GoogleMap.OnMapLongClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Button btn;
        public ImageView imgs;
        public ListView lst;

        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        private List<People> Fpeople = new ArrayList<People>();
        private List<People> me = new ArrayList<People>();
        private List<People> Speople = new ArrayList<People>();
        private GoogleMap mMap;
        private double longitude;
        private double latitude;
        private GoogleApiClient googleApiClient;
        private Marker FirstFriend;
        private Marker SecondFriend;
        private Marker ThirdFriend;

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)  {
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.fragment_sub_page01, container, false);
                populatePeopleList();
                populateListView(rootView);
                myProfileView(rootView);
                lst = (ListView) rootView.findViewById(R.id.peopleListView);

                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        Intent intent = new Intent(getActivity(), contact.class);
                        People currentStr = Fpeople.get(i);
                        intent.putExtra("EXTRA_SESSION_ID",currentStr.getNama());
                        startActivity(intent);
                    }
                });
                ListView lst2 = (ListView) rootView.findViewById(R.id.yourList);
                lst2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        Intent intent = new Intent(getActivity(), contact.class);
                        People currentStr = me.get(i);
                        intent.putExtra("EXTRA_SESSION_ID", currentStr.getNama());
                        startActivity(intent);
                    }
                });
                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView = inflater.inflate(R.layout.fragment_sub_page02, container, false);
                populateChatList();
                myChatView(rootView);

                lst = (ListView) rootView.findViewById(R.id.chatListView);
                lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        People currentStr = Speople.get(i);
                        Intent intent = new Intent(getActivity(), chat.class);
                        intent.putExtra("EXTRA_SESSION_CHATUSR", currentStr.getNama());
                        intent.putExtra("EXTRA_SESSION_CHATCONTENT", currentStr.getStatus());
                        startActivity(intent);
                    }
                });
                return rootView;
            }
            else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                View rootView = inflater.inflate(R.layout.activity_maps, container, false);
                SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                       .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                AppBarLayout toolbar2 = (AppBarLayout) rootView.findViewById(R.id.appbar2);
                toolbar2.setVisibility(rootView.INVISIBLE);
                ListView lst = (ListView) rootView.findViewById(R.id.chat_map_list);
                TextView txt1 = (TextView) rootView.findViewById(R.id.textView);
                TextView txt2 = (TextView) rootView.findViewById(R.id.textView2);
                txt1.setVisibility(rootView.INVISIBLE);
                txt2.setVisibility(rootView.INVISIBLE);
                lst.setVisibility(rootView.INVISIBLE);
                googleApiClient = new GoogleApiClient.Builder(getActivity())
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                googleApiClient.connect();
                /*btn = (Button) rootView.findViewById(R.id.button3);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        startActivity(intent);
                    }
                });*/

                //mMapView = (MapView) rootView.findViewById(R.id.map);
                //mMapView.onCreate(savedInstanceState);

                //mMapView.onResume(); // needed to get the map to display immediately

                /*try {
                    MapsInitializer.initialize(getActivity().getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                /*mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;

                        // For showing a move to my location button
                        //googleMap.setMyLocationEnabled(true);

                        // For dropping a marker at a point on the Map
                        LatLng sydney = new LatLng(-34, 151);
                        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                });*/

                return rootView;
            }
            else {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                return rootView;
            }
        }



        private void getCurrentLocation() {
            mMap.clear();
            Location location;
            //Creating a location object
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            }
            else {
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

            //Creating a LatLng Object to store Coordinates
            LatLng latLng = new LatLng(latitude, longitude);
            LatLng firstF = new LatLng(-6.2832917, 107.1704233);
            LatLng secondF = new LatLng(-6.2842928, 107.1704333);
            LatLng thirdF = new LatLng(-6.2832928, 107.1804333);
            //Adding marker to map
            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myuser);
            myBitmap = onCreateMarker(myBitmap);
            //msg = myBitmap.getWidth() + ", "+ myBitmap.getHeight();
            //Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            BitmapDescriptor myIcon = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(myBitmap, 140, 166, false));
            mMap.addMarker(new MarkerOptions()
                    .position(latLng) //setting position
                    .draggable(false) //Making the marker draggable
                    .title("Current Location")
                    .icon(myIcon)); //Adding a title
            FirstFriend = mMap.addMarker(new MarkerOptions().position(firstF).draggable(true).title("Ney"));
            SecondFriend = mMap.addMarker(new MarkerOptions().position(secondF).draggable(true).title("Axel"));
            ThirdFriend = mMap.addMarker(new MarkerOptions().position(thirdF).draggable(true).title("Rod"));

            //Moving the camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            //Animating the camera
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            //Displaying current coordinates in toast
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
            mMap.getUiSettings().setScrollGesturesEnabled(false);
            mMap.addMarker(new MarkerOptions().position(latLng).draggable(false));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.setOnMarkerDragListener(this);
            mMap.setOnMapLongClickListener(this);
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("EXTRA_SESSION_CHATUSR", "else");
                    startActivity(intent);
                }
            });
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
                    .draggable(false));
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
        private void populatePeopleList() {
            me.add(new People("MyUser", "Using this app", R.drawable.myuser, "08123477891"));
            Fpeople.add(new People("Ney", "Gabut", R.drawable.user1, "08123477891"));
            Fpeople.add(new People("Axel", "Playing something adorable adorable ok check lines go go go go go go go ok?",  R.drawable.user2, "08123422891"));
            Fpeople.add(new People("Rod", "Playing",  R.drawable.user3, "08123422891"));
        }
        private void populateChatList() {
            Speople.add(new People("Ney", "Fin dimana lu?", R.drawable.user1, "08123477891"));
            Speople.add(new People("Axel", "Sip", R.drawable.user2, "08123422891"));
            Speople.add(new People("Rod", "Ok got it", R.drawable.user3, "08123422891"));
        }

        private void populateListView(View view) {
            ArrayAdapter<People> adapter = new MyListAdapter();
            ListView list = (ListView) view.findViewById(R.id.peopleListView);
            list.setAdapter(adapter);
        }

        private void myProfileView(View view) {
            ArrayAdapter<People> adapter = new MyProfileAdapter();
            ListView list = (ListView) view.findViewById(R.id.yourList);
            list.setAdapter(adapter);
        }

        private void myChatView(View view) {
            ArrayAdapter<People> adapter = new MyChatAdapter();
            ListView list = (ListView) view.findViewById(R.id.chatListView);
            list.setAdapter(adapter);
        }

        private class MyProfileAdapter extends ArrayAdapter<People> {
            public MyProfileAdapter(){
                super(getActivity(), R.layout.item_view, me);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup Parent) {
                View itemView = convertView;
                People currentPeople = me.get(position);

                if (itemView == null) {
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.item_view, Parent, false);
                }

                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), currentPeople.geticonID());
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawable);

                TextView nameText = (TextView) itemView.findViewById(R.id.txtPeople);
                nameText.setText(currentPeople.getNama());

                TextView statusText = (TextView) itemView.findViewById(R.id.txtStatus);
                statusText.setText(currentPeople.getStatus());

                TextView numberText = (TextView) itemView.findViewById(R.id.txtNumber);
                numberText.setText(currentPeople.getNumber());
                return itemView;
                // return super.getView(position, convertView, Parent);
            }
        }

        private class MyListAdapter extends ArrayAdapter<People> {
            public MyListAdapter(){
                super(getActivity(), R.layout.item_view, Fpeople);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup Parent) {
                View itemView = convertView;
                People currentPeople = Fpeople.get(position);

                if (itemView == null) {
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.item_view, Parent, false);
                }

                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), currentPeople.geticonID());
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawable);

                TextView nameText = (TextView) itemView.findViewById(R.id.txtPeople);
                nameText.setText(currentPeople.getNama());

                TextView statusText = (TextView) itemView.findViewById(R.id.txtStatus);
                statusText.setText(currentPeople.getStatus());

                TextView numberText = (TextView) itemView.findViewById(R.id.txtNumber);
                numberText.setText(currentPeople.getNumber());

                return itemView;
                // return super.getView(position, convertView, Parent);
            }
        }

        private class MyChatAdapter extends ArrayAdapter<People> {
            public MyChatAdapter(){
                super(getActivity(), R.layout.item_chatview, Speople);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup Parent) {
                View itemView = convertView;
                People currentPeople = Speople.get(position);

                if (itemView == null) {
                    itemView = getActivity().getLayoutInflater().inflate(R.layout.item_chatview, Parent, false);
                }

                ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView3);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), currentPeople.geticonID());
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(roundedBitmapDrawable);

                TextView nameText = (TextView) itemView.findViewById(R.id.txtUser);
                nameText.setText(currentPeople.getNama());

                TextView statusText = (TextView) itemView.findViewById(R.id.txtChatting);
                statusText.setText(currentPeople.getStatus());

                return itemView;
                // return super.getView(position, convertView, Parent);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView temp= (TextView) view;
            Toast.makeText(getActivity(), temp.getText()+""+i, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Contact";
                case 1:
                    return "Chat";
                case 2:
                    return "Location";
            }
            return null;
        }
    }
}
