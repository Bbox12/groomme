package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;
import java.util.List;


public class StylishAdapter extends RecyclerView.Adapter<StylishAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Total> mItems;
    protected List<Total> list;
    private LayoutInflater mshit;

    private Context mContext;
    private String Name_p;
    private String User;
    private String rView;
    private ArrayList<Total> mModel;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;


    public StylishAdapter(Context acontext, ArrayList<Total> mItems) {
        //mshit = LayoutInflater.from(acontext);
        this.mItems = mItems;
        this.mContext = acontext;



    }
    public void setCoordinate(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }


    public ArrayList<Total> getmItems() {
        return mItems;
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setName(String name) {

        Name_p=name;

    }

    public void User(String user) {
        User=user;
    }

    public void Service_name(String s) {

        rView=s;
    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView images_p;
        private TextView _name,primary_text,primary_services;
        private TextView ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);

            images_p = itemView.findViewById(R.id.service_pic);
            _name= (TextView) itemView.findViewById(R.id.primary_text);
            primary_text=itemView.findViewById(R.id.primary_text2);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            primary_services=itemView.findViewById(R.id.primary_services);
        }


    }

    @Override
    public StylishAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_stylish_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final StylishAdapter.ViewHolder viewHolder, final int position) {
        final Total movie = mItems.get(position);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        viewHolder.images_p.getLayoutParams().width = (int) (metrics.widthPixels * 0.5);

        if (movie.getThumbnailUrl(position) != null) {
            String url =movie.getThumbnailUrl(position).replaceAll(" ", "%20");
            ImageLoader imageLoader = LruBitmapCache.getInstance(mContext)
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(viewHolder.images_p,
                    R.mipmap.ic_noimage, R.mipmap
                            .ic_noimage));
            viewHolder.images_p.setImageUrl(url, imageLoader);


            if (movie.getName(position) != null && !TextUtils.isEmpty(movie.getName(position))) {
                viewHolder._name.setText(movie.getName(position));
            }
            if (movie.getServices(position) != null && !TextUtils.isEmpty(movie.getServices(position))) {
                viewHolder.primary_services.setVisibility(View.VISIBLE);
                viewHolder.primary_services.setText(movie.getServices(position));
            }
            if (movie.getParlours(position) != null && !TextUtils.isEmpty(movie.getParlours(position))) {
                viewHolder.primary_text.setText(movie.getParlours(position));
            }else{
                viewHolder.primary_text.setVisibility(View.GONE);
            }

               viewHolder.ratingBar.setText(movie.getRate_i(position));


            viewHolder.images_p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(pref.getResposibility()==1) {
                        pref.createLogin(movie.getmobileno(position),movie.getName(position));
                    Intent o = new Intent(mContext, SalonHomePage.class);
                    mContext.startActivity(o);
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }else {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Registration required", Snackbar.LENGTH_LONG)
                                .setAction("Register", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i3 = new Intent(mContext, Account_settings.class);
                                        ((Activity) mContext).startActivity(i3);
                                        ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                        ((Activity) mContext).finish();
                                    }
                                });
                        snackbar.setActionTextColor(Color.YELLOW);
                        snackbar.show();
                    }
                }
            });

        }
    }






    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

