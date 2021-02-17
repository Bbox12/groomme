package com.zanrite.groomme.Booking;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.MyViewPager;
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
import java.text.DecimalFormat;
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

public class Success extends AppCompatActivity implements View.OnClickListener {


    private static final int MULTIPLE_PERMISSIONS = 1001;
    private PrefManager pref;
    private String _PhoneNo;
    private MyViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button Submit;
    private DatabaseReference mDatabase;
    private String User_pic;
    private float rate = 0.0f;
    private String Rate_user;
    private double My_lat = 0, My_long = 0;
    private Toolbar toolbar;
    private NetworkImageView Bill_driver;
    private Button Stop;
    private TextView S1, S2;
    private RecyclerView _driverRv;
    private TextView _bill_generated;
    private ProgressBar _p1;
    DecimalFormat df = new DecimalFormat("0.00");
    private String _Name;
    private TextView _name,providername,howwas;
    private String Total_cost,Raw_cost,Coupon_discount,Tax_amount;
    private CoordinatorLayout coordinatorLayout;
    private String Cost;
    private AppCompatImageView _sc1, _sc2, _sc3, _sc4;
    private TextInputEditText _comments;
    private  RatingBar ratingBar_c ;
    private  RatingBar ratingBar_s ;
    private String rating_c="0",rating_s="0",ratingSalon="0";
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private int image_no = 0;
    private boolean permissionsAllowd=false;
    private String filePath1 = "", filePath2 = "", filePath3 = "", filePath4 = "";
    private RatingBar ratingBarbill;
    private LinearLayout salonreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAndRequestPermissions();
        setContentView(R.layout.success);
        _bill_generated = findViewById(R.id.bill_generated);
       ratingBar_c = (RatingBar)findViewById(R.id.ratingBar_c);
        ratingBar_s = (RatingBar)findViewById(R.id.ratingBar_s);

        Bill_driver = findViewById(R.id.driver_bill);
        Stop = findViewById(R.id.success_ride);
        Submit = findViewById(R.id.bill_submit);
        Submit.setOnClickListener(this);
        S1 = findViewById(R.id.s1);
        S2 = findViewById(R.id.s2);
        S1.setText("Appointment");
        S2.setText(" completed");
        coordinatorLayout=findViewById(R.id.success_);
        toolbar = findViewById(R.id.toolbar_success);
        _p1=findViewById(R.id.p1);
        setSupportActionBar(toolbar);
        _name=findViewById(R.id._name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        viewPager = findViewById(R.id.rider_bill);

        howwas=findViewById(R.id.howwas);
        salonreview=findViewById(R.id.salonreview);

        ratingBarbill=findViewById(R.id.ratingBarbill);
        providername=findViewById(R.id.providername);
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        _sc1 = findViewById(R.id.image_1);
        _sc2 = findViewById(R.id.image_2);
        _sc3 = findViewById(R.id.image_3);
        _sc4 = findViewById(R.id.image_4);

        _sc1.setOnClickListener(this);
        _sc2.setOnClickListener(this);
        _sc3.setOnClickListener(this);
        _sc4.setOnClickListener(this);

        _comments=findViewById(R.id._comments);

        Stop.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getResposibility()==1) {
                    Intent o = new Intent(Success.this, UserHomeScreen.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else if(pref.getResposibility()==2) {
                    Intent o = new Intent(Success.this, SalonHomePage.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });

        pref = new PrefManager(getApplicationContext());

        if(pref.getResposibility()==2){
            howwas.setText("Feedback on customer");
            HashMap<String, String> user = pref.getUserDetails();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
            salonreview.setVisibility(View.GONE);
            ratingBarbill.setVisibility(View.VISIBLE);
        }else if(pref.getResposibility()==1){
            howwas.setText("Feedback on salon");
            HashMap<String, String> user = pref.getUserDetails2();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE2);
            salonreview.setVisibility(View.VISIBLE);
            ratingBarbill.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

            _p1.setVisibility(View.VISIBLE);
            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_THE_BOOKING,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray contacts = jsonObj.getJSONArray("pastbookings");
                                _p1.setVisibility(View.GONE);

                                // looping through All Contacts
                                if (contacts.length() > 0) {
                                    for (int i = 0; i < contacts.length(); i++) {
                                        JSONObject c = contacts.getJSONObject(i);
                                         _bill_generated.setText(c.getString("Payable"));
                                        String url;
                                        if(pref.getResposibility()==2){
                                             providername.setText(c.getString("name"));
                                             url =c.getString("Photo").replaceAll(" ", "%20");

                                         }else{
                                             providername.setText(c.getString("parlour_name"));
                                             url =c.getString("PPhoto").replaceAll(" ", "%20");
                                         }


                                        ImageLoader imageLoader = LruBitmapCache.getInstance(Success.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(Bill_driver,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        Bill_driver.setImageUrl(url, imageLoader);
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }



                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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

            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("_mId", _PhoneNo);
                    params.put("otp", pref.getOrderID());
                    return params;
                }

            };

            // Añade la peticion a la cola
            AppController.getInstance().addToRequestQueue(eventoReq);


        ratingBar_c.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_c = String.valueOf(rating);
            }
        });

