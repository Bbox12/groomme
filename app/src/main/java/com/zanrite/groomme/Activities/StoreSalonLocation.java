package com.zanrite.groomme.Activities;

import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.BuildConfig;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StoreSalonLocation extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private CheckBox thisplace,otherplace;
    private Toolbar toolbar;
    private Button Submit1;
    private int serviceAT=0;
    private ProgressBar progressBar;
    public static final int MULTIPLE_PERMISSIONS = 101;
    private static final String TAG = StoreSalonLocation.class.getSimpleName();
    private boolean permissionsAllowd=false;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
    private static final float POLYLINE_WIDTH = 8;
    private final int REQUEST_LOCATION = 200;
    private GoogleApiClient mGoogleApiClient;
    private double My_lat,My_long;
    private RelativeLayout rmap;
    private SupportMapFragment mapFragment;
    private GoogleMap mgoogleMap;
    private Marker markerCar;
    private DatabaseReference mDatabase;
    private String locality="",city="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesalonlocation);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        rmap=findViewById(R.id.rmap);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(StoreSalonLocation.this);

        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        thisplace=findViewById(R.id.thisplace);
        otherplace=findViewById(R.id.otherplace);
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarSplash);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(StoreSalonLocation.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else {
                    Intent o = new Intent(StoreSalonLocation.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });
        rmap.setVisibility(View.VISIBLE);
        checkAndRequestPermissions();
        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(StoreSalonLocation.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
         mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(StoreSalonLocation.this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(StoreSalonLocation.this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        thisplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    otherplace.setChecked(false);
                    if(mGoogleApiClient==null){
                        checkAndRequestPermissions();
                    }
                }
            }
        });

        otherplace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    thisplace.setChecked(false);
                   // rmap.setVisibility(View.GONE);
                    pref.setLatLong(0);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "You will have to provide location information later.", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.REMOVE_LATLONG,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.w("latlong", response);
                                                    progressBar.setVisibility(View.GONE);

                                                    String[] par = response.split("error");
                                                    progressBar.setVisibility(View.GONE);
                                                    pref.setLatLong(0);
                                                    Intent o3 = new Intent(StoreSalonLocation.this, ServiceTimings.class);
                                                    startActivity(o3);
                                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                                    finish();


                                                }



                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            vollyError(error);

                                        }

                                    }){
                                        @Override
                                        protected Map<String, String> getParams() {
                                            // Posting parameters to login url
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("mobile",_phoneNo);
                                            return params;
                                        }

                                    };

                                    // Añade la peticion a la cola
                                    AppController.getInstance().addToRequestQueue(eventoReq);

                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }
        });


    }


    private void checkAndRequestPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);


        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (listPermissionsNeeded.size()>0) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            if (mGoogleApiClient == null ) {
                buildLocationSettingsRequest();
                rebuildGoogleApiClient();
            }
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                onConnected(null);
            }if(mFusedLocationClient==null) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        // Initialize the map with both permissions
        // Fill with actual results from user
        if (requestCode == MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "sms & location services permission granted");
                    buildLocationSettingsRequest();
                    rebuildGoogleApiClient();
                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                        onConnected(null);
                    }
                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    explain("You need to give some mandatory permissions to continue");
                }
            }
        }


    }

    private void explain(String msg) {
        new AlertDialog.Builder(StoreSalonLocation.this)

                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.cancel();
                    }
                }).show();

    }


    protected LocationRequest createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }




    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
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


        if (mCurrentLocation != null ) {

            My_lat = mCurrentLocation.getLatitude();
            My_long = mCurrentLocation.getLongitude();

            getCompleteAddressString(My_lat, My_long);

            mDatabase.child(_phoneNo).child("Track").child("MyLat").setValue(String.valueOf(My_lat));
            mDatabase.child(_phoneNo).child("Track").child("MyLong").setValue(String.valueOf(My_long));
        if(rmap.getWidth()!=0) {
            if (markerCar == null) {
                markerCar = mgoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(My_lat, My_long))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_human)));
                markerCar.hideInfoWindow();
                markerCar.setAnchor(0.5f, 0.5f);
            } else {
                markerCar.setPosition(new LatLng(My_lat, My_long));
                markerCar.setAnchor(0.5f, 0.5f);
            }
            CameraPosition googlePlex;
            googlePlex = CameraPosition.builder()
                    .target(new LatLng(My_lat,My_long))
                    .zoom(18)// Sets the zoom
                    .build(); // Crea
            mgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
            final Circle circle = mgoogleMap.addCircle(new CircleOptions().center(new LatLng(My_lat, My_long))
                    .strokeColor(Color.RED).radius(100));

            ValueAnimator vAnimator = new ValueAnimator();
            vAnimator.setRepeatCount(ValueAnimator.INFINITE);
            vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
            vAnimator.setIntValues(0, 100);
            vAnimator.setDuration(5000);
            vAnimator.setEvaluator(new IntEvaluator());
            vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    // Log.e("", "" + animatedFraction);
                    circle.setRadius(animatedFraction * 20);
                }
            });
            vAnimator.start();
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
                    locality=returnedAddress.getSubLocality();
                    city=returnedAddress.getLocality();
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
    public void onConnected(Bundle bundle) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest());
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
                                resolvable.startResolutionForResult(StoreSalonLocation.this, REQUEST_CHECK_SETTINGS);
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
    public void onConnectionSuspended(int cause) {
        googleApiClientConnectionStateChange(false);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                boolean mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
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
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            if (!StoreSalonLocation.this.isFinishing()) {
                new androidx.appcompat.app.AlertDialog.Builder(StoreSalonLocation.this, R.style.AlertDialogTheme)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Could not load Google Map")
                        .setMessage("May be poor network!Contact customer care for booking offline")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        } else {
            mgoogleMap = googleMap;
            mgoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mgoogleMap.getUiSettings().setAllGesturesEnabled(true);
            mgoogleMap.getUiSettings().setMyLocationButtonEnabled(true);



        }

    }

    public void drawPolyLineOnMap(ArrayList<LatLng> list) {
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(list);

        mgoogleMap.clear();
        mgoogleMap.addPolyline(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mgoogleMap.animateCamera(cu);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(My_lat!=0){
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_LATLONG,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);

                                    String[] par = response.split("error");
                                    progressBar.setVisibility(View.GONE);
                                    if (par[1].contains("false")) {
                                        pref.setLatLong(1);
                                        StoreSalonLocation.ViewDialogFailedSuccess alert = new StoreSalonLocation.ViewDialogFailedSuccess();
                                        alert.showDialog(StoreSalonLocation.this, "Succesfully stored information.",false);

                                    }else{
                                        pref.setLatLong(0);
                                        ViewDialogFailed alert = new ViewDialogFailed();
                                        alert.showDialog(StoreSalonLocation.this, "Failed to store information! Please try another time.",true);
                                    }

                                }



                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            vollyError(error);

                        }

                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mobile",_phoneNo);
                            params.put("lat", String.valueOf(My_lat));
                            params.put("long", String.valueOf(My_long));
                            params.put("locality", String.valueOf(locality));
                            params.put("city", String.valueOf(city));
                            return params;
                        }

                    };

                    // Añade la peticion a la cola
                    AppController.getInstance().addToRequestQueue(eventoReq);
                }
                break;
            default:break;
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

    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);

                dialog1.setContentView(R.layout.custom_dialog_failed);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent o3 = new Intent(StoreSalonLocation.this, ServiceOffer.class);
                        startActivity(o3);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                        finish();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        }
    }

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate){
            if(!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.custom_dialog_success);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pref.getTimings()==0) {
                            Intent o3 = new Intent(StoreSalonLocation.this, ServiceTimings.class);
                            startActivity(o3);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            finish();
                        }else{
                            Intent o3 = new Intent(StoreSalonLocation.this, SalonHomePage.class);
                            startActivity(o3);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            finish();
                        }
                        dialog1.dismiss();
                    }
                });


                dialog1.show();
            }
        }
    }




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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            if(pref.getName()!=null){
                Intent o = new Intent(StoreSalonLocation.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }else {
                Intent o = new Intent(StoreSalonLocation.this, ServiceOffer.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }

}
