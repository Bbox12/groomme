package com.zanrite.groomme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

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

public class SalonGalleryImages extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SalonGalleryImages.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private Button Submit1;
    private int serviceAT=0;
    private ProgressBar progressBar;
    private boolean first=false;
    private Animation inFromRight,outtoLeft;
    private EditText specialistdetails;
    private boolean permissionsAllowd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private boolean Again=false;
    private ImageView specialistimage;
    private Bitmap bitmap;
    private String filePath;
    private CardView addphotoofspecialist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesalonsgalleryimages);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        addphotoofspecialist=findViewById(R.id.addphotoofspecialist);
        addphotoofspecialist.setOnClickListener(this);

        specialistimage=findViewById(R.id.specialistimage);
        specialistdetails=findViewById(R.id.specialistdetails);
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarSplash);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(SalonGalleryImages.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else {
                    Intent o = new Intent(SalonGalleryImages.this, SalonHomePage.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }


            }
        });

        inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(500);
        inFromRight.setInterpolator(new AccelerateInterpolator());

        outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(500);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(SalonGalleryImages.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }

        if(Again){
            if(bitmap!=null) {
                specialistimage.setImageBitmap(bitmap);
            }

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:


                            new CreateNewUser().execute();

                break;

            case R.id.addphotoofspecialist:
                if (permissionsAllowd) {
                    selectImage();
                } else {
                    checkAndRequestPermissions();
                }

            default:break;
        }
    }


    class CreateNewUser extends AsyncTask<Void, Integer, String> {


        private long totalSize;
        private boolean success=false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (specialistimage.getDrawable() != null) {
                specialistimage.setBackground(null);
                Bitmap bitmap2 = ((BitmapDrawable) specialistimage.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "2";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File destination2 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "2" + ".jpg");
                FileOutputStream fo;
                try {
                    destination2.createNewFile();
                    fo = new FileOutputStream(destination2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath = destination2.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




        }



        protected String doInBackground(Void... args) {
            if(filePath!=null) {
                return uploadFile();
            }else {
                return "Failed";
            }
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
            try {

                File sourceFile = new File(filePath);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("detail",   specialistdetails.getText().toString())
                        .addFormDataPart("user",_phoneNo)

                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.GALLERY_DETAIL)
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
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Slow connection.", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new CreateNewUser().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            progressBar.setVisibility(View.GONE);
            if (success) {
                ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                alert.showDialog(SalonGalleryImages.this, "Succesfully stored information.",false);

            } else {
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(SalonGalleryImages.this, "Failed to store information! Please try another time.",true);

            }
        }

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

    private void selectImage() {
        if (!SalonGalleryImages.this.isFinishing()) {
            final CharSequence[] items = {"Take from camera", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(SalonGalleryImages.this, R.style.AlertDialogTheme_new);
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
                bm=Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData()), 220, 220, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Again=true;
        specialistimage.setImageBitmap(bm);

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
            filePath = destination.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Again=true;
        specialistimage.setImageBitmap(thumbnail);

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
                        first=false;
                        Again=false;
                        Submit1.setBackground(getResources().getDrawable(R.drawable.button_background));
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
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
                        Intent o = new Intent(SalonGalleryImages.this, SalonHomePage.class);
                        startActivity(o);
                        finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
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
                Intent o = new Intent(SalonGalleryImages.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }else {
                Intent o = new Intent(SalonGalleryImages.this, SalonHomePage.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }


        }
        return true;
    }

}
