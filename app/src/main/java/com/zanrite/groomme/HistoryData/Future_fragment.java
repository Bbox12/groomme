package com.zanrite.groomme.HistoryData;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.zanrite.groomme.Adapters.Ride_adapter;
import com.zanrite.groomme.Models.Bookingmodel;
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
import java.util.Map;


/**
 * Created by parag on 27/01/18.
 */

public class Future_fragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView laterRv;
    private static final String TAG = Future_fragment.class.getSimpleName();
    private PrefManager pref;
    private String _PhoneNo;
    private TextView no_rides;
    private String _name;

    public Future_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.layout_past, container, false);
        pref = new PrefManager(getActivity());
        if(pref.getResposibility()==1){
            HashMap<String, String> user = pref.getUserDetails2();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE2);
            _name = user.get(PrefManager.KEY_NAME2);
        }else if(pref.getResposibility()==2){
            HashMap<String, String> user = pref.getUserDetails();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
            _name = user.get(PrefManager.KEY_NAME);
        }
        laterRv = V.findViewById(R.id.pastRv);
        no_rides = V.findViewById(R.id.no_past);
        progressBar = V.findViewById(R.id.progressBarpast);
        return V;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        final ArrayList<Bookingmodel> mItems=new ArrayList<Bookingmodel>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_PAST_BOOKINGS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.w("future", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("futurebookings");


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
                                    item1.setPPhoto(c.getString("PPhoto"));
                                    item1.setParlour_mobile(c.getString("parlour_mobile"));
                                    item1.setParlour_name(c.getString("parlour_name"));
                                    item1.setIsAccepted(c.getInt("isAccepted"));
                                    item1.setCrew_pic(c.getString("crew_pic"));
                                    item1.setIsCompleted(c.getInt("isCompleted"));
                                    item1.setIsRunning(c.getInt("isRunning"));
                                    item1.setIsCancelled(c.getInt("isCancelled"));
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
                            laterRv.setVisibility(View.VISIBLE);
                            no_rides.setVisibility(View.GONE);
                            Ride_adapter sAdapter = new Ride_adapter(getActivity(), mItems);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setPast(2);
                            sAdapter.setPef(pref);
                            laterRv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            laterRv.setLayoutManager(mLayoutManager);
                            laterRv.addOnItemTouchListener(
                                    new RecyclerTouchListener(getActivity(), laterRv,
                                            new RecyclerTouchListener.OnTouchActionListener() {

                                                @Override
                                                public void onClick(View view, final int position) {

                                                    if (mItems.size() != 0) {


                                                    }

                                                }

                                                @Override
                                                public void onRightSwipe(View view, int position) {

                                                }

                                                @Override
                                                public void onLeftSwipe(View view, int position) {

                                                }
                                            }));

                        } else{
                            laterRv.setVisibility(View.GONE);
                            no_rides.setVisibility(View.VISIBLE);

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
                params.put("mID", _PhoneNo);
                params.put("role", String.valueOf(pref.getResposibility()));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(getActivity(), "Network is unreachable. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getActivity(), "Network is unreachable. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof ServerError) {
            Toast.makeText(getActivity(), "Server Error.Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof NetworkError) {
            Toast.makeText(getActivity(), "Network Error. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof ParseError) {
            Toast.makeText(getActivity(), "Data Error. Please try another time",Toast.LENGTH_SHORT).show();

        }


    }

}
