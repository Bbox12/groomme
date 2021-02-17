package com.zanrite.groomme.UserPart;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.andreilisun.swipedismissdialog.OnSwipeDismissListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDirection;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Activities.ServiceOffer;
import com.zanrite.groomme.Activities.ServiceTimings;
import com.zanrite.groomme.Activities.Splash_screen;
import com.zanrite.groomme.Adapters.Image_Adapter;
import com.zanrite.groomme.Adapters.My_work_adapter_service;
import com.zanrite.groomme.Adapters.Mypopular;
import com.zanrite.groomme.Adapters.NearAdapter;
import com.zanrite.groomme.Adapters.SelfieAdapter;
import com.zanrite.groomme.Adapters.StylishAdapter;
import com.zanrite.groomme.Adapters._CommodityAdapter;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.Booking.Success;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.HistoryData.Tabs_past_future_ride;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/**
 * Created by parag on 19/06/17.
 */

public class UserHomeScreen extends YouTubeBaseActivity implements View.OnClickListener, Animation.AnimationListener, YouTubePlayer.OnInitializedListener{

    private static final String TAG = UserHomeScreen.class.getSimpleName();
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
    private static final float POLYLINE_WIDTH = 8;
    private static final int RECOVERY_DIALOG_REQUEST = 101;
    private final int REQUEST_LOCATION = 200;
    double My_lat, My_long;
    private GoogleApiClient mGoogleApiClient;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private String strAdd;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private RecyclerView Stylist_card;
    private String getMsg;
    private LinearLayoutManager staggeredGridLayoutManagerHorizontal_5;
    private String USER,PhoneNo;
    private PrefManager pref;
    private ConnectionDetector cd;
    private boolean isInternetPresent;
    private ProgressBar progressBar;
    private double Latitude = 0;
    private double Longitude = 0;
    Animation animFadein;
    Animation animFadeout;
    private ArrayList<Total> movieList=new ArrayList<Total>();
    private Button Go_forum;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RelativeLayout layoutBottomSheet_explore;
    private BottomSheetBehavior sheetBehavior_explore;
    private ImageView _i1,_i2,_i3,_i4,_i5;
    private int _from=0;
    private TextView _FromService;
    private RecyclerView mainService,allService,popularService,allBeauticians;
    private Button  booknow5, booknow6, booknow7, booknow8;
    private NetworkImageView  _sc5, _sc6, _sc7, _sc8,_ri1,_ri2,_ri3,_ri4;
    private TextView  _nc5, _nc6, _nc7, _nc8,r1,r2,r3,r4;
    private ImageLoader imageLoader;
    private TextView Price1, Discount1, DiscountP1, Price2, Discount2, DiscountP2, Price3, Discount3, DiscountP3, Price4, Discount4, DiscountP4;
    private Button  _newDeal;
    private Button _newItems;
    private Button _recommend;
    private Button topCategories,_cancel;
    private RecyclerView topCategoriesRv;
    private TextView _cname;
    private EditText _search2;
    private RelativeLayout layout2;
    private int currentPage=0;
    private RecyclerView _conn,_only;
    private int _popMobile1,_popMobile2,_popMobile3,_popMobile4;
    private int _newMobile1,_newMobile2,_newMobile3,_newMobile4;
    private Button _salonservice;
    private Button _bothservice,_allB;
    private TextView _timer;
    private LinearLayout ltimer;
    long MillisecondTime, StartTime, EndTime, TimeBuff, UpdateTime = 0L;
    private Handler handler;
    private int Seconds, Minutes, MilliSeconds, Hour;
    private TextView _pname1,_pname2,_pname3,_pname4;
    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean success=false;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private boolean _first=false,second=false;
    private TextView textCartItemCount;
    private double mCartItemCount=0;
    private NestedScrollView scroller,scroller2;
    private TextView orders,_rate1,_rate2,_rate3,_rate4;
    private RecyclerView salonRv,specialistRv,serviceRv,deralofthedayrv;
    private RelativeLayout newlaunch,recommend,_rsex;
    private YouTubePlayerView youTubeView;
    private TextView _titleYoutube;
    private LinearLayout _deals;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(this);
        setContentView(R.layout.userhomepage);
        youTubeView =  findViewById(R.id.youtube_view);
        youTubeView.initialize(Config_URL.APIKEY, this);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        coordinatorLayout=findViewById(R.id.cor_home_main);
        toolbar = findViewById(R.id.toolbardd);
        _titleYoutube=findViewById(R.id._titleYoutube);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        Stylist_card= (RecyclerView) findViewById(R.id.stylist_rv);
        progressBar =findViewById(R.id.progressBarSplash);
        popularService=findViewById(R.id.more);
        popularService.setNestedScrollingEnabled(false);
        topCategoriesRv=findViewById(R.id.topCategoriesRv);
        topCategoriesRv.setNestedScrollingEnabled(false);
        topCategories=findViewById(R.id.topCategories);
        topCategories.setOnClickListener(this);
        pref=new PrefManager(this);
        layout2=findViewById(R.id.layout2);
        HashMap<String, String> user = pref.getUserDetails2();
        USER = user.get(PrefManager.KEY_NAME2);
        PhoneNo=user.get(PrefManager.KEY_MOBILE2);
        layoutBottomSheet_explore = findViewById(R.id.bottom_sheet_explore);
        sheetBehavior_explore = BottomSheetBehavior.from(layoutBottomSheet_explore);
      _cname=findViewById(R.id._cname);
        deralofthedayrv=findViewById(R.id.deralofthedayrv);
        deralofthedayrv.setNestedScrollingEnabled(false);

