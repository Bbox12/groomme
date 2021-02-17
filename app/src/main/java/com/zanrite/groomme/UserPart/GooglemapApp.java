package com.zanrite.groomme.UserPart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zanrite.groomme.Adapters.NearAdapter;
import com.zanrite.groomme.Adapters.Populatesalonsrv;
import com.zanrite.groomme.Adapters.StylishAdapter;
import com.zanrite.groomme.BuildConfig;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.DirectionsJSONParser;
import com.zanrite.groomme.helpers.Marker_custom_infowindow;
import com.zanrite.groomme.helpers.MySeekBar;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.google.android.gms.maps.model.JointType.ROUND;

/**
 * Created by parag on 31/12/16.
 */
public class GooglemapApp extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, Animation.AnimationListener {
    private static final String TAG = GooglemapApp.class.getSimpleName();
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
    private static final float POLYLINE_WIDTH = 8;
    private final int REQUEST_LOCATION = 200;
    double My_lat, My_long;
    DecimalFormat dft = new DecimalFormat("0.00");
    DecimalFormat dfto = new DecimalFormat("0.000000");
    boolean doubleBackToExitPressedOnce = false;
    private GoogleApiClient mGoogleApiClient;
    private CoordinatorLayout coordinatorLayout;
    private String User_image;
    private String User_name;
    private GoogleMap googleMap;
    private String regId;
    private ProgressBar progressBar;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private String strAdd;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;




    private Dialog dialog1;
    private ArrayList<Total>mParlours;

    private PrefManager pref;
    private String _phoneNo;
    private ImageView myLocationButton;
    private SupportMapFragment mapFragment;
    private RecyclerView nearRv;
    private boolean _first=false;
    private Target target;
    private int _from=0;
    private String _name;
    private int _home=0;
    private String _userName;
    private TextView textView1,_change,primary_text;
    private int progresValue;
    private NearAdapter adapter;
    private Marker markerd;
    private Button _cancel;
    private EditText Search_2;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RelativeLayout _R1;
    private AutoCompleteTextView secondary_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        _userName = user.get(PrefManager.KEY_NAME2);
        setContentView(R.layout.dialogmap);
        _cancel=findViewById(R.id._cancel);
        _cancel.setOnClickListener(this);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        _R1=findViewById(R.id._main);
        _R1.setVisibility(View.GONE);

        _change=findViewById(R.id.change);
        _change.setOnClickListener(this);


        Search_2=findViewById(R.id.search);
        Search_2.setHint("Search salons");

        progressBar = findViewById(R.id.progressBar3_map);
        myLocationButton = findViewById(R.id.myLocationCustomButton);
        textView1 = (TextView) findViewById(R.id.seek_text);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapFragment.getMapAsync(GooglemapApp.this);
        buildLocationSettingsRequest();
        rebuildGoogleApiClient();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            onConnected(null);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        myLocationButton.setOnClickListener(this);
        primary_text=findViewById(R.id.primary_text);
        secondary_text=findViewById(R.id.secondary_text);

        if(_userName!=null){
           primary_text.setText("Welcome "+_userName);
        }else{
            primary_text.setText("Welcome Guest");
        }


