package com.zanrite.groomme.extendedcalendarview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.zanrite.groomme.Adapters.calenderbookingadapter;
import com.zanrite.groomme.Booking.BookingAdapter;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.Models.Bookingmodel;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalenderExpandableAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private  HashMap<String, ArrayList<Bookingmodel>>   _listDataChild;
    private PrefManager pref;

    public CalenderExpandableAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, ArrayList<Bookingmodel>>   listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Bookingmodel album_pos = (Bookingmodel) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.calenderrv, null);
        }

         EditText name, datetime,payable,otp,specialist;
         RecyclerView _moreRv;
         NetworkImageView img_profilo,img_specialist;
         TextView primary_text;


        img_profilo=convertView.findViewById(R.id.img_profilo);
        name=convertView.findViewById(R.id.name);
        datetime=convertView.findViewById(R.id.datetime);
        payable=convertView.findViewById(R.id.payable);
        _moreRv=convertView.findViewById(R.id.moreRv);
        _moreRv.setNestedScrollingEnabled(false);
        otp=convertView.findViewById(R.id.otp);
        img_specialist=convertView.findViewById(R.id.img_specialist);
        primary_text=convertView.findViewById(R.id.primary_text);
        specialist=convertView.findViewById(R.id.specialist);
        if(album_pos.getIsAccepted(childPosition)==1){
          primary_text.setVisibility(View.VISIBLE);
           primary_text.setText("Accpted by salon");
        }
        if(album_pos.getIsCompleted(childPosition)==1){
            primary_text.setVisibility(View.VISIBLE);
            primary_text.setText("Completed");
        }
        if(album_pos.getIsRunning(childPosition)==1){
            primary_text.setVisibility(View.VISIBLE);
            primary_text.setText("Runnung");
        } if(album_pos.getIsCancelled(childPosition)==1){
            primary_text.setVisibility(View.VISIBLE);
            primary_text.setText("Cancelled");
        }

        if (!TextUtils.isEmpty(album_pos.getOrderDate(childPosition))) {
            datetime.setText(album_pos.getOrderDate(childPosition) + " " + album_pos.getSlot(childPosition));
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (album_pos.getPayable(childPosition) != 0) {
            payable.setText("R " + df.format(album_pos.getPayable(childPosition)));
        }

        if (album_pos.getCrew_pic(childPosition) != null) {

            String url = album_pos.getCrew_pic(childPosition).replaceAll(" ", "%20");
            ImageLoader imageLoader = LruBitmapCache.getInstance(_context)
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(img_specialist,
                    R.mipmap.ic_launcher, R.mipmap
                            .ic_launcher));
            img_specialist.setImageUrl(url, imageLoader);


            if(pref.getResposibility()==1) {
                if (!TextUtils.isEmpty(album_pos.getParlour_name(childPosition))) {
                    name.setText(album_pos.getParlour_name(childPosition));
                }
                if (album_pos.getPPhoto(childPosition) != null) {

                    String url1 = album_pos.getPPhoto(childPosition).replaceAll(" ", "%20");
                    imageLoader = LruBitmapCache.getInstance(_context)
                            .getImageLoader();
                    imageLoader.get(url1, ImageLoader.getImageListener(img_profilo,
                            R.mipmap.ic_launcher, R.mipmap
                                    .ic_launcher));
                    img_profilo.setImageUrl(url1, imageLoader);

                }
            }else{
                if (!TextUtils.isEmpty(album_pos.getUserName(childPosition))) {
                    name.setText(album_pos.getUserName(childPosition));
                }
                if (album_pos.getPhoto(childPosition) != null) {

                    String url1 = album_pos.getPhoto(childPosition).replaceAll(" ", "%20");
                    imageLoader = LruBitmapCache.getInstance(_context)
                            .getImageLoader();
                    imageLoader.get(url1, ImageLoader.getImageListener(img_profilo,
                            R.mipmap.ic_launcher, R.mipmap
                                    .ic_launcher));
                    img_profilo.setImageUrl(url1, imageLoader);

                }
            }
            if (album_pos.getCrew_name(childPosition) != null) {
               specialist.setText(album_pos.getCrew_name(childPosition));
            }
            if (album_pos.getOTP(childPosition) != 0) {
               otp.setText(String.valueOf(album_pos.getOTP(childPosition)));
            }
            getOrders(album_pos.getOTP(childPosition),_moreRv,childPosition,0, album_pos.getDiscount(childPosition));


        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.servicelist_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void getOrders(final int otp, final RecyclerView _moreRv, final int position, final int check, final double discount) {
        final ArrayList<Total> mItems = new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_GETR_OTP,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.w("bookingservices", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("bookingservices");


                            // looping through All Contacts
                            if (contacts.length() > 0) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    Total item1 = new Total();
                                    item1.setID(c.getInt("ID"));
                                    item1.setPrice((c.getString("Price")));
                                    item1.setName(c.getString("Name"));
                                    item1.setDuration(c.getString("Duration"));
                                    mItems.add(item1);
                                }

                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }
                        if (mItems.size() != 0) {
                            ArrayList<String> _main = new ArrayList<String>();
                            for (int i = 0; i < mItems.size(); i++) {
                                _main.add(mItems.get(i).getID(i) + "_" + 1 + "_" +
                                        mItems.get(i).getPrice(i) + "_" + mItems.get(i).getDuration(i) +
                                        "_" + mItems.get(i).getName(i) +
                                        "_" + position + "_" + 2);
                            }



                                _moreRv.setVisibility(View.VISIBLE);
                                calenderbookingadapter sAdapter1 = new calenderbookingadapter(_context, mItems);
                                sAdapter1.notifyDataSetChanged();
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(_context, RecyclerView.VERTICAL, false);
                                _moreRv.setLayoutManager(mLayoutManager);
                                _moreRv.setItemAnimator(new DefaultItemAnimator());
                                _moreRv.setAdapter(sAdapter1);
                                _moreRv.setHasFixedSize(true);




                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                // vollyError(error);

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("otp", String.valueOf(otp));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    public void setPref(PrefManager pref1) {
        pref=pref1;
    }
}
