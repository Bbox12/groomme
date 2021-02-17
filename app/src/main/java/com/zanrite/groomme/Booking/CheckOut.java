package com.zanrite.groomme.Booking;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Adapters.TimeslotRv;
import com.zanrite.groomme.Adapters.homepageTeamdapter;
import com.zanrite.groomme.Alarm.AlarmSoundService;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.BuildConfig;
import com.zanrite.groomme.Fragments.SalonServiceShow;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.MyViewPager;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;


public class CheckOut extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_PICK_FROM = 10016;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 101;
    private final int REQUEST_LOCATION = 200;
    private final int REQUEST_CHECK_SETTINGS = 300;
    Boolean isInternetPresent = false;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyy-MM-dd");
    private ConnectionDetector cd;
    private PrefManager pref;
    private double My_lat = 0, My_long = 0;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private String postUrl;
    private TextView canteen_name;
    private RecyclerView _moreRv;
    private LinearLayout L1, L2;
    private LinearLayout R1;
    private TextView _moneyValue, _itemValue, _discount;
    private TextView discount, _moreValue, _servicable, _reason, minimum_order, minimum_person, canteen_time, canteen_time1;
    private DecimalFormat df = new DecimalFormat("0.00");
    private DecimalFormat dft = new DecimalFormat("0");
    private ArrayList<Total> mItems = new ArrayList<Total>();
    private Button _cancel, _confirm;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading = false;
    private int _last = 0;
    private int myID = 0;
    private boolean _end = false;
    private int hour;
    private TextView _tAmount, _dAmount, _payAmount, _cAmount;
    private String _total;
    private double _less, _more;
    private DatabaseReference mDatabase;
    private double Distance = 0;
    private String mobileIp;
    private String OTP;
    private int j = 0;
    private String commaSeparatedValues;
    private RelativeLayout _can;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    private boolean mResolvingError = false;
    private boolean first = false;
    private int _from = 0;



    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.w("eat", "hi");

        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.checkout_layout);

        cd = new ConnectionDetector(getApplicationContext());
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        progressBar = findViewById(R.id.progressBar3_eats);
        _can = findViewById(R.id.can);
        coordinatorLayout = findViewById(R.id.main_content);
        _cancel = findViewById(R.id._cancel_book);
        _confirm = findViewById(R.id._confirm_book);
        _confirm.setOnClickListener(this);
        _cancel.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _moreRv = findViewById(R.id.moreRv);
        _moreRv.setNestedScrollingEnabled(false);
        canteen_name = findViewById(R.id._name);
        canteen_name.setText(pref.getName());
        _moneyValue = findViewById(R.id.canteen_amount);
        _itemValue = findViewById(R.id._noofItems);
        _discount = findViewById(R.id.discount);
        _tAmount = findViewById(R.id._tamount);
        _dAmount = findViewById(R.id._damount);
        _cAmount = findViewById(R.id._camount);
        _payAmount = findViewById(R.id._payamount);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           
                    Intent i = new Intent(CheckOut.this, SalonServiceShow.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                
            }
        });

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CheckOut.this);
        createLocationCallback();
        buildLocationSettingsRequest();
        rebuildGoogleApiClient();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            onConnected(null);
        }

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(CheckOut.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }




    @Override
    protected void onStart() {
        super.onStart();
        if(pref.get_food_items()>0) {
            _itemValue.setText("No of items " + pref.get_food_items());
            if (pref.get_packagesharedPreferences() != null) {
                mItems.clear();
                Set<String> set = pref.get_packagesharedPreferences();
                double Rate = 0;
                for (String s : set) {
                    String[] pars = s.split("\\_");
                    Total items = new Total();
                    items.setID(Integer.parseInt(pars[0]));
                    items.setNoofItems((int) Double.parseDouble(pars[1]));
                    items.setPrice(pars[2]);
                    Rate = (int) (Double.parseDouble(pars[2]) + Rate);
                    items.setName((pars[4]));
                    mItems.add(items);
                }
                initCollapsingToolbar();
                _moreRv.setVisibility(View.VISIBLE);
                BookingAdapter sAdapter1 = new BookingAdapter(CheckOut.this, mItems);
                sAdapter1.notifyDataSetChanged();
                sAdapter1.setPref(pref);
                mLayoutManager = new LinearLayoutManager(CheckOut.this, RecyclerView.VERTICAL, false);
                _moreRv.setLayoutManager(mLayoutManager);
                _moreRv.setItemAnimator(new DefaultItemAnimator());
                _moreRv.setAdapter(sAdapter1);
                _moreRv.setHasFixedSize(true);
                _tAmount.setText(df.format(Rate));
                double tot=0;
                    if (pref.getcDiscount() != 0) {
                        _discount.setVisibility(View.VISIBLE);
                        double i=pref.getcDiscount();
                        _discount.setText(dft.format(i) + "% discount on all service");
                         tot = Rate* .01 * i;
                        _dAmount.setText("(-)"+df.format(tot));
                    } else {
                        _discount.setVisibility(View.GONE);
                    }

                if (pref.getCharge() == 0) {
                    _can.setVisibility(View.GONE);
                } else {
                    _can.setVisibility(View.VISIBLE);
                    _cAmount.setText(df.format(pref.getCharge()));
                }
                double j=Rate-tot+pref.getCharge();
                _total = df.format(j);
                _payAmount.setText("R" + _total);
                pref.setTotal(_total);
                _moneyValue.setText(_total);


            }
        }else{
            pref.set_food_items(0);
            pref.set_food_money(0);
            pref.packagesharedPreferences(null);
            pref.setnoOfItems(null);
            pref.setServiceAt(0);
            Intent o = new Intent(CheckOut.this, SalonServiceShow.class);
            startActivity(o);
            finish();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        progressBar.setVisibility(View.GONE);


    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public int getSpan() {
        int orientation = getScreenOrientation(CheckOut.this);
        if (isTV(CheckOut.this)) return 4;
        if (isTablet(CheckOut.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }






    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {





            case R.id._cancel_book:
                if(pref.getOrderID()!=null) {
                    final String url;
                    url = Config_URL.CUSTOMER_CANCELED;

                    if (!(CheckOut.this.isFinishing())) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(CheckOut.this);

                        LinearLayout ll_alert_layout = new LinearLayout(CheckOut.this);
                        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
                        final EditText ed_input = new EditText(CheckOut.this);
                        ll_alert_layout.addView(ed_input);

                        alertbox.setTitle("Cancellation reason");
                        alertbox.setMessage("Enter your reason");

                        //setting linear layout to alert dialog

                        alertbox.setView(ll_alert_layout);

                        alertbox.setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Toast.makeText(CheckOut.this, "not cancelled", Toast.LENGTH_SHORT).show();
                                        arg0.cancel();

                                    }
                                });


                        alertbox.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(final DialogInterface arg0, int arg1) {
                                        if (!TextUtils.isEmpty(ed_input.getText().toString())) {
                                            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, url,
                                                    new Response.Listener<String>() {

                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.w("bookingservices", response);
                                                            String[] pars = response.split("error");
                                                            if (pars[1].contains("false")) {
                                                                CheckOut.this.stopService(new Intent(CheckOut.this, AlarmSoundService.class));
                                                                pref.setOrderID(null);
                                                                mDatabase.child("Request").child(pref.getSalonPhone()).removeValue();
                                                                mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).removeValue();
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
                                                                pref.setPaymentMode(0);
                                                                    Intent o = new Intent(CheckOut.this, SalonHomePage.class);
                                                                    CheckOut.this.startActivity(o);
                                                                    ((Activity) CheckOut.this).finish();


                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    VolleyLog.d("uuu", "Error: " + error.getMessage());
                                                    // vollyError(error);
                                                }
                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("order", pref.getOrderID());
                                                    params.put("msg", ed_input.getText().toString());
                                                    return params;
                                                }
                                            };
                                            AppController.getInstance().addToRequestQueue(eventoReq);
                                        }
                                    }
                                });
                        alertbox.show();
                    }
                }
                else if (!CheckOut.this.isFinishing()) {

                    new AlertDialog.Builder(CheckOut.this, R.style.AlertDialogTheme)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Are you sure?")
                            .setMessage("Press Ok to cancel your booking ")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
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
                                    pref.setPaymentMode(0);
                                    Intent i = new Intent(CheckOut.this, SalonServiceShow.class);
                                    startActivity(i);
                                    finish();
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }


                break;


            case R.id._confirm_book:
                pref.setPaymentMode(0);
                if (mGoogleApiClient != null) {
                    mGoogleApiClient.connect();
                    if (checkPermissions()) {
                        startLocationUpdat();
                    } else {
                        requestPermissions();
                    }
                }
                if(pref.getOrderID()!=null){
                    mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).removeValue();
                }
                Intent i = new Intent(CheckOut.this, CheckoutTime.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;

            default:
                break;
        }
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
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(CheckOut.this, SalonServiceShow.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
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
            Log.i("HI", "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(CheckOut.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i("HI", "Requesting permission");
            ActivityCompat.requestPermissions(CheckOut.this,
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startLocationUpdat();
                }
            }
            break;
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length <= 0) {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i("HI", "User interaction was cancelled.");
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startLocationUpdat();


                } else {
                    showSnackbar(R.string.permission_denied_explanation,
                            R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
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
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }


    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                updateLocationUI(mCurrentLocation);
            }
        };
    }

    private void startLocationUpdat() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (mFusedLocationClient != null) {
            mFusedLocationClient.requestLocationUpdates(createLocationRequest(), new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {

                            List<Location> locationList = locationResult.getLocations();
                            if (locationList.size() > 0) {
                                Location location = locationList.get(locationList.size() - 1);
                                Log.i("MapsActivity", "Location: " + location);
                                mCurrentLocation = location;
                                updateLocationUI(mCurrentLocation);
                            }

                        }
                    },
                    Looper.myLooper());
        }
    }

    private void updateLocationUI(Location mCurrentLocatio) {


        My_lat = mCurrentLocatio.getLatitude();
        My_long = mCurrentLocatio.getLongitude();
        if (!first) {
            first = true;
            pref.setDropAt1(getCompleteAddressString(My_lat, My_long));
            pref.setDropLat(String.valueOf(My_lat));
            pref.setDropLong(String.valueOf(My_long));


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
                    strReturnedAddress.append(returnedAddress.getAddressLine(i));
                }
                strAdd = strReturnedAddress.toString();
                Log.w(" address", strReturnedAddress.toString());
            } else {
                strAdd = "Canont get Address! Put manually";
                Log.w(" address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            strAdd = "Canont get Address! Put manually";
            Log.w(" loction address", "Canont get Address!");
        }
        return strAdd;
    }


    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
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
                    startLocationUpdat();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                resolvable.startResolutionForResult(CheckOut.this, REQUEST_CHECK_SETTINGS);
                                break;
                            } catch (IntentSender.SendIntentException e) {
                                Log.w(" ClassCastException", "Canont get Address!" + e.getMessage());
                            } catch (ClassCastException e) {
                                Log.w(" ClassCastException", "Canont get Address!" + e.getLocalizedMessage());
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClientConnectionStateChange(false);
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        if (mResolvingError) {

        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        } else {
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    private void showErrorDialog(int errorCode) {
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    private void stopLocationUpdates() {

        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                    }
                });
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

    }

    protected synchronized void rebuildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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






