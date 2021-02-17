package com.zanrite.groomme.Booking;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.zanrite.groomme.Fragments.SalonServiceShow;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.MyViewHolder> {

    public String Op_time, Cl_time;
    private Context mContext;
    private ArrayList<Total> albumList;
    private String Mn_order;
    private PrefManager pref;
    private DecimalFormat df = new DecimalFormat("0.00");
    private int _from = 0;
    private double _orderValue = 0;
    private ArrayList<String> _main = new ArrayList<String>();
    private ArrayList<String> noOfitems = new ArrayList<String>();
    private int _delete=0;

    public BookingAdapter(Context mContext, ArrayList<Total> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    public void setPref(PrefManager pref1) {
        pref = pref1;
    }

    public void setFrom(int j) {
        _from = j;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookingadapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Total album = albumList.get(position);
        holder._name.setText(album.getName(position));
        holder._price.setText("R" + df.format(Double.parseDouble(album.getPrice(position))));
        holder._slNo.setText(String.valueOf(position + 1)+".");
        if (pref.getResposibility()==2) {
            holder._delete.setEnabled(false);
            holder._delete.setVisibility(View.GONE);
        }


        if(_delete==1){
            holder._delete.setVisibility(View.GONE);
        }


        if (pref.get_packagesharedPreferences() != null) {
            Set<String> set = pref.get_packagesharedPreferences();
            _main.clear();
            _main.addAll(set);
        }
        holder._delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (_delete == 0) {
                    final ArrayList<String> _main = new ArrayList<String>();
                    Set<String> set = pref.get_packagesharedPreferences();
                    _main.addAll(set);
                    if(_main.size()>1) {
                        for (int i = 0; i < _main.size(); i++) {
                            String[] pars = _main.get(i).split("\\_");
                            if (albumList.get(position).getID(position) == Integer.parseInt(pars[0])) {
                                _main.remove(i);
                                pref.set_food_items(pref.get_food_items() - 1);
                                double _orderValue = pref.get_food_money() - Double.parseDouble(pars[1]) * Double.parseDouble(albumList.get(position).getPrice(position));
                                pref.set_food_money((float) _orderValue);
                                break;
                            }

                        }
                        pref.packagesharedPreferences(_main);
                        removeAt(position);
                        if (pref.get_food_items() != 0) {
                            ((Activity)mContext).overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                            ((Activity)mContext).finish();
                            ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                            ((Activity)mContext).startActivity(((Activity)mContext).getIntent());

                        }
                    }else {
                        if (!((Activity) mContext).isFinishing()) {
                            new AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setTitle("Are you sure?")
                                    .setMessage("Your booking with " + pref.getCanteen() + " about to empty ")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            for (int i = 0; i < _main.size(); i++) {
                                                String[] pars = _main.get(i).split("\\_");
                                                if (albumList.get(position).getID(position) == Integer.parseInt(pars[0])) {
                                                    _main.remove(i);
                                                    pref.set_food_items(pref.get_food_items() - 1);
                                                    double _orderValue = pref.get_food_money() - Double.parseDouble(pars[1]) * Double.parseDouble(albumList.get(position).getPrice(position));
                                                    pref.set_food_money((float) _orderValue);
                                                    break;
                                                }

                                            }
                                            pref.packagesharedPreferences(_main);
                                            removeAt(position);
                                            pref.set_food_items(0);
                                            pref.set_food_money(0);
                                            pref.packagesharedPreferences(null);
                                            pref.setnoOfItems(null);
                                            pref.setServiceAt(0);
                                            Intent o = new Intent(mContext, SalonServiceShow.class);
                                            mContext.startActivity(o);
                                            ((Activity) mContext).finish();
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
        });

      
    }

    private void removeAt(int p1) {

        if (albumList != null && albumList.size() != 0 && albumList.get(p1) != null) {
            albumList.remove(p1);
            notifyItemRemoved(p1);
            notifyItemRangeChanged(p1, albumList.size());
        }

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void setDelete(int i) {
        _delete=i;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView _name, _price, _slNo;
        public ImageButton  _delete;


        public MyViewHolder(View view) {
            super(view);
            _name = view.findViewById(R.id._Name);
            _price = view.findViewById(R.id.price_);
            _slNo = view.findViewById(R.id._slNo);
            _delete = view.findViewById(R.id.button2_plus);
        }
    }
}