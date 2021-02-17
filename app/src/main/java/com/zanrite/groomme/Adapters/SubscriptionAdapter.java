package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;

import java.util.ArrayList;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private ArrayList<Total> mItems;
    private Context mContext;
    private String pref;


    public SubscriptionAdapter(Context acontext, ArrayList<Total> mItems) {
        this.mItems = mItems;
        this.mContext = acontext;


    }



    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.payrv, viewGroup, false);
        SubscriptionAdapter.ViewHolder viewHolder = new SubscriptionAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SubscriptionAdapter.ViewHolder viewHolder, final int position) {
        final Total album_pos = mItems.get(position);

            if (album_pos.getName(position)!=null) {
                viewHolder._tiitle.setText((album_pos.getName(position)));

            }

            if (album_pos.getDetail(position)!=null) {
                viewHolder.primary_text.setText(album_pos.getDetail(position));

            }
            if (album_pos.getValidity(position)!=null) {
                viewHolder.secondary_text.setText("Valid For \n"+album_pos.getValidity(position)+" days");

            }
            if (album_pos.getPrice(position)!=null) {
                viewHolder._price.setText("R "+album_pos.getPrice(position));

            }



    }

    public void setPref(String pref1) {
        pref=pref1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView _price,primary_text,secondary_text,_tiitle;
        private Button booknow;


        public ViewHolder(View itemView) {
            super(itemView);
            _price = itemView.findViewById(R.id._price);
            primary_text = itemView.findViewById(R.id.primary_text);
            secondary_text = itemView.findViewById(R.id.secondary_text);
            booknow=itemView.findViewById(R.id.booknow);
            _tiitle=itemView.findViewById(R.id._tiitle);


        }

    }



}