        newlaunch=findViewById(R.id.newlaunch);
        _deals=findViewById(R.id.deal);
        recommend=findViewById(R.id.recommend);
        _cancel=findViewById(R.id._cancel);
        _cancel.setOnClickListener(this);

        orders=findViewById(R.id.orders);

        _allB=findViewById(R.id._allB);
        _allB.setOnClickListener(this);

        _pname1=findViewById(R.id.parlour_name1);
        _pname2=findViewById(R.id.parlour_name2);
        _pname3=findViewById(R.id.parlour_name3);
        _pname4=findViewById(R.id.parlour_name4);
        _rate1=findViewById(R.id._rate1);
        _rate2=findViewById(R.id._rate2);
        _rate3=findViewById(R.id._rate3);
        _rate4=findViewById(R.id._rate4);
        lv = findViewById(R.id.listView);
        salonRv = findViewById(R.id.salonrv);
        salonRv.setNestedScrollingEnabled(false);
        specialistRv = findViewById(R.id.specialistrv);
        specialistRv.setNestedScrollingEnabled(false);
        serviceRv = findViewById(R.id.service);
        serviceRv.setNestedScrollingEnabled(false);


        _salonservice=findViewById(R.id._salonservice);
        Button _homeservice = findViewById(R.id._homeservice);
        _bothservice=findViewById(R.id._bothservice);
        _rsex=findViewById(R.id._rsex);

        _timer=findViewById(R.id._timer);
        ltimer=findViewById(R.id.ltimer);

        if(pref.getSex()==1){
            _rsex.setVisibility(View.VISIBLE);
        }else{
            _rsex.setVisibility(View.GONE);
        }

        _salonservice.setOnClickListener(this);
        _homeservice.setOnClickListener(this);
        _bothservice.setOnClickListener(this);

        sheetBehavior_explore.setHideable(true);
        sheetBehavior_explore.setPeekHeight(0);
        layoutBottomSheet_explore.setVisibility(View.VISIBLE);
        sheetBehavior_explore.setState(BottomSheetBehavior.STATE_EXPANDED);

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



