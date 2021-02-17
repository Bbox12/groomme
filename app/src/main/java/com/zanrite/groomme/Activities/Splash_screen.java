package com.zanrite.groomme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import android.os.CountDownTimer;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.BuildConfig;
import com.zanrite.groomme.Login.SignIn;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.Login.SmsActivity;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by parag on 30/08/16.
 */
public class Splash_screen extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    public static final int MULTIPLE_PERMISSIONS = 10;
    private static final String TAG = Splash_screen.class.getSimpleName();
    private ConnectionDetector cd;
    private PrefManager pref;
    private boolean permissionsAllowd;
    private RelativeLayout Splash;
    private CoordinatorLayout coordinatorLayout;
    private int Online;
    private String _phoneNo;
    private DatabaseReference mDatabase;
    private boolean launced=false;
    private int version_=7,imp=1;
    private int version_1=0,_imp_1=0;
    private CardView _c1;
    private String token="";
    private ProgressDialog mProgressDialog = null;
    private MyCountDownTimer myCountDownTimer;;
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
    private GoogleApiClient mGoogleApiClient;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private String strAdd;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private String locality="",city="";
    private boolean _first;

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.splash){
            launchHomeScreen();
        }

    }

    public class MyCountDownTimer extends CountDownTimer {



        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

        }



        @Override
        public void onTick(long millisUntilFinished) {

            if (!Splash_screen.this.isFinishing() ) {

                    if (mProgressDialog == null) {
                        mProgressDialog = new ProgressDialog(Splash_screen.this, R.style.AlertDialogTheme_new);
                        mProgressDialog.setTitle("Connecting Groom Me");
                        mProgressDialog.setMessage("please wait...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.show();
                    }


            }
        }

        @Override
        public void onFinish() {
            if (!Splash_screen.this.isFinishing()) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                launchHomeScreen();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermissions();
        setContentView(R.layout.splash_screen);
        cd = new ConnectionDetector(getApplicationContext());
        pref = new PrefManager(getApplicationContext());
        if(pref.getResposibility()==1){
            HashMap<String, String> user = pref.getUserDetails2();
            _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        }else if(pref.getResposibility()==2){
            HashMap<String, String> user = pref.getUserDetails();
            _phoneNo = user.get(PrefManager.KEY_MOBILE);
        }
        Splash = findViewById(R.id.splash);
        Splash.setOnClickListener(this);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        _c1 = findViewById(R.id._c1);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        pref.setHeaderImage("http://139.59.38.160/Groom/App/header/logup.png");


        buildLocationSettingsRequest();
        rebuildGoogleApiClient();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            onConnected(null);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


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


        if (!_first ) {
            _first=true;

            My_lat = mCurrentLocation.getLatitude();
            My_long = mCurrentLocation.getLongitude();
            pref.setDropLat(String.valueOf(My_lat));
            pref.setDropLong(String.valueOf(My_long));
            getCompleteAddressString(My_lat,My_long);
            if (myCountDownTimer == null) {
                myCountDownTimer = new MyCountDownTimer(2000, 100);
                myCountDownTimer.start();
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
                    pref.setparlour_locality(locality);
                    pref.setCity(city);
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
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            String emailLink = intent.getData().toString();
            boolean t=auth.isSignInWithEmailLink(emailLink);
            if (auth.isSignInWithEmailLink(emailLink)) {
               if(pref.getEmail()!=null) {
                   String email = pref.getEmail();

                   // The client SDK will parse the code from the link for you.
                   auth.signInWithEmailLink(email, emailLink)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       Log.d(TAG, "Successfully signed in with email link!");
                                       if(pref.getID3()==2) {
                                           Intent o = new Intent(Splash_screen.this, SignIn.class);
                                           o.putExtra("from", 1);
                                           startActivity(o);
                                           overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                           finish();
                                       }else{
                                           Intent o = new Intent(Splash_screen.this, SmsActivity.class);
                                           o.putExtra("from", 1);
                                           startActivity(o);
                                           overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                           finish();
                                       }
                                   } else {
                                       ViewDialogFailed alert = new ViewDialogFailed();
                                       alert.showDialog(Splash_screen.this, "The email address already exists! Please use forgot password.",true);

                                   }
                               }
                           });
               }else {
                   ViewDialogFailed alert = new ViewDialogFailed();
                   alert.showDialog(Splash_screen.this, "The email has been expired! Please signup again.",true);
               }
            }

        }


    }

    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);

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

                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(myCountDownTimer!=null) {
            myCountDownTimer.cancel();
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(myCountDownTimer!=null) {
            myCountDownTimer.cancel();
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myCountDownTimer!=null) {
            myCountDownTimer.cancel();
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
            if (permissionsAllowd) {
                mDatabase.child("Online").addValueEventListener(new FirebaseDataListener_online());
            } else {
                checkAndRequestPermissions();
            }

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            if (checkPermissions()) {
                startLocationUpdat();
            }else {
                requestPermissions();
            }
        }

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
            permissionsAllowd = true;
        }

    }






    private void explain(String msg) {
        new AlertDialog.Builder(Splash_screen.this)

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

    private void launchHomeScreen() {

            if (_phoneNo != null) {
                getInfo();
            } else {
                Intent o = new Intent(Splash_screen.this, ServiceOffer.class);
                o.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

            }

    }


    private void getInfo() {

        if (!Splash_screen.this.isFinishing() ) {



            if (TextUtils.isEmpty(token)) {
                if (pref.getRegID() != null) {
                    token = pref.getRegID();
                }
            }


            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.w("kk", response.toString());


                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray login = jsonObj.getJSONArray("users");

                                JSONArray version = jsonObj.getJSONArray("version");

                                for (int i = 0; i < version.length(); i++) {
                                    JSONObject c = version.getJSONObject(i);

                                    if (!c.isNull("Version")) {
                                        version_1 = ((c.getInt("Version")));
                                        _imp_1 = (c.getInt("Importance"));
                                        pref.setDate(c.getString("date"));
                                        pref.setTime(c.getString("time"));
                                        pref.setCancellationCharge(c.getString("cancellationcharge"));
                                        pref.setDistance((float) c.getDouble("distance"));
                                        pref.setYoutube(c.getString("youtube"));
                                        pref.setYoutubeCategory(c.getInt("youtubecategory"));
                                        pref.setYoutubeTitile(c.getString("_titleYoutube"));
                                        pref.setLadies(c.getInt("Ladies"));
                                        pref.setSex(c.getInt("SEX"));
                                    }
                                }


                                if (login.length() != 0) {
                                    for (int i = 0; i < login.length(); i++) {
                                        JSONObject c = login.getJSONObject(i);

                                        if (!c.isNull("parlour_mobile") ||!c.isNull("parlour_email")) {
                                            pref.setparlour_about(c.getString("parlour_about"));
                                            pref.setName(c.getString("parlour_name"));
                                            pref.setSalonPhone(c.getString("parlour_mobile"));
                                            pref.setAddress(c.getString("parlour_address"));


                                            pref.setPhoto(c.getString("pic"));
                                            pref.setparlour_pin(c.getString("parlour_pin"));
                                            pref.setEmail(c.getString("parlour_email"));
                                            if(!TextUtils.isEmpty(c.getString("parlour_city"))) {
                                                pref.setCity(c.getString("parlour_city"));
                                                pref.setparlour_locality(c.getString("parlour_locality"));
                                            }
                                            pref.setparlour_registration(c.getString("parlour_registration"));
                                            pref.setisVerified(c.getInt("isVerified"));

                                            pref.setPickLat(c.getString("latitude"));
                                            pref.setPickLong(c.getString("longitude"));

                                            pref.setservice_location(c.getString("service_location"));
                                            if (c.getInt("isSechedule") == 1) {
                                                pref.setTimings(1);
                                            }
                                            if (c.getInt("isSpecialist") == 1) {
                                                pref.setisSpecialist(1);
                                            }
                                            if (c.getInt("isLocation") == 1) {
                                                pref.setLatLong(1);
                                            }
                                            if (c.getInt("isServiceAt") == 1) {
                                                pref.setLocation(1);
                                            }
                                            displayFirebaseRegId();
                                        }
                                    }
                                    JSONArray salonpresentbooking = jsonObj.getJSONArray("salonpresentbooking");
                                    if (salonpresentbooking.length() != 0) {
                                        for (int i = 0; i < salonpresentbooking.length(); i++) {
                                            JSONObject c = salonpresentbooking.getJSONObject(i);

                                            if (!c.isNull("ID")) {
                                                pref.setOrderID(c.getString("OTP"));
                                                pref.setActualTime(c.getString("OrderDate") + " " + c.getString("ActualTime"));
                                                pref.setisRunning(c.getInt("isRunning"));
                                            }
                                        }
                                    } else {
                                        pref.set_food_items(0);
                                        pref.set_food_money(0);
                                        pref.setPref1(null);
                                        pref.setPref2(null);
                                        pref.setPref3(null);
                                        pref.setPref4(null);
                                        pref.setnoOfItems(null);
                                        pref.packagesharedPreferences(null);
                                        pref.setOrderID(null);
                                        pref.setActualTime(null);

                                    }


                                } else {
                                    JSONArray _Attendence = jsonObj.getJSONArray("login");
                                    for (int i = 0; i < _Attendence.length(); i++) {
                                        JSONObject c = _Attendence.getJSONObject(i);
                                        if (!c.isNull("PhoneNo")) {
                                      //      pref.createLogin(c.getString("PhoneNo"), c.getString("Name"));
                                            if (!c.isNull("User_Referrence_Code")) {
                                                pref.setReferalCode(c.getString("User_Referrence_Code"));
                                            }
                                        }

                                    }

                                    JSONArray customerpresentbooking = jsonObj.getJSONArray("customerpresentbooking");
                                    if (customerpresentbooking.length() != 0) {
                                        for (int i = 0; i < customerpresentbooking.length(); i++) {
                                            JSONObject c = customerpresentbooking.getJSONObject(i);

                                            if (!c.isNull("ID")) {
                                                pref.setOrderID(c.getString("OTP"));
                                                pref.setActualTime(c.getString("OrderDate") + " " + c.getString("ActualTime"));
                                                pref.setisRunning(c.getInt("isRunning"));
                                                pref.setRunnungParlourMobile(c.getString("parlour_mobile"));
                                            }
                                        }
                                    } else {
                                        pref.set_food_items(0);
                                        pref.set_food_money(0);
                                        pref.setPref1(null);
                                        pref.setPref2(null);
                                        pref.setPref3(null);
                                        pref.setPref4(null);
                                        pref.setnoOfItems(null);
                                        pref.setServiceAt(0);
                                        pref.packagesharedPreferences(null);
                                        pref.setOrderID(null);
                                        pref.setActualTime(null);
                                    }

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            go_trough();

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                                .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                                .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                                .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                                .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
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

            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", _phoneNo);
                    params.put("token", token);
                    params.put("role", String.valueOf(pref.getResposibility()));
                    return params;
                }

            };

            // Añade la peticion a la cola
            AppController.getInstance().addToRequestQueue(eventoReq);
        }

    }



    private void go_trough() {
            if (_imp_1 == imp) {
                if (!Splash_screen.this.isFinishing()) {
                    new AlertDialog.Builder(Splash_screen.this, R.style.AlertDialogTheme_new)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Update Groom Me")
                            .setMessage("A new version of Groom Me is available!")
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            } else {

                if (version_1 != version_) {
                    if (!Splash_screen.this.isFinishing()) {
                        new AlertDialog.Builder(Splash_screen.this, R.style.AlertDialogTheme)
                                .setTitle("Its time to update Groom Me")
                                .setMessage("A new version of Groom Me is available!")
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        go_again();

                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }
                } else {
                    go_again();
                }
            }

    }

    private void go_again() {
      if(!Splash_screen.this.isFinishing()) {
          if (mProgressDialog != null) {
              mProgressDialog.dismiss();
          }
          mDatabase.child("Online").removeEventListener(new FirebaseDataListener_online());
          mDatabase.child(_phoneNo).setValue("1");

          if (pref.getResposibility() == 2) {
              if (pref.getparlour_registration() == null || TextUtils.isEmpty(pref.getparlour_registration())) {
                  Intent o = new Intent(Splash_screen.this, RegisterAsSalon.class);
                  o.putExtra("from", 1);
                  startActivity(o);
                  finish();
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

              } else if (pref.getTimings() == 0) {
                  Intent o = new Intent(Splash_screen.this, ServiceTimings.class);
                  startActivity(o);
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                  finish();
              } else if (pref.getisSpecialist() == 0) {
                  Intent o = new Intent(Splash_screen.this, StoreSlaonSpecialist.class);
                  startActivity(o);
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                  finish();
              }else if (pref.getLocation() == 0) {
                  Intent o = new Intent(Splash_screen.this, StoreServiceInformation.class);
                  startActivity(o);
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                  finish();
              } else if (pref.getLatLong() == 0) {
                  CustomNotification1("Location of the salon not specified. Please specify.");
                  Intent o = new Intent(Splash_screen.this, StoreSalonLocation.class);
                  startActivity(o);
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                  finish();
              }   else {
                  Intent o = new Intent(Splash_screen.this, SalonHomePage.class);
                  startActivity(o);
                  overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                  finish();
              }
          } else if (pref.getResposibility() == 1) {
              Intent o = new Intent(Splash_screen.this, UserHomeScreen.class);
              startActivity(o);
              overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
              finish();
          } else {
              Intent o = new Intent(Splash_screen.this, SignUp.class);
              startActivity(o);
              overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
              finish();
          }
      }

    }

    private void displayFirebaseRegId() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        token = task.getResult().getToken();
                        pref.setRegID(token);
                        if(pref.getResposibility()==2) {
                            if (_phoneNo != null && mDatabase != null) {
                                mDatabase.child("RegID").child(_phoneNo).setValue(token);
                            }
                        }
                    }

                });
    }



    public void CustomNotification1(String durationo) {
        Uri alarmSound = Uri.parse("android.resource://"
                + Splash_screen.this.getPackageName() + "/" + R.raw.hollow);

        // Set Notification Title

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), Splash_screen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(Splash_screen.this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Need your attention!");
        bigText.setBigContentTitle(durationo);
        bigText.setSummaryText("Groom Me");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(durationo);
        mBuilder.setContentText("Need your attention!");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setSound(alarmSound);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager = (NotificationManager) Splash_screen.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_001";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    durationo,
                    NotificationManager.IMPORTANCE_HIGH);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }

            mBuilder.setChannelId(channelId);

        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private class FirebaseDataListener_online implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String val= (String) dataSnapshot.getValue();
            if (val != null) {
                if (!launced) {
                    launced = true;
                    Online = 1;

                }
            }else{
                if (!launced) {
                    launced = true;
                    if (!Splash_screen.this.isFinishing()) {
                        new AlertDialog.Builder(Splash_screen.this,R.style.AlertDialogTheme)
                                .setTitle("No service")
                                .setMessage("Please contact customer care!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })

                                .show();
                    }
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
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
                            ActivityCompat.requestPermissions(Splash_screen.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(Splash_screen.this,
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
                                resolvable.startResolutionForResult(Splash_screen.this, REQUEST_CHECK_SETTINGS);
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

            case MULTIPLE_PERMISSIONS: 
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(TAG, "sms & location services permission granted");
                        startLocationUpdat();



                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        explain("You need to give some mandatory permissions to continue");
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

}




