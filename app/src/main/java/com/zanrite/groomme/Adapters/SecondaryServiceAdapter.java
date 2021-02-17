package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.R;

import java.util.ArrayList;

public class SecondaryServiceAdapter extends RecyclerView.Adapter<SecondaryServiceAdapter.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Slots> mItems;
    private Context mContext;
    private Button submit1;
    private int selectedPosition=-1;
    private int i=0;


    public SecondaryServiceAdapter(Context aContext, ArrayList<Slots> mItems) {
        this.mItems = mItems;
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

    @Override
    public SecondaryServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.secondaryrv, viewGroup, false);
        SecondaryServiceAdapter.ViewHolder viewHolder = new SecondaryServiceAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SecondaryServiceAdapter.ViewHolder viewHolder, final int position) {
        final Slots album_pos = mItems.get(position);
        if (album_pos.getSlots(position) != null && !TextUtils.isEmpty(album_pos.getSlots(position))) {
            viewHolder._chck.setText(album_pos.getSlots(position));
        }


        if(i==0) {
            if (album_pos.isSelected(position)) {
                viewHolder._chck.setChecked(true);
                selectedPosition = position;
            } else {
                viewHolder._chck.setChecked(false);
            }

            viewHolder._chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        album_pos.setSelected(true);
                        if (selectedPosition >= 0) {
                            mItems.get(selectedPosition).setSelected(false);
                            notifyItemChanged(selectedPosition);
                        }
                        selectedPosition = position;
                    } else {
                        album_pos.setSelected(false);
                        ((Activity)mContext).overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                        ((Activity)mContext).finish();
                        ((Activity)mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        mContext.startActivity(((Activity)mContext).getIntent());
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setButton(Button submit2) {
        submit1=submit2;
    }

    public void setFrom(int i1) {
        i=i1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        private RelativeLayout _rl;
        private AppCompatCheckBox _chck;

        public ViewHolder(View itemView) {
            super(itemView);

            _rl = itemView.findViewById(R.id.rl);
            _chck = itemView.findViewById(R.id.slots);
        }

    }



}





