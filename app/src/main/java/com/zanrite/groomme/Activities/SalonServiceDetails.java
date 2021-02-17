package com.zanrite.groomme.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Models.SalonPriceModel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalonServiceDetails extends AppCompatActivity implements View.OnClickListener {

    private TextView _textprimary,_textsecondary,_txt1,_txt2;
    private EditText _detailsalon,_detailshome;
    private EditText _salon_price,_salon_time,_home_price,_home_time;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private PrefManager pref;
    private RelativeLayout _rsalon,_hsalon;
    private CheckBox _atsalon,_athome;
    private Button _submit;
    private Button _serviceFrom;
    private ProgressBar progressBar;
    private String mobileIp = "";
    private String TAG= SalonServiceDetails.class.getSimpleName();
    private String Name,_PhoneNo,WHO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        Name = user.get(PrefManager.KEY_NAME);
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

        setContentView(R.layout.addservicedetails);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        initCollapsingToolbar();

        progressBar = findViewById(R.id.progressBarSplash);

        _athome=findViewById(R.id.thisplacehome);
        _atsalon=findViewById(R.id.thisplacesalon);

        _txt1=findViewById(R.id.textView101);
        _txt2=findViewById(R.id.textView102);

        _textprimary=findViewById(R.id._textprimary);
        _textsecondary=findViewById(R.id._textsecondary);

        _submit=findViewById(R.id.submit);
        _serviceFrom=findViewById(R.id._serviceFrom);

        _detailsalon=findViewById(R.id._detailsalon);
        _salon_price=findViewById(R.id._salon_price);
        _salon_time=findViewById(R.id._salon_time);

        _rsalon=findViewById(R.id._rsalon);
        _hsalon=findViewById(R.id._hsalon);

        _detailshome=findViewById(R.id._detailshome);
        _home_price=findViewById(R.id._home_price);
        _home_time=findViewById(R.id._home_time);

        _submit.setOnClickListener(this);

       // Toast.makeText(getApplicationContext(),String.valueOf(pref.getServiceAt()),Toast.LENGTH_SHORT).show();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent o = new Intent(SalonServiceDetails.this, ServiceExpanded.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);



            }
        });

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(SalonServiceDetails.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
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
        } catch (Exception ex) {
        } // for now eat exceptions
        return null;
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);


        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);

        collapsingToolbar.setTitleEnabled(false);


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
                    isShow = true;
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    if (_txt1.getVisibility() == View.VISIBLE){
                        _txt1.setVisibility(View.GONE);
                        _txt2.setVisibility(View.GONE);
                        toolbar.setTitle(pref.getService());
                }
                } else if (isShow) {
                    collapsingToolbar.setTitleEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    isShow = false;
                    if (_txt1.getVisibility() == View.GONE) {
                        _txt1.setVisibility(View.VISIBLE);
                        _txt2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();

        if(pref.getFinalServiceID()!=0){
            _serviceFrom.setVisibility(View.GONE);

            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEWITHID,
                    new Response.Listener<String>() {
                        private String _name,_homePrice,_salonPrice,_homeTime,_service,_salonTime,_detailsSalon,_detailHome;
                        ArrayList<SalonPriceModel> listDataHeader = new ArrayList<SalonPriceModel>();
                        ArrayList<String> MenuArray=new ArrayList<String>();
                        @Override
                        public void onResponse(String response) {

                            Log.w("expand", response);


                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray services = jsonObj.getJSONArray("service");
                                for (int i = 0; i < services.length(); i++) {
                                    JSONObject c = services.getJSONObject(i);
                                 if(!c.isNull(c.getString("Name"))) {
                                     _name = (c.getString("Name"));
                                     _homePrice = (c.getString("Price_home"));
                                     _salonPrice = (c.getString("Price_salon"));
                                     _homeTime = (c.getString("Time_home"));
                                     _salonTime = (c.getString("Time_salon"));
                                     _service = (c.getString("Service"));
                                     _detailsSalon = (c.getString("Time_salon"));
                                     _detailHome = (c.getString("Service"));
                                 }

                                }

                            pref.setFinalServiceID(0);

                            } catch (final JSONException e) {
                                Log.e("HI", "Json parsing error: " + e.getMessage());


                            }
                               if(_name!=null) {
                                   String[] toka = _salonPrice.split("\\.");
                                   if (Integer.parseInt(toka[0]) != 0) {
                                       _atsalon.setChecked(true);
                                       _detailsalon.setText(_detailsSalon);
                                       _salon_price.setText(_salonPrice);
                                       _salon_time.setText(_salonTime);
                                   }
                                   String[] posa = _homePrice.split("\\.");
                                   if (Integer.parseInt(posa[0]) != 0) {
                                       _athome.setChecked(true);
                                       _detailshome.setText(_detailHome);
                                       _home_price.setText(_homePrice);
                                       _home_time.setText(_homeTime);
                                   }
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
                    params.put("id", String.valueOf(pref.getFinalServiceID()));
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(eventoReq);
        }else {
            _textprimary.setText(pref.getPrimaryservice() + "--->" + pref.getSecondaryservice());
            _textsecondary.setText(pref.getService());

            if (pref.getServiceAt() == 1) {
                _hsalon.setBackgroundColor(getResources().getColor(R.color.gray));
                _atsalon.setBackgroundColor(getResources().getColor(R.color.white));
                _serviceFrom.setText("You are servicing from Salon");
                _detailshome.setFocusableInTouchMode(false);
                _home_price.setFocusableInTouchMode(false);
                _home_time.setFocusableInTouchMode(false);
                _athome.setClickable(false);
                _atsalon.setChecked(true);


            } else if (pref.getServiceAt() == 2) {
                _rsalon.setBackgroundColor(getResources().getColor(R.color.gray));
               // _athome.setBackgroundColor(getResources().getColor(R.color.white));
                _detailsalon.setFocusableInTouchMode(false);
                _salon_price.setFocusableInTouchMode(false);
                _salon_time.setFocusableInTouchMode(false);
                _atsalon.setClickable(false);
                _serviceFrom.setText("You will service at Home");
                _atsalon.setClickable(false);
                _athome.setChecked(true);
            } else if (pref.getServiceAt() == 3) {
                _serviceFrom.setText("Service at home and Salon");
                _atsalon.setBackgroundColor(getResources().getColor(R.color.white));
             //   _athome.setBackgroundColor(getResources().getColor(R.color.white));
                _atsalon.setChecked(true);
                _athome.setChecked(true);
            } else {
                Intent o = new Intent(SalonServiceDetails.this, StoreServiceInformation.class);
                o.putExtra("_from", 2);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

            _athome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) {

                        _hsalon.setBackgroundColor(getResources().getColor(R.color.gray));
                        _athome.setBackgroundColor(Color.TRANSPARENT);
                        _detailshome.setFocusableInTouchMode(false);
                        _detailshome.setFocusable(false);
                        _home_price.setFocusableInTouchMode(false);
                        _home_time.setFocusableInTouchMode(false);
                        if (_atsalon.isChecked()) {
                            _serviceFrom.setText("You are servicing from Salon");
                        }
                        if (!_atsalon.isChecked()) {
                            pref.setLocation(0);
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(SalonServiceDetails.this, "Please select your serviceable preference", true);
                        } else {
                            pref.setServiceAt(1);
                        }
                    } else {
                        _hsalon.setBackground(getResources().getDrawable(R.drawable.openbackground));
                        _athome.setBackgroundColor(Color.WHITE);
                        _detailshome.setFocusable(true);
                        _detailshome.setFocusableInTouchMode(true);
                        _home_price.setFocusableInTouchMode(true);
                        _home_time.setFocusableInTouchMode(true);
                        if (!_atsalon.isChecked()) {
                            pref.setServiceAt(2);
                        } else {
                            pref.setServiceAt(3);
                        }
                    }
                }
            });
            _atsalon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (!b) {
                        _rsalon.setBackgroundColor(getResources().getColor(R.color.gray));
                        _atsalon.setBackgroundColor(Color.TRANSPARENT);
                        _detailsalon.setFocusable(false);
                        _detailsalon.setFocusableInTouchMode(false);
                        _salon_price.setFocusableInTouchMode(false);
                        _salon_time.setFocusableInTouchMode(false);

                        if (!_athome.isChecked()) {
                            pref.setLocation(0);
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(SalonServiceDetails.this, "Please select your serviceable preference", true);

                        } else {
                            _serviceFrom.setText("You will service at Home");
                            pref.setServiceAt(2);
                        }
                    } else {
                        _rsalon.setBackground(getResources().getDrawable(R.drawable.openbackground));
                        _atsalon.setBackgroundColor(Color.WHITE);
                        _detailsalon.setFocusable(true);
                        _detailsalon.setFocusableInTouchMode(true);
                        _salon_price.setFocusableInTouchMode(true);
                        _salon_time.setFocusableInTouchMode(true);
                        if (!_athome.isChecked()) {
                            pref.setServiceAt(1);
                        } else {
                            pref.setServiceAt(3);
                        }
                    }

                }
            });


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
                        Intent o = new Intent(SalonServiceDetails.this, StoreServiceInformation.class);
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
        switch (item.getItemId()) {


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(pref.getServiceAt()==1){
                StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_SALON_SERVICE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressBar.setVisibility(View.GONE);
                                Log.w("service", response);

                                String[] par = response.split("error");
                                if (par[1].contains("false")) {
                                       ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    alert.showDialog(SalonServiceDetails.this, "Succesfully stored information.",false);

                                }else{
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(SalonServiceDetails.this, "Failed to store information! Please try another time.",true);
                                }

                            }



                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        vollyError(error);

                    }

                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobile", _PhoneNo);
                        params.put("from", String.valueOf(pref.getServiceAt()));
                        params.put("service", String.valueOf(pref.getService()));
                        params.put("detail", _detailsalon.getText().toString());
                        params.put("price", _salon_price.getText().toString());
                        params.put("time", _salon_time.getText().toString());
                        params.put("IP", mobileIp);
                        return params;
                    }

                };

                // Añade la peticion a la cola
                AppController.getInstance().addToRequestQueue(eventoReq);
            }else if(pref.getServiceAt()==2){
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_SALON_SERVICE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.w("service", response);

                                    String[] par = response.split("error");
                                    if (par[1].contains("false")) {
                                         ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                        alert.showDialog(SalonServiceDetails.this, "Succesfully stored information.",false);

                                    }else{
                                        ViewDialogFailed alert = new ViewDialogFailed();
                                        alert.showDialog(SalonServiceDetails.this, "Failed to store information! Please try another time.",true);
                                    }

                                }



                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            vollyError(error);

                        }

                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mobile", _PhoneNo);
                            params.put("from", String.valueOf(pref.getServiceAt()));
                            params.put("service", String.valueOf(pref.getService()));
                            params.put("detail", _detailshome.getText().toString());
                            params.put("price", _home_price.getText().toString());
                            params.put("time", _home_time.getText().toString());
                            params.put("IP", mobileIp);
                            return params;
                        }

                    };

                    // Añade la peticion a la cola
                    AppController.getInstance().addToRequestQueue(eventoReq);
                }else if(pref.getServiceAt()==3){
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_SALONHOME_SERVICE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressBar.setVisibility(View.GONE);
                                    Log.w("service", response);

                                    String[] par = response.split("error");
                                    if (par[1].contains("false")) {
                                        ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                        alert.showDialog(SalonServiceDetails.this, "Succesfully stored information.",false);

                                    }else{
                                        ViewDialogFailed alert = new ViewDialogFailed();
                                        alert.showDialog(SalonServiceDetails.this, "Failed to store information! Please try another time.",true);
                                    }

                                }



                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            vollyError(error);

                        }

                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mobile",_PhoneNo );
                            params.put("from", String.valueOf(pref.getServiceAt()));
                            params.put("service", String.valueOf(pref.getService()));
                            params.put("hdetail", _detailshome.getText().toString());
                            params.put("hprice", _home_price.getText().toString());
                            params.put("htime", _home_time.getText().toString());
                            params.put("shdetail", _detailsalon.getText().toString());
                            params.put("shprice", _salon_price.getText().toString());
                            params.put("shtime", _salon_time.getText().toString());
                            params.put("IP", mobileIp);
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
                        pref.setSecondaryservice(null);
                        pref.setService(null);
                        Intent o = new Intent(SalonServiceDetails.this,ServiceExpanded.class);
                        startActivity(o);
                        finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }
                });
                dialog1.show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            Intent o = new Intent(SalonServiceDetails.this, ServiceExpanded.class);
            startActivity(o);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
        }

        return true;
    }


}
