package com.zanrite.groomme.Login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.RegisterAsSalon;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Activities.ServiceOffer;
import com.zanrite.groomme.Activities.ServiceTimings;
import com.zanrite.groomme.Activities.StoreSalonLocation;
import com.zanrite.groomme.Activities.StoreServiceInformation;
import com.zanrite.groomme.Activities.StoreSlaonSpecialist;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.MyViewPager;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


/**
 * Created by parag on 13/01/17.
 */
public class SmsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SmsActivity.class.getSimpleName();
    private static String _PhoneNo_giver = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    private AppCompatEditText input_user_mobile;
    private PrefManager pref;
    private AppCompatEditText input_user_password,input_user_confirm_password;
    private double My_long = 0, My_lat = 0;
    private CoordinatorLayout coordinatorLayout;
    private boolean permissionsAllowd=false;
    private boolean doubleBackToExitPressedOnce=false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private String vverificationId;
    private Dialog dialogWait;
    private Toolbar toolbar;
    private Button signup,signup2;
    private ActionCodeSettings actionCodeSettings;
    private TextInputLayout _mo,_m1;
    private ProgressDialog mProgressDialog;
    private MyViewPager viewPager;
    private ViewPagerAdapter adapter;

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.firstlayout;
                    break;
                case 1:
                    resId = R.id.secondlayout;
                    break;

            }
            return findViewById(resId);
        }
    }

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
        } catch (Exception ignored) {
        } // for now eat exceptions
        return null;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sms);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        input_user_password = findViewById(R.id.input_user_password);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        pref = new PrefManager(this);
        input_user_mobile = findViewById(R.id.input_user_mobile);
        input_user_confirm_password = findViewById(R.id.input_user_confirm_password);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        signup2=findViewById(R.id.signup2);
        signup2.setOnClickListener(this);
        String mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        viewPager = findViewById(R.id.viewPagerVertical);
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        input_user_mobile.requestFocus();
        input_user_password.setEnabled(false);

        _m1=findViewById(R.id.m1);
        _mo=findViewById(R.id.mo);

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
                alert.showDialog(SmsActivity.this, "Verification failed. Please check mobile no.",false);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

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

                vverificationId=verificationId;
                open_otp(verificationId);
            }
        };

        input_user_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
        actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://groomme.page.link/download")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.zanrite.groomme",
                                true, /* installIfNotAvailable */
                                "19"    /* minimumVersion */)
                        .build();

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            ViewDialogVerify alert = new ViewDialogVerify();
                            alert.showDialog(SmsActivity.this, "Verified mobile no. Please proceed.",true);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(SmsActivity.this, "Verification failed. Please check mobile no.",true);

                            }
                        }
                    }
                });
    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void open_otp(final String verificationId) {
        if(!SmsActivity.this.isFinishing()) {
            final Dialog dialog = new Dialog(SmsActivity.this, R.style.ThemeTransparent);
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
                                .addOnCompleteListener(SmsActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            input_user_password.setEnabled(true);
                                            ViewDialogVerify alert = new ViewDialogVerify();
                                            alert.showDialog(SmsActivity.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                                ViewDialogVerify alert = new ViewDialogVerify();
                                                alert.showDialog(SmsActivity.this, "Verification failed. Please check mobile no.",false);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup){
            if(pref.getID3()==0) {
               final String s=input_user_mobile.getText().toString();
                if (s.length() >= 10) {
                    if (TextUtils.isDigitsOnly(s)) {
                        if (isValidPhoneNumber(input_user_mobile.getText().toString())) {
                            input_user_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                            String salonmobileno = "+27" + input_user_mobile.getText().toString();
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    salonmobileno,        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    SmsActivity.this,               // Activity (for callback binding)
                                    mCallbacks);
                            ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                            Firstalert.showDialog(SmsActivity.this, "Verifying mobile no. Please wait.", true);

                        }
                    } else if(isValidEmail(s)) {

                        mAuth.sendSignInLinkToEmail(s, actionCodeSettings)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               Log.d(TAG, task.toString());
                                                               if (task.isSuccessful()) {
                                                                   pref.setID3(1);
                                                                   pref.setEmail(s);
                                                                   ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                                                                   Firstalert.showDialog(SmsActivity.this, "Verification link sent. Please check your email.", true);

                                                               }
                                                           }
                                                       }

                                );
                    }
                }
            }
           // viewPager.setCurrentItem(1);
        }

        if(v.getId()==R.id.signup2){

            if (!SmsActivity.this.isFinishing() ) {

                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(SmsActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    mProgressDialog.setTitle("Changing password.");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();
                }


            }
            if(!TextUtils.isEmpty(input_user_password.getText().toString())) {
                if(!TextUtils.isEmpty(input_user_confirm_password.getText().toString())){
                    if(input_user_confirm_password.getText().toString().equals(input_user_password.getText().toString())){
                StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_SIGNIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.w("details", response);
                                if (mProgressDialog != null) {
                                    mProgressDialog.dismiss();
                                }
                                try {

                                    String[] par = response.split("error");
                                    if (par[1].contains("false")) {
                                        String[] pars_ = par[1].split("false,");
                                        JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                                        JSONObject user = jObj.getJSONObject("user");
                                        if (user.getInt("role") == 1 || user.getInt("role") == 2) {
                                            pref.setID3(0);
                                            ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                            alert.showDialog(SmsActivity.this, "Password chaged succefully. Please sign in.", false);
                                        } else {
                                            ViewDialogFailed alert = new ViewDialogFailed();
                                            alert.showDialog(SmsActivity.this, "No user found with the information!", true);
                                        }
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        params.put("role", String.valueOf(1));
                        params.put("mobile", input_user_mobile.getText().toString());
                        params.put("password", input_user_password.getText().toString());
                        return params;
                    }

                };
                AppController.getInstance().addToRequestQueue(eventoReq);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please check both password.",Toast.LENGTH_SHORT).show();
                        input_user_confirm_password.requestFocus();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please input the confirm password",Toast.LENGTH_SHORT).show();
                    input_user_confirm_password.requestFocus();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Please input the password",Toast.LENGTH_SHORT).show();
                input_user_password.requestFocus();
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
                        Intent o3 = new Intent(SmsActivity.this, SignIn.class);
                        startActivity(o3);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                        finish();
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
                if(pref.getID3()==0) {
                    dialogButton.setVisibility(View.GONE);
                }else{
                    dialogButton.setVisibility(View.VISIBLE);
                }
                viewPager.setCurrentItem(1);

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
                        startActivity(intent);

                    }
                });

                dialogWait.show();


            }
        }
    }


    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
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


                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(1);
                        input_user_password.requestFocus();
                        dialog1.dismiss();
                    }
                });
                dialog1.show();


            }
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
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getID3()==1){

            input_user_mobile.setText(pref.getEmail());
            viewPager.setCurrentItem(1);
            input_user_password.requestFocus();
            _m1.setHint("New Password");
            input_user_password.setEnabled(true);
            if(dialogWait!=null){
                dialogWait.dismiss();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialogWait!=null){
            dialogWait.dismiss();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(dialogWait!=null){
            dialogWait.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialogWait!=null){
            dialogWait.dismiss();
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

                Intent o = new Intent(SmsActivity.this, ServiceOffer.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }




}

