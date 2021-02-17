package com.zanrite.groomme.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.PopulateSalons;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class ServiceTimings extends AppCompatActivity implements View.OnClickListener {

    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private Button Submit1;
    private ProgressBar progressBar;
    private EditText sunday_ot, sunday_ct;
    private EditText monday_ot, monday_ct;
    private EditText tuesday_ot, tuesday_ct;
    private EditText wednessday_ot, wednessday_ct;
    private EditText thursday_ot, thursday_ct;
    private EditText friday_ot, friday_ct;
    private EditText satureday_ot, satureday_ct;
    private SwitchCompat Sun, Mon, Tue, Wed, Thr, Fri, Sat, Week_state;
    private LinearLayout Lmon,Ltue,Lwed,Lthr,Lfri,Lsat,Lsun;
    private Calendar cal;
    private int Omon=0,Otue=0,Owed=0,Othr=0,Ofri=0,Osat=0,Osun=0;
    private CardView addimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salontimings);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        initCollapsingToolbar();
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarSplash);
        Sun =  findViewById(R.id.sunswitch);
        Mon =  findViewById(R.id.monswitch);
        Tue =  findViewById(R.id.tueswitch);
        Wed =  findViewById(R.id.wedswitch);
        Thr =  findViewById(R.id.thrswitch);
        Fri =  findViewById(R.id.friswitch);
        Sat =  findViewById(R.id.satswitch);




        Lmon =  findViewById(R.id.Lmon);
        Ltue =  findViewById(R.id.Ltue);
        Lwed =  findViewById(R.id.Lwed);
        Lthr =  findViewById(R.id.Lthr);
        Lfri =  findViewById(R.id.Lfri);
        Lsat =  findViewById(R.id.Lsat);
        Lsun =  findViewById(R.id.Lsun);


        sunday_ot = (EditText) findViewById(R.id.sunopen);
        sunday_ct = (EditText) findViewById(R.id.sunclose);

        monday_ot = (EditText) findViewById(R.id.monopen);
        monday_ct = (EditText) findViewById(R.id.monclose);

        tuesday_ot = (EditText) findViewById(R.id.tueopen);
        tuesday_ct = (EditText) findViewById(R.id.tueclose);

        wednessday_ot = (EditText) findViewById(R.id.wedopen);
        wednessday_ct = (EditText) findViewById(R.id.wedclose);

        thursday_ot = (EditText) findViewById(R.id.thropen);
        thursday_ct = (EditText) findViewById(R.id.thrclose);

        friday_ot = (EditText) findViewById(R.id.friopen);
        friday_ct = (EditText) findViewById(R.id.friclose);

        satureday_ot = (EditText) findViewById(R.id.satopen);
        satureday_ct = (EditText) findViewById(R.id.satclose);


        sunday_ct.setOnClickListener(this);
        sunday_ot.setOnClickListener(this);
        monday_ot.setOnClickListener(this);
        monday_ct.setOnClickListener(this);
        tuesday_ot.setOnClickListener(this);
        tuesday_ct.setOnClickListener(this);
        wednessday_ot.setOnClickListener(this);
        wednessday_ct.setOnClickListener(this);
        thursday_ot.setOnClickListener(this);
        thursday_ct.setOnClickListener(this);
        friday_ot.setOnClickListener(this);
        friday_ct.setOnClickListener(this);
        satureday_ot.setOnClickListener(this);
        satureday_ct.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(ServiceTimings.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

                }else {
                    Intent o = new Intent(ServiceTimings.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                }

            }
        });

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(ServiceTimings.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);


        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);
        collapsingToolbar.setTitleEnabled(false);
        // hiding & showing the title when toolbar expanded & collapsed


    }


    @Override
    protected void onResume() {
        super.onResume();

        if(pref.getSalonPhone()!=null){
            getTimings();
        }


        Sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit1.requestFocus();
                if(Sun.isChecked()){
                   Osun=1;
                    sunday_ot.setText("8:00 AM");
                    sunday_ct.setText("5:00 PM");

                }else{
                    Osun=0;
                    sunday_ot.setText("CLOSED");
                    sunday_ct.setText("CLOSED");
                }
            }
        });
        Mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mon.isChecked()){
                    Omon=1;
                    monday_ot.setText("8:00 AM");
                    monday_ct.setText("5:00 PM");
                }else{
                    Omon=0;
                    monday_ot.setText("CLOSED");
                    monday_ct.setText("CLOSED");
                }
            }
        });

        Tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tue.isChecked()){
                    Otue=1;
                    tuesday_ot.setText("8:00 AM");
                    tuesday_ct.setText("5:00 PM");
                }else{
                    Otue=0;
                    tuesday_ct.setText("CLOSED");
                    tuesday_ot.setText("CLOSED");

                }
            }
        });
        Wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Wed.isChecked()){

                    Owed=1;
                    wednessday_ot.setText("8:00 AM");
                    wednessday_ct.setText("5:00 PM");
                }else{

                    Owed=0;
                    wednessday_ct.setText("CLOSED");
                    wednessday_ot.setText("CLOSED");
                }
            }
        });
        Thr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Thr.isChecked()){

                    Othr=1;
                    thursday_ot.setText("8:00 AM");
                    thursday_ct.setText("5:00 PM");
                }else{

                    Othr=1;
                    thursday_ot.setText("CLOSED");
                    thursday_ct.setText("CLOSED");

                }
            }
        });
        Fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fri.isChecked()){

                    Ofri=1;
                    friday_ot.setText("8:00 AM");
                    friday_ct.setText("5:00 PM");
                }else{

                    Ofri=0;
                    friday_ct.setText("CLOSED");
                    friday_ot.setText("CLOSED");
                }
            }
        });
        Sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sat.isChecked()){

                    Osat=1;
                    satureday_ot.setText("8:00 AM");
                    satureday_ct.setText("5:00 PM");
                }else{
                    Osat=0;
                    satureday_ct.setText("CLOSED");
                    satureday_ot.setText("CLOSED");
                }
            }
        });




    }

    private void getTimings() {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                new Response.Listener<String>() {
                    private String sunday_ots, monday_ots, sunday_cts, monday_cts, tue_open_time, tue_close_time, thr_open_time, thr_close_time, wed_open_time, wed_close_time, fri_open_time, fri_close_time, sat_open_time, sat_close_time;

                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray timings = jsonObj.getJSONArray("timings");

                            // looping through All Contacts
                            if (timings.length() > 0) {
                                for (int i = 0; i < timings.length(); i++) {
                                    JSONObject c = timings.getJSONObject(i);
                                    if (c.getInt("sun_open_close") == 1) {
                                        sunday_ots = c.getString("sun_open_time");
                                        sunday_cts = c.getString("sun_close_time");
                                        Osun = 1;
                                    } else {
                                        sunday_ots = "CLOSED";
                                        sunday_cts = "CLOSED";
                                    }
                                    if (c.getInt("mon_open_close") == 1) {
                                        monday_ots = c.getString("mon_open_time");
                                        monday_cts = c.getString("mon_close_time");
                                        Omon = 1;
                                    } else {
                                        monday_ots = "CLOSED";
                                        monday_cts = "CLOSED";
                                    }
                                    if (c.getInt("tue_open_close") == 1) {
                                        tue_open_time = c.getString("tue_open_time");
                                        tue_close_time = c.getString("tue_close_time");
                                        Otue = 1;
                                    } else {
                                        tue_open_time = "CLOSED";
                                        tue_close_time = "CLOSED";
                                    }
                                    if (c.getInt("wed_open_close") == 1) {
                                        wed_open_time = c.getString("wed_open_time");
                                        wed_close_time = c.getString("wed_close_time");
                                        Owed = 1;
                                    } else {
                                        wed_open_time = "CLOSED";
                                        wed_close_time = "CLOSED";
                                    }
                                    if (c.getInt("thr_open_close") == 1) {
                                        thr_open_time = c.getString("thr_open_time");
                                        thr_close_time = c.getString("thr_close_time");
                                        Othr = 1;
                                    } else {
                                        thr_open_time = "CLOSED";
                                        thr_close_time = "CLOSED";
                                    }
                                    if (c.getInt("fri_open_close") == 1) {
                                        fri_open_time = c.getString("fri_open_time");
                                        fri_close_time = c.getString("fri_close_time");
                                        Ofri = 1;
                                    } else {
                                        fri_open_time = "CLOSED";
                                        fri_close_time = "CLOSED";
                                    }

                                    if (c.getInt("sat_open_close") == 1) {
                                        sat_open_time = c.getString("sat_open_time");
                                        sat_close_time = c.getString("sat_close_time");
                                        Osat = 1;
                                    } else {
                                        sat_open_time = "CLOSED";
                                        sat_close_time = "CLOSED";
                                    }

                                }

                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }
                        sunday_ot.setText(sunday_ots);
                        sunday_ct.setText(sunday_cts);
                        monday_ot.setText(monday_ots);
                        monday_ct.setText(monday_cts);
                        tuesday_ot.setText(tue_open_time);
                        tuesday_ct.setText(tue_close_time);
                        wednessday_ot.setText(wed_open_time);
                        wednessday_ct.setText(wed_close_time);
                        thursday_ot.setText(thr_open_time);
                        thursday_ct.setText(thr_close_time);
                        friday_ot.setText(fri_open_time);
                        friday_ct.setText(fri_close_time);
                        satureday_ot.setText(sat_open_time);
                        satureday_ct.setText(sat_close_time);
                        if (Osun == 1) {
                            Sun.setChecked(true);
                        }
                        if (Omon == 1) {
                            Mon.setChecked(true);
                        }
                        if (Otue == 1) {
                            Tue.setChecked(true);
                        }
                        if (Owed == 1) {
                            Wed.setChecked(true);
                        }
                        if (Othr == 1) {
                            Thr.setChecked(true);
                        }
                        if (Ofri == 1) {
                            Fri.setChecked(true);
                        }
                        if (Osat == 1) {
                            Sat.setChecked(true);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.monopen:
                if(Omon==1) {
                    open_time(monday_ot);
                }
                break;
            case R.id.monclose:
                if(Omon==1) {
                    open_time(monday_ct);
                }
                break;
            case R.id.tueopen:
                if(Otue==1) {
                    open_time(tuesday_ot);
                }
                break;
            case R.id.tueclose:
                if(Otue==1) {
                    open_time(tuesday_ct);
                }
                break;
            case R.id.wedopen:
                if(Owed==1) {
                    open_time(wednessday_ot);
                }
                break;
            case R.id.wedclose:
                if(Owed==1) {
                    open_time(wednessday_ct);
                }
                break;
            case R.id.thropen:
                if(Othr==1) {
                    open_time(thursday_ot);
                }
                break;
            case R.id.thrclose:
                if(Othr==1) {
                    open_time(thursday_ct);
                }
                break;
            case R.id.friopen:
                if(Ofri==1) {
                    open_time(friday_ot);
                }
                break;
            case R.id.friclose:
                if(Ofri==1) {
                    open_time(friday_ct);
                }
                break;
            case R.id.satopen:
                if(Osat==1) {
                    open_time(satureday_ot);
                }
                break;
            case R.id.satclose:
                if(Osat==1) {
                    open_time(satureday_ct);
                }
                break;
            case R.id.sunopen:
                if(Osun==1) {
                    open_time(sunday_ot);
                }
                break;
            case R.id.sunclose:
                if(Osun==1) {
                    open_time(sunday_ct);
                }
                break;
            case R.id.submit:
                boolean _sunvalid=false;
                boolean _monvalid=false;
                boolean _tuevalid=false;
                boolean _wedvalid=false;
                boolean _thrvalid=false;
                boolean _frivalid=false;
                boolean _satvalid=false;
                if(Osun==1){
                    if(TextUtils.isEmpty(sunday_ot.getText().toString())||TextUtils.isEmpty(sunday_ct.getText().toString())){
                        _sunvalid=true;
                        sunday_ot.setError("Empty");
                    }
                }if(Omon==1){
                if(TextUtils.isEmpty(monday_ot.getText().toString())||TextUtils.isEmpty(monday_ct.getText().toString())){
                    _monvalid=true;
                    monday_ot.setError("Empty");
                }
            }if(Otue==1){
                if(TextUtils.isEmpty(tuesday_ot.getText().toString())||TextUtils.isEmpty(tuesday_ct.getText().toString())){
                    _tuevalid=true;
                    tuesday_ot.setError("Empty");
                }
            }if(Owed==1){
                if(TextUtils.isEmpty(wednessday_ot.getText().toString())||TextUtils.isEmpty(wednessday_ct.getText().toString())){
                    _wedvalid=true;
                    wednessday_ot.setError("Empty");
                }
            }if(Othr==1){
                if(TextUtils.isEmpty(thursday_ot.getText().toString())||TextUtils.isEmpty(thursday_ct.getText().toString())){
                    _thrvalid=true;
                    thursday_ot.setError("Empty");
                }
            }
                if(Ofri==1){
                    if(TextUtils.isEmpty(friday_ot.getText().toString())||TextUtils.isEmpty(friday_ct.getText().toString())){
                        _frivalid=true;
                        friday_ot.setError("Empty");
                    }
                }
                if(Osat==1){
                    if(TextUtils.isEmpty(satureday_ot.getText().toString())||TextUtils.isEmpty(satureday_ct.getText().toString())){
                        _satvalid=true;
                        satureday_ot.setError("Empty");
                    }
                }
                if(!_sunvalid && !_monvalid && !_tuevalid && !_wedvalid && !_thrvalid && !_frivalid && !_satvalid) {
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_SCHEDULE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.w("timie", response.toString());

                                    String[] par = response.split("error");
                                    if (par[1].contains("false")) {
                                        pref.setTimings(1);
                                        ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                        alert.showDialog(ServiceTimings.this, "Succesfully stored information.", false);

                                    } else {
                                        ViewDialogFailed alert = new ViewDialogFailed();
                                        alert.showDialog(ServiceTimings.this, "Failed to store information! Please try another time.", true);
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
                            params.put("parlour",_phoneNo);
                            params.put("sun_open_close", String.valueOf(Osun));
                            params.put("sun_open_time", sunday_ot.getText().toString());
                            params.put("sun_close_time", sunday_ct.getText().toString());
                            params.put("mon_open_close", String.valueOf(Omon));
                            params.put("mon_open_time", monday_ot.getText().toString());
                            params.put("mon_close_time", monday_ct.getText().toString());
                            params.put("tue_open_close", String.valueOf(Otue));
                            params.put("tue_open_time", (tuesday_ot.getText().toString()));
                            params.put("tue_close_time", (tuesday_ct.getText().toString()));
                            params.put("wed_open_close", String.valueOf((Owed)));
                            params.put("wed_open_time", (wednessday_ot.getText().toString()));
                            params.put("wed_close_time", (wednessday_ct.getText().toString()));
                            params.put("thr_open_close", String.valueOf((Othr)));
                            params.put("thr_open_time", (thursday_ot.getText().toString()));
                            params.put("thr_close_time", (thursday_ct.getText().toString()));
                            params.put("fri_open_close", String.valueOf((Ofri)));
                            params.put("fri_open_time", (friday_ot.getText().toString()));
                            params.put("fri_close_time", (friday_ct.getText().toString()));
                            params.put("sat_open_close", String.valueOf((Osat)));
                            params.put("sat_open_time", (satureday_ot.getText().toString()));
                            params.put("sat_close_time", (satureday_ct.getText().toString()));
                            return params;
                        }

                    };

                    // Añade la peticion a la cola
                    AppController.getInstance().addToRequestQueue(eventoReq);
                }
            
                break;
            default:break;
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


    private void open_time(final EditText _time) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyy-MM-dd");
        final Dialog dialog1 = new Dialog(ServiceTimings.this, R.style.ThemeTransparent);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_time);

        TimePicker timePicker=dialog1.findViewById(R.id.timePickerExample);
        Button ok=dialog1.findViewById(R.id.button3);
        Calendar currCalendar = Calendar.getInstance();

        // Set the timezone which you want to display time.
        currCalendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));



            Calendar c=Calendar.getInstance();


                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker timePicker, int hour1, int minute) {

                        if (minute < 10) {

                            _time.setText(hour1 + ":0" + minute);
                        } else {
                            _time.setText(hour1 + ":" + minute);
                        }

                    }
                });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });
        dialog1.show();

    }

    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog1.setContentView(R.layout.custom_dialog_failed);

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
                        if(pref.getisSpecialist()==0) {
                            Intent o3 = new Intent(ServiceTimings.this, StoreSlaonSpecialist.class);
                            startActivity(o3);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                            finish();
                        }else{
                            Intent o3 = new Intent(ServiceTimings.this, SalonHomePage.class);
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
                Intent o = new Intent(ServiceTimings.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

            }else {
                Intent o = new Intent(ServiceTimings.this, ServiceOffer.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
            }

        }
        return true;
    }

}
