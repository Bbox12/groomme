package com.zanrite.groomme.Login;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.MediaRouteButton;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
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
import com.zanrite.groomme.Activities.Splash_screen;
import com.zanrite.groomme.Activities.StoreSalonLocation;
import com.zanrite.groomme.Activities.StoreServiceInformation;
import com.zanrite.groomme.Activities.StoreSlaonSpecialist;
import com.zanrite.groomme.BuildConfig;
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
import java.io.FileNotFoundException;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignUp.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button signup,forgot_pwd;
    private RadioGroup _radiogroup;
    private ImageView img_profilo;
    private AppCompatEditText input_user_email,input_user_name,input_user_password,input_user_mobile;
    private boolean Again;
    private Bitmap bitmap;
    private boolean permissionsAllowd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String filePath;
    private ProgressBar progressBar;
    private int _role=0;
    private Bitmap bm;
    private String salonmobileno;
    private boolean valid;
    private Dialog dialogWait;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private TextInputLayout _m0;
    private ActionCodeSettings actionCodeSettings;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        progressBar=findViewById(R.id.progress_signup);
        forgot_pwd=findViewById(R.id.forgot_pwd);
        signup=findViewById(R.id.signup);
        forgot_pwd.setOnClickListener(this);
        signup.setOnClickListener(this);
        img_profilo=findViewById(R.id.img_profilo);
        img_profilo.setOnClickListener(this);
        input_user_email=findViewById(R.id.input_user_email);
        input_user_name=findViewById(R.id.input_user_name);
        input_user_password=findViewById(R.id.input_user_password);
        input_user_mobile=findViewById(R.id.input_user_mobile);
        _radiogroup=findViewById(R.id.groupradio);
        _m0=findViewById(R.id.mo);
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
                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);

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


    @Override
    protected void onResume() {
        super.onResume();
        _radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedId=group.getCheckedRadioButtonId();
                if(selectedId==R.id.radia_id1){
                    _role=2;
                    _m0.setHint("Salon Name");
                }else  if(selectedId==R.id.radia_id2){
                    _role=1;
                    _m0.setHint("Name");
                }
            }
        });

        input_user_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()==10){
                    salonmobileno = "+27" + input_user_mobile.getText().toString();
                    input_user_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                    input_user_email.requestFocus();


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(Again){
            if(bitmap!=null) {
                img_profilo.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_profilo:

                if (permissionsAllowd) {
                    selectImage();
                } else {
                    checkAndRequestPermissions();
                }

                break;

            case R.id.signup:

                if(_role!=0){
                    if(!TextUtils.isEmpty(input_user_email.getText().toString()) || !TextUtils.isEmpty(input_user_mobile.getText().toString())){
                        if(!TextUtils.isEmpty(input_user_name.getText().toString())){
                            if(!TextUtils.isEmpty(input_user_password.getText().toString())){
                               valid=true;
                            }else{
                                Toast.makeText(getApplicationContext(),"Please input password",Toast.LENGTH_SHORT).show();
                                input_user_password.requestFocus();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Please input name",Toast.LENGTH_SHORT).show();
                            input_user_name.requestFocus();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Please input email or mobile no",Toast.LENGTH_SHORT).show();
                        input_user_email.requestFocus();
                        input_user_mobile.requestFocus();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please select register as ",Toast.LENGTH_SHORT).show();
                    _radiogroup.requestFocus();
                }
                if(valid) {
                    if (bm!=null) {
                        if((input_user_mobile.getText().toString().length()>=10)) {
                            salonmobileno = "+27" + input_user_mobile.getText().toString();
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    salonmobileno,        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    SignUp.this,               // Activity (for callback binding)
                                    mCallbacks);
                            ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                            Firstalert.showDialog(SignUp.this, "Verifying phone no. Please wait...", true);
                        }else{
                            if(!TextUtils.isEmpty(input_user_email.getText().toString())) {
                                ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                                Firstalert.showDialog(SignUp.this, "Verification link sent. Please check your email.", true);

                                mAuth.sendSignInLinkToEmail(input_user_email.getText().toString(), actionCodeSettings)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d(TAG, task.toString());
                                                if (task.isSuccessful()) {
                                                    pref.setID3(2);
                                                    pref.setEmail(input_user_email.getText().toString());

                                                }
                                            }
                                        }

                                        );
                            }
                        }




                    }else {
                        ViewDialogFailed alert = new ViewDialogFailed();
                        alert.showDialog(SignUp.this, "Please add salon photo. ",true);
                    }
                }
                break;
            case R.id.forgot_pwd:
                break;
            default:
                break;
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            new PostDataTOServer().execute();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);

                            }
                        }
                    }
                });
    }

    private void open_otp(final String verificationId) {
        if(!SignUp.this.isFinishing()) {
            final Dialog dialog = new Dialog(SignUp.this, R.style.ThemeTransparent);
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
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            ViewDialogVerify alert = new ViewDialogVerify();
                                            alert.showDialog(SignUp.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                                ViewDialogVerify alert = new ViewDialogVerify();
                                                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);
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
                    signup.setEnabled(false);
                }

                dialogButton.setText("Ok");
                if(dialogWait!=null) {
                    dialogWait.dismiss();
                }
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PostDataTOServer().execute();
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

                if(noDate){
                    dialogButton.setVisibility(View.VISIBLE);
                }else {
                    dialogButton.setVisibility(View.GONE);
                    signup.setEnabled(false);
                }

                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pref.getID3()!=0){
                            new PostDataTOServer().execute();
                        }
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
    private void selectImage() {
        if (!SignUp.this.isFinishing()) {
            final CharSequence[] items = {"Take from camera", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.AlertDialogTheme_new);
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
        img_profilo.setImageBitmap(bm);

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
        img_profilo.setImageBitmap(bm);

    }

    private void checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (listPermissionsNeeded.size()>0) {
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
        if (requestCode == MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            // Initialize the map with both permissions
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

            // Fill with actual results from user
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "sms & location services permission granted");
                    // process the normal flow
                    selectImage();

                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    explain("You need to give the mandatory permissions to continue");
                }
            }
        }


    }

    private void explain(String msg) {
        new AlertDialog.Builder(SignUp.this)

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


    private class PostDataTOServer  extends AsyncTask<Void, Integer, String> {


        private boolean success;
        private File destination;
        private String fileImage;



        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if (!SignUp.this.isFinishing() ) {
                if(dialogWait!=null){
                    dialogWait.dismiss();
                }
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(SignUp.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setTitle("Storing information.");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();
                }


            }

            if(TextUtils.isEmpty(input_user_email.getText().toString())){
                input_user_email.setText("NA");
            }
            if(TextUtils.isEmpty(input_user_mobile.getText().toString())){
                input_user_mobile.setText("NA");
            }
            if ( img_profilo.getDrawable() != null) {
                Bitmap bitmap1 = ((BitmapDrawable) img_profilo.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 70, bytes);
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
                        .addFormDataPart("role", String.valueOf(_role))
                        .addFormDataPart("mobile",input_user_mobile.getText().toString())
                        .addFormDataPart("email",input_user_email.getText().toString())
                        .addFormDataPart("username",input_user_name.getText().toString().toUpperCase())
                        .addFormDataPart("password", input_user_password.getText().toString().toUpperCase())
                        .addFormDataPart("image", sourceFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.URL_SIGNUP)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] par = res.split("error");
                if (par[1].contains("false")) {
                    String[] pars_ = par[1].split("false,");
                    JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                    JSONObject user = jObj.getJSONObject("user");
                    if (user.getInt("role") == 1 || user.getInt("role") == 2) {
                        pref.setResponsibility(user.getInt("role"));
                        success = par[1].contains("false");
                        pref.setSalonPhone(input_user_mobile.getText().toString());
                        if (user.getInt("role") == 2) {
                            if(  pref.getID3()!=2) {
                                pref.createLogin(user.getString("ID"), user.getString("Name"));
                            }
                        } else {
                            pref.createLogin2(user.getString("ID"), user.getString("Name"));
                        }
                    }
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
                                    new PostDataTOServer().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }

        protected void onPostExecute(String file_url) {

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (success) {

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
                alert.showDialog(SignUp.this, "Succesfully stored information.",false);

            }else{
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(SignUp.this, "Failed to store information! Please try another time.",true);
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

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
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

                        if( pref.getID3()==2) {
                            CustomNotification1("Please verify your email address.");
                            pref.setEmail(input_user_email.getText().toString());
                            Intent intent = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Min SDK 15
                            startActivity(intent);
                        }else{
                           getInfo();
                        }
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    private void getInfo() {
        if(pref.getResposibility()==1){
            HashMap<String, String> user = pref.getUserDetails2();
            _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        }else if(pref.getResposibility()==2){
            HashMap<String, String> user = pref.getUserDetails();
            _phoneNo = user.get(PrefManager.KEY_MOBILE);
        }
        if (!SignUp.this.isFinishing() ) {




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
                                        pref.setYoutubeTitile(c.getString("_titleYoutube"));
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
                                         //   pref.createLogin(c.getString("PhoneNo"), c.getString("Name"));
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
                    return params;
                }

            };

            // Añade la peticion a la cola
            AppController.getInstance().addToRequestQueue(eventoReq);
        }

    }




    private void go_again() {
        if(!SignUp.this.isFinishing()) {

            if (pref.getResposibility() == 2) {
                if (pref.getparlour_registration() == null || TextUtils.isEmpty(pref.getparlour_registration())) {
                    CustomNotification1("Address and registration of the salon not specified. Please specify.");
                    Intent o = new Intent(SignUp.this, RegisterAsSalon.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                } else if (pref.getLocation() == 0) {
                    Intent o = new Intent(SignUp.this, StoreServiceInformation.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else if (pref.getLatLong() == 0) {
                    CustomNotification1("Location of the salon not specified. Please specify.");
                    Intent o = new Intent(SignUp.this, StoreSalonLocation.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else if (pref.getTimings() == 0) {
                    CustomNotification1("Timing of the salon not specified. Please specify.");
                    Intent o = new Intent(SignUp.this, ServiceTimings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else if (pref.getisSpecialist() == 0) {
                    CustomNotification1("No specialist data found. Please add specialist.");
                    Intent o = new Intent(SignUp.this, StoreSlaonSpecialist.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                } else {
                    Intent o = new Intent(SignUp.this, SalonHomePage.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                }
            } else if (pref.getResposibility() == 1) {
                Intent o = new Intent(SignUp.this, UserHomeScreen.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
            }
        }

    }

    public void CustomNotification1(String durationo) {
        Uri alarmSound = Uri.parse("android.resource://"
                + SignUp.this.getPackageName() + "/" + R.raw.hollow);

        // Set Notification Title

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), Splash_screen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(SignUp.this, 0, ii, 0);

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

        NotificationManager mNotificationManager = (NotificationManager) SignUp.this.getSystemService(Context.NOTIFICATION_SERVICE);

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

                    Intent o = new Intent(SignUp.this, ServiceOffer.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