        //initCollapsingToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PhoneNo==null){
                    Intent i3 = new Intent(UserHomeScreen.this, ServiceOffer.class);
                    startActivity(i3);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                }else {
                    Snackbar snackbar1 = Snackbar
                            .make(coordinatorLayout, "Are you Sure to exit?", Snackbar.LENGTH_LONG)
                            .setAction("Exit", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                    finish();
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();
                }

            }
        });



        booknow5 = findViewById(R.id.booknow5);
        booknow6 = findViewById(R.id.booknow6);
        booknow7 = findViewById(R.id.booknow7);
        booknow8 = findViewById(R.id.booknow8);

        booknow5.setOnClickListener(this);
        booknow6.setOnClickListener(this);
        booknow7.setOnClickListener(this);
        booknow8.setOnClickListener(this);



        _sc5=findViewById(R.id.image_5);
        _sc6=findViewById(R.id.image_6);
        _sc7=findViewById(R.id.image_7);
        _sc8=findViewById(R.id.image_8);


        _ri1 = findViewById(R.id._ri1);
        _ri2 = findViewById(R.id._ri2);
        _ri3 = findViewById(R.id._ri3);
        _ri4 = findViewById(R.id._ri4);

        _ri1.setOnClickListener(this);
        _ri2.setOnClickListener(this);
        _ri3.setOnClickListener(this);
        _ri4.setOnClickListener(this);


        _sc6 = findViewById(R.id.image_6);
        _sc7 = findViewById(R.id.image_7);
        _sc8 = findViewById(R.id.image_8);
        _nc5 = findViewById(R.id.name_5);
        _nc6 = findViewById(R.id.name_6);
        _nc7 = findViewById(R.id.name_7);
        _nc8 = findViewById(R.id.name_8);
        r1 = findViewById(R.id._r1);
        r2 = findViewById(R.id._r2);
        r3 = findViewById(R.id._r3);
        r4 = findViewById(R.id._r4);

        _search2 = findViewById(R.id.search);
        collapsingToolbar =
                findViewById(R.id.toolbar_layout);

        collapsingToolbar.setTitleEnabled(false);
        appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);

        if(pref.getYoutubeTitile()!=null){
            _titleYoutube.setText(pref.getYoutubeTitile());
        }
       scroller = (NestedScrollView) findViewById(R.id._nscroll);

        if (scroller != null) {

            scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        toolbar.setVisibility(View.GONE);
                        _cname.setVisibility(View.GONE);
                        layout2.setBackgroundColor(getResources().getColor(R.color.black));
                        layoutBottomSheet_explore.setVisibility(View.VISIBLE);
                        sheetBehavior_explore.setState(BottomSheetBehavior.STATE_EXPANDED);
                        _search2.setVisibility(View.VISIBLE);
                    }

                    if (scrollY < 60) {
                        toolbar.setVisibility(View.VISIBLE);
                        _cname.setVisibility(View.VISIBLE);
                        layout2.setBackgroundColor(Color.TRANSPARENT);
                        _search2.setVisibility(View.VISIBLE);
                        layoutBottomSheet_explore.setVisibility(View.GONE);
                        sheetBehavior_explore.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }



                }
            });
        }

        scroller2 = (NestedScrollView) findViewById(R.id._nscroll2);

        if (scroller2 != null) {

            scroller2.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY > oldScrollY) {
                        toolbar.setVisibility(View.GONE);
                        _cname.setVisibility(View.GONE);
                      //  layout2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        _search2.setVisibility(View.VISIBLE);
                    }

                    if (scrollY < 60) {
                        toolbar.setVisibility(View.VISIBLE);
                        _cname.setVisibility(View.VISIBLE);
                      //  layout2.setBackgroundColor(getResources().getColor(R.color.white));
                        _search2.setVisibility(View.VISIBLE);
                    }



                }
            });
        }

        _newDeal = findViewById(R.id._newDeals);
        Button _newPopular = findViewById(R.id._newPopular);
        _newPopular.setOnClickListener(this);
        _newDeal.setOnClickListener(this);
         _recommend = findViewById(R.id._recommend);
        _recommend.setOnClickListener(this);
        _conn = findViewById(R.id.cooms);
        _conn.setNestedScrollingEnabled(false);
        _only = findViewById(R.id.onlyservice);
        _only.setNestedScrollingEnabled(false);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {

            if(pref.getYoutube()!=null) {
                if(pref.getYoutubeCategory()==1){
                    youTubePlayer.loadVideo(pref.getYoutube());
                }else{
                    youTubePlayer.loadPlaylist(pref.getYoutube());
                }

                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.valueOf(errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }



    @Override
    public void onStart() {
        super.onStart();

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(UserHomeScreen.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }

    private class FirebaseDataListener_Msg implements ValueEventListener {


        private String key;
        DecimalFormat dft = new DecimalFormat("0");

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if ((String)dataSnapshot.getValue()!=null) {
                Log.w("hello", "dataSnapshot, " + dataSnapshot.getChildrenCount());

                key = (String)dataSnapshot.getValue();

                mCartItemCount= 1;
                if (key != null) {
                    if(!second) {
                        second = true;
                       CustomNotification("New message from "+pref.getCanteen());
                       setupBadge();

                    }

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

    public void CustomNotification(String durationo) {
        Uri alarmSound = Uri.parse("android.resource://"
                + UserHomeScreen.this.getPackageName() + "/" + R.raw.hollow);


        String strtitle = durationo;


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        PendingIntent pendingIntent;
        if(pref.getPaymentMode()==0 && pref.getOrderID()!=null){
            Intent ii = new Intent(getApplicationContext(), CheckOut.class);
             pendingIntent = PendingIntent.getActivity(UserHomeScreen.this, 0, ii, 0);
        }else {
            Intent ii = new Intent(getApplicationContext(), UserHomeScreen.class);
             pendingIntent = PendingIntent.getActivity(UserHomeScreen.this, 0, ii, 0);
        }

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle(strtitle);
        bigText.setSummaryText("Groomme");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(strtitle);
        mBuilder.setContentText(strtitle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setSound(alarmSound);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager = (NotificationManager) UserHomeScreen.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_001";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    strtitle,
                    NotificationManager.IMPORTANCE_HIGH);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }

            mBuilder.setChannelId(channelId);

        }
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
        }
    }
    private class FirebaseDataListener_ride implements ValueEventListener {


        private String key;

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.getKey()!=null) {
                Log.w("hello", "dataSnapshot, " + dataSnapshot.getChildrenCount());

                        key = (String)dataSnapshot.getValue();
                if (key != null && key.contains("2")) {
                    if(!_first) {
                        _first = true;
                        CustomNotification("Booking ID "+pref.getOrderID()+" has been accepted by "+pref.getCanteen());

                    }

                }
                if (key != null && key.contains("3")) {
                    if(!_first) {
                        _first = true;
                        CustomNotification("Booking ID "+pref.getOrderID()+" has started. Please enjoy your makeover.");

                    }

                }
                if (key != null && key.contains("4")) {
                    if(!_first) {
                        _first = true;

                        Intent s12 = new Intent(UserHomeScreen.this, Success.class);
                        startActivity(s12);
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

    @Override
    public void onResume() {
        super.onResume();

        isInternetPresent = cd.isConnectingToInternet();
          if(pref.getOrderID()!=null) {
                  orders.setText("1");
                  DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                  mDatabase.child("Booking").child(pref.getOrderID()).child("STATUS").addValueEventListener(new FirebaseDataListener_ride());
                  mDatabase.child("Booking").child(pref.getOrderID()).child("SMSG").addValueEventListener(new FirebaseDataListener_Msg());

          }else{
              orders.setText("0");
              pref.set_food_items(0);
              pref.set_food_money(0);
              pref.setPref1(null);
              pref.setPref2(null);
              pref.setPref3(null);
              pref.setPref4(null);
              pref.setnoOfItems(null);
              pref.setServiceAt(0);
              pref.packagesharedPreferences(null);
              pref.setActualTime(null);
          }

          go();





    }

    private void go() {
        final ArrayList<Total> mService=new ArrayList<>();
        final ArrayList<Total> mDeals = new ArrayList<Total>();
        final ArrayList<Total> mNew = new ArrayList<Total>();
        final ArrayList<Total> populars = new ArrayList<Total>();
        final ArrayList<Total> mPopularStylist = new ArrayList<Total>();
        final ArrayList<Total>mSelfie=new ArrayList<Total>();
        final ArrayList<Total>mAllstylish=new ArrayList<Total>();
        final ArrayList<Total>mCategory=new ArrayList<Total>();
        final ArrayList<String> mName = new ArrayList<String>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_ALL_PARLOURS_HOMEPAGE,
                new Response.Listener<String>() {

                    private String _Feedback;
                    private String _noofsalons;

                    @Override
                    public void onResponse(String response) {

                        Log.w("mainlayout", response);

                        try {


                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray parlours = jsonObj.getJSONArray("parlours");
                            for (int i = 0; i < parlours.length(); i++) {
                                JSONObject c = parlours.getJSONObject(i);
                                success=true;
                                _noofsalons=(c.getString("noofsalon"));
                                Total item = new Total();
                                item.setThumbnailUrl("http://139.59.38.160/Groom/App/icon/ic_logo.png");
                                item.setName("SALONS");
                                item.setID(1001);
                                item.setNoofItems(Integer.parseInt(_noofsalons));
                                pref.setDistance((float) c.getDouble("distance"));
                                mService.add(item);

                            }




                            JSONArray selfie = jsonObj.getJSONArray("selfie");
                            for (int i = 0; i < selfie.length(); i++) {
                                JSONObject c = selfie.getJSONObject(i);
                                if (!c.isNull("Photo")) {
                                    Total item = new Total();
                                    item.setThumbnailUrl(c.getString("Photo"));
                                    mName.add(c.getString("parlour_name"));
                                    item.setName(c.getString("parlour_name"));
                                    item.setID(c.getInt("ID"));
                                    mSelfie.add(item);
                                }
                            }
                            JSONArray primaryservices = jsonObj.getJSONArray("primaryservices");
                            for (int i = 0; i < primaryservices.length(); i++) {
                                JSONObject c = primaryservices.getJSONObject(i);
                                if (!c.isNull("Name")) {
                                    Total item = new Total();
                                    item.setThumbnailUrl(c.getString("Photo"));
                                    item.setName(c.getString("Name"));
                                    mName.add(c.getString("Name"));
                                    item.setID(c.getInt("ID"));
                                    mService.add(item);
                                }
                            }
                            JSONArray popular = jsonObj.getJSONArray("popular");
                            for (int i = 0; i < popular.length(); i++) {
                                JSONObject c = popular.getJSONObject(i);
                                if (!c.isNull("parlour_name")) {
                                    Total item = new Total();
                                    item.setThumbnailUrl(c.getString("Photo"));
                                    item.setName(c.getString("parlour_name"));
                                    mName.add(c.getString("parlour_name"));
                                    item.setID(c.getInt("ID"));
                                    item.setmobileno(c.getString("parlour_mobile"));
                                    item.setAddress(c.getString("parlour_address"));
                                    item.setLatitude(c.getDouble("latitude"));
                                    item.setLongitude(c.getDouble("longitude"));
                                    item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                    item.setRate_t(String.valueOf(c.getInt("serviceTotalRating")));
                                    populars.add(item);
                                }
                            }
                            JSONArray newSalon = jsonObj.getJSONArray("new");
                            for (int i = 0; i < newSalon.length(); i++) {
                                JSONObject c = newSalon.getJSONObject(i);
                                if (!c.isNull("parlour_name")) {
                                    Total item = new Total();
                                    item.setThumbnailUrl(c.getString("Photo"));
                                    item.setName(c.getString("parlour_name"));
                                    mName.add(c.getString("parlour_name"));
                                    item.setID(c.getInt("ID"));
                                    item.setmobileno(c.getString("parlour_mobile"));
                                    item.setAddress(c.getString("parlour_address"));
                                    item.setLatitude(c.getDouble("latitude"));
                                    item.setLongitude(c.getDouble("longitude"));
                                    item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                    item.setRate_t(String.valueOf(c.getInt("serviceTotalRating")));
                                    mNew.add(item);
                                }
                            }


                            JSONArray dealItem = jsonObj.getJSONArray("deals");
                            if (dealItem.length() != 0) {
                                for (int i = 0; i < dealItem.length(); i++) {
                                    JSONObject c = dealItem.getJSONObject(i);
                                    if (!c.isNull("Photo")) {
                                        Total item = new Total();
                                        item.setThumbnailUrl(c.getString("Photo"));
                                        item.setName(c.getString("parlour_name"));
                                        mName.add(c.getString("parlour_name"));
                                        item.setID(c.getInt("ID"));
                                        item.setmobileno(c.getString("parlour_mobile"));
                                        item.setDiscount(c.getDouble("discountAmt"));
                                        item.setLatitude(c.getDouble("latitude"));
                                        item.setLongitude(c.getDouble("longitude"));
                                        item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                        item.setRate_t(String.valueOf(c.getInt("serviceTotalRating")));
                                        mDeals.add(item);
                                    }
                                }
                            }

                            JSONArray pstylist = jsonObj.getJSONArray("allStylist");
                            for (int k = 0; k < pstylist.length(); k++) {
                                JSONObject c = pstylist.getJSONObject(k);
                                if (!c.isNull("Photo")) {
                                    Total item1 = new Total();
                                    item1.setThumbnailUrl(c.getString("Photo"));
                                    item1.setName(c.getString("crew_name"));
                                    mName.add(c.getString("crew_name"));
                                    item1.setmobileno(c.getString("ParlourID"));
                                    item1.setServices(c.getString("service"));
                                    item1.setID(c.getInt("ID"));
                                    item1.setParlours(c.getString("parlour_name"));
                                    item1.setRate_i(c.getString("Rating"));
                                    item1.setRate_t(c.getString("TotalRating"));
                                    mAllstylish.add(item1);

                                }
                            }
                            JSONArray category = jsonObj.getJSONArray("category");
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject c = category.getJSONObject(i);
                                if (!c.isNull("Photo")) {
                                    Total item = new Total();
                                    item.setThumbnailUrl(c.getString("Photo"));
                                    item.setName(c.getString("Category"));
                                    item.setID(c.getInt("ID"));
                                    mCategory.add(item);
                                }
                            }

                            if (mCategory.size() > 0 && pref.getLadies()==1) {
                                _only.setVisibility(View.VISIBLE);
                                _CommodityAdapter sAdapter1 = new _CommodityAdapter(UserHomeScreen.this, mCategory);
                                sAdapter1.notifyDataSetChanged();
                                sAdapter1.setHasStableIds(true);
                                _only.setAdapter(sAdapter1);
                                _only.setHasFixedSize(true);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(UserHomeScreen.this, RecyclerView.HORIZONTAL, false);
                                _only.setLayoutManager(horizontalLayoutManagae);
                                _only.setItemAnimator(new DefaultItemAnimator());
                               // _only.smoothScrollToPosition(sAdapter1.getItemCount());

                            }else {
                                _only.setVisibility(View.GONE);
                            }

                            DecimalFormat dft = new DecimalFormat("0");
                            if (mDeals.size() > 0) {
                                Collections.shuffle(mDeals);
                                Image_Adapter hadapter = new Image_Adapter(UserHomeScreen.this,mDeals);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(UserHomeScreen.this, LinearLayoutManager.VERTICAL, false);
                                hadapter.notifyDataSetChanged();
                                hadapter.setPref(pref);
                                deralofthedayrv.setVisibility(View.VISIBLE);
                                deralofthedayrv.setItemAnimator(new DefaultItemAnimator());
                                deralofthedayrv.setAdapter(hadapter);
                                deralofthedayrv.setLayoutManager(mHorizontal);
                            }else{
                                _deals.setVisibility(View.GONE);
                            }
                            if(mService.size()!=0){

                                My_work_adapter_service adapter = new My_work_adapter_service(UserHomeScreen.this, mService);
                                adapter.notifyDataSetChanged();
                                adapter.setName(1);
                                adapter.setPref(pref);
                                adapter.setCoordinate(coordinatorLayout);
                                topCategoriesRv.setAdapter(adapter);
                                topCategoriesRv.setNestedScrollingEnabled(false);
                                LinearLayoutManager layoutManager4
                                        = new LinearLayoutManager(UserHomeScreen.this, LinearLayoutManager.HORIZONTAL, false);
                                topCategoriesRv.setLayoutManager(layoutManager4);
                                topCategoriesRv.setItemAnimator(new DefaultItemAnimator());

                            }

                            if(mAllstylish.size()!=0){
                                Collections.shuffle(mAllstylish);
                                StylishAdapter adapter = new StylishAdapter(UserHomeScreen.this, mAllstylish);
                                adapter.notifyDataSetChanged();
                                adapter.setPref(pref);
                                adapter.setCoordinate(coordinatorLayout);
                                adapter.User(USER);
                                Stylist_card.setAdapter(adapter);
                                Stylist_card.setNestedScrollingEnabled(false);
                                LinearLayoutManager layoutManager4
                                        = new LinearLayoutManager(UserHomeScreen.this, LinearLayoutManager.HORIZONTAL, false);
                                Stylist_card.setLayoutManager(layoutManager4);
                                Stylist_card.setItemAnimator(new DefaultItemAnimator());
                            }

                            if(mSelfie.size()!=0){
                                Collections.shuffle(mSelfie);
                                SelfieAdapter sAdapter4 = new SelfieAdapter(UserHomeScreen.this, mSelfie);
                                sAdapter4.notifyDataSetChanged();
                                sAdapter4.setHasStableIds(true);
                                _conn.setAdapter(sAdapter4);
                                _conn.setHasFixedSize(true);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(UserHomeScreen.this, RecyclerView.HORIZONTAL, false);
                                _conn.setLayoutManager(horizontalLayoutManagae);
                                _conn.setItemAnimator(new DefaultItemAnimator());
                                _conn.addOnItemTouchListener(
                                        new RecyclerTouchListener(UserHomeScreen.this, _conn,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));

                                                        open_logout(mSelfie.get(position).getThumbnailUrl(position));
                                                    }

                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));
                            }

                            if(populars.size()!=0){
                                Collections.shuffle(populars);
                                Mypopular sAdapter4 = new Mypopular(UserHomeScreen.this, populars);
                                sAdapter4.notifyDataSetChanged();
                                sAdapter4.setPref(pref);
                                sAdapter4.setCoordinate(coordinatorLayout);
                                sAdapter4.setHasStableIds(true);
                                popularService.setAdapter(sAdapter4);
                                popularService.setHasFixedSize(true);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(UserHomeScreen.this, RecyclerView.HORIZONTAL, false);
                                popularService.setLayoutManager(horizontalLayoutManagae);
                                popularService.setItemAnimator(new DefaultItemAnimator());

                            }

                            if (mNew.size() > 3) {
                                Collections.shuffle(mNew);
                                for (int i = 0; i < mNew.size(); i++) {
                                    if (mNew.get(0).getThumbnailUrl(0) != null) {
                                        String url = mNew.get(0).getThumbnailUrl(0).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_sc5,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _sc5.setImageUrl(url, imageLoader);
                                        _nc5.setText(mNew.get(0).getName(0));
                                        _newMobile1=mNew.get(0).getID(0);

                                    }

                                    if (mNew.get(1).getThumbnailUrl(1) != null) {
                                        String url = mNew.get(1).getThumbnailUrl(1).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_sc6,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _sc6.setImageUrl(url, imageLoader);
                                        _nc6.setText(mNew.get(1).getName(1));
                                        _newMobile2=mNew.get(1).getID(1);

                                    }
                                    if (mNew.get(2).getThumbnailUrl(2) != null) {
                                        String url = mNew.get(2).getThumbnailUrl(2).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_sc7,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _sc7.setImageUrl(url, imageLoader);
                                        _nc7.setText(mNew.get(2).getName(2));
                                        _newMobile3=mNew.get(2).getID(2);
                                    }
                                    if (mNew.get(3).getThumbnailUrl(3) != null) {
                                        String url = mNew.get(3).getThumbnailUrl(3).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_sc8,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _sc8.setImageUrl(url, imageLoader);
                                        _nc8.setText(mNew.get(3).getName(3));
                                        _newMobile4=mNew.get(3).getID(3);

                                    }
                                }
                            }else{
                                newlaunch.setVisibility(View.GONE);
                            }

                            JSONArray allStylist = jsonObj.getJSONArray("popularStylist");
                            for (int k = 0; k < allStylist.length(); k++) {
                                JSONObject c = allStylist.getJSONObject(k);
                                if (!c.isNull("Photo")) {
                                    Total item1 = new Total();
                                    item1.setThumbnailUrl(c.getString("Photo"));
                                    item1.setName(c.getString("crew_name"));
                                    item1.setmobileno(c.getString("ParlourID"));
                                    item1.setServices(c.getString("service"));
                                    item1.setParlours(c.getString("parlour_name"));
                                    item1.setRate_i(c.getString("Rating"));
                                    item1.setRate_t(c.getString("TotalRating"));
                                    mPopularStylist.add(item1);

                                }
                            }


                            if (mPopularStylist.size() > 3) {
                                Collections.shuffle(mPopularStylist);
                                for (int i = 0; i < mPopularStylist.size(); i++) {
                                    if (mPopularStylist.get(0).getThumbnailUrl(0) != null) {
                                        String url = mPopularStylist.get(0).getThumbnailUrl(0).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_ri1,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _ri1.setImageUrl(url, imageLoader);
                                        r1.setText(mPopularStylist.get(0).getName(0));
                                        _popMobile1= Integer.parseInt(mPopularStylist.get(0).getmobileno(0));
                                        _pname1.setText(mPopularStylist.get(0).getParlours(0));
                                        _rate1.setText(mPopularStylist.get(0).getRate_i(0));
                                    }

                                    if (mPopularStylist.get(1).getThumbnailUrl(1) != null) {
                                        String url = mPopularStylist.get(1).getThumbnailUrl(1).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_ri2,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _ri2.setImageUrl(url, imageLoader);
                                        r2.setText(mPopularStylist.get(1).getName(1));
                                        _popMobile2= Integer.parseInt(mPopularStylist.get(1).getmobileno(1));
                                        _pname2.setText(mPopularStylist.get(1).getParlours(1));
                                        _rate2.setText(mPopularStylist.get(1).getRate_i(1));

                                    }
                                    if (mPopularStylist.get(2).getThumbnailUrl(2) != null) {
                                        String url = mPopularStylist.get(2).getThumbnailUrl(2).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_ri3,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _ri3.setImageUrl(url, imageLoader);
                                        r3.setText(mPopularStylist.get(2).getName(2));
                                        _popMobile3= Integer.parseInt(mPopularStylist.get(2).getmobileno(2));
                                        _pname3.setText(mPopularStylist.get(2).getParlours(2));
                                        _rate3.setText(mPopularStylist.get(2).getRate_i(2));
                                    }
                                    if (mPopularStylist.get(3).getThumbnailUrl(3) != null) {
                                        String url = mPopularStylist.get(3).getThumbnailUrl(3).replaceAll(" ", "%20");
                                        imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                                                .getImageLoader();
                                        imageLoader.get(url, ImageLoader.getImageListener(_ri4,
                                                R.mipmap.ic_launcher, R.mipmap
                                                        .ic_launcher));
                                        _ri4.setImageUrl(url, imageLoader);
                                        r4.setText(mPopularStylist.get(3).getName(3));
                                        _popMobile3= Integer.parseInt(mPopularStylist.get(3).getmobileno(3));
                                        _pname4.setText(mPopularStylist.get(3).getParlours(3));
                                        _rate4.setText(mPopularStylist.get(3).getRate_i(3));
                                    }
                                }
                            }else{
                                recommend.setVisibility(View.GONE);
                            }
                            if (mName.size() != 0) {
                                Set<String> set = new HashSet<>(mName);
                                mName.clear();
                                mName.addAll(set);
                            }


                            adapter = new ArrayAdapter<String>(UserHomeScreen.this, R.layout.list_item, R.id.product_name, mName);
                            _search2.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence s, int start,
                                                          int before, int count) {
                                    if(s.toString().length()>1) {
                                        _cancel.setVisibility(View.VISIBLE);
                                        scroller.setVisibility(View.GONE);
                                        scroller2.setVisibility(View.VISIBLE);
                                        adapter.getFilter().filter(s);
                                        lv.setAdapter(adapter);
                                        lv.setVisibility(View.VISIBLE);

                                    }else{
                                        _cancel.setVisibility(View.GONE);
                                        scroller2.setVisibility(View.GONE);
                                        lv.setVisibility(View.GONE);
                                        scroller.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                }


                            });


                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                private NearAdapter nearadapter;

                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final ArrayList<Total> filteredModelList = new ArrayList<>();
                                    _search2.clearFocus();
                                    InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    in.hideSoftInputFromWindow(_search2.getWindowToken(), 0);
                                    lv.setVisibility(View.GONE);
                                    scroller2.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < mDeals.size(); i++) {
                                        Total model = mDeals.get(i);
                                        final String text = model.getName(i).toLowerCase();
                                        String value = (String) parent.getItemAtPosition(position);
                                        if (text.contains(value.toLowerCase())) {
                                            filteredModelList.add(model);
                                        }
                                    }
                                    if(filteredModelList.size()==0) {
                                        for (int i = 0; i < mNew.size(); i++) {
                                            Total model = mNew.get(i);
                                            final String text = model.getName(i).toLowerCase();
                                            String value = (String) parent.getItemAtPosition(position);
                                            if (text.contains(value.toLowerCase())) {
                                                filteredModelList.add(model);
                                            }
                                        }
                                    }
                                    if(filteredModelList.size()==0) {
                                        for (int i = 0; i < populars.size(); i++) {
                                            Total model = populars.get(i);
                                            final String text = model.getName(i).toLowerCase();
                                            String value = (String) parent.getItemAtPosition(position);
                                            if (text.contains(value.toLowerCase())) {
                                                filteredModelList.add(model);
                                            }
                                        }
                                    }
                                    if(filteredModelList.size()==0) {
                                        for (int i = 0; i < mNew.size(); i++) {
                                            Total model = mNew.get(i);
                                            final String text = model.getName(i).toLowerCase();
                                            String value = (String) parent.getItemAtPosition(position);
                                            if (text.contains(value.toLowerCase())) {
                                                filteredModelList.add(model);
                                            }
                                        }
                                    }
                                    if(filteredModelList.size()!=0) {

                                        nearadapter = new NearAdapter(UserHomeScreen.this, filteredModelList);
                                        nearadapter.notifyDataSetChanged();
                                        nearadapter.setLatLong(new LatLng(Double.parseDouble(pref.getDropLat()), Double.parseDouble(pref.getDropLong())));
                                        nearadapter.setPref(pref);
                                        nearadapter.setCoordinate(coordinatorLayout);
                                        specialistRv.setAdapter(nearadapter);
                                        specialistRv.setNestedScrollingEnabled(false);
                                        LinearLayoutManager layoutManager4
                                                = new LinearLayoutManager(UserHomeScreen.this, LinearLayoutManager.HORIZONTAL, false);
                                        specialistRv.setLayoutManager(layoutManager4);
                                        specialistRv.setItemAnimator(new DefaultItemAnimator());
                                    }

                                    final ArrayList<Total> filteredModelList2 = new ArrayList<>();


                                    for (int i = 0; i < mAllstylish.size(); i++) {
                                        Total model = mAllstylish.get(i);
                                        final String text = model.getName(i).toLowerCase();
                                        String value = (String) parent.getItemAtPosition(position);
                                        if (text.contains(value.toLowerCase())) {
                                            filteredModelList2.add(model);
                                        } else {
                                            final String text1 = model.getServices(i).toLowerCase();
                                            String value1 = (String) parent.getItemAtPosition(position);
                                            if (text1.contains(value1.toLowerCase())) {
                                                filteredModelList2.add(model);
                                            }
                                        }

                                    }

                                    if(filteredModelList2.size()!=0) {
                                        StylishAdapter adapter2 = new StylishAdapter(UserHomeScreen.this, filteredModelList2);
                                        adapter2.notifyDataSetChanged();
                                        adapter2.setPref(pref);
                                        specialistRv.setAdapter(adapter2);
                                        specialistRv.setNestedScrollingEnabled(false);
                                        LinearLayoutManager layoutManager5
                                                = new LinearLayoutManager(UserHomeScreen.this, LinearLayoutManager.HORIZONTAL, false);
                                        specialistRv.setLayoutManager(layoutManager5);
                                        specialistRv.setItemAnimator(new DefaultItemAnimator());
                                    }
                                }
                            });

                            if(success) {
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                appBarLayout.setVisibility(View.VISIBLE);
                                scroller.setVisibility(View.VISIBLE);
                                layoutBottomSheet_explore.setVisibility(View.VISIBLE);


                            }

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
                params.put("from", String.valueOf(1));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    private void open_logout(String _filePath) {

        if(!UserHomeScreen.this.isFinishing()  ) {

            SwipeDismissDialog.Builder builder = new SwipeDismissDialog.Builder(UserHomeScreen.this);

            LayoutInflater inflater = LayoutInflater.from(UserHomeScreen.this);
            View dialogView = inflater.inflate(R.layout.full_image, null);

            // Set the custom layout as alert dialog view
            builder.setView(dialogView);


            NetworkImageView _Profile = dialogView.findViewById(R.id.full_image_view);
            ImageLoader imageLoader = LruBitmapCache.getInstance(UserHomeScreen.this)
                    .getImageLoader();
            imageLoader.get(_filePath, ImageLoader.getImageListener(_Profile,
                    R.mipmap.ic_launcher, R.mipmap
                            .ic_launcher));
            _Profile.setImageUrl(_filePath, imageLoader);
            // Create the alert dialog
            final SwipeDismissDialog dialog = builder.setOnSwipeDismissListener(new OnSwipeDismissListener() {
                @Override
                public void onSwipeDismiss(View view, SwipeDismissDirection direction) {

                }
            })
                    .build();


            dialog.show();
        }

    }


    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            success=false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            go();
        } else if (error instanceof AuthFailureError) {
            success=false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            go();
        } else if (error instanceof ServerError) {
            success=false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            go();
        } else if (error instanceof NetworkError) {
            success=false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            go();
        } else if (error instanceof ParseError) {
            success=false;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            go();
        }
    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id._cancel:
              _search2.setText("");
                break;


            case R.id._i1:


                Intent _share = new Intent(UserHomeScreen.this, Refer_and_Earn_page.class);
                startActivity(_share);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;

            case R.id._ri1:
                if (_popMobile1 != 0) {
                    pref.createLogin(String.valueOf(_popMobile1),"");
                    nextScreen();
                }

                break;
            case R.id._ri2:
                if (_popMobile2 != 0) {
                    pref.createLogin(String.valueOf(_popMobile2),"");
                    nextScreen();
                }

                break;

            case R.id._ri3:
                if (_popMobile3 != 0) {
                    pref.createLogin(String.valueOf(_popMobile3),"");
                    nextScreen();
                }
                break;
            case R.id._ri4:
                if (_popMobile4 != 0) {
                    pref.createLogin(String.valueOf(_popMobile4),"");
                    nextScreen();
                }

                break;


            case R.id._homeservice:


                Intent s11 = new Intent(UserHomeScreen.this, GooglemapApp.class);
                s11.putExtra("home",1);
                startActivity(s11);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            case R.id._salonservice:
                Intent s12 = new Intent(UserHomeScreen.this, GooglemapApp.class);
                s12.putExtra("home",2);
                startActivity(s12);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            case R.id._bothservice:


                Intent s13 = new Intent(UserHomeScreen.this, GooglemapApp.class);
                s13.putExtra("home",3);
                startActivity(s13);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;




            case R.id.booknow5:
                if(_newMobile1!=0) {
                    pref.createLogin(String.valueOf(_newMobile1),_nc5.getText().toString());
                    nextScreen();
                }

                break;
            case R.id.booknow6:
                if(_newMobile2!=0) {
                    pref.createLogin(String.valueOf(_newMobile2),_nc6.getText().toString());
                    nextScreen();
                }

                break;

            case R.id.booknow7:
                if(_newMobile3!=0) {
                    pref.createLogin(String.valueOf(_newMobile3),_nc7.getText().toString());
                    nextScreen();
                }


                break;
            case R.id.booknow8:
                if(_newMobile4!=0) {
                    pref.createLogin(String.valueOf(_newMobile4),_nc8.getText().toString());
                    nextScreen();
                }

                break;

            case R.id.topCategories:

                Intent ou = new Intent(UserHomeScreen.this, UserTopCategories.class);
                startActivity(ou);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

                break;

            case R.id._newDeals:
                Intent pop = new Intent(UserHomeScreen.this, PopulateSalons.class);
                pref.setID4(1);
                startActivity(pop);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;

            case R.id._newPopular:

                Intent popu = new Intent(UserHomeScreen.this, PopulateSalons.class);
                pref.setID4(2);
                startActivity(popu);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

                break;
            case R.id._recommend:

                Intent popr = new Intent(UserHomeScreen.this, PopulateSalons.class);
                pref.setID4(3);
                startActivity(popr);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

                break;
            case R.id._allB:

                Intent allb = new Intent(UserHomeScreen.this, PopulateSalons.class);
                pref.setID4(4);
                startActivity(allb);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

                break;
            case R.id._i5:
                Intent acc = new Intent(UserHomeScreen.this, Account_settings.class);
                startActivity(acc);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
                break;
            case R.id._i2:
                Intent near = new Intent(UserHomeScreen.this, GooglemapApp.class);
                startActivity(near);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();

                break;
            case R.id._i3:

                 Snackbar snackbar1 = Snackbar
                            .make(coordinatorLayout, "Are you Sure?", Snackbar.LENGTH_LONG)
                            .setAction("Logout", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pref.setSalonPhone(null);
                                    pref.setLocation(0);
                                    pref.setLatLong(0);
                                    pref.setTimings(0);
                                    pref.setOrderID(null);
                                    pref.clearSession();
                                    pref.createLogin(null,null);
                                    pref.createLogin2(null,null);
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
                                    Intent i3 = new Intent(UserHomeScreen.this, Splash_screen.class);
                    startActivity(i3);
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    finish();
                                }
                            });
                    snackbar1.setActionTextColor(Color.RED);
                    snackbar1.show();


                break;
            case R.id._i4:
                if(PhoneNo!=null) {
                Intent i4 = new Intent(UserHomeScreen.this, Tabs_past_future_ride.class);
                startActivity(i4);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
        }else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Registration required", Snackbar.LENGTH_LONG)
                            .setAction("Register", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i3 = new Intent(UserHomeScreen.this, Account_settings.class);
                                    startActivity(i3);
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                    finish();
                                }
                            });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }
                break;

        }

    }

    private void nextScreen() {
        if(pref.getResposibility()==1) {
            Intent o = new Intent(UserHomeScreen.this, SalonHomePage.class);
            startActivity(o);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
        }else{
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Registration required", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Register", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i3 = new Intent(UserHomeScreen.this, Account_settings.class);
                            startActivity(i3);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            finish();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
    }


    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public int getSpan() {
        int orientation = getScreenOrientation(getApplicationContext());
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_message);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);



        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;

    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(mCartItemCount));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_message: {
                Intent i3 = new Intent(UserHomeScreen.this, InboxActivity.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
            }
            break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            pref.setSalonPhone(null);
            if(PhoneNo==null){
                Intent i3 = new Intent(UserHomeScreen.this, ServiceOffer.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                finish();
            }else {
                Snackbar snackbar1 = Snackbar
                        .make(coordinatorLayout, "Are you Sure to exit?", Snackbar.LENGTH_LONG)
                        .setAction("Exit", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                finish();
                            }
                        });
                snackbar1.setActionTextColor(Color.RED);
                snackbar1.show();
            }

        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
      //  pref.setSalonPhone(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // pref.setSalonPhone(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // pref.setSalonPhone(null);
    }



}
