package com.zanrite.groomme.Booking;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Adapters.Ride_adapter;
import com.zanrite.groomme.Alarm.AlarmNotificationService;
import com.zanrite.groomme.Alarm.AlarmReceiver;
import com.zanrite.groomme.Alarm.AlarmSoundService;
import com.zanrite.groomme.FCM.NotificationUtils;
import com.zanrite.groomme.Models.Bookingmodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GetBookings  extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private static final String TAG = GetBookings.class.getSimpleName();
    private ProgressBar progressBar;
    private RecyclerView laterRv;
    private PrefManager pref;
    private String _PhoneNo;
    private double My_lat=0,My_long=0,To_lat=0,To_long=0;
    private Toolbar toolbar;
    private String Vehicle;
    private DatabaseReference mDatabase;
    private MediaPlayer _Player=null;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 10009;
    private boolean _played=false;
    private boolean _first=false;
    private long position;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.later_dates);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE2);
        progressBar = findViewById(R.id.progressBar21);
        progressBar.setVisibility(View.VISIBLE);
        toolbar=findViewById(R.id.toolbar_later_dates);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        laterRv=findViewById(R.id.laterRv);
        progressBar.setVisibility(View.VISIBLE);
        coordinatorLayout=findViewById(R.id.main_content);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setGoTRide(0);
                Intent o = new Intent(GetBookings.this, SalonHomePage.class);
                o.putExtra("my_lat", My_lat);
                o.putExtra("my_long", My_long);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config_URL.PUSH_NOTIFICATION)) {
                    RegisterAlarmBroadcast();
                }
            }
        };

        Intent alarmIntent = new Intent(GetBookings.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(GetBookings.this, ALARM_REQUEST_CODE, alarmIntent, 0);
        if (pendingIntent != null) {
            stopAlarmManager();
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }



    @Override
    protected void onResume() {
        super.onResume();

        Intent i = getIntent();
        My_lat = i.getDoubleExtra("mylat", 0);
        My_long = i.getDoubleExtra("mylong", 0);
        mDatabase.child("Request").child(pref.getSalonPhone()).addValueEventListener(new FirebaseDataListener());


        progressBar.setVisibility(View.GONE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config_URL.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config_URL.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    @Override
    protected void onStop() {
        super.onStop();

    }

    private class FirebaseDataListener implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            long count = dataSnapshot.getChildrenCount();
            if (dataSnapshot.getValue()!=null) {

                    final ArrayList<Bookingmodel> mItems=new ArrayList<Bookingmodel>();
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    Log.w("details", response);


                                    try {

                                        JSONObject jsonObj = new JSONObject(response);
                                        JSONArray contacts = jsonObj.getJSONArray("bookings");


                                        // looping through All Contacts
                                        if (contacts.length() > 0) {
                                            for (int i = 0; i < contacts.length(); i++) {
                                                JSONObject c = contacts.getJSONObject(i);
                                                Bookingmodel item1 = new Bookingmodel();
                                                item1.setID(c.getInt("ID"));
                                                item1.setOTP(c.getInt("OTP"));
                                                item1.setAddressd(c.getString("addressd"));
                                                item1.setCrew_name(c.getString("crew_name"));
                                                item1.setDiscount(c.getDouble("Discount"));
                                                item1.setNoofItems(c.getInt("NoofItems"));
                                                item1.setHouseno(c.getString("houseno"));
                                                item1.setPayable(c.getDouble("Payable"));
                                                item1.setIDserviceAt(c.getInt("IDserviceAt"));
                                                item1.setOrderDate(c.getString("OrderDate"));
                                                item1.setSlot(c.getString("Slot"));
                                                item1.setPhoto(c.getString("Photo"));
                                                item1.setUserName(c.getString("name"));
                                                item1.setUserMobile(c.getString("PhoneNo"));
                                                item1.setPPhoto(c.getString("PPhoto"));
                                                item1.setParlour_mobile(c.getString("parlour_mobile"));
                                                item1.setParlour_name(c.getString("parlour_name"));
                                                item1.setActualTime(c.getString("ActualTime"));
                                                item1.setIsCancelled(c.getInt("isCancelled"));
                                                item1.setIsAccepted(c.getInt("isAccepted"));
                                                item1.setParlour_latitude(c.getDouble("Parlour_latitude"));
                                                item1.setParlour_longitude(c.getDouble("Parlour_longitude"));
                                                item1.setHome_latitude(c.getDouble("Home_latitude"));
                                                item1.setHome_longitude(c.getDouble("Home_longitude"));
                                                mItems.add(item1);
                                            }

                                        }


                                    } catch (final JSONException e) {
                                        Log.e("HI", "Json parsing error: " + e.getMessage());


                                    } if (mItems.size() != 0) {
                                        if(!_played) {
                                            RegisterAlarmBroadcast();
                                        }
                                        Ride_adapter sAdapter = new Ride_adapter(GetBookings.this, mItems);
                                        sAdapter.notifyDataSetChanged();
                                        sAdapter.setPef(pref);
                                        sAdapter.setPast(2);
                                        laterRv.setAdapter(sAdapter);
                                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(GetBookings.this);
                                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        laterRv.setLayoutManager(mLayoutManager);

                                    } else {
                                        _remove();
                                    }
                                    progressBar.setVisibility(View.GONE);


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
                            params.put("id", pref.getiPref1());
                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(eventoReq);


            } else {
                _remove();
            }
        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

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



    private void _remove() {
        stopAlarmManager();
        if(!GetBookings.this.isFinishing() && pref.getOrderID()==null) {
            pref.setOrderID(null);
            new AlertDialog.Builder(GetBookings.this, R.style.AlertDialogTheme)
                    .setTitle("No booking records")
                    .setMessage("We have not found any appoinments for you.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(pref.getResposibility()==2) {
                                Intent o = new Intent(GetBookings.this, SalonHomePage.class);
                                startActivity(o);
                                finish();
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            }else  if(pref.getResposibility()==1) {
                                Intent o = new Intent(GetBookings.this, UserHomeScreen.class);
                                startActivity(o);
                                finish();
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            }
                            dialog.cancel();
                        }
                    })

                    .show();
        }


    }


    private void RegisterAlarmBroadcast() {
        _played=true;
        triggerAlarmManager(System.currentTimeMillis());

    }
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    public void triggerAlarmManager(long alarmTriggerTime) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, (alarmTriggerTime), pendingIntent);
        //sst alarm manager with entered timer by converting into milliseconds
    }


    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
        stopService(new Intent(GetBookings.this, AlarmSoundService.class));
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
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
        switch (item.getItemId()) {


            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(GetBookings.this, SalonHomePage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }
}
