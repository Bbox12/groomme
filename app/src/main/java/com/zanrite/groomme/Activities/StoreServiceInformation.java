package com.zanrite.groomme.Activities;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.HashMap;
import java.util.Map;

public  class StoreServiceInformation extends AppCompatActivity implements View.OnClickListener {

    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RadioGroup _radioGroup;
    private RadioButton atsalon,athome,atboth;
    private Toolbar toolbar;
    private Button Submit1;
    private int serviceAT=0;
    private ProgressBar progressBar;
    private int _from=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storesalonserviceinformation);
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
        _radioGroup=findViewById(R.id.radioGroup);
        atsalon=findViewById(R.id.atsalon);
        athome=findViewById(R.id.athome);
        atboth=findViewById(R.id.atboth);
        Submit1=findViewById(R.id.submit);
        Submit1.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarSplash);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(StoreServiceInformation.this, SalonHomePage.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

                }else {
                    Intent o = new Intent(StoreServiceInformation.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                }

            }
        });
        serviceAT=1;
        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(StoreServiceInformation.this);
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
        Intent i=getIntent();
        _from=i.getIntExtra("_from",0);
        _radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.atsalon:
                        serviceAT=1;

                        break;
                    case R.id.athome:
                        serviceAT=2;

                        break;
                    case R.id.atboth:
                        serviceAT=3;

                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.submit:
              if(serviceAT!=0){
                  StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_SERVICE_LOCATION,
                          new Response.Listener<String>() {
                              @Override
                              public void onResponse(String response) {
                                  progressBar.setVisibility(View.GONE);
                                  Log.w("kk", response.toString());
                                      String[] par = response.split("error");
                                      if (par[1].contains("false")) {
                                          pref.setLocation(1);
                                          ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                          alert.showDialog(StoreServiceInformation.this, "Succesfully stored information.",false);

                                      }else{
                                          ViewDialogFailed alert = new ViewDialogFailed();
                                          alert.showDialog(StoreServiceInformation.this, "Failed to store information! Please try another time.",true);
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
                          params.put("mobile", _phoneNo);
                          params.put("service", String.valueOf(serviceAT));
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

               // Toast.makeText(getApplicationContext(),String.valueOf(serviceAT),Toast.LENGTH_SHORT).show();
                pref.setServiceAt(serviceAT);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pref.getSecondaryservice()!=null) {

                            Intent o = new Intent(StoreServiceInformation.this, SalonServiceDetails.class);
                            startActivity(o);
                            finish();
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                        }
                       else  if(pref.getLatLong()==0) {
                                Intent o3 = new Intent(StoreServiceInformation.this, StoreSalonLocation.class);
                                startActivity(o3);
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                                finish();
                                dialog1.dismiss();
                            }else{
                            Intent o = new Intent(StoreServiceInformation.this, SalonHomePage.class);
                            startActivity(o);
                            finish();
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
                        }
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
                Intent o = new Intent(StoreServiceInformation.this, SalonHomePage.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;

            }else {
                Intent o = new Intent(StoreServiceInformation.this, ServiceOffer.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);;
            }

        }
        return true;
    }

}
