package com.zanrite.groomme.Booking;

import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.text.BreakIterator;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Adapters.TimeslotRv;
import com.zanrite.groomme.Adapters.homepageTeamdapter;
import com.zanrite.groomme.Fragments.SalonServiceShow;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.ConnectionDetector;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;


public class CheckoutTime extends AppCompatActivity implements View.OnClickListener{

    Boolean isInternetPresent = false;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyy-MM-dd");
    private ConnectionDetector cd;
    private PrefManager pref;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private ArrayList<Total> mItems = new ArrayList<Total>();
    private LinearLayoutManager mLayoutManager;
    private boolean isLoading = false;
    private int _last = 0;
    private int myID = 0;
    private boolean _end = false;
    private int hour;
    private String _total;
    private double _less, _more;
    private DatabaseReference mDatabase;
    private double Distance = 0;
    private String mobileIp;
    private String OTP;
    private int j = 0;
    private String commaSeparatedValues;
    private RelativeLayout _can;
    private Button date_now,date_cancel;
    private RecyclerView _specialistRv,_timeSlotRv;
    private int _specialist=0,_timeslot=0;
    private String _setActualTime;
    private TextView _t2;
    private RelativeLayout _buttons;
    private CalendarView calendarView;;
    private String _date;
    private TextView _t1;
    private String _phoneNo2;


    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.w("eat", "hi");

        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.bookingtime);
        cd = new ConnectionDetector(getApplicationContext());
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        HashMap<String, String> user2 = pref.getUserDetails();
        _phoneNo2 = user2.get(PrefManager.KEY_MOBILE);
        progressBar = findViewById(R.id.progressBarSplash);
        _can = findViewById(R.id.can);
        coordinatorLayout = findViewById(R.id.cor_home_main);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _specialistRv=findViewById(R.id.specialistRv);
        _timeSlotRv=findViewById(R.id.timerv);
        _specialistRv.setNestedScrollingEnabled(true);
        _timeSlotRv.setNestedScrollingEnabled(false);

        _buttons=findViewById(R.id.buttons);
        calendarView = (CalendarView) findViewById(R.id.calendarView);


       _t1=findViewById(R.id._t1);
        _t2=findViewById(R.id.t2);

        date_now=findViewById(R.id.date_now);
        date_cancel=findViewById(R.id.date_cancel);
        date_now.setOnClickListener(this);
        date_cancel.setOnClickListener(this);
        _specialistRv=findViewById(R.id.specialistRv);
        _specialistRv.setNestedScrollingEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CheckoutTime.this, CheckOut.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
        });

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }


        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(CheckoutTime.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }

        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date2=inFormat.parse(pref.getDate());
            calendarView.setDate(date2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }




    @Override
    protected void onStart() {
        super.onStart();
        if (pref.get_packagesharedPreferences() != null) {
            mItems.clear();
            Set<String> set = pref.get_packagesharedPreferences();
            int Rate = 0;
            for (String s : set) {
                String[] pars = s.split("\\_");
                Total items = new Total();
                items.setID(Integer.parseInt(pars[0]));
                items.setNoofItems((int) Double.parseDouble(pars[1]));
                items.setPrice(String.valueOf((int) Double.parseDouble(pars[2])));
                Rate = (int) (Double.parseDouble(pars[2]) + Rate);
                items.setName((pars[4]));
                mItems.add(items);
            }
            initCollapsingToolbar();



        }
    }

    @Override
    public void onPause() {
        super.onPause();
        progressBar.setVisibility(View.GONE);


    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();


       calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull final CalendarView calendarView, int year, int month, int day) {


               if(month<10){
                   if(day<10){
                       _date=(String.valueOf(day)+"-"+"0"+String.valueOf((month+1))+"-"+"0"+String.valueOf(year));
                   }else{
                       _date=(String.valueOf(day)+"-"+"0"+String.valueOf((month+1))+"-"+String.valueOf(year));
                   }
               }else{
                   if(day<10){
                       _date=(String.valueOf(day)+"-"+"0"+String.valueOf((month+1))+"-"+"0"+String.valueOf(year));
                   }else{
                       _date=(String.valueOf(day)+"-"+"0"+String.valueOf((month+1))+"-"+String.valueOf(year));
                   }
               }
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               try {
                   Date date2 = sdf.parse(pref.getDate());
                   Date date3 = sdf.parse(_date);

                   if (date3 != null && (date3.after(date2) || date3.compareTo(date2)==0)) {
                       final ArrayList<Slots> mSLot = new ArrayList<Slots>();
                       final ArrayList<Album> mTeam = new ArrayList<>();
                       StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                               new Response.Listener<String>() {
                                   private boolean _open = false;
                                   private int Omon = 0, Otue = 0, Owed = 0, Othr = 0, Ofri = 0, Osat = 0, Osun = 0;

                                   @Override
                                   public void onResponse(String response) {

                                       Log.w("timeslot", response);


                                       try {

                                           JSONObject jsonObj = new JSONObject(response);


                                           JSONArray timeslot = jsonObj.getJSONArray("timeslot");

                                           if (timeslot.length() > 0) {
                                               for (int i = 0; i < timeslot.length(); i++) {
                                                   JSONObject c = timeslot.getJSONObject(i);
                                                   Slots item = new Slots();
                                                   item.setActualTime(c.getString("ActualTime"));
                                                   item.setSlots(c.getString("Slot"));
                                                   item.setID(c.getInt("ID"));
                                                   mSLot.add(item);
                                               }

                                           }

                                           JSONArray timings = jsonObj.getJSONArray("timings");
                                           SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
                                           Date date = inFormat.parse(_date);
                                           Calendar calendar = Calendar.getInstance();
                                           if (date != null) {
                                               calendar.setTime(date);
                                           }
                                           int day = calendar.get(Calendar.DAY_OF_WEEK);
                                           // looping through All Contacts
                                           if (timings.length() > 0) {
                                               for (int i = 0; i < timings.length(); i++) {
                                                   JSONObject c = timings.getJSONObject(i);
                                                   if (c.getInt("sun_open_close") == 1) {
                                                       Osun = 1;
                                                   }
                                                   if (c.getInt("mon_open_close") == 1) {
                                                       Omon = 1;
                                                   }
                                                   if (c.getInt("tue_open_close") == 1) {
                                                       Otue = 1;
                                                   }
                                                   if (c.getInt("wed_open_close") == 1) {
                                                       Owed = 1;
                                                   }
                                                   if (c.getInt("thr_open_close") == 1) {
                                                       Othr = 1;
                                                   }
                                                   if (c.getInt("fri_open_close") == 1) {
                                                       Ofri = 1;
                                                   }
                                                   if (c.getInt("sat_open_close") == 1) {
                                                       Osat = 1;
                                                   }

                                               }

                                           }

                                           switch (day) {
                                               case Calendar.SUNDAY:
                                                   if (Osun != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;
                                               case Calendar.MONDAY:
                                                   if (Omon != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;
                                               case Calendar.TUESDAY:
                                                   if (Otue != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                               case Calendar.WEDNESDAY:
                                                   if (Owed != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;
                                               case Calendar.THURSDAY:
                                                   if (Othr != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;
                                               case Calendar.FRIDAY:
                                                   if (Ofri != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;
                                               case Calendar.SATURDAY:
                                                   if (Osat != 1) {
                                                       errordate();
                                                   } else {
                                                       normaldate();
                                                       _open = true;
                                                   }
                                                   break;

                                           }

                                           if (mSLot.size() != 0 && _open) {
                                               _t2.setVisibility(View.VISIBLE);
                                               _timeSlotRv.setVisibility(View.VISIBLE);
                                               TimeslotRv sAdapter = new TimeslotRv(CheckoutTime.this, mSLot);
                                               sAdapter.notifyDataSetChanged();
                                               _timeSlotRv.setAdapter(sAdapter);
                                               StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                               _timeSlotRv.setLayoutManager(mLayoutManager);

                                               _timeSlotRv.addOnItemTouchListener(
                                                       new RecyclerTouchListener(CheckoutTime.this, _timeSlotRv,
                                                               new RecyclerTouchListener.OnTouchActionListener() {


                                                                   @Override
                                                                   public void onClick(View view, final int position) {
                                                                       Log.w("gallery", String.valueOf(position));
                                                                       if (mSLot.get(position) != null) {
                                                                           _timeslot = mSLot.get(position).getID(position);
                                                                           _setActualTime = mSLot.get(position).getActualTime(position);
                                                                           pref.setActualTime(_date + " " + _setActualTime);

                                                                       }
                                                                   }

                                                                   @Override
                                                                   public void onRightSwipe(View view, int position) {

                                                                   }

                                                                   @Override
                                                                   public void onLeftSwipe(View view, int position) {

                                                                   }
                                                               }));
                                           } else {
                                               _timeSlotRv.setVisibility(View.GONE);
                                               _t2.setVisibility(View.GONE);
                                               //    Toast.makeText(getApplicationContext(),"No specialist found! Please book another salon",Toast.LENGTH_SHORT).show();

                                           }
                                           JSONArray crew = jsonObj.getJSONArray("crew");
                                           if (crew.length() > 0) {
                                               for (int i = 0; i < crew.length(); i++) {
                                                   JSONObject c = crew.getJSONObject(i);
                                                   Album item1 = new Album();
                                                   item1.setThumbnailUrl(c.getString("pic"));
                                                   item1.setName(c.getString("name"));
                                                   item1.setmobileno(c.getString("ParlourID"));
                                                   item1.setCategory(c.getString("service"));
                                                   item1.setID(c.getInt("ID"));
                                                   mTeam.add(item1);
                                               }

                                           }
                                           if (mTeam.size() > 0) {
                                               _buttons.setVisibility(View.VISIBLE);
                                               _t1.setVisibility(View.VISIBLE);
                                               _specialistRv.setVisibility(View.VISIBLE);
                                               homepageTeamdapter sAdapter = new homepageTeamdapter(CheckoutTime.this, mTeam);
                                               sAdapter.notifyDataSetChanged();
                                               sAdapter.setPref(pref);
                                               sAdapter.setCoordinate(coordinatorLayout);
                                               _specialistRv.setAdapter(sAdapter);
                                               StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan2(), StaggeredGridLayoutManager.VERTICAL);
                                               _specialistRv.setLayoutManager(mLayoutManager);
                                               _specialistRv.setItemAnimator(new DefaultItemAnimator());
                                               _specialistRv.addOnItemTouchListener(
                                                       new RecyclerTouchListener(CheckoutTime.this, _specialistRv,
                                                               new RecyclerTouchListener.OnTouchActionListener() {


                                                                   @Override
                                                                   public void onClick(View view, final int position) {
                                                                       Log.w("gallery", String.valueOf(position));
                                                                       if (mTeam.get(position) != null) {
                                                                           _specialist = mTeam.get(position).getID(position);
                                                                       }

                                                                   }

                                                                   @Override
                                                                   public void onRightSwipe(View view, int position) {

                                                                   }

                                                                   @Override
                                                                   public void onLeftSwipe(View view, int position) {

                                                                   }
                                                               }));
                                           } else {
                                               _t2.setVisibility(View.GONE);
                                               _specialistRv.setVisibility(View.GONE);
                                               _timeSlotRv.setVisibility(View.GONE);
                                               Toast.makeText(getApplicationContext(), "No specialist found! Please book another salon", Toast.LENGTH_SHORT).show();
                                           }


                                       } catch (final JSONException | ParseException e) {
                                           Log.e("HI", "Json parsing error: " + e.getMessage());


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
                               params.put("id", _phoneNo2);
                               return params;
                           }

                       };
                       AppController.getInstance().addToRequestQueue(eventoReq);
                   }else{
                       Toast.makeText(getApplicationContext(),"You can not book on this date",Toast.LENGTH_SHORT).show();
                   }

               } catch (ParseException e) {
                   e.printStackTrace();
               }


           }
       });



    }

    private void errordate() {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Salon is close on this date! Please select another date.", Snackbar.LENGTH_LONG)
                ;
        snackbar.show();
        _specialistRv.setVisibility(View.GONE);
        _timeSlotRv.setVisibility(View.GONE);
    }

    private void normaldate() {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Salon is open on this date!", Snackbar.LENGTH_LONG)
                ;
        snackbar.show();
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
        int orientation = getScreenOrientation(CheckoutTime.this);
        if (isTV(CheckoutTime.this)) return 4;
        if (isTablet(CheckoutTime.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    public int getSpan2() {
        int orientation = getScreenOrientation(CheckoutTime.this);
        if (isTV(CheckoutTime.this)) return 3;
        if (isTablet(CheckoutTime.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 3;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 3;
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



    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);


        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.date_now:
                if(!TextUtils.isEmpty(_date)) {
                    if(_specialist!=0) {
                        if(_timeslot!=0) {
                            go();
                        }else {
                            Toast.makeText(getApplicationContext(),"Please select a timeslot",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"Please select a specialist",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Please select a date",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.date_cancel:
                Intent i = new Intent(CheckoutTime.this, CheckOut.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;




            default:
                break;
        }
    }

    private void go() {

        List<String> TotalDays = new ArrayList<String>();
        if(pref.get_packagesharedPreferences()!=null) {
            Set<String> set = pref.get_packagesharedPreferences();
            TotalDays.addAll(set);
        }
        commaSeparatedValues = TextUtils.join(",", TotalDays);
        progressBar.setVisibility(View.VISIBLE);



        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,Config_URL.URL_BOOKING_PRICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("volley", response);

                        try {
                            String[] pars = response.split("error");
                            if (pars[1].contains("false")) {
                                if (!CheckoutTime.this.isFinishing()) {
                                    String[] pars_ = pars[1].split("false,");
                                    JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                                    JSONObject user = jObj.getJSONObject("user");
                                    pref.setOrderID(user.getString("OrderID"));
                                    pref.setCanteen(user.getString("IDsalon"));
                                    if(pref.getServiceAt()==1){
                                        Intent i = new Intent(CheckoutTime.this, SelectePaymentOption.class);
                                        startActivity(i);
                                        finish();
                                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                    }else{
                                        Intent i = new Intent(CheckoutTime.this, HomeserviceSelected.class);
                                        startActivity(i);
                                        finish();
                                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                    }

                                    //finish();

                                }
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", _phoneNo);
                params.put("salon", _phoneNo2);
                params.put("specialist", String.valueOf(_specialist));
                params.put("data", commaSeparatedValues);
                params.put("total", pref.getTotal());
                params.put("discount", String.valueOf(pref.getcDiscount()));
                params.put("date", _date);
                params.put("slot", String.valueOf(_timeslot));
                params.put("NoofItems", String.valueOf(pref.get_food_items()));
                params.put("home", String.valueOf(pref.getServiceAt()));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    @Override
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(CheckoutTime.this, CheckOut.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }


    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }


}