        ratingBar_s.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_s = String.valueOf(rating);
            }
        });
        ratingBarbill.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingSalon = String.valueOf(rating);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.bill_submit:

                if(pref.getResposibility()==2){
                    new PostDataToServerSalon().execute(_comments.getText().toString());
                }else if(pref.getResposibility()==1){
                    new PostDataToServer().execute(_comments.getText().toString());
                }

                break;

            case R.id.success_ride:
            if(!TextUtils.isEmpty(_comments.getText().toString())) {
                S1.setText("Add");
                S2.setText(" Selfie");
                viewPager.setCurrentItem(1);
            }else{
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Please add a comments", Snackbar.LENGTH_LONG)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              _comments.requestFocus();
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();

            }
            break;
            case R.id.image_1:

                    image_no = 1;
                    if (permissionsAllowd) {
                        selectImage();
                    } else {
                        checkAndRequestPermissions();

                }

                break;
            case R.id.image_2:

                    image_no = 2;
                    if (permissionsAllowd) {
                        selectImage();
                    } else {
                        checkAndRequestPermissions();

                }

                break;
            case R.id.image_3:

                    image_no = 3;
                    if (permissionsAllowd) {
                        selectImage();
                    } else {
                        checkAndRequestPermissions();


                }

                break;
            case R.id.image_4:

                    image_no = 4;
                    if (permissionsAllowd) {
                            selectImage();

                    } else {
                        checkAndRequestPermissions();
                    }


                break;



        }
    }

    private class PostDataToServerSalon extends AsyncTask<String, Integer, String> {


        private boolean success;
        private File destination1, destination2, destination3, destination4;
        private boolean _imageCaptured=false;
        private MultipartBody requestBody;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            _p1.setVisibility(View.VISIBLE);

            if (_sc1.getDrawable() != null) {
                _sc1.setBackground(null);
                _imageCaptured = true;
                bitmap1 = ((BitmapDrawable) _sc1.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "1";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination1 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "1" + ".jpg");
                FileOutputStream fo;
                try {
                    destination1.createNewFile();
                    fo = new FileOutputStream(destination1);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath1 = destination1.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (_sc2.getDrawable() != null) {
                _sc2.setBackground(null);
                _imageCaptured = true;
                bitmap2 = ((BitmapDrawable) _sc2.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "2";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination2 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "2" + ".jpg");
                FileOutputStream fo;
                try {
                    destination2.createNewFile();
                    fo = new FileOutputStream(destination2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath2 = destination2.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (_sc3.getDrawable() != null) {
                _sc3.setBackground(null);
                _imageCaptured = true;
                bitmap3 = ((BitmapDrawable) _sc3.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "3";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination3 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination3.createNewFile();
                    fo = new FileOutputStream(destination3);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath3 = destination3.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            if (_sc4.getDrawable() != null) {
                _sc4.setBackground(null);
                _imageCaptured = true;
                bitmap4 = ((BitmapDrawable) _sc4.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "4";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap4.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination4 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination4.createNewFile();
                    fo = new FileOutputStream(destination4);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath4 = destination4.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        protected String doInBackground(String... args) {
            return uploadFile1(args[0]);
        }

        private String uploadFile1(String args) {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();
            try {
                if (!_imageCaptured) {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",ratingSalon)
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS_SALON)
                            .post(requestBody)
                            .build();

                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1) && !TextUtils.isEmpty(filePath2) &&
                        !TextUtils.isEmpty(filePath3)) {
                    File sourceFile1 = new File(filePath1);
                    File sourceFile2 = new File(filePath2);
                    File sourceFile3 = new File(filePath3);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",ratingSalon)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .addFormDataPart("image_2", sourceFile2.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile2))
                            .addFormDataPart("image_3", sourceFile3.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile3))

                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS_SALON)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1) && !TextUtils.isEmpty(filePath2)) {
                    File sourceFile1 = new File(filePath1);
                    File sourceFile2 = new File(filePath2);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",ratingSalon)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .addFormDataPart("image_2", sourceFile2.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile2))
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS_SALON)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1)) {
                    File sourceFile1 = new File(filePath1);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",ratingSalon)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS_SALON)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                }


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
                                    new PostDataToServerSalon().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }
        protected void onPostExecute(String file_url) {
            super.onPostExecute(file_url);
            _p1.setVisibility(View.GONE);
            if (success) {
                mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("STATUS").setValue("4");
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
                if (destination1 != null) {
                    File file = new File(destination1.getPath());
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
                if (destination2 != null) {
                    File file = new File(destination2.getPath());
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
                if (destination3 != null) {
                    File file = new File(destination1.getPath());
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
                if (destination4 != null) {
                    File file = new File(destination2.getPath());
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
                if (! Success.this.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Success.this, R.style.AlertDialogTheme)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Thank you")
                            .setMessage("Suucessfully added  review")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                        Intent o = new Intent(Success.this, SalonHomePage.class);
                                        startActivity(o);
                                        finish();
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Oops!Please select another assignment.", Snackbar.LENGTH_LONG).show();

            }

        }

    }
    private class PostDataToServer extends AsyncTask<String, Integer, String> {


        private boolean success;
        private File destination1, destination2, destination3, destination4;
        private boolean _imageCaptured=false;
        private MultipartBody requestBody;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            _p1.setVisibility(View.VISIBLE);

            if (_sc1.getDrawable() != null) {
                _sc1.setBackground(null);
                _imageCaptured = true;
                bitmap1 = ((BitmapDrawable) _sc1.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "1";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination1 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "1" + ".jpg");
                FileOutputStream fo;
                try {
                    destination1.createNewFile();
                    fo = new FileOutputStream(destination1);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath1 = destination1.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (_sc2.getDrawable() != null) {
                _sc2.setBackground(null);
                _imageCaptured = true;
                bitmap2 = ((BitmapDrawable) _sc2.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "2";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination2 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "2" + ".jpg");
                FileOutputStream fo;
                try {
                    destination2.createNewFile();
                    fo = new FileOutputStream(destination2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath2 = destination2.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            if (_sc3.getDrawable() != null) {
                _sc3.setBackground(null);
                _imageCaptured = true;
                bitmap3 = ((BitmapDrawable) _sc3.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "3";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination3 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination3.createNewFile();
                    fo = new FileOutputStream(destination3);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath3 = destination3.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            if (_sc4.getDrawable() != null) {
                _sc4.setBackground(null);
                _imageCaptured = true;
                bitmap4 = ((BitmapDrawable) _sc4.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "4";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap4.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                destination4 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;
                try {
                    destination4.createNewFile();
                    fo = new FileOutputStream(destination4);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath4 = destination4.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        protected String doInBackground(String... args) {
            return uploadFile(args[0]);
        }

        private String uploadFile(String args) {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build();
            try {
                if (!_imageCaptured) {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",rating_c)
                            .addFormDataPart("rating_s", rating_s)
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1) && !TextUtils.isEmpty(filePath2) &&
                        !TextUtils.isEmpty(filePath3)) {
                    File sourceFile1 = new File(filePath1);
                    File sourceFile2 = new File(filePath2);
                    File sourceFile3 = new File(filePath3);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",rating_c)
                            .addFormDataPart("rating_s", rating_s)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .addFormDataPart("image_2", sourceFile2.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile2))
                            .addFormDataPart("image_3", sourceFile3.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile3))
                      
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1) && !TextUtils.isEmpty(filePath2)) {
                    File sourceFile1 = new File(filePath1);
                    File sourceFile2 = new File(filePath2);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",rating_c)
                            .addFormDataPart("rating_s", rating_s)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .addFormDataPart("image_2", sourceFile2.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile2))
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                } else if (!TextUtils.isEmpty(filePath1)) {
                    File sourceFile1 = new File(filePath1);
                    requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("mobile", _PhoneNo)
                            .addFormDataPart("otp", pref.getOrderID())
                            .addFormDataPart("message", _comments.getText().toString())
                            .addFormDataPart("rating_c",rating_c)
                            .addFormDataPart("rating_s", rating_s)
                            .addFormDataPart("image_1", sourceFile1.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                            .build();

                    Request request = new Request.Builder()
                            .url(Config_URL.STORE_COMMENTS)
                            .post(requestBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    res = response.body().string();
                }


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
                                    new  PostDataToServer().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }

            }


            return res;

        }
        protected void onPostExecute(String file_url) {
            super.onPostExecute(file_url);
            _p1.setVisibility(View.GONE);
            if (success) {
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
                pref.setActualTime(null);
                if (destination1 != null) {
                    File file = new File(destination1.getPath());
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
                if (destination2 != null) {
                    File file = new File(destination2.getPath());
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
                if (destination3 != null) {
                    File file = new File(destination1.getPath());
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
                if (destination4 != null) {
                    File file = new File(destination2.getPath());
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
                if (! Success.this.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Success.this, R.style.AlertDialogTheme)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Thank you")
                            .setMessage("Suucessfully added  review")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                        Intent o = new Intent(Success.this, UserHomeScreen.class);
                                        startActivity(o);
                                        finish();
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            }

        }

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


        if (listPermissionsNeeded.size()>0) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            permissionsAllowd = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("Hi", "Permission callback called-------");
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
                        Log.d("Hi", "sms & location services permission granted");
                        permissionsAllowd = true;


                    } else {
                        Log.d("Hi", "Some permissions are not granted ask again ");

                        //checkAndRequestPermissions();
                    }
                }
            }
            break;

        }


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Success.this, R.style.AlertDialogTheme);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    if(image_no==1) {
                        image_no = 0;
                        _sc1.setImageDrawable(null);
                    }
                    if(image_no==2) {
                        _sc2.setImageDrawable(null);
                    }
                    if(image_no==3) {
                        _sc3.setImageDrawable(null);
                    }
                    if(image_no==4) {
                        _sc4.setImageDrawable(null);
                    }
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
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData()),500,500);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            if (image_no == 1) {
                _sc1.setImageBitmap(bitmap);

            } else if (image_no == 2) {
                _sc2.setImageBitmap(bitmap);


            } else
                if (image_no == 3) {
                    _sc3.setImageBitmap(bitmap);

                } else if (image_no == 4) {
                    _sc4.setImageBitmap(bitmap);

                }


        }
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image_no == 1) {
            _sc1.setImageBitmap(bitmap);

        } else if (image_no == 2) {
            _sc2.setImageBitmap(bitmap);


        } else
        if (image_no == 3) {
            _sc3.setImageBitmap(bitmap);

        } else if (image_no == 4) {
            _sc4.setImageBitmap(bitmap);

        }


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            if(pref.getResposibility()==1) {
                Intent o = new Intent(Success.this, UserHomeScreen.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else if(pref.getResposibility()==2) {
                Intent o = new Intent(Success.this, SalonHomePage.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }

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
                    resId = R.id.last1;
                    break;
                case 1:
                    resId = R.id.last2;
                    break;

            }
            return findViewById(resId);
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
}
