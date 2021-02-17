package com.zanrite.groomme.Fragments;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AboutFragment extends Fragment implements OnMapReadyCallback {



    private TextView aboutus,_address,_email,mobileo,pinno;
    private TextView sunday_ot, sunday_ct;
    private TextView monday_ot, monday_ct;
    private TextView tuesday_ot, tuesday_ct;
    private TextView wednessday_ot, wednessday_ct;
    private TextView thursday_ot, thursday_ct;
    private TextView friday_ot, friday_ct;
    private TextView satureday_ot, satureday_ct;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private  String _aboou,_pin,_addr,parlour_locality,parlour_city,parlour_registration,parlour_mobile,parlour_email;
    private String latitude,longitude;
    private FragmentActivity myContext;
    private RelativeLayout rmap;
    private SupportMapFragment mapFragment;
    private GoogleMap mgoogleMap;
    private Marker markerCar;
    private LinearLayout Lmon,Ltue,Lwed,Lthr,Lfri,Lsat,Lsun;
    private TextView _m,_t,_w,_th,_f,_s,_su;
    private String _phoneNo;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   
        View V = inflater.inflate(R.layout.aboutfragment, container, false);
        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);

        coordinatorLayout = V.findViewById(R.id
                .cor_home_main);

        _email =  V.findViewById(R.id.email);
        mobileo =  V.findViewById(R.id.mobileo);
        pinno =  V.findViewById(R.id.pinno);
        aboutus =  V.findViewById(R.id.aboutus);
        _address =  V.findViewById(R.id._address);
        sunday_ot =  V.findViewById(R.id.sunopen);
        sunday_ct =  V.findViewById(R.id.sunclose);

        monday_ot =  V.findViewById(R.id.monopen);
        monday_ct =  V.findViewById(R.id.monclose);

        tuesday_ot =  V.findViewById(R.id.tueopen);
        tuesday_ct =  V.findViewById(R.id.tueclose);

        wednessday_ot =  V.findViewById(R.id.wedopen);
        wednessday_ct =  V.findViewById(R.id.wedclose);

        thursday_ot =  V.findViewById(R.id.thropen);
        thursday_ct =  V.findViewById(R.id.thrclose);

        friday_ot =  V.findViewById(R.id.friopen);
        friday_ct =  V.findViewById(R.id.friclose);

        satureday_ot =  V.findViewById(R.id.satopen);
        satureday_ct =  V.findViewById(R.id.satclose);

        rmap=V.findViewById(R.id.rmap);


        _s =  V.findViewById(R.id._s);
        _m =  V.findViewById(R.id._m);
        _t =  V.findViewById(R.id._t);
        _w =  V.findViewById(R.id._w);
        _th =  V.findViewById(R.id._th);
        _f =  V.findViewById(R.id._f);
        _s =  V.findViewById(R.id._s);
        _su =  V.findViewById(R.id._su);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);



        return  V;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
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
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
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
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
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
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
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
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap == null) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Could not load Google Map", Snackbar.LENGTH_LONG)
                    ;
            snackbar.show();

        } else {
            mgoogleMap = googleMap;
            mgoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mgoogleMap.getUiSettings().setAllGesturesEnabled(true);
            mgoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

            double My_lat = 0,My_long=0;
            if(pref.getPickLat()!=null) {
                My_lat = Double.parseDouble(pref.getPickLat());
                My_long = Double.parseDouble(pref.getPickLong());
            }
            if(rmap.getWidth()!=0 && mgoogleMap!=null) {
                if (markerCar == null) {
                    markerCar = mgoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(My_lat, My_long))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_human)));
                    markerCar.hideInfoWindow();
                    markerCar.setAnchor(0.5f, 0.5f);
                } else {
                    markerCar.setPosition(new LatLng(My_lat, My_long));
                    markerCar.setAnchor(0.5f, 0.5f);
                }
                CameraPosition googlePlex;
                googlePlex = CameraPosition.builder()
                        .target(new LatLng(My_lat,My_long))
                        .zoom(12)// Sets the zoom
                        .build(); // Crea
                mgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
                final Circle circle = mgoogleMap.addCircle(new CircleOptions().center(new LatLng(My_lat, My_long))
                        .strokeColor(Color.RED).radius(100));

                ValueAnimator vAnimator = new ValueAnimator();
                vAnimator.setRepeatCount(ValueAnimator.INFINITE);
                vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
                vAnimator.setIntValues(0, 100);
                vAnimator.setDuration(5000);
                vAnimator.setEvaluator(new IntEvaluator());
                vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        // Log.e("", "" + animatedFraction);
                        circle.setRadius(animatedFraction * 20);
                    }
                });
                vAnimator.start();
            }

        }
    }
    @Override
    public void onResume() {
        super.onResume();

        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                new Response.Listener<String>() {
                    private String sunday_ots,monday_ots,sunday_cts,monday_cts,tue_open_time,tue_close_time,thr_open_time,thr_close_time,wed_open_time,wed_close_time
                            ,fri_open_time,fri_close_time,sat_open_time,sat_close_time;
                    private int Omon=0,Otue=0,Owed=0,Othr=0,Ofri=0,Osat=0,Osun=0;

                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("users");



                            // looping through All Contacts
                            if (contacts.length() > 0) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    _aboou=c.getString("parlour_about");
                                    _pin=c.getString("parlour_pin");
                                    _addr=c.getString("parlour_address");
                                    parlour_mobile=c.getString("parlour_mobile");
                                    parlour_locality=c.getString("parlour_locality");
                                    parlour_city=c.getString("parlour_city");
                                    parlour_registration=c.getString("parlour_registration");
                                    parlour_email=c.getString("parlour_email");



                                }

                            }

                            JSONArray timings = jsonObj.getJSONArray("timings");


                            if (timings.length() > 0) {
                                for (int i = 0; i < timings.length(); i++) {
                                    JSONObject c = timings.getJSONObject(i);
                                    if(c.getInt("sun_open_close")==1){
                                        sunday_ots=c.getString("sun_open_time");
                                        sunday_cts=c.getString("sun_close_time");
                                        Osun=1;
                                        _su.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _su.setTextColor(getResources().getColor(R.color.red));
                                        sunday_ots="CLOSED";
                                        sunday_cts="CLOSED";
                                    }
                                    if(c.getInt("mon_open_close")==1){
                                        monday_ots=c.getString("mon_open_time");
                                        monday_cts=c.getString("mon_close_time");
                                        Omon=1;
                                        _m.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _m.setTextColor(getResources().getColor(R.color.red));
                                        monday_ots="CLOSED";
                                        monday_cts="CLOSED";
                                    }
                                    if(c.getInt("tue_open_close")==1){
                                        tue_open_time=c.getString("tue_open_time");
                                        tue_close_time=c.getString("tue_close_time");
                                        Otue=1;
                                        _t.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _t.setTextColor(getResources().getColor(R.color.red));
                                        tue_open_time="CLOSED";
                                        tue_close_time="CLOSED";
                                    }
                                    if(c.getInt("wed_open_close")==1){
                                        wed_open_time=c.getString("wed_open_time");
                                        wed_close_time=c.getString("wed_close_time");
                                        Owed=1;
                                        _w.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _w.setTextColor(getResources().getColor(R.color.red));
                                        wed_open_time="CLOSED";
                                        wed_close_time="CLOSED";
                                    }
                                    if(c.getInt("thr_open_close")==1){
                                        thr_open_time=c.getString("thr_open_time");
                                        thr_close_time=c.getString("thr_close_time");
                                        Othr=1;
                                        _th.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _th.setTextColor(getResources().getColor(R.color.red));
                                        thr_open_time="CLOSED";
                                        thr_close_time="CLOSED";
                                    }
                                    if(c.getInt("fri_open_close")==1){
                                        fri_open_time=c.getString("fri_open_time");
                                        fri_close_time=c.getString("fri_close_time");
                                        Ofri=1;
                                        _f.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _f.setTextColor(getResources().getColor(R.color.red));
                                        fri_open_time="CLOSED";
                                        fri_close_time="CLOSED";
                                    }

                                    if(c.getInt("sat_open_close")==1){
                                        sat_open_time=c.getString("sat_open_time");
                                        sat_close_time=c.getString("sat_close_time");
                                        Osat=1;
                                        _s.setTextColor(getResources().getColor(R.color.green));
                                    }else{
                                        _s.setTextColor(getResources().getColor(R.color.red));
                                        sat_open_time="CLOSED";
                                        sat_close_time="CLOSED";
                                    }

                                }

                            }




                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                        sunday_ot.setText(sunday_ots);
                        sunday_ct.setText(sunday_cts);
                        monday_ot.setText(monday_ots);
                        monday_ct.setText(monday_cts);
                        tuesday_ot.setText(tue_open_time);
                        tuesday_ct.setText(tue_close_time);
                        wednessday_ot.setText(wed_open_time);
                        wednessday_ct.setText(wed_close_time);
                        thursday_ot.setText(thr_open_time);
                        thursday_ct.setText(thr_close_time);
                        friday_ot.setText(fri_open_time);
                        friday_ct.setText(fri_close_time);
                        satureday_ot.setText(sat_open_time);
                        satureday_ct.setText(sat_close_time);
                        if(_addr!=null){
                            _address.setText(_addr+"\n"+parlour_city+"\n"+parlour_locality);
                        }

                        if(_aboou!=null){
                            aboutus.setText(_aboou);
                        }
                        if(_pin!=null){
                            pinno.setText("Zip Code: "+_pin);
                        }
                        if(parlour_mobile!=null){
                            mobileo.setText("Phone No: "+parlour_mobile);
                        }
                        if(parlour_email!=null){
                            _email.setText("Email : "+parlour_email);
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

}
