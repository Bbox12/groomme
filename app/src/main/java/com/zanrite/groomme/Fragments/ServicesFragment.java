package com.zanrite.groomme.Fragments;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Activities.RegisterAsSalon;
import com.zanrite.groomme.Adapters.SecondAdapter;
import com.zanrite.groomme.Adapters.SecondaryServiceAdapter;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.Models.SalonPriceModel;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.R;
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

public class ServicesFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ride_later,ride_now;
    private LinearLayout servicelinarlayout;
    private TextView no_rides;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager pref;
    private RecyclerView _moreRv;
    private RecyclerView all_services;
    private Context mContext;
    private String _phoneNo;

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V= inflater.inflate(R.layout.servicefragment, container, false);
        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        ride_later=V.findViewById(R.id.ride_later);
        ride_later.setOnClickListener(this);
        ride_now=V.findViewById(R.id.ride_now);
        ride_now.setOnClickListener(this);
        no_rides=V.findViewById(R.id.no_rides);
        coordinatorLayout = V.findViewById(R.id
                .cor_home_main);
        _moreRv=V.findViewById(R.id.moreRv);
        _moreRv.setNestedScrollingEnabled(false);


        if(pref.getOrderID()!=null){
            ride_later.setVisibility(View.VISIBLE);
            ride_now.setVisibility(View.GONE);

        }


        all_services=V.findViewById(R.id.all_servicessss);
        all_services.setNestedScrollingEnabled(false);
        all_services.setBackgroundColor(Color.RED);
        if(pref.getResposibility()==2) {
            prepareListData(_moreRv);
        }else{
            getServices();
        }
        return  V;

    }

    private void getServices() {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEFRAGMENT,
                new Response.Listener<String>() {
                    ArrayList<SalonPriceModel> listDataHeader = new ArrayList<SalonPriceModel>();
                    ArrayList<String> MenuArray=new ArrayList<String>();
                    ArrayList<Slots> mCategory = new ArrayList<Slots>();
                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("fewservice");
                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                SalonPriceModel item = new SalonPriceModel();
                                item.setID(c.getInt("ID"));
                                item.setName(c.getString("Name"));
                                item.setPrice_home(c.getString("Price_home"));
                                item.setPrice_salon(c.getString("Price_salon"));
                                item.setTime_home(c.getString("Time_home"));
                                item.setTime_salon(c.getString("Time_salon"));
                                item.setService(c.getString("Service"));
                                MenuArray.add(c.getString("Service"));
                                listDataHeader.add(item);


                            }


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


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }
                        if(listDataHeader.size()!=0) {
                            no_rides.setVisibility(View.GONE);
                            SecondAdapter sAdapter1 = new SecondAdapter(getActivity(), listDataHeader);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setCoordinateLayout(coordinatorLayout);
                            sAdapter1.setAdap(sAdapter1);
                            sAdapter1.setPref(pref);
                            sAdapter1.setButton(ride_later,ride_now);
                            // _moreRv.setVisibility(View.VISIBLE);
                            _moreRv.setItemAnimator(new DefaultItemAnimator());
                            _moreRv.setAdapter(sAdapter1);
                            _moreRv.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            _moreRv.setLayoutManager(mHorizontal);
                        }else{
                            _moreRv.setVisibility(View.GONE);
                            no_rides.setVisibility(View.VISIBLE);
                        }

                        if (mCategory.size() > 0) {
                            all_services.setVisibility(View.VISIBLE);
                            SecondaryServiceAdapter sAdapter = new SecondaryServiceAdapter(getActivity(), mCategory);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setHasStableIds(true);
                            all_services.setAdapter(sAdapter);
                            all_services.setHasFixedSize(true);
                            LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                            all_services.setLayoutManager(horizontalLayoutManagae);
                            all_services.setItemAnimator(new DefaultItemAnimator());

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
                params.put("id",_phoneNo);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       mContext=context;

    }


        private void prepareListData(final RecyclerView expListViews) {

            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSERVICEFRAGMENT,
                    new Response.Listener<String>() {
                        ArrayList<SalonPriceModel> listDataHeader = new ArrayList<SalonPriceModel>();
                        ArrayList<String> MenuArray=new ArrayList<String>();
                        ArrayList<Slots> mCategory = new ArrayList<Slots>();
                        @Override
                        public void onResponse(String response) {

                            Log.w("expand", response);


                            try {

                                JSONObject jsonObj = new JSONObject(response);
                                JSONArray services = jsonObj.getJSONArray("fewservice");
                                    for (int i = 0; i < services.length(); i++) {
                                        JSONObject c = services.getJSONObject(i);
                                        SalonPriceModel item = new SalonPriceModel();
                                        item.setID(c.getInt("ID"));
                                        item.setName(c.getString("Name"));
                                        item.setPrice_home(c.getString("Price_home"));
                                        item.setPrice_salon(c.getString("Price_salon"));
                                        item.setTime_home(c.getString("Time_home"));
                                        item.setTime_salon(c.getString("Time_salon"));
                                        item.setService(c.getString("Service"));
                                        MenuArray.add(c.getString("Service"));
                                        listDataHeader.add(item);


                                }


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


                            } catch (final JSONException e) {
                                Log.e("HI", "Json parsing error: " + e.getMessage());


                            }
                            if(listDataHeader.size()!=0) {
                                no_rides.setVisibility(View.GONE);
                                SecondAdapter sAdapter1 = new SecondAdapter(getActivity(), listDataHeader);
                                sAdapter1.notifyDataSetChanged();
                                sAdapter1.setHasStableIds(true);
                                sAdapter1.setCoordinateLayout(coordinatorLayout);
                                sAdapter1.setAdap(sAdapter1);
                                sAdapter1.setPref(pref);
                                sAdapter1.setButton(ride_later,ride_now);
                               // _moreRv.setVisibility(View.VISIBLE);
                                _moreRv.setItemAnimator(new DefaultItemAnimator());
                                _moreRv.setAdapter(sAdapter1);
                                _moreRv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                                mHorizontal.setAutoMeasureEnabled(false);
                                _moreRv.setLayoutManager(mHorizontal);
                            }else{
                                _moreRv.setVisibility(View.GONE);
                                no_rides.setVisibility(View.VISIBLE);
                            }

                            if (mCategory.size() > 0) {
                                all_services.setVisibility(View.VISIBLE);
                                SecondaryServiceAdapter sAdapter = new SecondaryServiceAdapter(getActivity(), mCategory);
                                sAdapter.notifyDataSetChanged();
                                sAdapter.setHasStableIds(true);
                                all_services.setAdapter(sAdapter);
                                all_services.setHasFixedSize(true);
                                LinearLayoutManager horizontalLayoutManagae = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                                all_services.setLayoutManager(horizontalLayoutManagae);
                                all_services.setItemAnimator(new DefaultItemAnimator());

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
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(eventoReq);
        }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.ride_later:
                if(getActivity()!=null) {
                    Intent o = new Intent(getActivity(), SalonServiceShow.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    ((Activity)mContext).finish();
                    ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }
            break;

            case R.id.ride_now:
                if(getActivity()!=null) {
                    Intent o1 = new Intent(getActivity(), CheckOut.class);
                    startActivity(o1);
                    ((Activity)mContext).finish();
                    ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }
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
                            ((Activity)mContext). finish();
            ((Activity)mContext).overridePendingTransition(0, 0);
            startActivity(((Activity)mContext).getIntent());
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
                            ((Activity)mContext). finish();
                            ((Activity)mContext).overridePendingTransition(0, 0);
            startActivity(((Activity)mContext).getIntent());
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
                            ((Activity)mContext). finish();
                            ((Activity)mContext).overridePendingTransition(0, 0);
                            startActivity(((Activity)mContext).getIntent());
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
                            ((Activity)mContext). finish();
                            ((Activity)mContext).overridePendingTransition(0, 0);
                            startActivity(((Activity)mContext).getIntent());
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
                            ((Activity)mContext). finish();
                            ((Activity)mContext).overridePendingTransition(0, 0);
                            startActivity(((Activity)mContext).getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


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
        int orientation = getScreenOrientation(getActivity());
        if (isTV(getActivity())) return 2;
        if (isTablet(getActivity()))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }


}
