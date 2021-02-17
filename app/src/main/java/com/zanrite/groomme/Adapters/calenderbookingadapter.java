package com.zanrite.groomme.Adapters;

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

public class calenderbookingadapter extends RecyclerView.Adapter<calenderbookingadapter.MyViewHolder> {

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

    public calenderbookingadapter(Context mContext, ArrayList<Total> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }



    @Override
    public calenderbookingadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookingadapter, parent, false);

        return new calenderbookingadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final calenderbookingadapter.MyViewHolder holder, final int position) {
        final Total album = albumList.get(position);
        holder._name.setText(album.getName(position));
        holder._price.setText("R" + df.format(Double.parseDouble(album.getPrice(position))));
        holder._slNo.setText(String.valueOf(position + 1));
        holder._delete.setVisibility(View.GONE);



    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView _name, _price, _slNo;
        public ImageButton _delete;


        public MyViewHolder(View view) {
            super(view);
            _name = view.findViewById(R.id._Name);
            _price = view.findViewById(R.id.price_);
            _slNo = view.findViewById(R.id._slNo);
            _delete = view.findViewById(R.id.button2_plus);
        }
    }
}