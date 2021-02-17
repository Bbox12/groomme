package com.zanrite.groomme.HistoryData;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
import com.zanrite.groomme.Models.Bookingmodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.extendedcalendarview.CalenderExpandableAdapter;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by parag on 18/02/18.
 */

public class PastFragment extends Fragment {
    private ProgressBar progressBar;
    private ExpandableListView laterRv;
    private static final String TAG = PastFragment.class.getSimpleName();
    private PrefManager pref;
    private String _PhoneNo;
    private String _name;

    public PastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.layout_past_calender, container, false);
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
        progressBar = V.findViewById(R.id.progressBar8);
       // myCalendar = V.findViewById(R.id.agenda_calendar_view);
        return V;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);

        final ArrayList<String> listDataHeader = new ArrayList<String>();
        final HashMap<String, ArrayList<Bookingmodel>>  listDataChild = new HashMap<String,ArrayList<Bookingmodel>> ();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_BOOKING_REPORTS_CALENDER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("pastbookingsdates");
                            JSONArray secondaryservice = jsonObj.getJSONArray("pastbookings");
                            if (services.length() != 0 && secondaryservice.length() != 0) {
                                for (int i = 0; i < services.length(); i++) {
                                    JSONObject d = services.getJSONObject(i);
                                    listDataHeader.add(d.getString("OrderDate"));
                                    ArrayList<Bookingmodel> mItems=new ArrayList<Bookingmodel>();
                                    for (int j = 0; j < secondaryservice.length(); j++) {
                                        JSONObject c = secondaryservice.getJSONObject(j);
                                        if (c.getString("OrderDate").contains(d.getString("OrderDate"))) {

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
                                            item1.setCrew_pic(c.getString("crew_pic"));
                                            item1.setParlour_mobile(c.getString("parlour_mobile"));
                                            item1.setParlour_name(c.getString("parlour_name"));
                                            item1.setIsAccepted(c.getInt("isAccepted"));
                                            item1.setIsCompleted(c.getInt("isCompleted"));
                                            item1.setIsRunning(c.getInt("isRunning"));
                                            item1.setIsCancelled(c.getInt("isCancelled"));
                                            mItems.add(item1);
                                        }
                                    }
                                    listDataChild.put(listDataHeader.get(i), mItems);
                                }
                            }



                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        } if (listDataChild.size() != 0) {
                            CalenderExpandableAdapter listAdapter = new CalenderExpandableAdapter(getActivity(), listDataHeader, listDataChild);
                            listAdapter.setPref(pref);
                            laterRv.setAdapter(listAdapter);
                        } else{
                            laterRv.setVisibility(View.GONE);

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

