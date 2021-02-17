package com.zanrite.groomme.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.RegisterAsSalon;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Activities.SalonServiceRegistration;
import com.zanrite.groomme.Activities.ServiceOffer;
import com.zanrite.groomme.Adapters.FirstAdapter;
import com.zanrite.groomme.Adapters.Populatesalonsrv;
import com.zanrite.groomme.Adapters.SecondaryServiceAdapter;
import com.zanrite.groomme.Adapters.StylishAdapter;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.Models.SalonPriceModel;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.Models.servicemodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.FilterService;
import com.zanrite.groomme.UserPart.PopulateSalons;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SalonServiceShow extends AppCompatActivity implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private RecyclerView serviceRv;
    private PrefManager pref;
    private LinearLayout ride_later,ride_now,lride;
    private TextView no_rides;
    private EditText Search_2;
    private  ArrayList<SalonPriceModel> listDataHeader = new ArrayList<SalonPriceModel>();
    private  ArrayList<String> MenuArray=new ArrayList<String>();
    private RecyclerView all_services;
    private RelativeLayout Lt;
    private TextView cart_badge;
    private int _position=100;
    private String Name,_PhoneNo;
    private LinearLayout yes_rides;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salonserviceregistrationshow);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        yes_rides=findViewById(R.id.yes_rides);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        serviceRv = findViewById(R.id.serviceRv);
        serviceRv.setNestedScrollingEnabled(false);
        pref = new PrefManager(this);
        HashMap<String, String> user = pref.getUserDetails();
        Name = user.get(PrefManager.KEY_NAME);
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

        ride_later=findViewById(R.id.ride_later);
        ride_later.setOnClickListener(this);
        ride_now=findViewById(R.id.ride_now);
        lride=findViewById(R.id.lride);
        ride_now.setOnClickListener(this);
        no_rides=findViewById(R.id.no_rides);
        Search_2=findViewById(R.id.search);
        Search_2.setHint("Search salons");
        if(pref.getResposibility()==1){
            ride_later.setVisibility(View.GONE);
            ride_now.setVisibility(View.VISIBLE);
            if(pref.getOrderID()!=null){
                lride.setVisibility(View.GONE);
            }
        }


        progressBar=findViewById(R.id.progressBarSplash);
        cart_badge=findViewById(R.id.cart_badge);

        Lt=findViewById(R.id.Lt);
        Lt.setOnClickListener(this);

        all_services=findViewById(R.id.all_services);
        all_services.setNestedScrollingEnabled(false);

        // preparing list data
        prepareListData();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent o = new Intent(SalonServiceShow.this, SalonHomePage.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);


            }

        });

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(SalonServiceShow.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }

        Search_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()>1) {


                        final ArrayList<String> filteredModelList = new ArrayList<>();

                        Set<String> set = new HashSet<String>(MenuArray);
                        MenuArray.clear();
                        MenuArray.addAll(set);
                        for (int i = 0; i < MenuArray.size(); i++) {
                            String model = MenuArray.get(i);
                            final String text = model.toLowerCase();
                            if (text.contains(String.valueOf(s).toLowerCase())) {
                                filteredModelList.add(model);
                            }
                        }
                        if (filteredModelList.size() != 0) {
                            FirstAdapter sAdapter1 = new FirstAdapter(SalonServiceShow.this,filteredModelList, listDataHeader);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setPref(pref);
                            sAdapter1.setButton(ride_later,ride_now);
                            sAdapter1.setCoordinateLayout(coordinatorLayout);
                            serviceRv.setVisibility(View.VISIBLE);
                            serviceRv.setItemAnimator(new DefaultItemAnimator());
                            serviceRv.setAdapter(sAdapter1);
                            serviceRv.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(SalonServiceShow.this, RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            serviceRv.setLayoutManager(mHorizontal);
                        }
                            final ArrayList<SalonPriceModel> filteredModelList1 = new ArrayList<>();
                            final ArrayList<String> MenuArray1 = new ArrayList<>();
                            for (int i = 0; i < listDataHeader.size(); i++) {
                                SalonPriceModel model = listDataHeader.get(i);
                                final String text = model.getName(i).toLowerCase();
                                if (text.contains(String.valueOf(s).toLowerCase())) {
                                    filteredModelList1.add(model);
                                    MenuArray1.add(model.getService(i));

                                }
                            }
                            if (filteredModelList1.size() != 0) {
                                FirstAdapter sAdapter1 = new FirstAdapter(SalonServiceShow.this,MenuArray1, filteredModelList1);
                                sAdapter1.notifyDataSetChanged();
                                sAdapter1.setHasStableIds(true);
                                sAdapter1.setPref(pref);
                                sAdapter1.setButton(ride_later,ride_now);
                                sAdapter1.setCoordinateLayout(coordinatorLayout);
                                serviceRv.setVisibility(View.VISIBLE);
                                serviceRv.setItemAnimator(new DefaultItemAnimator());
                                serviceRv.setAdapter(sAdapter1);
                                serviceRv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(SalonServiceShow.this, RecyclerView.VERTICAL, false);
                                mHorizontal.setAutoMeasureEnabled(false);
                                serviceRv.setLayoutManager(mHorizontal);
                            }


                            if(filteredModelList.size()==0 && filteredModelList1.size()==0){
                                Toast.makeText(getApplicationContext(),"No data found!",Toast.LENGTH_SHORT).show();
                                prepareListData();
                            }



                }else {
                    prepareListData();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }

    private void prepareListData() {
        final ArrayList<servicemodel> mService = new ArrayList<servicemodel>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEFRAGMENT,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);
                        listDataHeader.clear();
                        MenuArray.clear();

                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("service");
                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                if(!c.isNull("ID")) {
                                    SalonPriceModel item = new SalonPriceModel();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Name"));
                                    item.setPrice_home(c.getString("Price_home"));
                                    item.setPrice_salon(c.getString("Price_salon"));
                                    item.setTime_home(c.getString("Time_home"));
                                    item.setTime_salon(c.getString("Time_salon"));
                                    item.setService(c.getString("Service"));
                                    item.setPrimaryServiceID(c.getInt("Primary"));
                                    MenuArray.add(c.getString("Service"));
                                    listDataHeader.add(item);
                                }

                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }


                        if(listDataHeader.size()!=0) {
                            no_rides.setVisibility(View.GONE);
                            yes_rides.setVisibility(View.VISIBLE);
                            if(MenuArray.size()!=0) {
                                Set<String> set = new HashSet<>(MenuArray);
                                MenuArray.clear();
                                MenuArray.addAll(set);
                            }

                            FirstAdapter sAdapter1 = new FirstAdapter(SalonServiceShow.this,MenuArray, listDataHeader);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setPref(pref);
                            sAdapter1.setButton(ride_later,ride_now);
                            sAdapter1.setCoordinateLayout(coordinatorLayout);
                            serviceRv.setVisibility(View.VISIBLE);
                            serviceRv.setItemAnimator(new DefaultItemAnimator());
                            serviceRv.setAdapter(sAdapter1);
                            serviceRv.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(SalonServiceShow.this, RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            serviceRv.setLayoutManager(mHorizontal);

                        }else{
                            no_rides.setVisibility(View.VISIBLE);
                            yes_rides.setVisibility(View.GONE);
                            if(pref.getResposibility()!=1){
                                Intent o = new Intent(SalonServiceShow.this, SalonServiceRegistration.class);
                                o.putExtra("from", 1);
                                startActivity(o);
                                finish();
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            }
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
                params.put("id",_PhoneNo );
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    private void prepareLadiesList(final int id) {
        final ArrayList<servicemodel> mService = new ArrayList<servicemodel>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEFRAGMENT,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);
                        listDataHeader.clear();
                        MenuArray.clear();

                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("service");
                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                if(!c.isNull("ID")) {
                                    SalonPriceModel item = new SalonPriceModel();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Name"));
                                    item.setPrice_home(c.getString("Price_home"));
                                    item.setPrice_salon(c.getString("Price_salon"));
                                    item.setTime_home(c.getString("Time_home"));
                                    item.setTime_salon(c.getString("Time_salon"));
                                    item.setService(c.getString("Service"));
                                    item.setPrimaryServiceID(c.getInt("Primary"));
                                    MenuArray.add(c.getString("Service"));
                                    listDataHeader.add(item);
                                }

                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }


                        if(listDataHeader.size()!=0) {
                            no_rides.setVisibility(View.GONE);
                            yes_rides.setVisibility(View.VISIBLE);
                            if(MenuArray.size()!=0) {
                                Set<String> set = new HashSet<>(MenuArray);
                                MenuArray.clear();
                                MenuArray.addAll(set);
                            }

                            FirstAdapter sAdapter1 = new FirstAdapter(SalonServiceShow.this,MenuArray, listDataHeader);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setPref(pref);
                            sAdapter1.setButton(ride_later,ride_now);
                            sAdapter1.setCoordinateLayout(coordinatorLayout);
                            serviceRv.setVisibility(View.VISIBLE);
                            serviceRv.setItemAnimator(new DefaultItemAnimator());
                            serviceRv.setAdapter(sAdapter1);
                            serviceRv.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(SalonServiceShow.this, RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            serviceRv.setLayoutManager(mHorizontal);
                        }else{
                            no_rides.setVisibility(View.VISIBLE);
                            yes_rides.setVisibility(View.GONE);
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
                params.put("id", _PhoneNo);
                params.put("ladies", String.valueOf(id));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         prepareListData();
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
                            prepareListData();
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
                            prepareListData();
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
                            prepareListData();
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
                            prepareListData();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.Lt:

                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEFRAGMENT,
                            new Response.Listener<String>() {


                                @Override
                                public void onResponse(String response) {

                                    Log.w("expand", response);


                                    try {

                                        JSONObject jsonObj = new JSONObject(response);

                                        final ArrayList<Slots> mCategory = new ArrayList<Slots>();
                                        JSONArray category = jsonObj.getJSONArray("category");
                                        for (int i = 0; i < category.length(); i++) {
                                            JSONObject c = category.getJSONObject(i);
                                            if (!c.isNull("Category")) {
                                                Slots item = new Slots();
                                                item.setSlots(c.getString("Category"));
                                                item.setID(c.getInt("ID"));
                                                mCategory.add(item);
                                            }
                                        }
                                        if (mCategory.size() > 0) {
                                            all_services.setVisibility(View.VISIBLE);
                                            SecondaryServiceAdapter sAdapter1 = new SecondaryServiceAdapter(SalonServiceShow.this, mCategory);
                                            sAdapter1.notifyDataSetChanged();
                                            sAdapter1.setHasStableIds(true);
                                            //sAdapter1.setFrom(1);
                                            all_services.setAdapter(sAdapter1);
                                            all_services.setHasFixedSize(true);
                                            LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(SalonServiceShow.this, RecyclerView.HORIZONTAL, false);
                                            all_services.setLayoutManager(horizontalLayoutManagae);
                                            all_services.setItemAnimator(new DefaultItemAnimator());

                                            all_services.addOnItemTouchListener(
                                                    new RecyclerTouchListener(SalonServiceShow.this, all_services,
                                                            new RecyclerTouchListener.OnTouchActionListener() {




                                                                @Override
                                                                public void onClick(View view, final int position) {
                                                                    Log.w("gallery", String.valueOf(position));

                                                                    if(_position!=position){
                                                                        _position=position;
                                                                        if(mCategory.get(position)!=null) {
                                                                            prepareLadiesList(mCategory.get(position).getID(position));
                                                                            cart_badge.setText("1");
                                                                            all_services.setVisibility(View.GONE);
                                                                           // Search_2.setText(mCategory.get(position).getSlots(position)+" services");
                                                                        }
                                                                    }else {
                                                                        cart_badge.setText("0");
                                                                        all_services.setVisibility(View.GONE);
                                                                        prepareListData();
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
                                            all_services.setVisibility(View.GONE);
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
                            params.put("id", _PhoneNo);
                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(eventoReq);

                break;

            case R.id.ride_later:

                if(pref.getResposibility()!=1){
                    Intent o = new Intent(SalonServiceShow.this, SalonServiceRegistration.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }
                break;

            case R.id.ride_now:
                if (pref.getnoOfItems().size()!=0) {
                    Intent o1 = new Intent(SalonServiceShow.this, CheckOut.class);
                    startActivity(o1);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                } else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Your cart is empty! Please add services", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
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
                break;
                default:break;
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
        if(keyCode== KeyEvent.KEYCODE_BACK) {
            if (pref.getResposibility() == 1) {
                Intent o = new Intent(SalonServiceShow.this, SalonHomePage.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
            } else {
                if (pref.getName() != null) {
                    Intent o = new Intent(SalonServiceShow.this, SalonHomePage.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);

                } else {
                    Intent o = new Intent(SalonServiceShow.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                }

            }
        }
        return true;
    }

}
