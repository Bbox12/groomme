package com.zanrite.groomme.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.ExpandableListAdapter;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceExpanded extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private RecyclerView serviceRv;
    private int _from=0;
    private String _image,_name;
    ExpandableListView expListView;
    private ImageView _pic;
    private TextView _servicename;
    private PrefManager pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(getApplicationContext());
        setContentView(R.layout.serviceexpanded);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);

        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        _pic=findViewById(R.id.img_profilo);
        _servicename=findViewById(R.id.name);


        Intent i=getIntent();
        _from=pref.getServiceID();
        _image=pref.getPrimaryimage();
        _name=pref.getPrimaryservice();

        if (_image != null) {
            Picasso.Builder builder = new Picasso.Builder(this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(_image).into(_pic);
            _servicename.setText(_name);
            _servicename.setAllCaps(true);

        }


        expListView = findViewById(R.id.lvExp);

        // preparing list data
        prepareListData(_from,expListView);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pref.getName()!=null){
                    Intent o = new Intent(ServiceExpanded.this, SalonServiceRegistration.class);
                    o.putExtra("from",1);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else {
                    Intent o = new Intent(ServiceExpanded.this, ServiceOffer.class);
                    startActivity(o);
                    finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }


            }
        });

        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(ServiceExpanded.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }


    }


    private void prepareListData(final int id, final ExpandableListView expListViews) {
        final ArrayList<String> listDataHeader = new ArrayList<String>();
        final HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETSECONDARYSERVICES,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("service");
                            JSONArray secondaryservice = jsonObj.getJSONArray("secondaryservice");
                            if (services.length() != 0 && secondaryservice.length() != 0) {
                                for (int i = 0; i < services.length(); i++) {
                                    JSONObject c = services.getJSONObject(i);
                                    listDataHeader.add(c.getString("Service"));
                                    List<String> list = new ArrayList<String>();
                                    for (int j = 0; j < secondaryservice.length(); j++) {
                                        JSONObject d = secondaryservice.getJSONObject(j);
                                        if (c.getInt("ID") == d.getInt("SecondaryServiceID")) {

                                            list.add(d.getString("Name"));
                                        }
                                    }
                                    listDataChild.put(listDataHeader.get(i), list);
                                }
                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }
                        Log.w("headersize", String.valueOf(listDataHeader.size()));
                        Log.w("datasize", String.valueOf(listDataChild.size()));
                        ExpandableListAdapter listAdapter = new ExpandableListAdapter(ServiceExpanded.this, listDataHeader, listDataChild);
                        expListViews.setAdapter(listAdapter);


                        // Listview on child click listener
                        expListViews.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                                pref.setSecondaryservice(listDataHeader.get(groupPosition));
                                pref.setService(listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition));
                                Intent o = new Intent(ServiceExpanded.this, SalonServiceDetails.class);
                                startActivity(o);
                                finish();
                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);



                                return false;
                            }
                        });


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
                params.put("id", String.valueOf(_from));
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            if(pref.getName()!=null){
                Intent o = new Intent(ServiceExpanded.this, SalonServiceRegistration.class);
                o.putExtra("from",1);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }else {
                Intent o = new Intent(ServiceExpanded.this, ServiceOffer.class);
                startActivity(o);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }

}
