package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Alarm.AlarmSoundService;
import com.zanrite.groomme.Booking.BookingAdapter;
import com.zanrite.groomme.Booking.CheckOut;
import com.zanrite.groomme.Booking.Success;
import com.zanrite.groomme.Models.Bookingmodel;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.UserPart.Wb_access;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by parag on 22/09/16.
 */
public class Ride_adapter extends RecyclerView.Adapter<Ride_adapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Bookingmodel> mItems;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private String _PhoneNo;
    private double My_lat=0,My_long=0;
    private DatabaseReference mDatabase;
    private String Driver_image;
    private String con,u_PhoneNo,Vehicle;
    private double from_lat=0,from_long=0,to_lat=0,to_long=0;
    private String OTP;
    private int Rate_ = 0;
    DecimalFormat dft=new DecimalFormat("0.000000");
    private PrefManager pref;
    private int _seat;
    private String UNIQUE_ID;
    private String _rate;
    List<String> lst = new ArrayList<String>();
    private DecimalFormat df = new DecimalFormat("0.00");
    long MillisecondTime, StartTime, EndTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, Hour;
    private int _past=0;

    public Ride_adapter(Context aContext, ArrayList<Bookingmodel> mItems) {
        this.mItems = mItems;
        this.mContext=aContext;

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    public void setPef(PrefManager pref1) {
        pref=pref1;
    }

    public void setPast(int i) {
        _past=i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private EditText name, datetime,payable,otp,specialist,serviceat;
        private TextInputLayout inputFromA, inputFromT;
        private Button laterEdit,laterCancel,laterChange;
        private RecyclerView _moreRv;
        private NetworkImageView img_profilo,img_specialist;
        private ImageView _map;
        private TextView primary_text;

        public ViewHolder(View itemView) {
            super(itemView);

            img_profilo=itemView.findViewById(R.id.img_profilo);
            name=itemView.findViewById(R.id.name);
            datetime=itemView.findViewById(R.id.datetime);
            payable=itemView.findViewById(R.id.payable);
            laterEdit=itemView.findViewById(R.id.ride_later_edit);
            laterCancel=itemView.findViewById(R.id.ride_later_cancel);
            _moreRv=itemView.findViewById(R.id.moreRv);
            otp=itemView.findViewById(R.id.otp);
            img_specialist=itemView.findViewById(R.id.img_specialist);
            primary_text=itemView.findViewById(R.id.primary_text);
            specialist=itemView.findViewById(R.id.specialist);
            laterChange=itemView.findViewById(R.id.ride_later_edit_edit);
            serviceat=itemView.findViewById(R.id.serviceat);
            _map=itemView.findViewById(R.id._map);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.later_rv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Bookingmodel album_pos = mItems.get(position);



        if(album_pos.getIsAccepted(position)==1){
            viewHolder.primary_text.setVisibility(View.VISIBLE);
            viewHolder.primary_text.setText("Accpted by salon");
        }
        if(album_pos.getIsRunning(position)==1){
            pref.setisRunning(1);
            viewHolder.primary_text.setVisibility(View.VISIBLE);
            viewHolder.primary_text.setText("Running");
        }

        if(album_pos.getIsCompleted(position)==1){
            viewHolder.primary_text.setVisibility(View.VISIBLE);
            viewHolder.primary_text.setText("Completed");
        }
        if(album_pos.getIsCancelled(position)==1){
            viewHolder.primary_text.setVisibility(View.VISIBLE);
            viewHolder.primary_text.setText("Cancelled");
            viewHolder.laterCancel.setVisibility(View.GONE);
            viewHolder.laterEdit.setVisibility(View.GONE);
        }

        if(album_pos.getIDserviceAt(position)==1){
            viewHolder.serviceat.setText("At Salon");
        }else  if(album_pos.getIDserviceAt(position)==2){
            viewHolder.serviceat.setText("At Home");
        }

        if(pref.getResposibility()==1){
            if(album_pos.getIDserviceAt(position)==1){
                viewHolder._map.setVisibility(View.VISIBLE);
            }else{
                viewHolder._map.setVisibility(View.GONE);
            }
        }else if(pref.getResposibility()==2){
            if(album_pos.getIDserviceAt(position)==2){
                viewHolder._map.setVisibility(View.VISIBLE);
            }else{
                viewHolder._map.setVisibility(View.GONE);
            }
        }

        viewHolder._map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(album_pos.getIDserviceAt(position)==1){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+ pref.getDropLat()+","+pref.getDropLong()+"&daddr="+album_pos.getParlour_latitude(position)+","+album_pos.getParlour_longitude(position)+"&mode=transit"));
                    ((Activity)mContext).startActivity(intent);
                }else  if(album_pos.getIDserviceAt(position)==2){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr="+ pref.getDropLat()+","+pref.getDropLong()+"&daddr="+album_pos.getHome_latitude(position)+","+album_pos.getHome_longitude(position)+"&mode=transit"));
                    ((Activity)mContext).startActivity(intent);
                }
            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        try {
            Date mDate = sdf.parse(album_pos.getOrderDate(position)+" "+album_pos.getActualTime(position));
            StartTime =mDate.getTime();
            Date mDate1 = sdf.parse(time1);
            long ccc=mDate1.getTime();
            MillisecondTime = ccc-StartTime;
            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);
            Hour=Seconds/3600;

            Minutes = (Seconds % 3600)/60;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(pref.getOrderID()!=null) {
            if (Minutes != 0 && Minutes < 30) {
                //  viewHolder.laterCancel.setVisibility(View.GONE);

                if (album_pos.getIsAccepted(position)!=0) {
                    viewHolder.laterEdit.setText("Start Now");
                }

            } else {

                if (pref.getOrderID().contains(String.valueOf(album_pos.getOTP(position)))) {
                    if(album_pos.getIsRunning(position)!=0) {
                        viewHolder.laterCancel.setVisibility(View.GONE);
                        viewHolder.laterEdit.setText("Stop service");
                        viewHolder.laterChange.setVisibility(View.GONE);
                    }else{
                       // viewHolder.laterCancel.setVisibility(View.GONE);
                        viewHolder.laterEdit.setText("Start Now");
                    }

                }
            }
        }



        if (!TextUtils.isEmpty(album_pos.getOrderDate(position))) {
            viewHolder.datetime.setText(album_pos.getOrderDate(position) + " " + album_pos.getSlot(position));
        }

        if (album_pos.getPayable(position) != 0) {
            viewHolder.payable.setText("R " + df.format(album_pos.getPayable(position)));
        }

        if (album_pos.getCrew_pic(position) != null) {

            String url = album_pos.getCrew_pic(position).replaceAll(" ", "%20");
            ImageLoader imageLoader = LruBitmapCache.getInstance(mContext)
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(viewHolder.img_specialist,
                    R.mipmap.ic_launcher, R.mipmap
                            .ic_launcher));
            viewHolder.img_specialist.setImageUrl(url, imageLoader);

        }
        if (pref.getResposibility() == 2) {
            if (!TextUtils.isEmpty(album_pos.getUserName(position))) {
                viewHolder.name.setText(album_pos.getUserName(position));
                viewHolder.name.setHint("Customer name");
            }
            if (album_pos.getPhoto(position) != null) {

                String url = album_pos.getPhoto(position).replaceAll(" ", "%20");
                ImageLoader imageLoader = LruBitmapCache.getInstance(mContext)
                        .getImageLoader();
                imageLoader.get(url, ImageLoader.getImageListener(viewHolder.img_profilo,
                        R.mipmap.ic_noimage, R.mipmap
                                .ic_noimage));
                viewHolder.img_profilo.setImageUrl(url, imageLoader);

            }
            viewHolder.laterChange.setVisibility(View.GONE);

        } else if (pref.getResposibility() == 1) {
            if (!TextUtils.isEmpty(album_pos.getUserName(position))) {
                viewHolder.name.setText(album_pos.getParlour_name(position));
                viewHolder.name.setHint("Salon name");
            }
            if (album_pos.getPPhoto(position) != null) {

                String url = album_pos.getPPhoto(position).replaceAll(" ", "%20");
                ImageLoader imageLoader = LruBitmapCache.getInstance(mContext)
                        .getImageLoader();
                imageLoader.get(url, ImageLoader.getImageListener(viewHolder.img_profilo,
                        R.mipmap.ic_launcher, R.mipmap
                                .ic_launcher));
                viewHolder.img_profilo.setImageUrl(url, imageLoader);

            }
            viewHolder.laterEdit.setVisibility(View.GONE);
        }

        if (album_pos.getCrew_name(position) != null) {
            viewHolder.specialist.setText(album_pos.getCrew_name(position));
        }

        if(_past==3){
            viewHolder.laterCancel.setVisibility(View.GONE);
            viewHolder.laterEdit.setVisibility(View.GONE);
            viewHolder.laterChange.setVisibility(View.GONE);
        }
        getOrders(album_pos.getOTP(position),viewHolder._moreRv,position,0, album_pos.getDiscount(position));


        if (album_pos.getOTP(position) != 0) {
            viewHolder.otp.setText(String.valueOf(album_pos.getOTP(position)));
        }

        viewHolder.laterChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pref.setOrderID(String.valueOf(album_pos.getOTP(position)));
                    pref.setName(album_pos.getParlour_name(position));
                    pref.setSalonPhone(album_pos.getParlour_mobile(position));
                    getOrders(album_pos.getOTP(position),viewHolder._moreRv,position,1,album_pos.getDiscount(position));


            }
        });


        viewHolder.laterEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(album_pos.getIsAccepted(position)==0) {
                    if (pref.getResposibility() == 2) {
                        pref.setOrderID(String.valueOf(album_pos.getOTP(position)));
                    }
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.SALON_ACCPTED,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    Log.w("bookingservices", response);
                                    String[] pars = response.split("error");

                                    if (pars[1].contains("false")) {
                                        mContext.stopService(new Intent(mContext, AlarmSoundService.class));
                                        mDatabase.child("Request").child(pref.getSalonPhone()).removeValue();
                                        mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("STATUS").setValue("2");
                                        pref.setUserPhoneNo(String.valueOf(album_pos.getUserMobile(position)));
                                        pref.setOrderID(String.valueOf(album_pos.getOTP(position)));
                                        pref.setActualTime(album_pos.getOrderDate(position) + " " + album_pos.getActualTime(position));
                                        Intent i = new Intent(mContext, Wb_access.class);
                                        i.putExtra("from",album_pos.getUserMobile(position));
                                        ((Activity) mContext).startActivity(i);
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
                            params.put("order", pref.getOrderID());
                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(eventoReq);
                }else{
                    if(album_pos.getIsRunning(position)==0) {
                        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.SALON_RUNNING,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        Log.w("bookingservices", response);
                                        String[] pars = response.split("error");
                                        if (pars[1].contains("false")) {
                                            mDatabase.child("Request").child(pref.getSalonPhone()).removeValue();
                                            mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).child("STATUS").setValue("3");
                                            String time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                            pref.setActualTime(time1);
                                            pref.setisRunning(album_pos.getOTP(position));
                                            Intent o = new Intent(mContext, SalonHomePage.class);
                                            mContext.startActivity(o);
                                            ((Activity) mContext).finish();
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
                                params.put("order", pref.getOrderID());
                                return params;
                            }

                        };
                        AppController.getInstance().addToRequestQueue(eventoReq);
                    }else{
                        Intent o = new Intent(mContext, Success.class);
                        mContext.startActivity(o);
                        ((Activity) mContext).finish();
                    }
                }

            }
        });

        viewHolder.laterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   final String url;
                   if (pref.getResposibility() == 2) {
                       url = Config_URL.SALON_CANCELED;
                       pref.setOrderID(String.valueOf(album_pos.getOTP(position)));

                   } else {
                       url = Config_URL.CUSTOMER_CANCELED;
                   }
                if(Minutes!=0 && Minutes>30) {
                   if (!((Activity) mContext).isFinishing()) {
                       AlertDialog.Builder alertbox = new AlertDialog.Builder(mContext);

                       LinearLayout ll_alert_layout = new LinearLayout(mContext);
                       ll_alert_layout.setOrientation(LinearLayout.VERTICAL);
                       final EditText ed_input = new EditText(mContext);
                       ll_alert_layout.addView(ed_input);

                       alertbox.setTitle("Cancellation reason");
                       alertbox.setMessage("Enter your reason");

                       //setting linear layout to alert dialog

                       alertbox.setView(ll_alert_layout);

                       alertbox.setNegativeButton("CANCEL",
                               new DialogInterface.OnClickListener() {

                                   public void onClick(DialogInterface arg0, int arg1) {
                                       Toast.makeText(mContext, "not cancelled", Toast.LENGTH_SHORT).show();
                                       arg0.cancel();

                                   }
                               });


                       alertbox.setPositiveButton("OK",
                               new DialogInterface.OnClickListener() {

                                   public void onClick(final DialogInterface arg0, int arg1) {
                                       if (!TextUtils.isEmpty(ed_input.getText().toString())) {
                                           StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, url,
                                                   new Response.Listener<String>() {

                                                       @Override
                                                       public void onResponse(String response) {
                                                           Log.w("bookingservices", response);
                                                           String[] pars = response.split("error");
                                                           if (pars[1].contains("false")) {
                                                               mContext.stopService(new Intent(mContext, AlarmSoundService.class));
                                                               pref.setOrderID(null);
                                                               mDatabase.child("Request").child(pref.getSalonPhone()).removeValue();
                                                               mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).removeValue();
                                                               if (pref.getResposibility() == 2) {
                                                                   Intent o = new Intent(mContext, SalonHomePage.class);
                                                                   mContext.startActivity(o);
                                                                   ((Activity) mContext).finish();

                                                               } else {
                                                                   Intent o = new Intent(mContext, UserHomeScreen.class);
                                                                   mContext.startActivity(o);
                                                                   ((Activity) mContext).finish();
                                                               }


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
                                                   params.put("order", pref.getOrderID());
                                                   params.put("msg", ed_input.getText().toString());
                                                   return params;
                                               }
                                           };
                                           AppController.getInstance().addToRequestQueue(eventoReq);
                                       }
                                   }
                               });
                       alertbox.show();
                   }

               }else{
                   if(!((Activity)mContext).isFinishing()) {
                       new AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
                               .setTitle("Cancellation window has been closed.")
                               .setMessage("You will be charged with cancellation charge if cancellation done now")
                               .setPositiveButton("Cancel Appointment", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, url,
                                               new Response.Listener<String>() {

                                                   @Override
                                                   public void onResponse(String response) {
                                                       Log.w("bookingservices", response);
                                                       String[] pars = response.split("error");
                                                       if (pars[1].contains("false")) {
                                                           mContext.stopService(new Intent(mContext, AlarmSoundService.class));
                                                           mDatabase.child("Booking").child(String.valueOf(pref.getOrderID())).removeValue();
                                                           pref.set_food_items(0);
                                                           pref.set_food_money(0);
                                                           pref.setPref1(null);
                                                           pref.setPref2(null);
                                                           pref.setPref3(null);
                                                           pref.setPref4(null);
                                                           pref.setnoOfItems(null);
                                                           pref.setServiceAt(0);
                                                           pref.packagesharedPreferences(null);
                                                           mDatabase.child("Request").child(pref.getSalonPhone()).removeValue();
                                                           pref.setOrderID(null);
                                                           if (pref.getResposibility() == 2) {
                                                               Intent o = new Intent(mContext, SalonHomePage.class);
                                                               mContext.startActivity(o);
                                                               ((Activity) mContext).finish();

                                                           } else {
                                                               Intent o = new Intent(mContext, UserHomeScreen.class);
                                                               mContext.startActivity(o);
                                                               ((Activity) mContext).finish();
                                                           }


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
                                               params.put("order", pref.getOrderID());
                                               params.put("msg", "Add cancellation charge");
                                               return params;
                                           }
                                       };
                                       AppController.getInstance().addToRequestQueue(eventoReq);
                                       dialog.cancel();
                                   }
                               })
                               .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                       dialog.cancel();
                                   }
                               })

                               .show();
                   }
               }
            }
        });
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

                            pref.packagesharedPreferences(_main);
                            pref.set_food_items(_main.size());
                            pref.setcDiscount((float) discount);

                            if (pref.get_packagesharedPreferences() != null) {
                                _moreRv.setVisibility(View.VISIBLE);
                                BookingAdapter sAdapter1 = new BookingAdapter(mContext, mItems);
                                sAdapter1.notifyDataSetChanged();
                                sAdapter1.setPref(pref);
                                sAdapter1.setDelete(1);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                                _moreRv.setLayoutManager(mLayoutManager);
                                 _moreRv.setItemAnimator(new DefaultItemAnimator());
                                _moreRv.setAdapter(sAdapter1);
                                 _moreRv.setHasFixedSize(true);

                                 }

                            if(check!=0){
                                Intent i = new Intent(mContext, CheckOut.class);
                                ((Activity) mContext).startActivity(i);
                                ((Activity) mContext).finish();
                            }

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


    @Override
    public int getItemCount() {
        return mItems.size();
    }




}





