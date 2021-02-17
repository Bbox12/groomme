package com.zanrite.groomme.Booking;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HomeserviceSelected extends AppCompatActivity  implements View.OnClickListener{

    private PrefManager pref;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private EditText address,house_no,land_mark,mAutocompleteView;
    private Toolbar toolbar;
    private Button Ride_now,Ride_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeserviceselected);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        progressBar = findViewById(R.id.progressBarSplash);
        coordinatorLayout = findViewById(R.id.cor_home_main);
        address = findViewById(R.id.address);
        house_no = findViewById(R.id.house_no);
        land_mark = findViewById(R.id.land_mark);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAutocompleteView = findViewById(R.id.autocomplete_places_drop);
        mAutocompleteView.setOnClickListener(null);
        Ride_now = findViewById(R.id.ride_now);
        Ride_now.setOnClickListener(this);
        Ride_cancel = findViewById(R.id.ride_cancel);

        mAutocompleteView.setText(pref.getDropAt1());


        Ride_cancel.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeserviceSelected.this, CheckoutTime.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
        });


        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(HomeserviceSelected.this);
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
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mAutocompleteView.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){



            case R.id.ride_now:
                if (_phoneNo != null) {
                    if (!TextUtils.isEmpty(mAutocompleteView.getText().toString())) {
                        if (TextUtils.isEmpty(house_no.getText().toString())) {
                            house_no.setError("Please input House.Flat No");
                        } else {
                            go(mAutocompleteView.getText().toString());
                        }


                    } else {
                        if (TextUtils.isEmpty(address.getText().toString())) {
                            address.setError("Address Missing");
                        } else {
                            if (TextUtils.isEmpty(house_no.getText().toString())) {
                                house_no.setError("Please input House.Flat No");
                            } else {
                                go(address.getText().toString());
                            }
                        }

                    }

                }
                break;
            case R.id.ride_cancel:
                Intent i = new Intent(HomeserviceSelected.this, CheckoutTime.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;
        }

    }

    private void go(final String addresss) {


        progressBar.setVisibility(View.VISIBLE);



        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_BOOKING_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("volley", response);


                            String[] pars = response.split("error");
                            if (pars[1].contains("false")) {
                                if (!HomeserviceSelected.this.isFinishing()) {

                                        Intent i = new Intent(HomeserviceSelected.this, SelectePaymentOption.class);
                                        startActivity(i);
                                        finish();
                                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                                }
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d("uuu", "Error: " + error.getMessage());
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
                params.put("order", pref.getOrderID());
                params.put("address", addresss);
                params.put("house", house_no.getText().toString());
                params.put("landmark", land_mark.getText().toString());
                params.put("latitude", pref.getDropLat());
                params.put("longitude",pref.getDropLong());
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
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
            Intent i = new Intent(HomeserviceSelected.this, CheckoutTime.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

}
