package com.zanrite.groomme.UserPart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.FirstserviceAdapter;
import com.zanrite.groomme.Adapters.Mypopular;
import com.zanrite.groomme.Adapters.NearAdapter;
import com.zanrite.groomme.Adapters.Populatesalonsrv;
import com.zanrite.groomme.Adapters.SecondaryServiceAdapter;
import com.zanrite.groomme.Adapters.StylishAdapter;
import com.zanrite.groomme.Adapters.TimeslotRv;
import com.zanrite.groomme.Adapters.homepageTeamdapter;
import com.zanrite.groomme.Booking.CheckoutTime;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.zanrite.groomme.UserPart.UserHomeScreen.getScreenOrientation;

public class PopulateServices extends AppCompatActivity implements View.OnClickListener {

    Boolean isInternetPresent=false;
    private ConnectionDetector cd;
    private PrefManager pref;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView _moreRv,all_services,salonrv;
    private LinearLayout L4;
    private EditText Search_2;
    private ArrayList<String> mItems = new ArrayList<String>();
    private boolean _collapsed=false;
    private StaggeredGridLayoutManager mLayoutManager;
    private int _last=0;
    private NestedScrollView _nsScroll;
    private boolean _end=false;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
    private int hour;
    private boolean _searched=false;
    private int total=0;
    private  ArrayList<String>values=new ArrayList<String>();
    private boolean isLoading=false;
    private boolean _first=false;
    private Handler handler;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private ArrayList<String> MenuArray=new ArrayList<String>();
    private LinearLayoutManager mHorizontal;
    private EditText popular;
    private int _from;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private int _commodity=0;
    private String _Name;
    private NestedScrollView Nscroll;
    private ArrayList<Total> mParlours;
    private LinearLayout layout2;
    private NestedScrollView scroller2,scroller;
    private ListView lv;
    private Button _cancel;
    private String _userName;
    private TextView _change,primary_text;
    private AutoCompleteTextView secondary_text;
    private String _city="";
    private RecyclerView specialistrv,more,serviceoffer;
    private TextView _t1,_t2,_t3,_t0;
    private ShimmerFrameLayout mShimmerViewContainer;
    private boolean success=false;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.populateservices);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        cd=new ConnectionDetector(getApplicationContext());
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user=pref.getUserDetails2();
        _phoneNo=user.get(PrefManager.KEY_MOBILE2);
        _userName = user.get(PrefManager.KEY_NAME2);
        progressBar=findViewById(R.id.progressBar3_eats);
        coordinatorLayout=findViewById(R.id.main_content);
        Search_2=findViewById(R.id.search);
        _cancel=findViewById(R.id._cancel);
        _cancel.setOnClickListener(this);
        lv=findViewById(R.id.listView);
        collapsingToolbar=findViewById(R.id.toolbar_layout);

        secondary_text=findViewById(R.id.secondary_text);
        _change=findViewById(R.id.change);
        _change.setOnClickListener(this);
        specialistrv=findViewById(R.id.specialistrv);
        specialistrv.setNestedScrollingEnabled(false);

        more=findViewById(R.id.more);
        more.setNestedScrollingEnabled(false);
        salonrv=findViewById(R.id.salonrv);
        salonrv.setNestedScrollingEnabled(false);
        _moreRv=findViewById(R.id.moreRv);
        _moreRv.setNestedScrollingEnabled(false);

        serviceoffer=findViewById(R.id.serviceoffer);
        serviceoffer.setNestedScrollingEnabled(false);


        collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER);
        appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);

        primary_text=findViewById(R.id.primary_text);
        if(_userName!=null){
            primary_text.setText("Welcome "+_userName);
        }else{
            primary_text.setText("Welcome Guest");
        }
        mHorizontal = new LinearLayoutManager(PopulateServices.this, RecyclerView.VERTICAL, false);
        mHorizontal.setAutoMeasureEnabled(false);
        _moreRv.setLayoutManager(mHorizontal);
        layout2=findViewById(R.id.layout2);
        _t1=findViewById(R.id._t1);
        _t2=findViewById(R.id._t2);
        _t3=findViewById(R.id._t3);
        _t0=findViewById(R.id._t0);

        /*     if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(PopulateSalons.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
*/

       scroller = (NestedScrollView) findViewById(R.id._nscroll);


            scroller2 = (NestedScrollView) findViewById(R.id._nscroll2);

            if (scroller2 != null) {

                scroller2.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                        if (scrollY > oldScrollY) {
                            layout2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            Search_2.setVisibility(View.VISIBLE);
                        }

                        if (scrollY < 60) {
                            layout2.setBackgroundColor(getResources().getColor(R.color.white));
                            Search_2.setVisibility(View.VISIBLE);
                        }


                    }
                });
            }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent s11 = new Intent(PopulateServices.this, GooglemapApp.class);
                startActivity(s11);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });

        if (scroller != null) {
            scroller.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    View view = (View) scroller.getChildAt(scroller.getChildCount() - 1);

                    int diff = (view.getBottom() - (scroller.getHeight() + scroller
                            .getScrollY()));
                    Log.i("scrollY", String.valueOf(diff));
                    if (diff == 0) {
                        fab.setVisibility(View.GONE);
                    }else{
                        fab.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }


    @Override
    protected void onResume(){
        super.onResume();
        Intent o=getIntent();
        _from=o.getIntExtra("from",0);
        _Name=o.getStringExtra("name");

        if(_Name!=null){
            collapsingToolbar.setTitleEnabled(true);
            collapsingToolbar.setTitle(_Name+" SERVICES");

        }
        secondary_text.setText(pref.getparlour_locality()+","+pref.getCity());
        Search_2.setHint("Search services...");

        getEats(pref.getCity());


        Search_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()>1) {
                    _cancel.setVisibility(View.VISIBLE);
                    scroller.setVisibility(View.GONE);
                    scroller2.setVisibility(View.VISIBLE);
                     ArrayAdapter<String> adapter = null;
                     ArrayList<String>mArray=new ArrayList<>();
                    for (int i = 0; i < mParlours.size(); i++) {
                        mArray.add(mParlours.get(i).getSecondaryService(i).toUpperCase());
                        mArray.add(mParlours.get(i).getService(i).toUpperCase());
                      //  mArray.add(mParlours.get(i).getName(i).toUpperCase());

                    }
                    if (mArray.size() != 0) {
                        Set<String> set1 = new HashSet<>(mArray);
                        mArray.clear();
                        mArray.addAll(set1);
                    }
                    adapter = new ArrayAdapter<String>(PopulateServices.this, R.layout.list_item, R.id.product_name, mArray);
                    adapter.getFilter().filter(s);
                    lv.setAdapter(adapter);
                    lv.setVisibility(View.VISIBLE);}
                else {
                    _cancel.setVisibility(View.GONE);
                    if (mParlours.size() > 0) {
                        if (MenuArray.size() != 0) {
                            Set<String> set1 = new HashSet<>(MenuArray);
                            MenuArray.clear();
                            MenuArray.addAll(set1);
                        }

                        FirstserviceAdapter hadapter = new FirstserviceAdapter(PopulateServices.this, MenuArray, mParlours);
                        hadapter.notifyDataSetChanged();
                        hadapter.setFrom(1);
                        hadapter.setPref(pref);
                        hadapter.setCoordinateLayout(coordinatorLayout);
                        _moreRv.setVisibility(View.VISIBLE);
                        _moreRv.setItemAnimator(new DefaultItemAnimator());
                        _moreRv.setAdapter(hadapter);
                        _moreRv.setItemViewCacheSize(10);
                        _moreRv.setHasFixedSize(true);
                        _moreRv.setDrawingCacheEnabled(true);
                        _moreRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                        scroller.setVisibility(View.VISIBLE);
                        scroller2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final ArrayList<Total> filteredModelList = new ArrayList<>();

                Set<Total> set = new HashSet<>(mParlours);
                mParlours.clear();
                mParlours.addAll(set);
                for (int i = 0; i < mParlours.size(); i++) {
                    Total model = mParlours.get(i);
                    final String text = model.getName(i).toLowerCase();
                    String text2= (String) parent.getItemAtPosition(position);
                    if (text.equals(text2.toLowerCase())) {
                        filteredModelList.add(model);

                    } else {
                        final String text1 = model.getService(i).toLowerCase();
                        if (text1.equals(text2.toLowerCase())) {
                            if(filteredModelList.size()>0){
                                boolean _p=false;
                                for (int j = 0; j < filteredModelList.size(); j++) {
                                    if (filteredModelList.get(j).getName(i).toLowerCase().equals(model.getName(i).toLowerCase())) {
                                        _p=true;
                                        break;
                                    }
                                }
                                if(!_p){
                                    filteredModelList.add(model);
                                }
                            }else{
                                filteredModelList.add(model);
                            }


                        }else {
                            final String text3 = model.getSecondaryService(i).toLowerCase();
                            if (text3.equals(text2.toLowerCase())) {
                                if(filteredModelList.size()>0){
                                    boolean _p=false;
                                    for (int j = 0; j < filteredModelList.size(); j++) {
                                        if (filteredModelList.get(j).getName(i).toLowerCase().equals(model.getName(i).toLowerCase())) {
                                           _p=true;
                                           break;
                                        }
                                    }
                                    if(!_p){
                                        filteredModelList.add(model);
                                    }
                                }else{
                                    filteredModelList.add(model);
                                }

                            }
                        }
                    }
                }
                if (filteredModelList.size() != 0) {
                    Populatesalonsrv hadapter = new Populatesalonsrv(PopulateServices.this, filteredModelList);
                    hadapter.notifyDataSetChanged();
                    hadapter.setPref(pref);
                    salonrv.setVisibility(View.VISIBLE);
                    salonrv.setItemAnimator(new DefaultItemAnimator());
                    salonrv.setAdapter(hadapter);
                    salonrv.setItemViewCacheSize(10);
                    salonrv.setHasFixedSize(true);
                    salonrv.setDrawingCacheEnabled(true);
                    salonrv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    LinearLayoutManager mHorizontal = new LinearLayoutManager(PopulateServices.this, RecyclerView.HORIZONTAL, false);
                    salonrv.setLayoutManager(mHorizontal);

                } else {
                    Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();


                }

            }
        });


    }





    private void getEats(final String city) {
        mParlours = new ArrayList<Total>();
        MenuArray.clear();
        final ArrayList<Slots> mSLot = new ArrayList<Slots>();
            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_PARTICULAR_SERVICES,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            final ArrayList<Album> mTeam = new ArrayList<>();
                            final ArrayList<Total> populars = new ArrayList<Total>();
                            Log.w("populateservice", response);

                            _t0.setText(_Name.toLowerCase()+" services near "+city.toLowerCase());

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray items = jsonObj.getJSONArray("service");
                                        if (items.length() != 0) {
                                            for (int i = 0; i < items.length(); i++) {
                                                JSONObject c = items.getJSONObject(i);
                                                if (!c.isNull("parlour_name")) {
                                                    Total item = new Total();
                                                    MenuArray.add(c.getString("Name"));
                                                    item.setThumbnailUrl(c.getString("Photo"));
                                                    item.setName(c.getString("parlour_name"));
                                                    item.setID(c.getInt("ID"));
                                                    item.setService(c.getString("Name"));
                                                    item.setAddress(c.getString("parlour_address"));
                                                    item.setSecondaryService(c.getString("SecondaryService"));
                                                    item.setLatitude(c.getDouble("latitude"));
                                                    item.setLongitude(c.getDouble("longitude"));
                                                    item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                                    item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                                    item.setmobileno(c.getString("ParlourID"));
                                                    mParlours.add(item);
                                                }

                                            }
                                        }

                                JSONArray items1 = jsonObj.getJSONArray("secondaryservice");
                                if (items1.length() != 0) {
                                    for (int i = 0; i < items1.length(); i++) {
                                        JSONObject c = items1.getJSONObject(i);
                                        if (!c.isNull("Service")) {
                                            Slots item = new Slots();
                                            item.setSlots(c.getString("Service").toUpperCase());
                                            item.setID(c.getInt("ID"));
                                            mSLot.add(item);
                                        }

                                    }
                                }

                                JSONArray parlours = jsonObj.getJSONArray("popsalons");
                                if(pref.getDistance()!=0){

                                    for (int i = 0; i < parlours.length(); i++) {
                                        JSONObject c = parlours.getJSONObject(i);
                                        int k= (int) pref.getDistance();
                                        if (pref.getDropLat() != null) {
                                            double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"), c.getDouble("longitude")), new LatLng(Double.parseDouble(pref.getDropLat()), Double.parseDouble(pref.getDropLong()))) / 1000;
                                            if (dist2 < pref.getDistance() ) {
                                                Total item = new Total();
                                                item.setThumbnailUrl(c.getString("Photo"));
                                                item.setName(c.getString("parlour_name"));
                                                item.setmobileno(c.getString("parlour_mobile"));
                                                item.setID(c.getInt("ID"));
                                                item.setAddress(c.getString("parlour_address"));
                                                item.setLatitude(c.getDouble("latitude"));
                                                item.setLongitude(c.getDouble("longitude"));
                                                item.setDiscount(c.getDouble("discountAmt"));
                                                item.setDistance(dist2);
                                                item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                                item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                                populars.add(item);
                                            }
                                        }
                                    }

                                }

                                if(populars.size()!=0){
                                    _t2.setVisibility(View.VISIBLE);
                                    more.setVisibility(View.VISIBLE);
                                    _t2.setText("Popular salons near "+city.toLowerCase()+" ("+String.valueOf(populars.size())+")");
                                    Collections.shuffle(populars);
                                    Mypopular sAdapter4 = new Mypopular(PopulateServices.this, populars);
                                    sAdapter4.notifyDataSetChanged();
                                    sAdapter4.setPref(pref);
                                    sAdapter4.setCoordinate(coordinatorLayout);
                                    sAdapter4.setHasStableIds(true);
                                    more.setAdapter(sAdapter4);
                                    more.setHasFixedSize(true);
                                    LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(PopulateServices.this, RecyclerView.HORIZONTAL, false);
                                    more.setLayoutManager(horizontalLayoutManagae);
                                    more.setItemAnimator(new DefaultItemAnimator());

                                }else{
                                    _t2.setVisibility(View.GONE);
                                    more.setVisibility(View.GONE);
                                }

                                JSONArray crew = jsonObj.getJSONArray("mostpopular");
                                if (crew.length() > 0) {
                                    for (int i = 0; i < crew.length(); i++) {
                                        JSONObject c = crew.getJSONObject(i);
                                        Album item1 = new Album();
                                        item1.setThumbnailUrl(c.getString("Photo"));
                                        item1.setName(c.getString("crew_name"));
                                        item1.setmobileno(c.getString("ParlourID"));
                                        item1.setCategory(c.getString("service"));
                                        mTeam.add(item1);
                                    }
                                }


                            } catch (final JSONException e) {
                                Log.e("HI", "Json parsing error: " + e.getMessage());
                            }

                            if (mSLot.size() != 0 ) {
                                _t0.setVisibility(View.VISIBLE);
                                serviceoffer.setVisibility(View.VISIBLE);
                                SecondaryServiceAdapter sAdapter = new SecondaryServiceAdapter(PopulateServices.this, mSLot);
                                sAdapter.notifyDataSetChanged();
                                serviceoffer.setAdapter(sAdapter);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(PopulateServices.this, RecyclerView.HORIZONTAL, false);
                                serviceoffer.setLayoutManager(horizontalLayoutManagae);
                                serviceoffer.addOnItemTouchListener(
                                        new RecyclerTouchListener(PopulateServices.this, serviceoffer,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        final ArrayList<String> mName=new ArrayList<String>();
                                                        final ArrayList<Total> filteredModelList = new ArrayList<>();
                                                        final ArrayList<Album> filterCrew = new ArrayList<Album>();
                                                        Set<Total> set = new HashSet<>(mParlours);
                                                        mParlours.clear();
                                                        mParlours.addAll(set);
                                                        _t3.setVisibility(View.GONE);
                                                        _moreRv.setVisibility(View.GONE);
                                                        _t2.setText("Salons with "+mSLot.get(position).getSlots(position).toLowerCase()+" near "+city.toLowerCase());
                                                        for (int i = 0; i < mParlours.size(); i++) {
                                                            Total model = mParlours.get(i);
                                                            final String text = model.getSecondaryService(i).toLowerCase();
                                                            final String text2 =mSLot.get(position).getSlots(position).toLowerCase();
                                                            if (text.contains(text2)) {
                                                                filteredModelList.add(model);
                                                                mName.add(model.getService(i));

                                                            }
                                                        }

                                                        for (int i = 0; i < mTeam.size(); i++) {
                                                            Album model = mTeam.get(i);
                                                            final String text = model.getCategory(i).toLowerCase();
                                                            final String text2 =mSLot.get(position).getSlots(position).toLowerCase();
                                                            if (text.contains(text2)) {
                                                                filterCrew.add(model);

                                                            }
                                                        }

                                                        if (filterCrew.size() != 0) {
                                                            specialistrv.setVisibility(View.VISIBLE);
                                                            homepageTeamdapter sAdapter = new homepageTeamdapter(PopulateServices.this, filterCrew);
                                                            sAdapter.notifyDataSetChanged();
                                                            sAdapter.setFrom(1);
                                                            sAdapter.setPref(pref);
                                                            sAdapter.setCoordinate(coordinatorLayout);
                                                            specialistrv.setAdapter(sAdapter);
                                                            specialistrv.setLayoutManager(new LinearLayoutManager(PopulateServices.this, LinearLayoutManager.HORIZONTAL, false));
                                                        }


                                                        if (filteredModelList.size() != 0) {
                                                            if (mName.size() != 0) {
                                                                Set<String> set1 = new HashSet<>(mName);
                                                                mName.clear();
                                                                mName.addAll(set1);
                                                            }

                                                            NearAdapter adapter = new NearAdapter(PopulateServices.this, filteredModelList);
                                                            adapter.notifyDataSetChanged();
                                                            adapter.setPref(pref);
                                                            adapter.setCoordinate(coordinatorLayout);
                                                            _moreRv.setAdapter(adapter);
                                                            _moreRv.setNestedScrollingEnabled(false);
                                                            LinearLayoutManager layoutManager4
                                                                    = new LinearLayoutManager(PopulateServices.this, LinearLayoutManager.VERTICAL, false);
                                                            _moreRv.setLayoutManager(layoutManager4);
                                                            _moreRv.setItemAnimator(new DefaultItemAnimator());
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();


                                                        }

                                                    }

                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));
                            }else{
                                _t0.setVisibility(View.GONE);
                                serviceoffer.setVisibility(View.GONE);
                            }
                            if (mParlours.size() > 0) {
                                _t3.setText("More salons near "+city.toLowerCase()+" ("+String.valueOf(mParlours.size())+")");
                                _t3.setVisibility(View.VISIBLE);
                                _moreRv.setVisibility(View.VISIBLE);
                                if(MenuArray.size()!=0) {
                                    Set<String> set = new HashSet<>(MenuArray);
                                    MenuArray.clear();
                                    MenuArray.addAll(set);
                                }
                                success=true;
                                    FirstserviceAdapter hadapter = new FirstserviceAdapter(PopulateServices.this,MenuArray, mParlours);
                                    hadapter.notifyDataSetChanged();
                                    hadapter.setFrom(1);
                                    hadapter.setPref(pref);
                                    hadapter.setCoordinateLayout(coordinatorLayout);
                                    _moreRv.setVisibility(View.VISIBLE);
                                    _moreRv.setItemAnimator(new DefaultItemAnimator());
                                    _moreRv.setAdapter(hadapter);
                                    _moreRv.setItemViewCacheSize(10);
                                    _moreRv.setHasFixedSize(true);
                                    _moreRv.setDrawingCacheEnabled(true);
                                    _moreRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


                            }else{
                                _t3.setVisibility(View.GONE);
                                _moreRv.setVisibility(View.GONE);
                            }
                            if (mTeam.size() != 0) {
                                _t1.setText("Specialist near "+city.toLowerCase()+" ("+String.valueOf(mTeam.size())+")");
                                _t1.setVisibility(View.VISIBLE);
                                specialistrv.setVisibility(View.VISIBLE);
                                homepageTeamdapter sAdapter = new homepageTeamdapter(PopulateServices.this, mTeam);
                                sAdapter.notifyDataSetChanged();
                                sAdapter.setFrom(1);
                                sAdapter.setPref(pref);
                                sAdapter.setCoordinate(coordinatorLayout);
                                specialistrv.setAdapter(sAdapter);
                                specialistrv.setLayoutManager(new LinearLayoutManager(PopulateServices.this, LinearLayoutManager.HORIZONTAL, false));
                            }else{
                                _t1.setVisibility(View.GONE);
                                specialistrv.setVisibility(View.GONE);
                            }
                            if(success) {
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);
                                appBarLayout.setVisibility(View.VISIBLE);
                                scroller.setVisibility(View.VISIBLE);
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", String.valueOf(_from));
                    params.put("city", String.valueOf(city.toUpperCase()));
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(eventoReq);



    }










    public int getSpan() {
        int orientation = getScreenOrientation(getApplicationContext());
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(PopulateServices.this, UserHomeScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.w("STOP","STOP");
        _moreRv.setVisibility(View. GONE);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        _moreRv.setVisibility(View. GONE);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id._cancel) {
            Search_2.setText("");
        }
        if(view.getId()==R.id.change){
            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,  Config_URL.GET_LOCATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.w("populateservice", response);
                            final ArrayList<String > mCity = new ArrayList<>();
                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray items = jsonObj.getJSONArray("salons");
                                if (items.length() != 0) {
                                    for (int i = 0; i < items.length(); i++) {
                                        JSONObject c = items.getJSONObject(i);
                                        if(!TextUtils.isEmpty(c.getString("city"))) {
                                            mCity.add(c.getString("city").toUpperCase());
                                        }
                                        if(!TextUtils.isEmpty(c.getString("locality"))) {
                                            mCity.add(c.getString("locality").toUpperCase());
                                        }

                                    }
                                }


                            } catch (final JSONException e) {
                                Log.e("HI", "Json parsing error: " + e.getMessage());
                            }

                            if(mCity.size()>0) {
                                Set<String> set1 = new HashSet<>(mCity);
                                mCity.clear();
                                mCity.addAll(set1);
                                mCity.trimToSize();
                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                        getApplicationContext(), android.R.layout.simple_list_item_1, mCity) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                                        text.setTextColor(Color.WHITE);
                                        return view;
                                    }
                                };
                                secondary_text.setAdapter(arrayAdapter);
                                secondary_text.setCursorVisible(false);
                                secondary_text.showDropDown();
                                secondary_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String _city1 = (String) parent.getItemAtPosition(position);
                                        getEats(_city1);


                                    }
                                });
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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", String.valueOf(_from));
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(eventoReq);
        }
    }

    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
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
}






