package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Populatesalonsrv extends RecyclerView.Adapter<Populatesalonsrv.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Total> mItems;
    protected List<Total> list;

    private Context mContext;
    private LatLng latLng;
    private DecimalFormat dft = new DecimalFormat("0.00");
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private int _deals=0;


    public Populatesalonsrv(Context acontext, ArrayList<Total> mItems) {
        this.mItems = mItems;
        this.mContext = acontext;



    }



    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setLatLong(LatLng latLng1) {
        latLng=latLng1;
    }


    public void setPref(PrefManager pref1) {
        pref=pref1;
    }



    public void setCoordinate(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setDeals(int from) {
        _deals=from;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView images_p;
        private TextView _name,_secondarytext;
        private Button booknow;
        private LinearLayout _L1;
        private RatingBar ratingBar;
        private TextView discount_1;



        public ViewHolder(View itemView) {
            super(itemView);

            images_p = itemView.findViewById(R.id.service_pic);
            _name= (TextView) itemView.findViewById(R.id.primary_text);
            _secondarytext= (TextView) itemView.findViewById(R.id.secondary_text);
            booknow=itemView.findViewById(R.id.booknow);
            _L1=itemView.findViewById(R.id._l1);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            discount_1=itemView.findViewById(R.id.discount_2);
        }


    }

    @Override
    public Populatesalonsrv.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;

            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.populatesalonsrv, viewGroup, false);

        return new Populatesalonsrv.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Populatesalonsrv.ViewHolder viewHolder, final int position) {
        final Total movie = mItems.get(position);



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


                if(pref.getDropLat()!=null) {
                    double dist2 = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(movie.getLatitude(position), movie.getLongitude(position)), new LatLng(Double.parseDouble(pref.getDropLat()), Double.parseDouble(pref.getDropLong()))) / 1000;

                    viewHolder._secondarytext.setText(dft.format(dist2) + " Km");

            } else {
                viewHolder._secondarytext.setVisibility(View.GONE);
            }

            if(_deals==1) {
                viewHolder.discount_1.setVisibility(View.VISIBLE);
                DecimalFormat df= new DecimalFormat("0");
                viewHolder.discount_1.setText(df.format(movie.getDiscount(position)) + "%\noff");
            }

            viewHolder.ratingBar.setRating(Float.parseFloat(movie.getRate_i(position)));
            viewHolder.booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(pref.getResposibility()==1) {
                        pref.createLogin(String.valueOf(movie.getID(position)),movie.getName(position));
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
                                        ((Activity) mContext).finish();
                                        ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
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

