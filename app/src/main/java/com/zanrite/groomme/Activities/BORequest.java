package com.zanrite.groomme.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.zanrite.groomme.Models.EventObjects;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class BORequest extends AppCompatActivity {
    private static final String TAG = BORequest.class.getSimpleName();
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    private Calendar cal = Calendar.getInstance();
    private RelativeLayout mLayout;
    private int eventIndex;
    private PrefManager pref;
    private String _PhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_main);
        pref = new PrefManager(getApplicationContext());
        if(pref.getResposibility()==1){
            HashMap<String, String> user = pref.getUserDetails2();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE2);
        }else if(pref.getResposibility()==2){
            HashMap<String, String> user = pref.getUserDetails();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        }

        mLayout = (RelativeLayout)findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();
        currentDate = (TextView)findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
        previousDay = (ImageView)findViewById(R.id.previous_day);
        nextDay = (ImageView)findViewById(R.id.next_day);
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
    }
    private void previousCalendarDate(){
        mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }
    private void nextCalendarDate(){
        mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }
    private String displayDateInString(Date mDate){
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }
    private void displayDailyEvents(){
     /*   Date calendarDate = cal.getTime();
        List<EventObjects> dailyEvent = mQuery.getAllFutureEvents(calendarDate);
        for(EventObjects eObject : dailyEvent){
            Date eventDate = eObject.getDate();
            Date endDate = eObject.getEnd();
            String eventMessage = eObject.getMessage();
            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eventMessage);
        }*/
    }
    private int getEventTimeFrame(Date start, Date end){
        long timeDifference = end.getTime() - start.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);
        int hours = mCal.get(Calendar.HOUR);
        int minutes = mCal.get(Calendar.MINUTE);
        return (hours * 60) + ((minutes * 60) / 100);
    }
    private void displayEventSection(Date eventDate, int height, String message){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[]hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }
    private void createEventView(int topMargin, int height, String message){
        TextView mEventView = new TextView(BORequest.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        mLayout.addView(mEventView, eventIndex - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        final ArrayList<Bookingmodel> mItems=new ArrayList<Bookingmodel>();
      /*  StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_PAST_BOOKINGS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.w("presentbookings", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("presentbookings");


                            // looping through All Contacts
                            if (contacts.length() > 0) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    EventObjects mQuery = new EventObjects(c.getInt("OTP"),c.getString("OrderDate"),);
                                    item1.setID(c.getInt("ID"));
                                    item1.(c.getInt("OTP"));
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
                            Ride_adapter sAdapter = new Ride_adapter(getApplicationContext(), mItems);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setPef(pref);
                            sAdapter.setPast(1);
                            laterRv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            laterRv.setLayoutManager(mLayoutManager);


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
        AppController.getInstance().addToRequestQueue(eventoReq);*/
    }


    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(getApplicationContext(), "Network is unreachable. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getApplicationContext(), "Network is unreachable. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof ServerError) {
            Toast.makeText(getApplicationContext(), "Server Error.Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof NetworkError) {
            Toast.makeText(getApplicationContext(), "Network Error. Please try another time",Toast.LENGTH_SHORT).show();

        } else if (error instanceof ParseError) {
            Toast.makeText(getApplicationContext(), "Data Error. Please try another time",Toast.LENGTH_SHORT).show();

        }


    }

}