package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Total> mItems;
    private Context mContext;
    private PrefManager pref;


    public MessageAdapter(Context acontext, ArrayList<Total> mItems) {
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
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_message, viewGroup, false);
        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder viewHolder, final int position) {
        final Total album_pos = mItems.get(position);


        if (pref.getResposibility()==1) {
            HashMap<String, String> user = pref.getUserDetails2();
            if (Integer.parseInt(user.get(PrefManager.KEY_MOBILE2))==album_pos.getIDUser(position)) {
                if (album_pos.getDate(position) != null) {
                    viewHolder.date1.setText(parseDateToddMMyyyy(album_pos.getDate(position)));

                }

                if (album_pos.getDetail(position) != null) {
                    viewHolder.msg.setText(album_pos.getDetail(position));

                }
                viewHolder.r1.setVisibility(View.GONE);
            }
            else{
                viewHolder.date1.setVisibility(View.GONE);
                viewHolder.msg.setVisibility(View.GONE);
                if (album_pos.getDate(position)!=null) {
                    viewHolder.date2.setText(parseDateToddMMyyyy(album_pos.getDate(position)));

                }
                if (album_pos.getDetail(position)!=null) {
                    viewHolder.rcv_body.setText(album_pos.getDetail(position));

                }
                if (album_pos.getName(position)!=null) {
                    viewHolder.name.setText(album_pos.getName(position));

                }
            }
        }else{
            HashMap<String, String> user = pref.getUserDetails();
            if (Integer.parseInt(user.get(PrefManager.KEY_MOBILE))==album_pos.getIDSalon(position)) {
                if (album_pos.getDate(position) != null) {
                    viewHolder.date1.setText(parseDateToddMMyyyy(album_pos.getDate(position)));

                }

                if (album_pos.getDetail(position) != null) {
                    viewHolder.msg.setText(album_pos.getDetail(position));

                }
                viewHolder.r1.setVisibility(View.GONE);
            }
            else{
                viewHolder.date1.setVisibility(View.GONE);
                viewHolder.msg.setVisibility(View.GONE);
                if (album_pos.getDate(position)!=null) {
                    viewHolder.date2.setText(parseDateToddMMyyyy(album_pos.getDate(position)));

                }
                if (album_pos.getDetail(position)!=null) {
                    viewHolder.rcv_body.setText(album_pos.getDetail(position));

                }
                if (album_pos.getName(position)!=null) {
                    viewHolder.name.setText(album_pos.getName(position));

                }
            }
        }






    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView msg,name,rcv_body,date1,date2;
        private RelativeLayout r1;


        public ViewHolder(View itemView) {
            super(itemView);
            date1 = itemView.findViewById(R.id.mdate);
            date2 = itemView.findViewById(R.id.rdate);
            msg = itemView.findViewById(R.id.message_body);
            name = itemView.findViewById(R.id.name);
            rcv_body = itemView.findViewById(R.id.rcv_body);
            r1=itemView.findViewById(R.id.r1);


        }

    }



    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}






