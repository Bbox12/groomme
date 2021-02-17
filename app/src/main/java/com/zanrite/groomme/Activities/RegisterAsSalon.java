package com.zanrite.groomme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zanrite.groomme.Adapters.SecondaryServiceAdapter;
import com.zanrite.groomme.Adapters._CommodityAdapter;
import com.zanrite.groomme.Booking.CheckoutTime;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by parag on 05/01/18.
 */

public class RegisterAsSalon extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = RegisterAsSalon.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;

    private Toolbar toolbar;
    private EditText dFirstname,dRegistration,dEmail,dAddress,dCity,dState,dLocality,dPin,dAbout;
    private EditText d_PhoneNo;
    private ImageView dImage;
    private Button Submit1;
    private ConnectionDetector cd;
    private boolean isInternetPresent=false;
    private PrefManager pref;
    private ProgressBar progressBar;
    private String Name,_PhoneNo,WHO;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String filePath;
    private int mYear, mMonth, mDay;
    private String Cam;
    private String salonname,salonimage,salonmobileno,salonemailaddress,salonaddress,saloncity,salonpin,saloonregistration;
    private DatabaseReference mDatabase;
    private boolean Again=false;
    private String fileImage;
    private double My_lat=0,My_long=0;
    private String Again_upload="NO";
    private TextView Text101,Text102;
    private int Online=0;
    private int Owner_ID=0;
    private String mobileIp;
    private String regId;
    private boolean reload=false;
    private DatePickerDialog datePickerDialog;
    private Bitmap bitmap;
    private TextInputLayout m0,m1,m2,m3,m4,m5,m6,m7,m8;
    private boolean permissionsAllowd=false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private Dialog dialogWait;
    private int _from=0;
   private RelativeLayout rtToolbar;
    private Bitmap bm;
    private CoordinatorLayout coordinatorLayout;
    private String token="";
    private RecyclerView all_services;
    final ArrayList<String> mServices = new ArrayList<String>();
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
    private static final int REQUEST_PICK_FROM = 10016;
    private int _address=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        Name = user.get(PrefManager.KEY_NAME);
        WHO = user.get(PrefManager.KEY_WHO);
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

        setContentView(R.layout.registerassalon);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        dAbout=findViewById(R.id.aboutus);
        d_PhoneNo=findViewById(R.id.mobileo);
        dFirstname=findViewById(R.id.fnameo);
        dRegistration=findViewById(R.id.register);
        dEmail=findViewById(R.id.emailo);
        dAddress=findViewById(R.id.addresso);
        dCity=findViewById(R.id.city);
        dLocality=findViewById(R.id.locality);
        dPin=findViewById(R.id.pino);
        dImage=findViewById(R.id.img_profilo);
        Text101=findViewById(R.id.textView101);
        Text102=findViewById(R.id.textView102);
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        m0=findViewById(R.id.m0);
        m1=findViewById(R.id.m1);
        m2=findViewById(R.id.m2);
        m3=findViewById(R.id.m3);
        m4=findViewById(R.id.m4);
        m6=findViewById(R.id.m6);
        m7=findViewById(R.id.m7);
        m8=findViewById(R.id.m8);
        rtToolbar=findViewById(R.id._rtoolbar);


        all_services=findViewById(R.id.all_services);
        all_services.setNestedScrollingEnabled(false);
        all_services.setVisibility(View.VISIBLE);


        dImage.setOnClickListener(this);
        dRegistration.setOnClickListener(this);
        dAddress.setOnClickListener(this);

        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        progressBar= findViewById(R.id.progress_driver);

        Intent i=getIntent();
        _from=i.getIntExtra("from",0);
        initCollapsingToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(RegisterAsSalon.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else {
                    Intent o = new Intent(RegisterAsSalon.this, ServiceOffer.class);
                    o.putExtra("my_lat", My_lat);
                    o.putExtra("my_long", My_long);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mobileIp = getMobileIPAddress();
        if(TextUtils.isEmpty(mobileIp)){
            mobileIp= getWifiIPAddress();
        }

        mAuth=FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                ViewDialogVerify alert = new ViewDialogVerify();
                alert.showDialog(RegisterAsSalon.this, "Verification failed. Please check mobile no.",false);



                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                 open_otp(verificationId);
            }
        };

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(RegisterAsSalon.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }

        if (!com.google.android.libraries.places.api.Places.isInitialized()) {
            com.google.android.libraries.places.api.Places.initialize(getApplicationContext(),Config_URL.APIKEY);
        }
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);


        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Salon");
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);

                    isShow = false;
                }
            }
        });
    }



    private void open_otp(final String verificationId) {
        if(!RegisterAsSalon.this.isFinishing()) {
            final Dialog dialog = new Dialog(RegisterAsSalon.this, R.style.ThemeTransparent);
            if(dialogWait!=null){
                dialogWait.dismiss();
            }

            dialog.setContentView(R.layout.custom_dialog_otp);
            final EditText Otp = dialog.findViewById(R.id.inputOtp_ride);
            Button Start = dialog.findViewById(R.id.btn_dialog);
            WebView _webview = dialog.findViewById(R.id.webView);
            _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
            _webview.loadUrl("file:///android_asset/verify.gif");
            _webview.getSettings().setLoadsImagesAutomatically(true);
            _webview.getSettings().setLoadWithOverviewMode(true);
            _webview.getSettings().setUseWideViewPort(true);
            dialog.setCancelable(true);

            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(Otp.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please input OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Otp.getText().toString());
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(RegisterAsSalon.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            ViewDialogVerify alert = new ViewDialogVerify();
                                            alert.showDialog(RegisterAsSalon.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                                ViewDialogVerify alert = new ViewDialogVerify();
                                                alert.showDialog(RegisterAsSalon.this, "Verification failed. Please check mobile no.",false);
                                            }
                                        }
                                    }
                                });

                    }
                    dialog.cancel();
                }
            });


            dialog.show();
        }

    }
    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return null;
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }




    @Override
    protected void onResume() {
        super.onResume();
        if(Again){
            if(bitmap!=null) {
                dImage.setImageBitmap(bitmap);
            }
        }else {
            if(_address==0) {
                StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_ALL_PARLOURS,
                        new Response.Listener<String>() {


                            final ArrayList<Slots> mCategory = new ArrayList<Slots>();

                            @Override
                            public void onResponse(String response) {

                                Log.w("mainlayout", response);

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    JSONArray category = jsonObj.getJSONArray("category");
                                    for (int i = 0; i < category.length(); i++) {
                                        JSONObject c = category.getJSONObject(i);
                                        if (!c.isNull("Category")) {
                                            Slots item = new Slots();
                                            item.setSlots(c.getString("Category"));
                                            item.setID(c.getInt("ID"));
                                            mCategory.add(item);
                                        }
                                    }


                                    if (_PhoneNo != null) {
                                        dFirstname.setText(Name);
                                        dRegistration.setText(pref.getparlour_registration());
                                        d_PhoneNo.setText(pref.getSalonPhone());
                                        //  d_PhoneNo.setEnabled(false);
                                        //  d_PhoneNo.setActivated(false);
                                        //  d_PhoneNo.setFocusableInTouchMode(false);
                                        //   m1.setEnabled(false);
                                        //   m1.setError("Can not change");
                                        //    d_PhoneNo.setTextColor(getResources().getColor(R.color.black_overlay));
                                        dAddress.setText(pref.getAddress());
                                        dAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_address, 0, 0, 0);

                                        dPin.setText(pref.getparlour_pin());
                                        dEmail.setText(pref.getEmail());
                                        dAbout.setText(pref.getparlour_about());
                                        if (pref.getCity() != null) {
                                            dCity.setText(pref.getCity());
                                        }

                                        if (pref.getparlour_locality() != null) {
                                            dLocality.setText(pref.getparlour_locality());
                                        }

                                        if (pref.getPhoto() != null && !Again) {
                                            Target target = new Target() {


                                                @Override
                                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                    if (bitmap != null) {
                                                        bm = bitmap;
                                                        dImage.setImageBitmap(bm);
                                                    }
                                                }

                                                @Override
                                                public void onBitmapFailed(Drawable errorDrawable) {
                                                    Toast.makeText(getApplicationContext(), "Unable to retrieve image.", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                                }
                                            };
                                            Picasso.with(RegisterAsSalon.this).load(pref.getPhoto()).into(target);

                                        }


                                    }


                                    if (mCategory.size() > 0) {
                                        all_services.setVisibility(View.VISIBLE);
                                        SecondaryServiceAdapter sAdapter1 = new SecondaryServiceAdapter(RegisterAsSalon.this, mCategory);
                                        sAdapter1.notifyDataSetChanged();
                                        sAdapter1.setHasStableIds(true);
                                        sAdapter1.setFrom(1);
                                        all_services.setAdapter(sAdapter1);
                                        all_services.setHasFixedSize(true);
                                        LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(RegisterAsSalon.this, RecyclerView.HORIZONTAL, false);
                                        all_services.setLayoutManager(horizontalLayoutManagae);
                                        all_services.setItemAnimator(new DefaultItemAnimator());

                                        all_services.addOnItemTouchListener(
                                                new RecyclerTouchListener(RegisterAsSalon.this, all_services,
                                                        new RecyclerTouchListener.OnTouchActionListener() {


                                                            @Override
                                                            public void onClick(View view, final int position) {
                                                                Log.w("gallery", String.valueOf(position));
                                                                if (mCategory.get(position) != null) {
                                                                    mServices.add(String.valueOf(mCategory.get(position).getID(position)));
                                                                }

                                                                if (mServices.size() != 0) {
                                                                    Set<String> set1 = new HashSet<>(mServices);
                                                                    mServices.clear();
                                                                    mServices.addAll(set1);
                                                                }
                                                            }

                                                            @Override
                                                            public void onRightSwipe(View view, int position) {

                                                            }

                                                            @Override
                                                            public void onLeftSwipe(View view, int position) {

                                                            }
                                                        }));
                                    }
                                    progressBar.setVisibility(View.GONE);
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
                        params.put("from", String.valueOf(1));
                        return params;
                    }

                };
                AppController.getInstance().addToRequestQueue(eventoReq);
            }
        }

        d_PhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                      m1.setCounterEnabled(true);
                      m1.setError(null);

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()==10){
                    salonmobileno =  "+27"+d_PhoneNo.getText().toString();
                    d_PhoneNo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                    dFirstname.requestFocus();


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dAbout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m0.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dRegistration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m4.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        dEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m3.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m1.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m6.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m8.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                m7.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dLocality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dLocality.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addresso:
                List<com.google.android.libraries.places.api.model.Place.Field> placeFields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME, com.google.android.libraries.places.api.model.Place.Field.ADDRESS
                        , com.google.android.libraries.places.api.model.Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, placeFields)
                        .build(this);
                startActivityForResult(intent, REQUEST_PICK_FROM);

                break;
            case R.id.submit:
                boolean valid=false;
                if(!TextUtils.isEmpty(d_PhoneNo.getText().toString())&& !TextUtils.isEmpty(dFirstname.getText().toString())&& !TextUtils.isEmpty(dAbout.getText().toString())
                        && !TextUtils.isEmpty(dCity.getText().toString())  && !TextUtils.isEmpty(dRegistration.getText().toString() ) && !TextUtils.isEmpty(dCity.getText().toString())
                ){
                    valid=true;


                }else{
                    Toast.makeText(getApplicationContext(),"Please fill up the form ",Toast.LENGTH_SHORT).show();
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[com]+";
                if (dEmail.getText().toString().trim().length() == 0 && !dEmail.getText().toString().matches(emailPattern)) {
                    m3.setError("Empty");
                    valid=false;
                }

                if(TextUtils.isEmpty(dAbout.getText().toString())){
                    m0.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(d_PhoneNo.getText().toString())){
                    m1.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dAbout.getText().toString())){
                    m0.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dFirstname.getText().toString())){
                    m2.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dRegistration.getText().toString())){
                    m4.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dAddress.getText().toString())){
                    m6.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dPin.getText().toString())){
                    m8.setError("Can not empty");
                    valid=false;
                }
                if(TextUtils.isEmpty(dCity.getText().toString())){
                    m7.setError("Can not empty");
                    valid=false;
                }
               if(valid) {
                   if (bm!=null) {
                       if(mServices.size()>0) {
                           if(!d_PhoneNo.getText().toString().contains("NA") && TextUtils.isDigitsOnly(d_PhoneNo.getText().toString())) {
                               salonmobileno = "+27" + d_PhoneNo.getText().toString();
                               PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                       salonmobileno,        // Phone number to verify
                                       60,                 // Timeout duration
                                       TimeUnit.SECONDS,   // Unit of timeout
                                       RegisterAsSalon.this,               // Activity (for callback binding)
                                       mCallbacks);
                               ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                               Firstalert.showDialog(RegisterAsSalon.this, "Verifying phone no. Please wait...", true);

                           }else{
                               new PostDataTOServer().execute();
                           }
                       }else{
                           Toast.makeText(getApplicationContext(),"Please select category of the salon",Toast.LENGTH_SHORT).show();
                           all_services.requestFocus();
                       }

                   }else {
                       ViewDialogFailed alert = new ViewDialogFailed();
                       alert.showDialog(RegisterAsSalon.this, "Please add salon photo. ",true);
                   }
               }
                break;

            case R.id.img_profilo:

                    if (permissionsAllowd) {
                        selectImage();
                    } else {
                        checkAndRequestPermissions();
                    }

                break;





        }
    }








    private void vollyError(VolleyError error) {
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


    public class ViewDialogVerifyWait {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                dialogWait = new Dialog(activity);
                dialogWait.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogWait.setCancelable(true);
                dialogWait.setContentView(R.layout.custom_dialog_verify);
                WebView _webview = dialogWait.findViewById(R.id.webView);
                _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
                _webview.loadUrl("file:///android_asset/verify.gif");
                _webview.getSettings().setLoadsImagesAutomatically(true);
                _webview.getSettings().setLoadWithOverviewMode(true);
                _webview.getSettings().setUseWideViewPort(true);
                TextView text = dialogWait.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialogWait.findViewById(R.id.btn_dialog);

                if(noDate){
                    dialogButton.setVisibility(View.VISIBLE);
                }else {
                    dialogButton.setVisibility(View.GONE);
                    Submit1.setEnabled(false);
                }

                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogWait.dismiss();
                    }
                });
                if(noDate) {
                    dialogWait.show();
                }else {
                    dialogWait.dismiss();
                }


            }
        }
    }

    public class ViewDialogVerify {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.custom_dialog_verify);
                WebView _webview = dialog1.findViewById(R.id.webView);
                _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
                _webview.loadUrl("file:///android_asset/verify.gif");
                _webview.getSettings().setLoadsImagesAutomatically(true);
                _webview.getSettings().setLoadWithOverviewMode(true);
                _webview.getSettings().setUseWideViewPort(true);
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                if(noDate){
                    dialogButton.setVisibility(View.VISIBLE);
                }else {
                    dialogButton.setVisibility(View.GONE);
                    Submit1.setEnabled(false);
                }

                dialogButton.setText("Ok");
                if(dialogWait!=null) {
                    dialogWait.dismiss();
                }
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mServices.size()>0) {
                            new PostDataTOServer().execute();
                        }else{
                            Toast.makeText(getApplicationContext(),"Please select category of the salon",Toast.LENGTH_SHORT).show();
                            all_services.requestFocus();
                        }

                        dialog1.dismiss();
                    }
                });

                    dialog1.show();


            }
        }
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            ViewDialogVerify alert = new ViewDialogVerify();
                            alert.showDialog(RegisterAsSalon.this, "Verified mobile no. Please proceed.",true);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(RegisterAsSalon.this, "Verification failed. Please check mobile no.",false);

                            }
                        }
                    }
                });
    }

    private void selectImage() {
        if (!RegisterAsSalon.this.isFinishing()) {
            final CharSequence[] items = {"Take from camera", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAsSalon.this, R.style.AlertDialogTheme_new);
            builder.setTitle("Add a Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take from camera")) {
                        dialog.dismiss();
                        cameraIntent();
                    } else if (items[item].equals("Choose from Library")) {
                        dialog.dismiss();
                         galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
        if (requestCode == REQUEST_PICK_FROM) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (!TextUtils.isEmpty(place.getAddress())) {
                    LatLng queriedLocation = place.getLatLng();
                   getCompleteAddressString(queriedLocation.latitude,queriedLocation.longitude);
                }
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
            }  // The user canceled the operation.

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
                    _address=1;
                    strReturnedAddress.append(returnedAddress.getSubLocality());
                    dAddress.setText(returnedAddress.getFeatureName());
                    dAddress.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_address, 0, 0, 0);
                    dLocality.setText(returnedAddress.getLocality());
                    dCity.setText(returnedAddress.getSubLocality());
                    dPin.setText(returnedAddress.getPostalCode());

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

    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                bm=Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData()), 220, 220, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Again=true;
        dImage.setImageBitmap(bm);

    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            filePath = destination.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Again=true;
        dImage.setImageBitmap(bm);

    }

    private void checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);List<String> listPermissionsNeeded = new ArrayList<>();
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            selectImage();
        }

    }
    public static final int MULTIPLE_PERMISSIONS = 10;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        selectImage();

                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");

                        checkAndRequestPermissions();
                    }
                }
            }
            break;

        }


    }




    private class PostDataTOServer  extends AsyncTask<Void, Integer, String> {


        private boolean success;
        private File destination;
        private String commaSeparatedValues="";


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if(mServices.size()>0) {
                  commaSeparatedValues = TextUtils.join(",", mServices);
            }

            if ( dImage.getDrawable() != null) {
                Bitmap bitmap1 = ((BitmapDrawable) dImage.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 50, bytes);
                destination = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    fileImage = destination.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }





        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            try {
                File sourceFile = new File(fileImage);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ID",_PhoneNo)
                        .addFormDataPart("about",dAbout.getText().toString().toUpperCase())
                        .addFormDataPart("mobile",d_PhoneNo.getText().toString().toUpperCase())
                        .addFormDataPart("name", dFirstname.getText().toString().toUpperCase())
                        .addFormDataPart("email", dEmail.getText().toString())
                        .addFormDataPart("registration",dRegistration.getText().toString().toUpperCase())
                        .addFormDataPart("address",dAddress.getText().toString().toUpperCase())
                        .addFormDataPart("city",dCity.getText().toString().toUpperCase())
                        .addFormDataPart("locality", dLocality.getText().toString().toUpperCase())
                        .addFormDataPart("data", commaSeparatedValues)
                        .addFormDataPart("zip",dPin.getText().toString())
                        .addFormDataPart("image", sourceFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("IP",mobileIp)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.URL_SALON_REGISTYRATION)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars=res.split("error");
                success = pars[1].contains("false");
                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Slow connection.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                   new PostDataTOServer().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

            }


            return res;

        }

        protected void onPostExecute(String file_url) {

            progressBar.setVisibility(View.GONE);

            if (success) {
                pref.setPhoto("http://139.59.38.160/Groom/App/salon_images/"+fileImage);
                if (destination != null){
                    File file = new File(destination.getPath());
                    file.delete();
                    if (file.exists()) {
                        try {
                            file.getCanonicalFile().delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (file.exists()) {
                            getApplicationContext().deleteFile(file.getName());
                        }
                    }
                }
                ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                alert.showDialog(RegisterAsSalon.this, "Succesfully stored information.",false);

            }else{
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(RegisterAsSalon.this, "Failed to store information! Please try another time.",true);
            }

        }


    }
    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg,Boolean noDate){
            if(!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    dialog1.setContentView(R.layout.custom_dialog_success);
                    TextView text = dialog1.findViewById(R.id.text_dialog);
                    text.setText(msg);

                    Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                    dialogButton.setText("Ok");
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pref.setSalonPhone(d_PhoneNo.getText().toString());
                            if(pref.getLocation()==0) {
                                Intent o3 = new Intent(RegisterAsSalon.this, StoreServiceInformation.class);
                                startActivity(o3);
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                                finish();
                            }else{
                                Intent o3 = new Intent(RegisterAsSalon.this, SalonHomePage.class);
                                startActivity(o3);
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                                finish();
                            }
                            dialog1.dismiss();
                        }
                    });


                dialog1.show();
            }
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
                Intent o = new Intent(RegisterAsSalon.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_down1, R.anim.slide_up1);

            }else {
                Intent o = new Intent(RegisterAsSalon.this, ServiceOffer.class);
                o.putExtra("my_lat", My_lat);
                o.putExtra("my_long", My_long);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_down1, R.anim.slide_up1);
            }
        }
        return true;
    }

}

