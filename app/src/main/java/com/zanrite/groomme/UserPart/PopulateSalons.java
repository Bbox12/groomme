package com.zanrite.groomme.UserPart;

import android.app.UiModeManager;
import android.content.Context;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Adapters.Mypopular;
import com.zanrite.groomme.Adapters.NearAdapter;
import com.zanrite.groomme.Adapters.Populatesalonsrv;
import com.zanrite.groomme.Adapters.StylishAdapter;
import com.zanrite.groomme.Adapters.homepageTeamdapter;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;

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

public class PopulateSalons extends AppCompatActivity implements View.OnClickListener{

    Boolean isInternetPresent=false;
    private ConnectionDetector cd;
    private PrefManager pref;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView _moreRv;
    private LinearLayout L4;
    private EditText Search_2;
    final ArrayList<Total>mParlours = new ArrayList<Total>();
    final ArrayList<Total>mAllstylish=new ArrayList<Total>();
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
    private Button _cancel;
    public static final int PAGE_START = 1;
    private LinearLayoutManager mHorizontal;
    private int _from;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private int _commodity=0;
    private String _Name;
    private NestedScrollView Nscroll;
    private TextView _moreName,_change,primary_text;
    private LinearLayout layout2;
    private AutoCompleteTextView secondary_text;
    private String _city="";
    private RecyclerView specialistrv,more;
    private TextView _t1,_t2, _t3;
    private String _userName;
    private ShimmerFrameLayout mShimmerViewContainer;
    private NestedScrollView scroller;
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
        Search_2.setHint("Search salons");
        _moreName=findViewById(R.id.top);
        progressBar.setVisibility(View.VISIBLE);
        layout2=findViewById(R.id.layout2);
        _change=findViewById(R.id.change);
        _change.setOnClickListener(this);

        _moreRv=findViewById(R.id.moreRv);
        _moreRv.setNestedScrollingEnabled(false);

        secondary_text=findViewById(R.id.secondary_text);

        specialistrv=findViewById(R.id.specialistrv);
        specialistrv.setNestedScrollingEnabled(false);

        more=findViewById(R.id.more);
        more.setNestedScrollingEnabled(false);

        _t1=findViewById(R.id._t1);
        _t2=findViewById(R.id._t2);
        _t3=findViewById(R.id._t3);
        _cancel=findViewById(R.id._cancel);
        _cancel.setOnClickListener(this);
        primary_text=findViewById(R.id.primary_text);
        if(_userName!=null){
            primary_text.setText("Welcome "+_userName);
        }else{
            primary_text.setText("Welcome Guest");
        }

        collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setExpandedTitleGravity(Gravity.CENTER);
        appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);

        mHorizontal = new LinearLayoutManager(PopulateSalons.this, RecyclerView.VERTICAL, false);
        mHorizontal.setAutoMeasureEnabled(false);
        _moreRv.setLayoutManager(mHorizontal);



        collapsingToolbar=findViewById(R.id.toolbar_layout);
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


         fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent s11 = new Intent(PopulateSalons.this, GooglemapApp.class);
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
        _from=pref.getID4();
        _Name=o.getStringExtra("name");
        if(_from<3) {
            Search_2.setHint("Search salons");
        }else {
            Search_2.setHint("Search specialist");
        }
          /*  collapsingToolbar.setTitleEnabled(true);
            if(_from==0) {
                collapsingToolbar.setTitle("Salons");
            }else  if(_from==1) {
                collapsingToolbar.setTitle("Best Deals");
            }else  if(_from==2) {
                collapsingToolbar.setTitle("Popular Salons");
            }else  if(_from==3) {
                collapsingToolbar.setTitle("Popular Specialist");
            }else  if(_from==4) {
                collapsingToolbar.setTitle("Specialist");
            }*/

        secondary_text.setText(pref.getparlour_locality()+","+pref.getCity());

        getEats(pref.getCity());

        Search_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()>1) {

                    if(_from<3) {
                    final ArrayList<Total> filteredModelList = new ArrayList<>();
                        _cancel.setVisibility(View.VISIBLE);
                    Set<Total> set = new HashSet<>(mParlours);
                    mParlours.clear();
                    mParlours.addAll(set);
                    for (int i = 0; i < mParlours.size(); i++) {
                        Total model = mParlours.get(i);
                        final String text = model.getName(i).toLowerCase();
                        if (text.contains(String.valueOf(s).toLowerCase())) {
                            filteredModelList.add(model);
                        }
                    }
                    if (filteredModelList.size() != 0) {
                        Populatesalonsrv adapter = new Populatesalonsrv(PopulateSalons.this, filteredModelList);
                        adapter.notifyDataSetChanged();
                        adapter.setPref(pref);
                        adapter.setDeals(_from);
                        adapter.setCoordinate(coordinatorLayout);
                        _moreRv.setAdapter(adapter);
                        _moreRv.setNestedScrollingEnabled(false);
                        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                        _moreRv.setLayoutManager(mLayoutManager);
                        _moreRv.setItemAnimator(new DefaultItemAnimator());
                    } else {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();

                    }

                    }else{
                        final ArrayList<Total> filteredModelList = new ArrayList<>();
                        _cancel.setVisibility(View.GONE);
                        Set<Total> set = new HashSet<>(mAllstylish);
                        mAllstylish.clear();
                        mAllstylish.addAll(set);
                        for (int i = 0; i < mAllstylish.size(); i++) {
                            Total model = mAllstylish.get(i);
                            final String text = model.getName(i).toLowerCase();
                            if (text.contains(String.valueOf(s).toLowerCase())) {
                                filteredModelList.add(model);
                            } else {
                                final String text1 = model.getServices(i).toLowerCase();
                                if (text1.contains(String.valueOf(s).toLowerCase())) {
                                    filteredModelList.add(model);
                                }
                            }
                        }
                        if (filteredModelList.size() != 0) {
                            StylishAdapter adapter = new StylishAdapter(PopulateSalons.this, filteredModelList);
                            adapter.notifyDataSetChanged();
                            adapter.setPref(pref);
                            adapter.setCoordinate(coordinatorLayout);
                            _moreRv.setAdapter(adapter);
                            _moreRv.setNestedScrollingEnabled(false);
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                            _moreRv.setLayoutManager(mLayoutManager);
                            _moreRv.setItemAnimator(new DefaultItemAnimator());
                        } else {
                            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();

                        }
                    }}else{
                    if(_from<3) {

                        if (mParlours.size() > 0) {
                            _moreRv.setVisibility(View.VISIBLE);
                            Populatesalonsrv adapter = new Populatesalonsrv(PopulateSalons.this, mParlours);
                            adapter.notifyDataSetChanged();
                            adapter.setDeals(_from);
                            adapter.setPref(pref);
                            adapter.setCoordinate(coordinatorLayout);
                            _moreRv.setAdapter(adapter);
                            _moreRv.setNestedScrollingEnabled(false);
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                            _moreRv.setLayoutManager(mLayoutManager);
                            _moreRv.setItemAnimator(new DefaultItemAnimator());
                        } else {

                            if (!PopulateSalons.this.isFinishing()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PopulateSalons.this, R.style.AlertDialogTheme)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setTitle("Coming soom")
                                        .setMessage("No salon available for now. Will be available soon. Please come back later.")
                                        .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(PopulateSalons.this, UserHomeScreen.class);
                                                startActivity(i);
                                                finishAffinity();
                                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                                dialog.cancel();
                                            }
                                        });

                                builder.show();
                            }
                        }
                    }else {
                        if(mAllstylish.size()!=0){
                            _moreRv.setVisibility(View.VISIBLE);
                            StylishAdapter adapter = new StylishAdapter(PopulateSalons.this, mAllstylish);
                            adapter.notifyDataSetChanged();
                            adapter.setPref(pref);
                            adapter.setCoordinate(coordinatorLayout);
                            _moreRv.setAdapter(adapter);
                            _moreRv.setNestedScrollingEnabled(false);
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                            _moreRv.setLayoutManager(mLayoutManager);
                            _moreRv.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getEats(final String city) {
     mParlours.clear();
     mAllstylish.clear();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_PARTICULAR_SERVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("populateservice", response);
                        final ArrayList<Album> mTeam = new ArrayList<>();
                        final ArrayList<Total> populars = new ArrayList<Total>();
                        progressBar.setVisibility(View.GONE);
                        try {
                          //  success=true;
                            JSONObject jsonObj = new JSONObject(response);
                            if(_from<3) {
                                JSONArray items = jsonObj.getJSONArray("salons");
                                if (items.length() != 0) {
                                    for (int i = 0; i < items.length(); i++) {
                                        JSONObject c = items.getJSONObject(i);
                                        if (!c.isNull("parlour_name")) {
                                            success=true;
                                            Total item = new Total();
                                            item.setThumbnailUrl(c.getString("Photo"));
                                            item.setName(c.getString("parlour_name"));
                                            item.setmobileno(c.getString("parlour_mobile"));
                                            item.setID(c.getInt("ID"));
                                            item.setAddress(c.getString("parlour_address"));
                                            item.setLatitude(c.getDouble("latitude"));
                                            item.setLongitude(c.getDouble("longitude"));
                                            item.setDiscount(c.getDouble("discountAmt"));
                                            if(pref.getDropLat()!=null) {
                                                double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"), c.getDouble("longitude")), new LatLng(Double.parseDouble(pref.getDropLat()), Double.parseDouble(pref.getDropLong()))) / 1000;
                                                item.setDistance(dist2);
                                            }
                                            item.setRate_i(String.valueOf(c.getDouble("serviceRating")));
                                            item.setRate_t(String.valueOf(c.getDouble("serviceTotalRating")));
                                            mParlours.add(item);
                                        }

                                    }
                                }
                            }
                           if(_from==3) {
                               JSONArray pstylist = jsonObj.getJSONArray("popularStylist");
                               for (int k = 0; k < pstylist.length(); k++) {
                                   JSONObject c = pstylist.getJSONObject(k);
                                   if (!c.isNull("Photo")) {
                                       Total item1 = new Total();
                                       item1.setThumbnailUrl(c.getString("Photo"));
                                       item1.setName(c.getString("crew_name"));
                                       item1.setmobileno(c.getString("ParlourID"));
                                       item1.setServices(c.getString("service"));
                                       item1.setID(c.getInt("ID"));
                                       item1.setRate_i(c.getString("Rating"));
                                       item1.setRate_t(c.getString("TotalRating"));
                                       mAllstylish.add(item1);

                                   }
                               }
                           }else if(_from==4) {
                               JSONArray pstylist = jsonObj.getJSONArray("allStylist");
                               for (int k = 0; k < pstylist.length(); k++) {
                                   JSONObject c = pstylist.getJSONObject(k);
                                   if (!c.isNull("Photo")) {
                                       Total item1 = new Total();
                                       item1.setThumbnailUrl(c.getString("Photo"));
                                       item1.setName(c.getString("crew_name"));
                                       item1.setmobileno(c.getString("ParlourID"));
                                       item1.setServices(c.getString("service"));
                                       item1.setID(c.getInt("ID"));
                                       item1.setParlours(c.getString("parlour_name"));
                                       item1.setRate_i(c.getString("Rating"));
                                       item1.setRate_t(c.getString("TotalRating"));
                                       mAllstylish.add(item1);

                                   }
                               }
                           }

                            JSONArray crew = jsonObj.getJSONArray("crew");
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


                            JSONArray parlours = jsonObj.getJSONArray("salons");
                            if(pref.getDistance()!=0){

                                for (int i = 0; i < parlours.length(); i++) {
                                    JSONObject c = parlours.getJSONObject(i);
                                    if (pref.getDropLat() != null) {
                                        double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(c.getDouble("latitude"), c.getDouble("longitude")), new LatLng(Double.parseDouble(pref.getDropLat()), Double.parseDouble(pref.getDropLong()))) / 1000;
                                        if (dist2 < pref.getDistance() && c.getDouble("serviceTotalRating")!=0) {
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
                                if(_from==1){
                                    _t2.setText("Popular salons  with great deals near "+city.toLowerCase()+" ("+String.valueOf(populars.size())+")");
                                }else {
                                    _t2.setText("Popular salons near " + city.toLowerCase() + " (" + String.valueOf(populars.size()) + ")");
                                }
                                Collections.shuffle(populars);
                                Mypopular sAdapter4 = new Mypopular(PopulateSalons.this, populars);
                                sAdapter4.notifyDataSetChanged();
                                sAdapter4.setPref(pref);
                                sAdapter4.setCoordinate(coordinatorLayout);
                                sAdapter4.setHasStableIds(true);
                                more.setAdapter(sAdapter4);
                                more.setHasFixedSize(true);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(PopulateSalons.this, RecyclerView.HORIZONTAL, false);
                                more.setLayoutManager(horizontalLayoutManagae);
                                more.setItemAnimator(new DefaultItemAnimator());

                            }else {
                                _t2.setVisibility(View.GONE);
                                more.setVisibility(View.GONE);
                            }
                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());
                        }






                        if(_from<3) {
                            if (mParlours.size()> 0) {
                                if(_from==1) {
                                    _t3.setText("More salons with great deals near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")");

        }else{
                                    _t3.setText("More salons near " + city.toLowerCase() + " (" + String.valueOf(mParlours.size()) + ")");

                                }
                                _t3.setVisibility(View.VISIBLE);
                                if(_from==2){
                                    _t3.setVisibility(View.GONE);
                                    _moreRv.setVisibility(View.GONE);
                                }else{
                                    _moreRv.setVisibility(View.VISIBLE);
                                }

                                NearAdapter adapter = new NearAdapter(PopulateSalons.this, mParlours);
                                adapter.notifyDataSetChanged();
                                adapter.setPref(pref);
                                adapter.setCoordinate(coordinatorLayout);
                                _moreRv.setAdapter(adapter);
                                _moreRv.setNestedScrollingEnabled(false);
                                LinearLayoutManager layoutManager4
                                        = new LinearLayoutManager(PopulateSalons.this, LinearLayoutManager.VERTICAL, false);
                                _moreRv.setLayoutManager(layoutManager4);
                                _moreRv.setItemAnimator(new DefaultItemAnimator());
                            }else {
                                _t3.setVisibility(View.GONE);
                                _moreRv.setVisibility(View.GONE);
                            }
                        }else {
                            if(mAllstylish.size()!=0){
                                _moreRv.setVisibility(View.VISIBLE);
                                _t3.setVisibility(View.VISIBLE);
                               if(_from==4){
                                    _t3.setText("More specialist near "+city.toLowerCase()+" ("+String.valueOf(populars.size())+")");
                                }else {
                                   _t3.setText("Popular  specialist near "+city.toLowerCase()+" ("+String.valueOf(populars.size())+")");
                               }
                                StylishAdapter adapter = new StylishAdapter(PopulateSalons.this, mAllstylish);
                                adapter.notifyDataSetChanged();
                                adapter.setPref(pref);
                                adapter.setCoordinate(coordinatorLayout);
                                _moreRv.setAdapter(adapter);
                                _moreRv.setNestedScrollingEnabled(false);
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                _moreRv.setLayoutManager(mLayoutManager);
                                _moreRv.setItemAnimator(new DefaultItemAnimator());
                            }
                        }
                        if (mTeam.size() != 0) {
                            success=true;
                            _t1.setText("Specialist near "+city.toLowerCase()+" ("+String.valueOf(mTeam.size())+")");
                            _t1.setVisibility(View.VISIBLE);
                            if(_from==4 || _from==3 ) {
                                _t1.setVisibility(View.GONE);
                                specialistrv.setVisibility(View.GONE);
                            }else {
                                specialistrv.setVisibility(View.VISIBLE);
                            }
                            homepageTeamdapter sAdapter = new homepageTeamdapter(PopulateSalons.this, mTeam);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setFrom(1);
                            sAdapter.setPref(pref);
                            sAdapter.setCoordinate(coordinatorLayout);
                            specialistrv.setAdapter(sAdapter);
                            specialistrv.setLayoutManager(new LinearLayoutManager(PopulateSalons.this, LinearLayoutManager.HORIZONTAL, false));
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
            Intent i = new Intent(PopulateSalons.this, UserHomeScreen.class);
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


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public int getSpan() {
        int orientation = getScreenOrientation(PopulateSalons.this);
        if (isTablet(PopulateSalons.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id._cancel) {
            Search_2.setText("");
        }
       if(v.getId()==R.id.change){
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
                                        _city = (String) parent.getItemAtPosition(position);
                                            getEats(_city);


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


}






