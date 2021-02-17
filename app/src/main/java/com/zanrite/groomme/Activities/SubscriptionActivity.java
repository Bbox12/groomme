package com.zanrite.groomme.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.SubscriptionAdapter;
import com.zanrite.groomme.Booking.Payment;
import com.zanrite.groomme.Models.Total;
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

public class SubscriptionActivity  extends  AppCompatActivity implements View.OnClickListener {



private RecyclerView messagesView;
private CoordinatorLayout coordinatorLayout;
private Toolbar toolbar;
private String _phoneNo;
private PrefManager pref;
private String _name;
private TextView norides;
    private LinearLayout layoutBottomSheet_explore;
    private BottomSheetBehavior sheetBehavior_explore;
    private TextView _price,_wallet,payuLink,googleLink;
    private RelativeLayout _credit;
    private CollapsingToolbarLayout _collaspsingToolbar;

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriptionactivity);
        coordinatorLayout = findViewById(R.id
        .cor_home_main);
    layoutBottomSheet_explore = findViewById(R.id.bottom_sheet_pay);
    sheetBehavior_explore = BottomSheetBehavior.from(layoutBottomSheet_explore);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("SUBSCRIPTION");
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        _name = user.get(PrefManager.KEY_NAME);

        messagesView = findViewById(R.id.messages_view);
        messagesView.setNestedScrollingEnabled(false);

        _collaspsingToolbar=findViewById(R.id.toolbar_layout);


        _price=findViewById(R.id._price);
    _wallet=findViewById(R.id._wallet);
    payuLink=findViewById(R.id.payuLink);
    googleLink=findViewById(R.id.googleLink);
    _credit=findViewById(R.id.credit);

    _credit.setOnClickListener(this);
    payuLink.setOnClickListener(this);
    googleLink.setOnClickListener(this);
        norides=findViewById(R.id.no_rides);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {


        Intent o = new Intent(SubscriptionActivity.this, SalonHomePage.class);
        o.putExtra("from", 1);
        startActivity(o);
        finish();
         overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


        }

        });

    sheetBehavior_explore.setHideable(true);
    sheetBehavior_explore.setPeekHeight(0);

    if(pref.getHeaderImage()!=null){
        ImageView Top1 = findViewById(R.id.top1);
        Picasso.Builder builder = new Picasso.Builder(SubscriptionActivity.this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });
        builder.build().load(pref.getHeaderImage()).into(Top1);
    }


        }

@Override
protected void onResume() {
        super.onResume();
        getMessage();


        }

private void getMessage() {
final ArrayList<Total> mService = new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,Config_URL.URL_REQUEST_SUBSCRIPTION,
        new Response.Listener<String>() {
@Override
public void onResponse(String response) {

        Log.w("expand", response);


        try {

        JSONObject jsonObj = new JSONObject(response);
        JSONArray services = jsonObj.getJSONArray("subscription");
        for (int i = 0; i < services.length(); i++) {
        JSONObject c = services.getJSONObject(i);
        if(!c.isNull("Name")) {
        Total item = new Total();
        item.setID(c.getInt("ID"));
        item.setName(c.getString("Name"));
        item.setDetail(c.getString("Details"));
        item.setPrice(c.getString("Price"));
        item.setValidity(c.getString("Validity"));
        mService.add(item);

        }

        }



        } catch (final JSONException e) {
        Log.e("HI", "Json parsing error: " + e.getMessage());


        }


        if(mService.size()!=0) {
        norides.setVisibility(View.GONE);
        SubscriptionAdapter sAdapter1 = new SubscriptionAdapter(SubscriptionActivity.this, mService);
        sAdapter1.notifyDataSetChanged();
        sAdapter1.setHasStableIds(true);
        sAdapter1.setPref(_name);
        messagesView.setVisibility(View.VISIBLE);
        messagesView.setItemAnimator(new DefaultItemAnimator());
        messagesView.setAdapter(sAdapter1);
        messagesView.setHasFixedSize(true);
        LinearLayoutManager mHorizontal = new LinearLayoutManager(SubscriptionActivity.this, RecyclerView.HORIZONTAL, false);
        mHorizontal.setAutoMeasureEnabled(false);
        messagesView.setLayoutManager(mHorizontal);
        }else{
        norides.setVisibility(View.VISIBLE);
        messagesView.setVisibility(View.GONE);
        }
    messagesView.addOnItemTouchListener(
            new RecyclerTouchListener(SubscriptionActivity.this, messagesView,
                    new RecyclerTouchListener.OnTouchActionListener() {


                        @Override
                        public void onClick(View view, final int position) {
                            Log.w("gallery", String.valueOf(position));
                            layoutBottomSheet_explore.setVisibility(View.VISIBLE);
                            sheetBehavior_explore.setState(BottomSheetBehavior.STATE_EXPANDED);
                            _collaspsingToolbar.setScrimsShown(true);

                            _price.setText("R "+mService.get(position).getPrice(position));
                            _wallet.setText("R "+mService.get(position).getPrice(position));
                        }

                        @Override
                        public void onRightSwipe(View view, int position) {
                            _price.setText("R "+mService.get(position).getPrice(position));
                            _wallet.setText("R "+mService.get(position).getPrice(position));
                        }

                        @Override
                        public void onLeftSwipe(View view, int position) {
                            _price.setText("R "+mService.get(position).getPrice(position));
                            _wallet.setText("R "+mService.get(position).getPrice(position));
                        }
                    }));
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
        params.put("_mId", "");
        return params;
        }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
        }


@Override
public void onClick(View view) {
switch (view.getId()){
    case R.id.credit:


        Intent o = new Intent(SubscriptionActivity.this, Payment.class);
        o.putExtra("from", 1);
        startActivity(o);
        finish();
         overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


        break;
    case R.id.payuLink:

        break;
    case R.id.googleLink:

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
         finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
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
         finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
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
         finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
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
         finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
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
         finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
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

        Intent o = new Intent(SubscriptionActivity.this, SalonHomePage.class);
        o.putExtra("from", 1);
        startActivity(o);
        finish();
         overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
        }
        }