        nearRv=findViewById(R.id.nearRv);
        nearRv.setNestedScrollingEnabled(false);



     
        Search_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()>1) {
                        final ArrayList<Total> filteredModelList = new ArrayList<>();
                        _cancel.setVisibility(View.VISIBLE);
                        Set<Total> set = new HashSet<>(mParlours);
                        mParlours.clear();
                        mParlours.addAll(set);
                        for (int i = 0; i < mParlours.size(); i++) {
                            Total model = mParlours.get(i);
                            final String text = model.getName(i).toLowerCase();
                            if (text.contains(String.valueOf(s).toLowerCase())) {
                                filteredModelList.add(model);
                            }
                        }
                        if (filteredModelList.size() != 0) {
                            adapter = new NearAdapter(GooglemapApp.this, mParlours);
                            adapter.notifyDataSetChanged();
                            adapter.setLatLong(new LatLng(My_lat,My_long));
                            adapter.setPref(pref);
                            adapter.setCoordinate(coordinatorLayout);
                            nearRv.setAdapter(adapter);
                            nearRv.setNestedScrollingEnabled(false);
                            LinearLayoutManager layoutManager4
                                    = new LinearLayoutManager(GooglemapApp.this, LinearLayoutManager.VERTICAL, false);
                            nearRv.setLayoutManager(layoutManager4);
                            nearRv.setItemAnimator(new DefaultItemAnimator());
                        } else {
                            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        _cancel.setVisibility(View.GONE);

                    }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        if (map == null) {
            //Ride_now.setEnabled(false);
            if (!GooglemapApp.this.isFinishing()) {
                new AlertDialog.Builder(GooglemapApp.this, R.style.AlertDialogTheme)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Cannot load Google Map")
                        .setMessage("May be poor network!Contact customer care for booking offline")
                        .setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ""));
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        } else {
            //Ride_now.setEnabled(true);
            coordinatorLayout.setVisibility(View.VISIBLE);
            googleMap = map;

            Marker_custom_infowindow adapter = new Marker_custom_infowindow(GooglemapApp.this);
            googleMap.setInfoWindowAdapter(adapter);


            googleMap.getUiSettings().setZoomControlsEnabled(false);
            googleMap.getUiSettings().setAllGesturesEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
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
            googleMap.setMyLocationEnabled(true);
            googleMap.setMaxZoomPreference(20f);
            googleMap.setMinZoomPreference(12f);



            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                @Override
                public void onMapLoaded() {

                }
            });


        }


    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mCurrentLocation = location;
                updateLocationUI();
            }
        }
    };

    private void startLocationUpdat() {

        if (mFusedLocationClient != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }
    }


    private void updateLocationUI() {


        if (mCurrentLocation != null && googleMap != null ) {
            My_lat = mCurrentLocation.getLatitude();
            My_long = mCurrentLocation.getLongitude();
          if(!_first){
              _first=true;
              if(googleMap!=null) {
                  if(_userName!=null) {
                      Marker markerCar = googleMap.addMarker(new MarkerOptions()
                              .position(new LatLng(My_lat, My_long))
                              .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_green)));
                      markerCar.setTitle(_userName);
                      markerCar.showInfoWindow();
                      markerCar.setAnchor(0.5f, 0.5f);

                  }

                  secondary_text.setText(getCompleteAddressString(My_lat, My_long));

                  CameraPosition
                  googlePlex = CameraPosition.builder()
                          .target(new LatLng(My_lat, My_long))
                          .zoom(18) // Sets the zoom
                          .build(); // Crea
                  googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
              }
          }
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String strAdd = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() != 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getSubLocality());
                }
                strAdd = strReturnedAddress.toString();
                Log.w(" address", strReturnedAddress.toString());
            } else {
                strAdd = "Location not identified";
                Log.w(" address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            strAdd = "Location not identified";
            Log.w(" loction address", "Canont get Address!");
        }
        return strAdd;
    }



    @Override
    protected void onResume() {
        super.onResume();


       if(pref.getDropLat()!=null) {
           My_lat = Double.parseDouble(pref.getDropLat());
           My_long = Double.parseDouble(pref.getDropLong());
       }

       Intent i=getIntent();
       _from=i.getIntExtra("from",0);
       _name=i.getStringExtra("category");
        _home=i.getIntExtra("home",0);

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            if (checkPermissions()) {
                startLocationUpdat();
            }else {
                requestPermissions();
            }
        }
        secondary_text.setText(pref.getparlour_locality()+","+pref.getCity());
        go(pref.getCity());
    }

    private void go(final String city) {
        mParlours=new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_ALL_PARLOURS,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.w("mainlayout", response);

                        try {
                            JSONObject jsonObj = new JSONObject(response);



                            if(_from==0) {
                                if(_home==0) {
                                    JSONArray parlours = jsonObj.getJSONArray("distance");

                                    for (int i = 0; i < parlours.length(); i++) {
                                        JSONObject c = parlours.getJSONObject(i);
                                        if (!c.isNull("parlour_name")) {
                                            double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"),c.getDouble("longitude")), new LatLng(My_lat,My_long)) / 1000;
                                            if(dist2< pref.getDistance()) {
                                                Total item = new Total();
                                                item.setThumbnailUrl(c.getString("Photo"));
                                                item.setName(c.getString("parlour_name"));
                                                item.setID(c.getInt("ID"));
                                                item.setmobileno(c.getString("parlour_mobile"));
                                                item.setLatitude(c.getDouble("latitude"));
                                                item.setLongitude(c.getDouble("longitude"));
                                                item.setAddress(c.getString("address"));
                                                item.setDiscount(c.getDouble("discountAmt"));
                                                item.setDistance(dist2);
                                                item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                                item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                                mParlours.add(item);
                                            }
                                        }
                                    }
                                }else{
                                    JSONArray parlours = jsonObj.getJSONArray("home");

                                    for (int i = 0; i < parlours.length(); i++) {
                                        JSONObject c = parlours.getJSONObject(i);
                                        if (!c.isNull("parlour_name")) {
                                            double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"), c.getDouble("longitude")), new LatLng(My_lat, My_long)) / 1000;

                                            if(dist2< pref.getDistance()) {
                                                Total item = new Total();
                                                item.setThumbnailUrl(c.getString("Photo"));
                                                item.setName(c.getString("parlour_name"));
                                                item.setID(c.getInt("ID"));
                                                item.setAddress(c.getString("address"));
                                                item.setmobileno(c.getString("parlour_mobile"));
                                                item.setLatitude(c.getDouble("latitude"));
                                                item.setLongitude(c.getDouble("longitude"));
                                                item.setDiscount(c.getDouble("discountAmt"));
                                                item.setDistance(dist2);
                                                item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                                item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                                mParlours.add(item);
                                            }
                                        }
                                    }
                                }
                            }else{
                                JSONArray parlours = jsonObj.getJSONArray("map");
                                for (int i = 0; i < parlours.length(); i++) {
                                    JSONObject c = parlours.getJSONObject(i);
                                    if (!c.isNull("parlour_name")) {
                                        double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"), c.getDouble("longitude")), new LatLng(My_lat, My_long)) / 1000;

                                        if(dist2< pref.getDistance()) {
                                            Total item = new Total();
                                            item.setThumbnailUrl(c.getString("Photo"));
                                            item.setName(c.getString("parlour_name"));
                                            item.setID(c.getInt("ID"));
                                            item.setAddress(c.getString("address"));
                                            item.setmobileno(c.getString("parlour_mobile"));
                                            item.setLatitude(c.getDouble("latitude"));
                                            item.setLongitude(c.getDouble("longitude"));
                                            item.setDiscount(c.getDouble("discountAmt"));
                                            item.setDistance(dist2);
                                            item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                            item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                            mParlours.add(item);
                                        }
                                    }
                                }
                            }

                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            _R1.setVisibility(View.VISIBLE);
                            if(mParlours.size()!=0){


                                int i=0;
                                for( i=0;i<mParlours.size();i++){
                                    final String _name=mParlours.get(i).getName(i);
                                    final LatLng lat=new LatLng(mParlours.get(i).getLatitude(i), mParlours.get(i).getLongitude(i));
                                    if( googleMap!=null) {
                                        Marker markerCar = googleMap.addMarker(new MarkerOptions()
                                                .position(lat)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.homemarker)));
                                        markerCar.setTitle(_name);
                                        markerCar.showInfoWindow();
                                        markerCar.setAnchor(0.5f, 0.5f);
                                    }




                                }
                                if(_from==1){
                                    textView1.setText("Ladies salons near "+city.toLowerCase()+" ("+String.valueOf(mParlours.size())+")");
                                }else if(_from==2){
                                    textView1.setText("Gents salons near "+city.toLowerCase()+" ("+String.valueOf(mParlours.size())+")");
                                }else if(_from==4){
                                    textView1.setText("Kids salons near "+city.toLowerCase()+" ("+String.valueOf(mParlours.size())+")");
                                }else if(_from==5){
                                    textView1.setText("Bridal salons near "+city.toLowerCase()+" ("+String.valueOf(mParlours.size())+")");
                                }else {
                                    if(_home==1){
                                        textView1.setText("Salons near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")"+ " (With home service)");
                                    }else  if(_home==2){
                                        textView1.setText("Salons near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")"+ " (No home service)");
                                    }else  if(_home==3){
                                        textView1.setText("Salons near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")"+ " (Both salon &amp; home service)");
                                    }else {
                                        textView1.setText("Salons near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")");
                                    }
                                }
                                adapter = new NearAdapter(GooglemapApp.this, mParlours);
                                adapter.notifyDataSetChanged();
                                adapter.setLatLong(new LatLng(My_lat,My_long));
                                adapter.setPref(pref);
                                adapter.setCoordinate(coordinatorLayout);
                                nearRv.setAdapter(adapter);
                                nearRv.setNestedScrollingEnabled(false);
                                LinearLayoutManager layoutManager4
                                        = new LinearLayoutManager(GooglemapApp.this, LinearLayoutManager.VERTICAL, false);
                                nearRv.setLayoutManager(layoutManager4);
                                nearRv.setItemAnimator(new DefaultItemAnimator());
                                nearRv.addOnItemTouchListener(
                                        new RecyclerTouchListener(GooglemapApp.this, nearRv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        if(mParlours.size()>0 && mParlours.get(position)!=null) {
                                                            LatLng origin = new LatLng(mParlours.get(position).getLatitude(position), mParlours.get(position).getLongitude(position));
                                                            googleMap.clear();
                                                            Marker markerCar = googleMap.addMarker(new MarkerOptions()
                                                                    .position(origin)
                                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.homemarker)));
                                                            markerCar.setTitle(mParlours.get(position).getName(position));
                                                            markerCar.showInfoWindow();
                                                            markerCar.setAnchor(0.5f, 0.5f);
                                                            LatLng dest = new LatLng(My_lat, My_long);
                                                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                                                    .position(dest)
                                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_green)));
                                                            marker.setAnchor(0.5f, 0.5f);
                                                            String url = getDirectionsUrl(origin, dest);
                                                            new Get_distance_draw_polyiline().execute(url);
                                                        }

                                                    }

                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));
                            }else{
                                if(_from==1){
                                    textView1.setText("No ladies salons near "+city.toLowerCase());
                                }else if(_from==2){
                                    textView1.setText("No gents salons near "+city.toLowerCase());
                                }else if(_from==4){
                                    textView1.setText("No kids salons near "+city.toLowerCase());
                                }else if(_from==5){
                                    textView1.setText("No bridal salons near "+city.toLowerCase());
                                }

                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "No salon found near you! Please come back later.", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent o = new Intent(GooglemapApp.this, UserHomeScreen.class);
                                                o.putExtra("from",1);
                                                startActivity(o);
                                                finish();
                                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                            }
                                        });
                                snackbar.setActionTextColor(Color.RED);
                                snackbar.show();
                            }




                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                vollyError(error);


            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("from", String.valueOf(_from));
                params.put("home", String.valueOf(_home));
                params.put("city", String.valueOf(city.toUpperCase()));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);


    }





    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.myLocationCustomButton) {
            Myloc();
        }

        if(view.getId()==R.id._cancel) {
            Search_2.setText("");
            go(pref.getCity());
        }

        if(view.getId()==R.id.change){
            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOCATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.w("populateservice", response);
                            final ArrayList<String > mCity = new ArrayList<>();
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray items = jsonObj.getJSONArray("salons");
                                if (items.length() != 0) {
                                    for (int i = 0; i < items.length(); i++) {
                                        JSONObject c = items.getJSONObject(i);
                                        if(!TextUtils.isEmpty(c.getString("city"))) {
                                            mCity.add(c.getString("city").toUpperCase());
                                        }
                                        if(!TextUtils.isEmpty(c.getString("locality"))) {
                                            mCity.add(c.getString("locality").toUpperCase());
                                        }

                                    }
                                }


                            } catch (final JSONException e) {
                                Log.e("HI", "Json parsing error: " + e.getMessage());
                            }

                            if(mCity.size()>0) {
                                Set<String> set1 = new HashSet<>(mCity);
                                mCity.clear();
                                mCity.addAll(set1);
                                mCity.trimToSize();

                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                        getApplicationContext(), android.R.layout.simple_list_item_1, mCity) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                                        text.setTextColor(Color.WHITE);
                                        return view;
                                    }
                                };
                                secondary_text.setAdapter(arrayAdapter);
                                secondary_text.setCursorVisible(false);
                                secondary_text.showDropDown();
                                secondary_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String _city = (String) parent.getItemAtPosition(position);
                                        go(_city);


                                    }
                                });
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    vollyError(error);

                }

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("from", String.valueOf(_from));
                    params.put("home", String.valueOf(_home));
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(eventoReq);
        }
    }








    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        String output = "json";
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +"&key="+"AIzaSyBGCYHr6KetBnbnC2A5F0FO1E0KI3b-qpY";

    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception ", e.toString());
        } finally {
            if (iStream != null) {
                iStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }







    private void Myloc() {
        if(googleMap!=null) {
            CameraPosition googlePlex;
            googlePlex = CameraPosition.builder()
                    .target(new LatLng(My_lat, My_long))
                    .zoom(18) // Sets the zoom
                    .build(); // Crea
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }
    }


    private class Get_distance_draw_polyiline extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ProgressDialog mProgressDialog;
        private double Distance;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String duration = "";
            Random rand = new Random();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                PolylineOptions lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    if (j == 0) {    // Get distance from the list
                        String pars = point.get("distance");
                        Log.w("Distance", pars);
                        String[] pars1 = pars.split(" ");
                        if (pref.getDropAt() != null) {
                            String[] pars2 = new String[0];
                            if (pars1[0].contains(".")) {
                                pars2 = pars1[0].split("\\.");
                                Distance = Double.parseDouble(pars2[0]);
                            } else if (pars1[0].contains(",")) {
                                pars2 = pars1[0].split(",");
                                Distance = Double.parseDouble(pars2[0]);
                            } else {
                                Distance = Double.parseDouble(pars1[0]);
                            }

                        }
                        continue;
                    } else if (j == 1) {
                        duration = point.get("duration");


                        continue;
                    }

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                    builder.include(position);
                }



            }

            if (points != null) {
                PolylineOptions lineOptions = new PolylineOptions();
                lineOptions.addAll(points);
                lineOptions.width(18);
                lineOptions.startCap(new SquareCap());
                lineOptions.endCap(new SquareCap());
                lineOptions.jointType(ROUND);
                lineOptions.color(Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                if (googleMap != null) {
                    Polyline polylineFinal = googleMap.addPolyline(lineOptions);
                    LatLngBounds bounds = builder.build();
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (height * 0.20); // offset from edges of the map 10% of screen
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                    googleMap.animateCamera(cu);
                }
            }

        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(GooglemapApp.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(GooglemapApp.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        builder = new LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest());
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    startLocationUpdat();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(GooglemapApp.this, REQUEST_CHECK_SETTINGS);
                                break;
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                                Log.w(" ClassCastException", "Canont get Address!"+e.getLocalizedMessage());
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }}
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mFusedLocationClient != null) {
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        }

                        //   mGoogleMap.setMyLocationEnabled(true);
                    }

                }
            }
            break;
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdat();
                } else {
                    showSnackbar(R.string.permission_denied_explanation,
                            R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });
                }
            }
            break;
        }


    }

    protected LocationRequest createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }









    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            dialog.cancel();
        }
    }








    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof AuthFailureError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ServerError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof NetworkError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ParseError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }



    }








    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClientConnectionStateChange(false);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        }
    }





    private void stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }



    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

    }

    protected synchronized void rebuildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,0,this )
                .addConnectionCallbacks(this /* ConnectionCallbacks */)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        googleApiClientConnectionStateChange(true);
                    }
                })
                .addApi(LocationServices.API)
                .build();

    }

    private void googleApiClientConnectionStateChange(final boolean connected) {
        final Context appContext = this.getApplicationContext();

    }



    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();

        stopLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopLocationUpdates();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_black, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent o = new Intent(GooglemapApp.this, UserHomeScreen.class);
            o.putExtra("from",1);
            startActivity(o);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


        }
        return true;
    }




}
