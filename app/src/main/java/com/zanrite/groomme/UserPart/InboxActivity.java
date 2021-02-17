package com.zanrite.groomme.UserPart;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Adapters.MessageAdapter;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InboxActivity extends AppCompatActivity implements View.OnClickListener {

    // replace this with a real channelID from Scaledrone dashboard
    private EditText editText;

    private MessageAdapter messageAdapter;
    private RecyclerView messagesView;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private String _phoneNo1="",_phoneNo2="";
    private PrefManager pref;
    private ImageButton imgbutton;
    private String _name1,_name2;
    private TextView norides;
    private int _from=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Inbox");
        pref = new PrefManager(getApplicationContext());

            HashMap<String, String> user = pref.getUserDetails();
            if(user.get(PrefManager.KEY_MOBILE)!=null) {
                _phoneNo1 = user.get(PrefManager.KEY_MOBILE);
                _name1 = user.get(PrefManager.KEY_NAME);
            }
            HashMap<String, String> user2 = pref.getUserDetails2();
        if(user.get(PrefManager.KEY_MOBILE2)!=null) {
            _phoneNo2 = user2.get(PrefManager.KEY_MOBILE2);
            _name2 = user2.get(PrefManager.KEY_NAME2);
        }

        editText = (EditText) findViewById(R.id.editText);

        messagesView = findViewById(R.id.messages_view);
        messagesView.setNestedScrollingEnabled(false);
        imgbutton=findViewById(R.id.imgbutton);
        imgbutton.setOnClickListener(this);

        norides=findViewById(R.id.no_rides);
        Intent i=getIntent();
        _from=i.getIntExtra("from",0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pref.getResposibility()==2) {
                    Intent o = new Intent(InboxActivity.this, SalonHomePage.class);
                    o.putExtra("from", 1);
                    startActivity(o);
                    finish();
                 overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else {
                    if(_from==0) {
                        Intent o = new Intent(InboxActivity.this, UserHomeScreen.class);
                        startActivity(o);
                        finish();
                     overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }else{
                        Intent o = new Intent(InboxActivity.this, SalonHomePage.class);
                        startActivity(o);
                        finish();
                     overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }
                }

            }

        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(_from==0) {
            getMessage();
        }else{
            imgbutton.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            getAllMessage();
        }
        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(InboxActivity.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }

    }


    private void getMessage() {
        final ArrayList<Total> mService = new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,Config_URL.URL_REQUEST_MOBILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("msg");
                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                if(!c.isNull("Name")) {
                                    Total item = new Total();
                                    item.setID(c.getInt("ID"));
                                    item.setDate(c.getString("Date"));
                                    item.setName(c.getString("Name"));
                                    item.setmobileno(c.getString("PhoneNo"));
                                    item.setDetail(c.getString("Msg"));
                                    item.setIDSalon(c.getInt("IDSalon"));
                                    item.setIDUser(c.getInt("IDUser"));
                                    mService.add(item);

                                }

                            }



                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }


                        if(mService.size()!=0) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            if(pref.getResposibility()==1) {
                                mDatabase.child("Booking").child(pref.getOrderID()).child("CMSG").removeValue();
                            }else if(pref.getResposibility()==2) {
                                mDatabase.child("Booking").child(pref.getOrderID()).child("SMSG").removeValue();
                            }
                            norides.setVisibility(View.GONE);
                            MessageAdapter sAdapter1 = new MessageAdapter(InboxActivity.this, mService);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setPref(pref);
                            messagesView.setVisibility(View.VISIBLE);
                            messagesView.setItemAnimator(new DefaultItemAnimator());
                            messagesView.setAdapter(sAdapter1);
                            messagesView.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(InboxActivity.this, RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            messagesView.setLayoutManager(mHorizontal);
                        }else{
                            norides.setVisibility(View.VISIBLE);
                            messagesView.setVisibility(View.GONE);
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
                params.put("otp", pref.getOrderID());
                params.put("id", "1");
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }
    private void getAllMessage() {
        final ArrayList<Total> mService = new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,Config_URL.URL_GET_ALL_MESSAGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("expand", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("msg");
                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                if(!c.isNull("Name")) {
                                    Total item = new Total();
                                    item.setID(c.getInt("ID"));
                                    item.setDate(c.getString("Date"));
                                    item.setName(c.getString("Name"));
                                    item.setDetail(c.getString("Msg"));
                                    item.setIDSalon(c.getInt("IDSalon"));
                                    item.setIDUser(c.getInt("IDUser"));
                                    mService.add(item);

                                }

                            }



                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }


                        if(mService.size()!=0) {
                            norides.setVisibility(View.GONE);
                            MessageAdapter sAdapter1 = new MessageAdapter(InboxActivity.this, mService);
                            sAdapter1.notifyDataSetChanged();
                            sAdapter1.setHasStableIds(true);
                            sAdapter1.setPref(pref);
                            messagesView.setVisibility(View.VISIBLE);
                            messagesView.setItemAnimator(new DefaultItemAnimator());
                            messagesView.setAdapter(sAdapter1);
                            messagesView.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(InboxActivity.this, RecyclerView.VERTICAL, false);
                            mHorizontal.setAutoMeasureEnabled(false);
                            messagesView.setLayoutManager(mHorizontal);
                        }else{
                            norides.setVisibility(View.VISIBLE);
                            messagesView.setVisibility(View.GONE);
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
                params.put("mobile", _phoneNo2);
                params.put("salon", _phoneNo1);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.imgbutton){
            if(pref.getOrderID()!=null) {
                StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_MESSAGE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.w("expand", response);


                                String[] pars = response.split("error");
                                if (pars[1].contains("false")) {
                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                    if (pref.getResposibility() == 1) {
                                        mDatabase.child("Booking").child(pref.getOrderID()).child("CMSG").setValue("1");
                                    } else if (pref.getResposibility() == 2) {
                                        mDatabase.child("Booking").child(pref.getOrderID()).child("SMSG").setValue("1");
                                    }
                                    editText.setText("");
                                    getMessage();
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
                        String _phoneNo3 = null;
                        if(pref.getResposibility()==1) {
                            _phoneNo3=_phoneNo1;
                        }else {
                            _phoneNo3=_phoneNo2;
                        }

                        params.put("name", _phoneNo3);
                        params.put("msg", editText.getText().toString());
                        params.put("otp", pref.getOrderID());
                        params.put("who", String.valueOf(pref.getResposibility()));
                        return params;
                    }

                };
                AppController.getInstance().addToRequestQueue(eventoReq);
            }else{
                if (!InboxActivity.this.isFinishing()) {
                    if(pref.getResposibility()==1) {
                        new AlertDialog.Builder(InboxActivity.this, R.style.AlertDialogTheme)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Please book an appointment")
                                .setMessage("You can start messaging the salon after booking an appointment!")
                                .setPositiveButton("Book", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent o = new Intent(InboxActivity.this, PopulateSalons.class);
                                        startActivity(o);
                                        finish();
                                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }else {
                        if(pref.getResposibility()==1) {
                            new AlertDialog.Builder(InboxActivity.this, R.style.AlertDialogTheme)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setTitle("No appointment booked.")
                                    .setMessage("Message to the customer directly.")
                                    .setPositiveButton("Message", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent o = new Intent(InboxActivity.this, PopulateSalons.class);
                                            startActivity(o);
                                            finish();
                                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                    }
                }
            }
        }


    }
    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           getMessage();
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
                            getMessage();
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
                            getMessage();
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
                            getMessage();
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
                            getMessage();
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
            if(pref.getResposibility()==2) {
                Intent o = new Intent(InboxActivity.this, SalonHomePage.class);
                o.putExtra("from", 1);
                startActivity(o);
                finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else {
                if(_from==0) {
                    Intent o = new Intent(InboxActivity.this, UserHomeScreen.class);
                    startActivity(o);
                    finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else{
                    Intent o = new Intent(InboxActivity.this, SalonHomePage.class);
                    startActivity(o);
                    finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }
            }


        }
        return true;
    }
}




