package com.zanrite.groomme.Booking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity implements View.OnClickListener {
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private PrefManager pref;
    private String _phoneNo;
    private AppCompatCheckBox _cash;
    private int paymentmode=0;
    private  ProgressDialog mProgressDialog = null;

    @Override
    public void onClick(View view) {
     switch (view.getId()){

         case R.id.finalsubmit:
             go();
                  break;
         default:break;
         }


    }

    private void go() {

        if (!Payment.this.isFinishing() ) {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(Payment.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    mProgressDialog.setTitle("Appointment created");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();
                }


        }
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_BOOKING_MODE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("volley", response);


                        String[] pars = response.split("error");
                        if (pars[1].contains("false")) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("UserMobile").setValue(_phoneNo);
                            mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("SalonMobile").setValue(pref.getSalonPhone());
                            mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("OTP").setValue(pref.getOrderID());
                            mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("STATUS").setValue("1");
                            mDatabase.child("Request").child(pref.getSalonPhone()).setValue(String.valueOf(pref.getOrderID()));
                            if (!Payment.this.isFinishing() ) {
                                if (mProgressDialog != null) {
                                    mProgressDialog.dismiss();
                                }
                            }
                            pref.setPaymentMode(paymentmode);
                            Intent i = new Intent(Payment.this, Wb_access.class);
                            startActivity(i);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
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
                                    go();
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
                                    go();
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
                                    go();
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
                                    go();
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
                params.put("order", pref.getOrderID());
                params.put("payment", String.valueOf(paymentmode));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
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
        toolbar.setTitle("Payment");

        Button finalsubmit = findViewById(R.id.finalsubmit);
        finalsubmit.setOnClickListener(this);

        _cash=findViewById(R.id.cash);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent o = new Intent(Payment.this, SalonHomePage.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


            }

        });

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(Payment.this);
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
    protected void onResume() {
        super.onResume();
        _cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    paymentmode=1;

                }
            }
        });

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

                if(paymentmode==0) {
                    Intent i = new Intent(Payment.this, SelectePaymentOption.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else{
                    Intent i = new Intent(Payment.this, SalonHomePage.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!Payment.this.isFinishing() ) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!Payment.this.isFinishing() ) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Payment.this.isFinishing() ) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }
}
