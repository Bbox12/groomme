package com.zanrite.groomme.BottomBar;

/**
 * Created by parag on 02/02/18.
 */

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
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaRecorder;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Fragments.SalonServiceShow;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.Models.Login;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.PopulateSalons;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class Account_settings extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static String TAG = Account_settings.class.getSimpleName();
    EditText _nameText;
    EditText _emailText;
    EditText _userName;
    EditText _userMobile;
    EditText _bday;
    EditText _place;
    private CoordinatorLayout coordinatorLayout;
    private ConnectionDetector cd;
    private Toolbar toolbar;
    private PrefManager pref;
    private ProgressBar progressBar;
    private AppBarLayout App_bar;
    private CollapsingToolbarLayout Coll;
    private String _filePath;
    private EditText _userBday;
    private ImageView _profileImage;
    private AutoCompleteTextView _userGender;
    private boolean _getImage = false;
    private String _newPlace = "", _newBday = "", _newGender = "", _newEmail, _newName;
    private ArrayList<String> mIns = new ArrayList<String>();
    private String _userPhone;
    private String _url;
    private boolean permissionsAllowd = false;
    public static final int MULTIPLE_PERMISSIONS = 10;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String mobileIp;
    private Button _submit;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String vverificationId;
    private Bitmap bm;
    private boolean Again=false;
    private Bitmap bitmap;
    private static boolean isvalidename(String name) {
        boolean check = false;
        String specialCharacters = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
        String str2[] = name.split("");

        for (int i = 0; i < str2.length; i++) {
            check = !specialCharacters.contains(str2[i]);
        }
        return check;

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
        } catch (Exception ex) { } // for now eat exceptions
        return null;
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);


        coordinatorLayout = findViewById(R.id
                .coll);
        progressBar = findViewById(R.id.progressBar2);
        cd = new ConnectionDetector(getApplicationContext());
        toolbar = findViewById(R.id.toolbar_mode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _userPhone = user.get(PrefManager.KEY_MOBILE2);
        _emailText = findViewById(R.id.input_email);
        _userName = findViewById(R.id.input_user_name);
        _userMobile = findViewById(R.id.input_user_mobile);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        _profileImage = findViewById(R.id.profile_picture);
        _profileImage.setOnClickListener(this);
        _userBday.setOnClickListener(this);

        _submit=findViewById(R.id.submit);
        _submit.setOnClickListener(this);

        checkAndRequestPermissions();

        initCollapsingToolbar();
        _userGender.setOnClickListener(this);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o3 = new Intent(Account_settings.this, UserHomeScreen.class);
                startActivity(o3);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
            }
        });

        mobileIp = getMobileIPAddress();
        if(TextUtils.isEmpty(mobileIp)){
            mobileIp= getWifiIPAddress();
        }


        mAuth= FirebaseAuth.getInstance();

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
                alert.showDialog(Account_settings.this, "Verification failed. Please check mobile no.",false);

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

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(Account_settings.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            Account_settings.ViewDialogVerify alert = new Account_settings.ViewDialogVerify();
                            alert.showDialog(Account_settings.this, "Verified mobile no. Please proceed.",true);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Account_settings.ViewDialogVerify alert = new Account_settings.ViewDialogVerify();
                                alert.showDialog(Account_settings.this, "Verification failed. Please check mobile no.",true);

                            }
                        }
                    }
                });

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(Account_settings.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }



    private void open_otp(final String verificationId) {
        if(!Account_settings.this.isFinishing()) {
            final Dialog dialog = new Dialog(Account_settings.this, R.style.ThemeTransparent);

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
                                .addOnCompleteListener(Account_settings.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Account_settings.ViewDialogVerify alert = new Account_settings.ViewDialogVerify();
                                            alert.showDialog(Account_settings.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                                Account_settings.ViewDialogVerify alert = new Account_settings.ViewDialogVerify();
                                                alert.showDialog(Account_settings.this, "Verification failed. Please check mobile no.",false);
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
    protected void onResume() {
        super.onResume();
        if(_getImage){
            if(bitmap!=null) {
                _profileImage.setImageBitmap(bitmap);
            }
        }else{
            mIns.clear();
            new GetUser().execute();

            _userMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(s.toString().length()==10){
                        _userMobile.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.ic_call, 0, R.mipmap.ic_cor, 0);
                        _emailText.requestFocus();

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submit:
                if (!validate()) {
                } else {
                    boolean ok = false;
                    if (!TextUtils.isEmpty(_userGender.getText().toString())) {
                        _newGender = _userGender.getText().toString();
                    }else{
                        _userGender.setError("Empty");
                    }




                    if (!TextUtils.isEmpty(_userBday.getText().toString())) {
                        _newBday = _userBday.getText().toString();
                    }else{
                        _userBday.setError("Empty");
                    }


                    if (!TextUtils.isEmpty(_userName.getText().toString())) {
                        _newName = _userName.getText().toString();
                        ok = true;
                    } else {
                        _userName.setError("Empty");
                    }

                    if (ok) {
                        String salonmobileno = "+27" + _userMobile.getText().toString();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                salonmobileno,        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                Account_settings.this,               // Activity (for callback binding)
                                mCallbacks);

                    }
                }

                break;

            case R.id.input_gender:
                _userGender.showDropDown();
                break;


            case R.id.profile_picture:

                if (permissionsAllowd) {
                    selectImage();
                } else {
                    checkAndRequestPermissions();
                }
                break;



            case R.id.input_bday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (dayOfMonth > 9) {
                                    _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                } else {
                                    _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                }
                            }
                        }, mYear - 18, mMonth, mDay);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                datePickerDialog.show();
                break;


            default:
                break;
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

                }

                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       new PostSignUpData().execute();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Account_settings.this, R.style.AlertDialogTheme);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        _profileImage.setImageBitmap(bm);
        _getImage=true;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
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
            _filePath = destination.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        _profileImage.setImageBitmap(thumbnail);
        _getImage=true;
    }

    private void checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            permissionsAllowd = true;
        }

    }

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
                    collapsingToolbar.setTitle("Profile");
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

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void signup(final String org) {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }


    }



    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String uName = _userName.getText().toString().trim();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }


        if (uName.isEmpty() || uName.length() < 3 || !isvalidename(uName)) {
            _userName.setError("Please input valid name");
            valid = false;
        } else {
            _userName.setError(null);
        }


        return valid;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_black, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {



            case R.id.action_notification:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Account_settings.this, UserHomeScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("_mId", _userPhone)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.GET_LOGIN)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    JSONArray _Attendence = jsonObj.getJSONArray("login");
                    for (int i = 0; i < _Attendence.length(); i++) {
                        JSONObject c = _Attendence.getJSONObject(i);
                        if (!c.isNull("PhoneNo")) {
                            Login item = new Login();
                            item.setName(c.getString("Name"));
                            item.set_Phone_no(c.getString("PhoneNo"));
                            item.setPhoto(c.getString("Photo"));
                            item.setEmail(c.getString("Email"));
                            item.setGender(c.getString("Gender"));
                            item.setBday(c.getString("Date_of_Birth"));
                            mItems.add(item);
                        }

                    }
                } catch (final JSONException e) {
                    Log.e("HI", "Json parsing error: " + e.getMessage());


                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Slow connection.", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new GetUser().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }

        protected void onPostExecute(String file_url) {
            progressBar.setVisibility(View.GONE);
            if (mItems.size() != 0) {
                progressBar.setVisibility(View.GONE);
                _userName.setText(mItems.get(0).getName(0));
                _userMobile.setText(mItems.get(0).get_Phone_no(0));
                _emailText.setText(mItems.get(0).getEmail(0));
                _userGender.setText(mItems.get(0).getGender(0));
                _userBday.setText(mItems.get(0).getBday(0));
                _filePath = mItems.get(0).getPhoto(0);
                if(!TextUtils.isEmpty(_userMobile.getText().toString())){
                    _userMobile.setEnabled(true);
                    _userMobile.setFocusableInTouchMode(true);
                }else{
                    _userMobile.setEnabled(false);
                    _userMobile.setFocusableInTouchMode(false);
                }


                    Picasso.Builder builder = new Picasso.Builder(Account_settings.this);
                    builder.listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            exception.printStackTrace();
                        }
                    });
                    builder.build().load(_filePath).into(_profileImage);


            }


            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getBaseContext(), android.R.layout.simple_list_item_1, getResources()
                    .getStringArray(R.array.Gender_Names));
            final String[] selection1 = new String[1];
            _userGender.setAdapter(arrayAdapter);
            _userGender.setCursorVisible(false);
            _userGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    _userGender.showDropDown();
                    selection1[0] = (String) parent.getItemAtPosition(position);

                }
            });

        }

    }

    private class PostSignUpData extends AsyncTask<Void, Integer, String> {


        private boolean success;
        private File destination;
        private String _fileImage;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
                    if (((BitmapDrawable) _profileImage.getDrawable()).getBitmap() != null) {
                        Bitmap bitmap1 = ((BitmapDrawable) _profileImage.getDrawable()).getBitmap();
                        final File mediaStorageDir = new File(
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                                Locale.getDefault()).format(new Date());
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                        destination = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                        FileOutputStream fo;
                        try {
                            destination.createNewFile();
                            fo = new FileOutputStream(destination);
                            fo.write(bytes.toByteArray());
                            fo.close();
                            _filePath = destination.getPath();

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
                File sourceFile = new File(_filePath);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ID",_userPhone )
                        .addFormDataPart("name", _newName.toUpperCase())
                        .addFormDataPart("email", _newEmail)
                        .addFormDataPart("gender", _newGender)
                        .addFormDataPart("bday", _newBday)
                        .addFormDataPart("mobile", _userMobile.getText().toString())
                        .addFormDataPart("image", sourceFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("IP",mobileIp)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.UPDATE_PROFILE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Slow connection.", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new PostSignUpData().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }

        protected void onPostExecute(String file_url) {
            progressBar.setVisibility(View.GONE);
            if (success) {
                if (destination != null) {
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
            //    pref.createLogin2(_userMobile.getText().toString(),_newName);
                Intent o3 = new Intent(Account_settings.this, UserHomeScreen.class);
                startActivity(o3);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Please add your image", Snackbar.LENGTH_LONG).show();

            }

        }

    }
}