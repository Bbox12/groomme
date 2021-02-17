package com.zanrite.groomme.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.MyAdapter;
import com.zanrite.groomme.Adapters.homepageTeamdapter;
import com.zanrite.groomme.Alarm.AlarmNotificationService;
import com.zanrite.groomme.Alarm.AlarmReceiver;
import com.zanrite.groomme.Alarm.AlarmSoundService;
import com.zanrite.groomme.Booking.GetBookings;
import com.zanrite.groomme.HistoryData.Tabs_past_future_ride;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.InboxActivity;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SalonHomePage extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SalonHomePage.class.getSimpleName();
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    private RecyclerView specialistrv;
    private TextView _time,_name;
    private Animation inFromRight,outtoLeft;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView img_profilo;
    private ImageView _i1,_i2,_i3,_i4,_i5,_i6,_i7,_i8,_i9,Appointments,Inbox,Invoices,Subscription,callus,visitus,mailus,logout;
    private LinearLayout bottomlayout;
    private HorizontalScrollView _H1,_H2;
    private boolean _first=false,second=false;
    private EditText LogOut;
    private LinearLayout layoutBottomSheet_explore;
    private BottomSheetBehavior sheetBehavior_explore;
    private TextView running;
    private static final int ALARM_REQUEST_CODE = 10009;
    private TextView cart_badge,cart_badge_appointments,discount_1,salonspecialist,_norating;
    private PendingIntent pendingIntent;
    private DecimalFormat dft;
    private ImageView Top1;
    private ShimmerFrameLayout mShimmerViewContainer;
    private AppBarLayout appBarLayout;
    private NestedScrollView scroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salonhomepage);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        scroller = (NestedScrollView) findViewById(R.id._nscroll);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);


        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        running=findViewById(R.id.running);

        toolbar.setTitle(R.string.app_name);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Services"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        img_profilo=findViewById(R.id.img_profilo);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager=findViewById(R.id.viewPagerVertical);
        ratingBar=findViewById(R.id.ratingBar);
        discount_1=findViewById(R.id.discount_1);
        _time=findViewById(R.id._time);
        _name=findViewById(R.id._name);
        specialistrv=findViewById(R.id.specialistrv);
        _norating=findViewById(R.id._norating);

        cart_badge=findViewById(R.id.cart_badge);
        cart_badge_appointments=findViewById(R.id.cart_badge_appointments);
        bottomlayout=findViewById(R.id.bottomlayout);
        bottomlayout.setVisibility(View.GONE);


        Top1=findViewById(R.id.top1);

        _H1=findViewById(R.id.h1);
        _H2=findViewById(R.id.h2);



        Appointments = findViewById(R.id.Appointments);
        Inbox = findViewById(R.id.Inbox);
        Invoices = findViewById(R.id.Invoices);
        Subscription = findViewById(R.id.Subscription);
        callus = findViewById(R.id.callus);
        visitus = findViewById(R.id.visitus);
        mailus = findViewById(R.id.mailus);
        logout = findViewById(R.id.logout);
        Appointments.setOnClickListener(this);
        Inbox.setOnClickListener(this);
        Invoices.setOnClickListener(this);
        Subscription.setOnClickListener(this);
        callus.setOnClickListener(this);
        visitus.setOnClickListener(this);
        mailus.setOnClickListener(this);
        logout.setOnClickListener(this);

        _i1 = findViewById(R.id._i1);
        _i2 = findViewById(R.id._i2);
        _i3 = findViewById(R.id._i3);
        _i4 = findViewById(R.id._i4);
        _i5 = findViewById(R.id._i5);
        _i1.setOnClickListener(this);
        _i2.setOnClickListener(this);
        _i3.setOnClickListener(this);
        _i4.setOnClickListener(this);
        _i5.setOnClickListener(this);


        _i6 = findViewById(R.id._i6);
        _i7 = findViewById(R.id._i7);
        _i8 = findViewById(R.id._i8);
        _i9 = findViewById(R.id._i9);
        _i6.setOnClickListener(this);
        _i7.setOnClickListener(this);
        _i8.setOnClickListener(this);
        _i9.setOnClickListener(this);


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

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        if(pref.getResposibility()==2) {
            layoutBottomSheet_explore = findViewById(R.id.bottom_sheet_salon);
            sheetBehavior_explore = BottomSheetBehavior.from(layoutBottomSheet_explore);
            sheetBehavior_explore.setHideable(false);
            sheetBehavior_explore.setPeekHeight(0);
            layoutBottomSheet_explore.setVisibility(View.VISIBLE);
            sheetBehavior_explore.setState(BottomSheetBehavior.STATE_EXPANDED);
            Intent alarmIntent = new Intent(SalonHomePage.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(SalonHomePage.this, ALARM_REQUEST_CODE, alarmIntent, 0);
            if (pendingIntent != null) {
                stopAlarmManager();
            }
        }

         dft = new DecimalFormat("0");
        salonspecialist=findViewById(R.id.salonspecialist);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getResposibility()==1){
                    Intent o = new Intent(SalonHomePage.this, UserHomeScreen.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else {
                    Intent o = new Intent(SalonHomePage.this, Splash_screen.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });
    }


    public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
        stopService(new Intent(SalonHomePage.this, AlarmSoundService.class));
        NotificationManager notificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
        }

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);



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
                    collapsingToolbar.setCollapsedTitleGravity(Gravity.LEFT);
                    isShow = true;
                    collapsingToolbar.setTitleEnabled(false);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    toolbar.setTitle(pref.getName());
                    if(pref.getResposibility()==2) {
                        if (sheetBehavior_explore.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                            layoutBottomSheet_explore.setVisibility(View.GONE);
                            sheetBehavior_explore.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    }
                } else if (isShow) {
                    collapsingToolbar.setTitleEnabled(false);
                    toolbar.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                    isShow = false;
                    if(pref.getResposibility()==2) {
                        if (sheetBehavior_explore.getState() != BottomSheetBehavior.STATE_EXPANDED || sheetBehavior_explore.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                            layoutBottomSheet_explore.setVisibility(View.VISIBLE);
                            sheetBehavior_explore.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }
                }
            }
        });

    }



    private class FirebaseDataListener_ride implements ValueEventListener {


        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if ((String) dataSnapshot.getValue()!=null) {
               if(!_first){
                   _first=true;
                if(pref.getOrderID()==null) {
                    pref.setiPref1((String) dataSnapshot.getValue());
                    Intent o = new Intent(SalonHomePage.this, GetBookings.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

               }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    private class FirebaseDataListener_Msg implements ValueEventListener {


        private String key;

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.getKey()!=null) {
                key = (String)dataSnapshot.getValue();

                if (key != null) {
                    if(!second) {
                        second = true;
                        cart_badge.setText(key);

                    }

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pref.getOrderID()!=null) {
            cart_badge_appointments.setText("1");
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Booking").child(pref.getOrderID()).child("SMSG").addValueEventListener(new FirebaseDataListener_Msg());
        }

        if(pref.getResposibility()==2){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Request").child(pref.getSalonPhone()).addValueEventListener(new FirebaseDataListener_ride());
        }
        if(pref.getResposibility()==1){
            _H1.setVisibility(View.GONE);
            _H2.setVisibility(View.VISIBLE);
        }else{
            _H1.setVisibility(View.VISIBLE);
            _H2.setVisibility(View.GONE);
        }
            final ArrayList<Album> mTeam = new ArrayList<>();

        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                new Response.Listener<String>() {
                    private int isVerified=0;
                    private String sunday_ots,monday_ots,sunday_cts,monday_cts,tue_open_time,tue_close_time,thr_open_time,thr_close_time,wed_open_time,wed_close_time
                            ,fri_open_time,fri_close_time,sat_open_time,sat_close_time;
                    private int Omon=0,Otue=0,Owed=0,Othr=0,Ofri=0,Osat=0,Osun=0;
                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("users");


                            JSONArray crew = jsonObj.getJSONArray("crew");


                            // looping through All Contacts
                            if (crew.length() > 0) {
                                for (int i = 0; i < crew.length(); i++) {
                                    JSONObject c = crew.getJSONObject(i);
                                    Album item1 = new Album();
                                    item1.setThumbnailUrl(c.getString("pic"));
                                    item1.setName(c.getString("name"));
                                    item1.setmobileno(c.getString("ParlourID"));
                                    item1.setCategory(c.getString("service"));
                                    mTeam.add(item1);
                                }

                            }

                            if (mTeam.size() != 0) {
                                salonspecialist.setVisibility(View.VISIBLE);
                                specialistrv.setVisibility(View.VISIBLE);
                                homepageTeamdapter sAdapter = new homepageTeamdapter(SalonHomePage.this, mTeam);
                                sAdapter.notifyDataSetChanged();
                                sAdapter.setFrom(2);
                                sAdapter.setPref(pref);
                                sAdapter.setCoordinate(coordinatorLayout);
                                specialistrv.setAdapter(sAdapter);
                                specialistrv.setLayoutManager(new LinearLayoutManager(SalonHomePage.this, LinearLayoutManager.HORIZONTAL, false));

                            } else {
                                salonspecialist.setVisibility(View.GONE);
                                specialistrv.setVisibility(View.GONE);
                            }



                            JSONArray version = jsonObj.getJSONArray("version");

                            for (int i = 0; i < version.length(); i++) {
                                JSONObject c = version.getJSONObject(i);

                                if (!c.isNull("Version")) {
                                    pref.setDate(c.getString("date"));
                                    pref.setTime(c.getString("time"));
                                }
                            }

                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            appBarLayout.setVisibility(View.VISIBLE);
                            scroller.setVisibility(View.VISIBLE);

                            if (contacts.length() > 0) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
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
                                    pref.setparlour_registration(c.getString("parlour_registration"));
                                    pref.setisVerified(c.getInt("isVerified"));

                                    pref.setPickLat(c.getString("latitude"));
                                    pref.setPickLong(c.getString("longitude"));

                                    pref.setservice_location(c.getString("service_location"));
                                    if(c.getInt("isSechedule")==1){
                                        pref.setTimings(1);
                                    }
                                    if(c.getInt("isSpecialist")==1){
                                        pref.setisSpecialist(1);
                                    }
                                    if(c.getInt("isLocation")==1){
                                        pref.setLatLong(1);
                                    }
                                    if(c.getInt("isServiceAt")==1){
                                        pref.setLocation(1);
                                    }
                                    pref.setPickLat(c.getString("latitude"));
                                    pref.setPickLong(c.getString("longitude"));
                                    isVerified=c.getInt("isVerified");
                                    pref.setcDiscount((float) c.getDouble("discountAmt"));
                                    ratingBar.setRating((float) (c.getDouble("serviceRating")));
                                    _norating.setText("("+c.getInt("serviceTotalRating")+")");
                                    if(c.getDouble("discountAmt")!=0){
                                        discount_1.setVisibility(View.VISIBLE);
                                        discount_1.setText(dft.format(c.getDouble("discountAmt"))+"% off on all services");
                                    }else {
                                        discount_1.setVisibility(View.GONE);
                                    }
                                }



                            }

                            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                            JSONArray timings = jsonObj.getJSONArray("timings");
                            SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = inFormat.parse(pref.getDate());
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.US);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            int day = calendar.get(Calendar.DAY_OF_WEEK);
                            // looping through All Contacts
                            if (timings.length() > 0) {
                                for (int i = 0; i < timings.length(); i++) {
                                    JSONObject c = timings.getJSONObject(i);
                                    if(c.getInt("sun_open_close")==1){
                                        sunday_ots=c.getString("sun_open_time");
                                        sunday_cts=c.getString("sun_close_time");
                                        Osun=1;
                                    }else{
                                        sunday_ots="CLOSED";
                                        sunday_cts="CLOSED";
                                    }
                                    if(c.getInt("mon_open_close")==1){
                                        monday_ots=c.getString("mon_open_time");
                                        monday_cts=c.getString("mon_close_time");
                                        Omon=1;
                                    }else{
                                        monday_ots="CLOSED";
                                        monday_cts="CLOSED";
                                    }
                                    if(c.getInt("tue_open_close")==1){
                                        tue_open_time=c.getString("tue_open_time");
                                        tue_close_time=c.getString("tue_close_time");
                                        Otue=1;
                                    }else{
                                        tue_open_time="CLOSED";
                                        tue_close_time="CLOSED";
                                    }
                                    if(c.getInt("wed_open_close")==1){
                                        wed_open_time=c.getString("wed_open_time");
                                        wed_close_time=c.getString("wed_close_time");
                                        Owed=1;
                                    }else{
                                        wed_open_time="CLOSED";
                                        wed_close_time="CLOSED";
                                    }
                                    if(c.getInt("thr_open_close")==1){
                                        thr_open_time=c.getString("thr_open_time");
                                        thr_close_time=c.getString("thr_close_time");
                                        Othr=1;
                                    }else{
                                        thr_open_time="CLOSED";
                                        thr_close_time="CLOSED";
                                    }
                                    if(c.getInt("fri_open_close")==1){
                                        fri_open_time=c.getString("fri_open_time");
                                        fri_close_time=c.getString("fri_close_time");
                                        Ofri=1;
                                    }else{
                                        fri_open_time="CLOSED";
                                        fri_close_time="CLOSED";
                                    }

                                    if(c.getInt("sat_open_close")==1){
                                        sat_open_time=c.getString("sat_open_time");
                                        sat_close_time=c.getString("sat_close_time");
                                        Osat=1;
                                    }else{
                                        sat_open_time="CLOSED";
                                        sat_close_time="CLOSED";
                                    }

                                }

                            }

                            switch (day) {
                                case Calendar.SUNDAY:
                                    if(Osun==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(sunday_ots);
                                        Date date4 = sdf.parse(sunday_cts);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;
                                case Calendar.MONDAY:
                                    if(Omon==1){

                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(monday_ots);
                                        Date date4 = sdf.parse(monday_cts);

                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;
                                case Calendar.TUESDAY:
                                    if(Otue==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(tue_open_time);
                                        Date date4 = sdf.parse(tue_close_time);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                case Calendar.WEDNESDAY:
                                    if(Owed==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(wed_open_time);
                                        Date date4 = sdf.parse(wed_close_time);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;
                                case Calendar.THURSDAY:
                                    if(Othr==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(thr_open_time);
                                        Date date4 = sdf.parse(thr_close_time);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;
                                case Calendar.FRIDAY:
                                    if(Ofri==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(fri_open_time);
                                        Date date4 = sdf.parse(fri_close_time);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;
                                case Calendar.SATURDAY:
                                    if(Osat==1){
                                        Date date2 = format.parse(pref.getTime());
                                        Date date3 = sdf.parse(sat_open_time);
                                        Date date4 = sdf.parse(sat_close_time);
                                        if(date2.after(date3) && date2.before(date4)){
                                            _time.setText("OPEN NOW");
                                            _time.setTextColor(getResources().getColor(R.color.green));
                                        }else {
                                            _time.setText("CLOSED");
                                            _time.setTextColor(getResources().getColor(R.color.red));
                                        }
                                    }else{
                                        _time.setText("CLOSED");
                                        _time.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    break;

                            }
                           if(isVerified==0){
                               bottomlayout.setVisibility(View.VISIBLE);
                           }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(pref.getName()!=null){
                           _name.setText(pref.getName());
                           initCollapsingToolbar();
                        }
                        if(pref.getPhoto()!=null){

                                Picasso.Builder builder = new Picasso.Builder(SalonHomePage.this);
                                builder.listener(new Picasso.Listener() {
                                    @Override
                                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                        exception.printStackTrace();
                                    }
                                });
                                builder.build().load(pref.getPhoto()).into(img_profilo);

                        }
                        if(pref.getHeaderImage()!=null){
                            Picasso.Builder builder = new Picasso.Builder(SalonHomePage.this);
                            builder.listener(new Picasso.Listener() {
                                @Override
                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                    exception.printStackTrace();
                                }
                            });
                            builder.build().load(pref.getHeaderImage()).into(Top1);
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
                params.put("role", String.valueOf(pref.getResposibility()));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);



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



    public void sendEmail(String s) {

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@groomme.co.za"});
        i.putExtra(Intent.EXTRA_SUBJECT, "ERROR");
        i.putExtra(Intent.EXTRA_TEXT, s);
        try {
            startActivity(Intent.createChooser(i, "Email us.."));
        } catch (android.content.ActivityNotFoundException ex) {

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "There are no email clients installed.", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id._i6:

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No website available for the salon", Snackbar.LENGTH_SHORT)
                        ;
                snackbar.show();
                break;

            case R.id._i7:
                Intent message = new Intent(SalonHomePage.this, InboxActivity.class);
                message.putExtra("from",1);
                startActivity(message);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;


            case R.id.Subscription:
                Intent Invoices = new Intent(SalonHomePage.this, SubscriptionActivity.class);
                startActivity(Invoices);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;




            case R.id.Inbox:
                Intent bottomi = new Intent(SalonHomePage.this, InboxActivity.class);
                startActivity(bottomi);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;



            case R.id.callus:
                String phn = "1234567890";
                Intent bottom4 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phn));
                startActivity(bottom4);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;


            case R.id.visitus:
                Intent bottom3 = new Intent(SalonHomePage.this, Wb1_access.class);
                startActivity(bottom3);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;


            case R.id.mailus:
                sendEmail("Mail us ");
                break;


            case R.id.logout:
                pref.setSalonPhone(null);
                pref.setLocation(0);
                pref.setLatLong(0);
                pref.setTimings(0);
                pref.setOrderID(null);
                pref.clearSession();
                pref.createLogin(null,null);
                pref.setSalonPhone(null);
                pref.setOrderID(null);
                pref.setResponsibility(0);
                pref.packagesharedPreferences(null);
                pref.setUserPhoneNo(null);
                pref.setRunnungParlourMobile(null);
                pref.setisRunning(0);
                pref.setActualTime(null);
                pref.set_food_money(0);
                pref.setparlour_about(null);
                pref.setAddress(null);
                pref.setTimings(0);
                pref.setEmail(null);
                pref.setPhoto(null);
                Intent bottom2 = new Intent(SalonHomePage.this, Splash_screen.class);
                startActivity(bottom2);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;


            case R.id.Appointments:
                Intent bottom1 = new Intent(SalonHomePage.this, Tabs_past_future_ride.class);
                startActivity(bottom1);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;



            case R.id._i1:
                Intent o = new Intent(SalonHomePage.this, RegisterAsSalon.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;
            case R.id._i2:
                Intent o1 = new Intent(SalonHomePage.this, StoreServiceInformation.class);
                startActivity(o1);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
                break;
            case R.id._i3:
                Intent o2 = new Intent(SalonHomePage.this, StoreSalonLocation.class);
                startActivity(o2);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
                break;
            case R.id._i4:
                Intent o3 = new Intent(SalonHomePage.this, ServiceTimings.class);
                startActivity(o3);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
                break;
            case R.id._i5:
                Intent o4 = new Intent(SalonHomePage.this, StoreSlaonSpecialist.class);
                startActivity(o4);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
                break;

            case R.id._i9:

                String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                String shareBody = "I recommend this salon "+pref.getName() ;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, url);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download Groom Me");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody+"via "+url );
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));

           break;

            case R.id._i8:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+ pref.getDropLat()+","+pref.getDropLong()+"&daddr="+pref.getPickLat()+","+pref.getPickLong()+"&mode=transit"));
                startActivity(intent);
                break;

            default:
                break;

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
            if(pref.getResposibility()==1) {
                Intent o = new Intent(SalonHomePage.this, UserHomeScreen.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else if(pref.getResposibility()==2) {
                Intent o = new Intent(SalonHomePage.this, Splash_screen.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else{
                Intent o = new Intent(SalonHomePage.this, ServiceOffer.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }



}
