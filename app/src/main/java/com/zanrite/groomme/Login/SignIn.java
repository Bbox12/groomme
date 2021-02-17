package com.zanrite.groomme.Login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.zanrite.groomme.Activities.RegisterAsSalon;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Activities.ServiceOffer;
import com.zanrite.groomme.Activities.ServiceTimings;
import com.zanrite.groomme.Activities.Splash_screen;
import com.zanrite.groomme.Activities.StoreSalonLocation;
import com.zanrite.groomme.Activities.StoreServiceInformation;
import com.zanrite.groomme.Activities.StoreSlaonSpecialist;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignIn.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button signup,forgot_pwd;
    private AppCompatEditText input_user_password,input_user_mobile;
    private String filePath;
    private ProgressBar progressBar;
    private Bitmap bm;
    private String salonmobileno;
    private boolean valid;
    private Dialog dialogWait;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ActionCodeSettings actionCodeSettings;
    private int _from=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        progressBar=findViewById(R.id.progress_signup);
        forgot_pwd=findViewById(R.id.forgot_pwd);
        signup=findViewById(R.id.signup);
        forgot_pwd.setOnClickListener(SignIn.this);
        signup.setOnClickListener(this);
        input_user_password=findViewById(R.id.input_user_password);
        input_user_mobile=findViewById(R.id.input_user_mobile);



    }


    @Override
    protected void onResume() {
        super.onResume();
      


    Intent intent = getIntent();
    _from=intent.getIntExtra("from",0);
    if(_from==1){
        input_user_mobile.setText(pref.getEmail());
    }

        input_user_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start,
                                      int before, int count) {
                if(pref.getID3()==0) {

                    if (s.toString().length() >= 10) {
                        if (TextUtils.isDigitsOnly(s)) {
                            if (isValidPhoneNumber(input_user_mobile.getText().toString())) {
                                input_user_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                                input_user_password.requestFocus();
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private static boolean isValidPhoneNumber(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            String[] str2 =phone.split("");
            int j=0;
            for (String s : str2) {
                if (!TextUtils.isEmpty(s)) {
                    j++;
                }
            }
            check = j == 10 || j == 13;
        }
        return check;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
          

            case R.id.signup:
                
                    if(!TextUtils.isEmpty(input_user_mobile.getText().toString())){
                            if(!TextUtils.isEmpty(input_user_password.getText().toString())){
                            valid=true;
                            }else{
                                Toast.makeText(getApplicationContext(),"Please input password",Toast.LENGTH_SHORT).show();
                                input_user_password.requestFocus();
                            }
                       
                    }else{
                        Toast.makeText(getApplicationContext(),"Please input email or mobile no",Toast.LENGTH_SHORT).show();
                        input_user_mobile.requestFocus();
                    }

                if(valid) {
                        if(TextUtils.isDigitsOnly(input_user_mobile.getText().toString())) {
                            checkEmail();
                        }else {
                            if (_from == 1) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(SignIn.this, "Verified Email address. Please proceed.", true);
                            } else {
                                if(!TextUtils.isEmpty(input_user_mobile.getText().toString())) {
                                    ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                                    Firstalert.showDialog(SignIn.this, "Verification link sent. Please check your email.", true);

                                    mAuth.sendSignInLinkToEmail(input_user_mobile.getText().toString(), actionCodeSettings)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                           Log.d(TAG, task.toString());
                                                                           if (task.isSuccessful()) {
                                                                               pref.setID3(2);
                                                                               pref.setEmail(input_user_mobile.getText().toString());

                                                                           }
                                                                       }
                                                                   }

                                            );
                                }

                            }
                        }

                }
                break;
            case R.id.forgot_pwd:
                Intent o = new Intent(SignIn.this, SmsActivity.class);
                o.putExtra("from", 1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            default:
                break;
        }
    }

    private void checkEmail() {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        try {

                            String[] par = response.split("error");
                            if (par[1].contains("false")) {
                                String[] pars_ = par[1].split("false,");
                                JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                                JSONObject user = jObj.getJSONObject("user");
                                if (user.getInt("role") == 1 || user.getInt("role") == 2) {
                                    pref.setID3(0);
                                    pref.setResponsibility(user.getInt("role"));
                                    if (user.getInt("role") == 2) {
                                        pref.createLogin(user.getString("ID"), user.getString("Name"));
                                    } else {
                                        pref.createLogin2(user.getString("ID"), user.getString("Name"));
                                    }
                                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    alert.showDialog(SignIn.this, "Success! Welcome to Groom Me.", false);
                                } else {
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(SignIn.this, "No user found with the information!", true);
                                }
                            }else{
                                ViewDialogFailed alert = new ViewDialogFailed();
                                alert.showDialog(SignIn.this, "Please check the information provided!", true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("role",String.valueOf(0));
                params.put("mobile",input_user_mobile.getText().toString());
                params.put("password", input_user_password.getText().toString());
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

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
                }

                dialogButton.setText("Ok");
                if(dialogWait!=null) {
                    dialogWait.dismiss();
                }
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       checkEmail();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    public class ViewDialogVerifyWait {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                dialogWait = new Dialog(activity);
                dialogWait.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogWait.setCancelable(false);
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
                dialogButton.setVisibility(View.GONE);
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

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.custom_dialog_success);
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);



                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getInfo();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    private void getInfo() {

        if (!SignIn.this.isFinishing() ) {
            if(pref.getResposibility()==1){
                HashMap<String, String> user = pref.getUserDetails2();
                _phoneNo = user.get(PrefManager.KEY_MOBILE2);
            }else if(pref.getResposibility()==2){
                HashMap<String, String> user = pref.getUserDetails();
                _phoneNo = user.get(PrefManager.KEY_MOBILE);
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
                                        pref.setTime(c.getString("time"));
                                        pref.setCancellationCharge(c.getString("cancellationcharge"));
                                        pref.setDistance((float) c.getDouble("distance"));
                                        pref.setYoutube(c.getString("youtube"));
                                        pref.setYoutubeCategory(c.getInt("youtubecategory"));
                                    }
                                }


                                if (login.length() != 0) {
                                    for (int i = 0; i < login.length(); i++) {
                                        JSONObject c = login.getJSONObject(i);

                                        if (!c.isNull("parlour_mobile")) {
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
                                            pref.setparlour_locality(c.getString("parlour_locality"));
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

                            go_again();

                        }


                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    vollyError(error);

                }

            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", _phoneNo);
                    params.put("role", String.valueOf(pref.getResposibility()));
                    return params;
                }

            };

            // Añade la peticion a la cola
            AppController.getInstance().addToRequestQueue(eventoReq);
        }

    }




    private void go_again() {
        if(!SignIn.this.isFinishing()) {

            if (pref.getResposibility() == 2) {
                if (pref.getparlour_registration() == null || TextUtils.isEmpty(pref.getparlour_registration())) {
                    Intent o = new Intent(SignIn.this, RegisterAsSalon.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                } else if (pref.getTimings() == 0) {
                    Intent o = new Intent(SignIn.this, ServiceTimings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else if (pref.getisSpecialist() == 0) {
                    Intent o = new Intent(SignIn.this, StoreSlaonSpecialist.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                }else if (pref.getLocation() == 0) {
                    Intent o = new Intent(SignIn.this, StoreServiceInformation.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else if (pref.getLatLong() == 0) {
                    CustomNotification1("Location of the salon not specified. Please specify.");
                    Intent o = new Intent(SignIn.this, StoreSalonLocation.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                }   else {
                    Intent o = new Intent(SignIn.this, SalonHomePage.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                }
            } else if (pref.getResposibility() == 1) {
                Intent o = new Intent(SignIn.this, UserHomeScreen.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
            }
        }

    }

    public void CustomNotification1(String durationo) {
        Uri alarmSound = Uri.parse("android.resource://"
                + SignIn.this.getPackageName() + "/" + R.raw.hollow);

        // Set Notification Title

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), Splash_screen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(SignIn.this, 0, ii, 0);

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

        NotificationManager mNotificationManager = (NotificationManager) SignIn.this.getSystemService(Context.NOTIFICATION_SERVICE);

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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {

                    Intent o = new Intent(SignIn.this, ServiceOffer.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

}
