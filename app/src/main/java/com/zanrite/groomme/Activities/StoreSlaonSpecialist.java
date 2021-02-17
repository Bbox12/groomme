package com.zanrite.groomme.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.SpecialistServiceRv;
import com.zanrite.groomme.Adapters.StoreSlaonSpecialistEdit;
import com.zanrite.groomme.Adapters.TimeslotRv;
import com.zanrite.groomme.Adapters.adapter_team;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
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
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class StoreSlaonSpecialist extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = StoreSlaonSpecialist.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private Button Submit1,Submit2;
    private int serviceAT=0;
    private ProgressBar progressBar;
    private LinearLayout nospecialist,secondlayout,firstlayout;
    private RecyclerView specialistrv;
    private CardView addimage,addphotoofspecialist;
    private boolean first=false;
    private Animation inFromRight,outtoLeft;
    private EditText specialistname,specialistdetails;
    private EditText specialistservice;
    private boolean permissionsAllowd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private boolean Again=false;
    private ImageView specialistimage;
    private Bitmap bitmap;
    private String filePath;
    private int _leave=0;
    private RecyclerView specialistservicerv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesalonspecialistinformation);
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
        specialistservicerv=findViewById(R.id.specialistservicerv);
        specialistservicerv.setNestedScrollingEnabled(false);

        nospecialist=findViewById(R.id.nospecialist);
        firstlayout=findViewById(R.id.firstlayout);
        secondlayout=findViewById(R.id.secondlayout);
        specialistrv=findViewById(R.id.specialistrv);
        specialistrv.setNestedScrollingEnabled(true);
        specialistimage=findViewById(R.id.specialistimage);
        specialistname=findViewById(R.id.specialistname);
        specialistdetails=findViewById(R.id.specialistdetails);
        specialistservice=findViewById(R.id.specialistservice);
        specialistservice.setOnClickListener(this);
        addimage=findViewById(R.id.addimage);
        addimage.setOnClickListener(this);
        addphotoofspecialist=findViewById(R.id.addphotoofspecialist);
        addphotoofspecialist.setOnClickListener(this);
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        Submit2=findViewById(R.id.submit2);
        progressBar = findViewById(R.id.progressBarSplash);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(StoreSlaonSpecialist.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

                }else {
                    if (!first) {
                        Intent o = new Intent(StoreSlaonSpecialist.this, ServiceOffer.class);
                        startActivity(o);
                        finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                    } else {
                        first = false;
                        Again = false;
                        firstlayout.setVisibility(View.VISIBLE);
                        secondlayout.setVisibility(View.GONE);
                        secondlayout.setAnimation(outtoLeft);
                        firstlayout.setAnimation(inFromRight);
                        Submit1.setBackground(getResources().getDrawable(R.drawable.editt2));
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                    }
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
        Intent i=getIntent();
        _leave=i.getIntExtra("leave",0);
    }





    @Override
    protected void onResume() {
        super.onResume();

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(StoreSlaonSpecialist.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }




            if (Again) {
                if (bitmap != null) {
                    specialistimage.setImageBitmap(bitmap);
                }
                first=true;
                Submit1.setVisibility(View.GONE);
                Submit2.setVisibility(View.VISIBLE);
                firstlayout.setVisibility(View.GONE);
                secondlayout.setVisibility(View.VISIBLE);
                secondlayout.setAnimation(inFromRight);
            } else {
                first=false;
                firstlayout.setVisibility(View.VISIBLE);
                secondlayout.setAnimation(inFromRight);
                secondlayout.setVisibility(View.GONE);
                Submit1.setBackground(getResources().getDrawable(R.drawable.editt2));
                final ArrayList<Album> mTeam = new ArrayList<>();
                final ArrayList<Slots> mServices = new ArrayList<Slots>();
                StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETCREWIMAGE,
                        new Response.Listener<String>() {
                            private int isDeleted = 0;

                            @Override
                            public void onResponse(String response) {

                                Log.w("volley", response);


                                try {

                                    JSONObject jsonObj = new JSONObject(response);
                                    JSONArray contacts = jsonObj.getJSONArray("users");
                                    JSONArray services = jsonObj.getJSONArray("service");

                                    if (services.length() > 0) {
                                        for (int i = 0; i < services.length(); i++) {
                                            JSONObject c = services.getJSONObject(i);
                                            if(!c.isNull("Name")) {
                                                Slots item = new Slots();
                                                item.setSlots(c.getString("Name"));
                                                item.setID(c.getInt("id"));
                                                mServices.add(item);
                                            }
                                        }

                                    }
                                    // looping through All Contacts
                                    if (contacts.length() > 0) {
                                        for (int i = 0; i < contacts.length(); i++) {
                                            JSONObject c = contacts.getJSONObject(i);
                                            Album item = new Album();
                                            item.setThumbnailUrl(c.getString("pic"));
                                            item.setName(c.getString("name"));
                                            item.setDetail(c.getString("service"));
                                            item.setID(c.getInt("ID"));
                                            item.setAvailable(c.getInt("available"));
                                            mTeam.add(item);
                                        }

                                    }

                                    if (mTeam.size() != 0) {
                                        firstlayout.setVisibility(View.VISIBLE);
                                        secondlayout.setVisibility(View.GONE);
                                        nospecialist.setVisibility(View.GONE);
                                        specialistrv.setVisibility(View.VISIBLE);
                                        Submit1.setVisibility(View.VISIBLE);
                                        adapter_team sAdapter = new adapter_team(StoreSlaonSpecialist.this, mTeam);
                                        sAdapter.notifyDataSetChanged();
                                        sAdapter.setPref(pref);
                                        specialistrv.setAdapter(sAdapter);
                                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                        specialistrv.setLayoutManager(mLayoutManager);
                                        Submit1.setText("Add More");
                                    } else {
                                        firstlayout.setVisibility(View.VISIBLE);
                                        secondlayout.setVisibility(View.GONE);
                                        nospecialist.setVisibility(View.VISIBLE);
                                    }
                                    if (mServices.size() != 0) {
                                        specialistservicerv.setVisibility(View.VISIBLE);
                                        SpecialistServiceRv sAdapter = new SpecialistServiceRv(StoreSlaonSpecialist.this, mServices);
                                        sAdapter.notifyDataSetChanged();
                                        sAdapter.setButton(Submit2);
                                        sAdapter.setprogressBar(progressBar);
                                        sAdapter.setspecialistimage(specialistimage);
                                        sAdapter.setspecialistdetails(specialistdetails);
                                        sAdapter.setpref(pref);
                                        sAdapter.setspecialistname(specialistname);
                                        sAdapter.setspecialistservice(specialistservice);
                                        specialistservicerv.setAdapter(sAdapter);
                                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                                        specialistservicerv.setLayoutManager(mLayoutManager);

                                    } else {
                                        specialistservicerv.setVisibility(View.GONE);
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
                        params.put("id", _phoneNo);
                        return params;
                    }

                };
                AppController.getInstance().addToRequestQueue(eventoReq);

        }
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
        int orientation = getScreenOrientation(getApplicationContext());
        if (isTV(getApplicationContext())) return 2;
        if (isTablet(getApplicationContext()))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(!first){
                    first=true;
                    Submit1.setText("Submit");
                    firstlayout.setVisibility(View.GONE);
                    secondlayout.setVisibility(View.VISIBLE);
                    secondlayout.setAnimation(inFromRight);
                }
                break;

            case R.id.addimage:
                first=true;
                firstlayout.setVisibility(View.GONE);
                secondlayout.setVisibility(View.VISIBLE);
                secondlayout.setAnimation(inFromRight);
                Submit1.setBackgroundColor(Submit1.getContext().getResources().getColor(R.color.top));
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
        if (!StoreSlaonSpecialist.this.isFinishing()) {
            final CharSequence[] items = {"Take from camera", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(StoreSlaonSpecialist.this, R.style.AlertDialogTheme_new);
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
                        first=false;
                        Again=false;
                        firstlayout.setVisibility(View.VISIBLE);
                        secondlayout.setVisibility(View.GONE);
                        secondlayout.setAnimation(outtoLeft);
                        firstlayout.setAnimation(inFromRight);
                        Submit1.setBackground(getResources().getDrawable(R.drawable.editt2));
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
                pref.setisSpecialist(1);
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
                        first=false;
                        Again=false;
                        firstlayout.setVisibility(View.VISIBLE);
                        secondlayout.setVisibility(View.GONE);
                        secondlayout.setAnimation(outtoLeft);
                        firstlayout.setAnimation(inFromRight);
                        Submit1.setBackground(getResources().getDrawable(R.drawable.editt2));
                        Intent o = new Intent(StoreSlaonSpecialist.this, SalonHomePage.class);
                        startActivity(o);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                        finish();
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
                Intent o = new Intent(StoreSlaonSpecialist.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

            }else {
                if (!first) {
                    Intent o = new Intent(StoreSlaonSpecialist.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                } else {
                    first = false;
                    Again = false;
                    firstlayout.setVisibility(View.VISIBLE);
                    secondlayout.setVisibility(View.GONE);
                    secondlayout.setAnimation(outtoLeft);
                    firstlayout.setAnimation(inFromRight);
                    Submit1.setBackground(getResources().getDrawable(R.drawable.editt2));
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                }
            }
        }

        return true;
    }

}
