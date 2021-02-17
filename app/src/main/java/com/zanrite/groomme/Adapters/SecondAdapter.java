package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Models.SalonPriceModel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<SalonPriceModel> mService;
    private Context mContext;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private ArrayList<String> _main = new ArrayList<String>();
    private LinearLayout ride_later,ride_now;
    private SecondAdapter hadapter;

    public SecondAdapter(Context aContext, ArrayList<SalonPriceModel> mService) {
        this.mService = mService;
        this.mContext=aContext;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public void setPref(PrefManager pref1) {
        pref=pref1;
    }

    public void setCoordinateLayout(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setButton(LinearLayout ride_later1, LinearLayout ride_now1) {
        ride_later=ride_later1;
        ride_now=ride_now1;
    }

    public void setAdap(SecondAdapter hadapter1) {
        hadapter=hadapter1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        private TextView _salon_price,_salon_time,_home_price,_home_time;
        private  TextView txtListChild,plzselect;
        private ImageView _edit;
        private RadioGroup radioGroup;
        private RadioButton atsalon,athome;




        public ViewHolder(View itemView) {
            super(itemView);



            _salon_price=itemView.findViewById(R.id._salon_price);
            _salon_time=itemView.findViewById(R.id._salon_time);


            txtListChild=itemView
                    .findViewById(R.id.lblListItem);

            _home_price=itemView.findViewById(R.id._home_price);
            _home_time=itemView.findViewById(R.id._home_time);
            _edit=itemView.findViewById(R.id._edit);
            radioGroup=itemView.findViewById(R.id.radioGroup);
            athome=itemView.findViewById(R.id.athome);
            atsalon=itemView.findViewById(R.id.atsalon);
            plzselect=itemView.findViewById(R.id.plzselect);


        }

    }

    @Override
    public SecondAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragmentexpandablelist, viewGroup, false);
        SecondAdapter.ViewHolder viewHolder = new SecondAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SecondAdapter.ViewHolder viewHolder, final int position) {
        final SalonPriceModel album_pos = mService.get(position);

        viewHolder.txtListChild.setText(album_pos.getName(position));

        String[] toka = album_pos.getPrice_salon(position).split("\\.");
        if(Integer.parseInt(toka[0])!=0){
            viewHolder._salon_price.setText("R "+album_pos.getPrice_salon(position));
            viewHolder._salon_time.setText(album_pos.getTime_salon(position)+" Min");
        }else{
            viewHolder._salon_price.setText("No service");
            viewHolder._salon_time.setText("No service");
            viewHolder.atsalon.setVisibility(View.GONE);
            viewHolder.atsalon.setChecked(false);
        }

        String[] poisa = album_pos.getPrice_home(position).split("\\.");
        if(Integer.parseInt(poisa[0])!=0){
            viewHolder._home_price.setText("R "+album_pos.getPrice_home(position));
            viewHolder._home_time.setText(album_pos.getTime_home(position)+" Min");
        }else{
            viewHolder._home_price.setText("No service");
            viewHolder._home_time.setText("No service");
            viewHolder.athome.setVisibility(View.GONE);
            viewHolder.athome.setChecked(false);
        }
       if(pref.getResposibility()==1 && pref.getOrderID()==null){
           viewHolder._edit.setVisibility(View.GONE);
           viewHolder.radioGroup.setVisibility(View.VISIBLE);
           if (pref.get_packagesharedPreferences() != null) {
               Set<String> set = pref.get_packagesharedPreferences();
               if(set.size()!=0){
               _main.clear();
               _main.addAll(set);
               for (String s : set) {
                   String[] pars = s.split("\\_");
                   if (Double.parseDouble(pars[0]) == album_pos.getID(position)) {
                           if (Integer.parseInt(pars[6]) == 1) {

                               viewHolder.atsalon.setChecked(true);


                           } else {
                               viewHolder.athome.setChecked(true);
                           }

                       //
                       ride_now.setVisibility(View.VISIBLE);
                      // break;
                   }
               }
               }
           }
       }else  if(pref.getResposibility()==2){
           viewHolder._edit.setVisibility(View.VISIBLE);
           viewHolder.radioGroup.setVisibility(View.GONE);
           viewHolder.plzselect.setVisibility(View.GONE);
       }



        viewHolder._edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.setFinalServiceID(album_pos.getID(position));
                showPopupMenu(viewHolder._edit);
            }
        });

        if(pref.getServiceAt()==1){
            viewHolder.athome.setVisibility(View.GONE);

        }else if(pref.getServiceAt()==2){
            viewHolder.atsalon.setVisibility(View.GONE);
        }

      /*  if(pref.getOrderID()!=null){
            viewHolder.athome.setVisibility(View.GONE);
            viewHolder.atsalon.setVisibility(View.GONE);
            viewHolder.plzselect.setText("You have already booked an appointment!");

        }*/


    /*    if(viewHolder.atsalon.getVisibility()==View.GONE && viewHolder.athome.getVisibility()==View.GONE){
            if(pref.getServiceAt()==1) {
                viewHolder.plzselect.setText("No service at salon");
            }
            if(pref.getServiceAt()==2) {
                viewHolder.plzselect.setText("No service at home");
            }
        }
*/


        viewHolder.athome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(pref.getServiceAt()!=1 && pref.getOrderID()==null) {
                    if(pref.getResposibility()==1){
                        ride_now.setVisibility(View.VISIBLE);
                    }else{
                        ride_now.setVisibility(View.GONE);
                    }

                    viewHolder.plzselect.setText("You have selected");

                    if (pref.get_packagesharedPreferences() != null) {
                        Set<String> set = pref.get_packagesharedPreferences();
                        if(set.size()!=0) {
                            _main.clear();
                            _main.addAll(set);
                            for (String s : set) {
                                String[] pars = s.split("\\_");
                                if (Double.parseDouble(pars[0]) != album_pos.getID(position)) {
                                    _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position) + "_" +
                                            album_pos.getPrice_home(position) + "_" + album_pos.getTime_home(position) +
                                            "_" + album_pos.getName(position) +
                                            "_" + position + "_" + 2);
                                    break;
                                }
                            }
                        }else {
                            _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position)+ "_" +
                                    album_pos.getPrice_home(position)+ "_" + album_pos.getTime_home(position) +
                                    "_" + album_pos.getName(position) +
                                    "_" + position+"_"+2);
                        }
                    }else{
                        _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position) + "_" +
                                album_pos.getPrice_home(position)+ "_" + album_pos.getTime_home(position) +
                                "_" + album_pos.getName(position) +
                                "_" + position+"_"+2);
                    }

                    pref.packagesharedPreferences(_main);
                    pref.setnoOfItems(_main);
                    setPrice(Double.parseDouble(album_pos.getPrice_home(position)));

                   // viewHolder._noItem.setText("1");
                    if(pref.getServiceAt()==0){
                        pref.setServiceAt(2);
                    }
                    }else{
                        Toast.makeText(mContext,"You have selected salon service. Please select all salon service",Toast.LENGTH_SHORT).show();
                        buttonView.setChecked(false);
                    }
                }else {
                    if (pref.getServiceAt() != 1) {
                        int Rate_ = pref.get_food_items();
                        if (Rate_ > 0) {
                            Rate_ = Rate_ - 1;

                            for (int i = 0; i < _main.size(); i++) {
                                String[] pars = _main.get(i).split("\\_");
                                if (album_pos.getID(position) == Integer.parseInt(pars[0])) {
                                    _main.remove(i);
                                  //  _main.add(s + "_" + Rate_ + "_" + Rate_ * Double.parseDouble(album_pos.getPrice_home(position)) + "_" + album_pos.getTime_home(position) + "_" + album_pos.getName(position) + "_" + position + "_" + 2);
                                }
                            }
                            pref.packagesharedPreferences(_main);
                            pref.setnoOfItems(_main);
                            pref.set_food_items(pref.get_food_items() - 1);
                        }

                        if (Rate_ == 0) {
                            for (int i = 0; i < _main.size(); i++) {
                                String[] pars = _main.get(i).split("\\_");
                                if (album_pos.getID(position) == Integer.parseInt(pars[0])) {
                                    _main.remove(i);
                                }
                            }
                            if (pref.getServiceAt() != 0) {
                                pref.setServiceAt(0);
                            }

                            pref.packagesharedPreferences(_main);
                            pref.setnoOfItems(_main);
                            pref.set_food_items(pref.get_food_items() - 1);
                        }
                    } else {
                        Toast.makeText(mContext, "You have selected salon service. Please select all salon service", Toast.LENGTH_SHORT).show();
                        buttonView.setChecked(false);
                    }
                }
            }
        });
        viewHolder.atsalon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(pref.getServiceAt()!=2 && pref.getOrderID()==null) {
                        if (pref.getResposibility() == 1) {
                            ride_now.setVisibility(View.VISIBLE);
                        } else {
                            ride_now.setVisibility(View.GONE);
                        }
                        viewHolder.plzselect.setText("You have selected");
                        if (pref.getServiceAt() == 0) {
                            pref.setServiceAt(1);
                        }

                        if (pref.get_packagesharedPreferences() != null) {
                            Set<String> set = pref.get_packagesharedPreferences();
                            if(set.size()!=0) {
                                _main.clear();
                                _main.addAll(set);
                                for (String s : set) {
                                    String[] pars = s.split("\\_");
                                    if (Double.parseDouble(pars[0]) != album_pos.getID(position)) {
                                        _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position) + "_" +
                                                album_pos.getPrice_salon(position) + "_" + album_pos.getTime_salon(position) +
                                                "_" + album_pos.getName(position) +
                                                "_" + position + "_" + 1);
                                        break;
                                    }
                                }
                            }else {
                                _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position) + "_" +
                                        album_pos.getPrice_salon(position) + "_" + album_pos.getTime_salon(position) +
                                        "_" + album_pos.getName(position) +
                                        "_" + position + "_" + 1);
                            }
                        }else {
                            _main.add(album_pos.getID(position) + "_" + album_pos.getPrimaryServiceID(position) + "_" +
                                    album_pos.getPrice_salon(position) + "_" + album_pos.getTime_salon(position) +
                                    "_" + album_pos.getName(position) +
                                    "_" + position + "_" + 1);
                        }
                        pref.packagesharedPreferences(_main);
                        pref.setnoOfItems(_main);
                        setPrice(Double.parseDouble(album_pos.getPrice_home(position)));

                    }else{
                        Toast.makeText(mContext,"You have selected home service. Please select all home service",Toast.LENGTH_SHORT).show();
                        buttonView.setChecked(false);
                    }

                }else {
                    if (pref.getServiceAt() != 2) {
                        int Rate_ = pref.get_food_items();
                        if (Rate_ > 0) {
                            Rate_ = Rate_ - 1;

                            for (int i = 0; i < _main.size(); i++) {
                                String[] pars = _main.get(i).split("\\_");
                                if (album_pos.getID(position) == Integer.parseInt(pars[0])) {
                                    _main.remove(i);
                                   // _main.add(s + "_" + Rate_ + "_" + Rate_ * Double.parseDouble(album_pos.getPrice_salon(position)) + "_" + album_pos.getTime_salon(position) + "_" + album_pos.getName(position) + "_" + position + "_" + 1);
                                }
                            }
                            pref.packagesharedPreferences(_main);
                            pref.setnoOfItems(_main);
                            pref.set_food_items(pref.get_food_items() - 1);
                        }

                        if (Rate_ == 0) {
                            for (int i = 0; i < _main.size(); i++) {
                                String[] pars = _main.get(i).split("\\_");
                                if (album_pos.getID(position) == Integer.parseInt(pars[0])) {
                                    _main.remove(i);
                                }
                            }
                            if (pref.getServiceAt() != 0) {
                                pref.setServiceAt(0);
                            }

                            pref.packagesharedPreferences(_main);
                            pref.setnoOfItems(_main);
                            pref.set_food_items(pref.get_food_items() - 1);
                        }
                    } else {
                        Toast.makeText(mContext, "You have selected home service. Please select all home service", Toast.LENGTH_SHORT).show();
                        buttonView.setChecked(false);
                    }
                }

            }
        });

    }




    private void setPrice(double v) {
        int itemSelected = pref.get_food_items();
        itemSelected = itemSelected + 1;
        pref.set_food_items(itemSelected);
      //  _iValue.setText(String.valueOf(pref.get_food_items()));
      //  double _orderValue = pref.get_food_money() + v;
      //  pref.set_food_money((float) _orderValue);
     //   _mValue.setText("R" + df.format(pref.get_food_money()));


    }



    @Override
    public int getItemCount() {
        return mService.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_edit, popup.getMenu());
        popup.setOnMenuItemClickListener(new SecondAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    Intent o = new Intent(mContext, SalonServiceDetailsID.class);
                    mContext.startActivity(o);
                    ((Activity)mContext).finish();
                    ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    return true;
                case R.id.action_delete:
                    HashMap<String, String> user = pref.getUserDetails();
                    final String _PhoneNo = user.get(PrefManager.KEY_MOBILE);
                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.STORE_SALONHOME_SERVICE_DELETE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.w("service", response);

                                    String[] par = response.split("error");
                                    if (par[1].contains("false")) {
                                        pref.setFinalServiceID(0);
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Succesfully stored information.", Snackbar.LENGTH_LONG)
                                                .setAction("Refresh", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        ((Activity)mContext). finish();
                                                        ((Activity)mContext).overridePendingTransition(0, 0);
                                                        ((Activity)mContext).startActivity(((Activity) mContext).getIntent());
                                                    }
                                                });

                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();


                                    }else{
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Failed to store information! Please try another time.", Snackbar.LENGTH_LONG)
                                                .setAction("Refresh", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        ((Activity)mContext). finish();
                                                        ((Activity)mContext).overridePendingTransition(0, 0);
                                                        ((Activity)mContext).startActivity(((Activity) mContext).getIntent());
                                                    }
                                                });
                                                ;
                                        snackbar.setActionTextColor(Color.RED);
                                        snackbar.show();
                                                  }

                                }



                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            vollyError(error);

                        }

                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Posting parameters to login url
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("mobile", _PhoneNo);
                            params.put("from", String.valueOf(pref.getFinalServiceID()));
                            return params;
                        }

                    };

                    // Añade la peticion a la cola
                    AppController.getInstance().addToRequestQueue(eventoReq);
                    return true;
                default:
            }
            return false;
        }
    }

    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof AuthFailureError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ServerError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof NetworkError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ParseError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }

}


