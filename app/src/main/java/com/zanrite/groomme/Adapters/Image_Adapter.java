package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.PrefManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Image_Adapter extends RecyclerView.Adapter<Image_Adapter.ViewHolder> {

    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.US);
    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    // The items to display in your RecyclerView
    private ArrayList<Total> mItems;
    private Context mContext;
    private String Mobile;
    private double My_lat = 0, My_long = 0;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager pref;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<String>_foods=new ArrayList<String>();
    private TextView orders;

    public Image_Adapter(Context aContext, ArrayList<Total> mItems) {
        this.mItems = mItems;
        this.mContext = aContext;

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }



    public void setPref(PrefManager pref1) {
        pref = pref1;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.welcome_slide1, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    public void setCoordinatorlayout(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Total album_pos = mItems.get(position);


        DecimalFormat dft = new DecimalFormat("0");
        if (album_pos.getThumbnailUrl(position) != null) {
            String url = album_pos.getThumbnailUrl(position).replaceAll(" ", "%20");
            if (TextUtils.isEmpty(url)) {
                url = album_pos.getThumbnailUrl(position).replaceAll(" ", "%20");
            }
            Picasso.Builder builder = new Picasso.Builder(mContext);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(url).into(viewHolder._image1);
        }
        viewHolder.discount.setText( dft.format(album_pos.getDiscount(position))+"% off");

        viewHolder.primary_name.setText(album_pos.getName(position));





        viewHolder.button2_minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.createLogin(String.valueOf(album_pos.getID(position)),album_pos.getName(position));
                if(pref.getResposibility()==1) {
                    Intent o = new Intent(mContext, SalonHomePage.class);
                    ((Activity) mContext).startActivity(o);
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else{
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Registration required", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Register", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i3 = new Intent(mContext, Account_settings.class);
                                    ((Activity) mContext).startActivity(i3);
                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                    ((Activity) mContext).finish();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView primary_name,discount;
        private ImageView _image1;
        private Button button2_minus1;
        private RelativeLayout _r1;


        public ViewHolder(View itemView) {
            super(itemView);


            _image1 =itemView.findViewById(R.id.img_profilo);
            button2_minus1=itemView.findViewById(R.id.button2_minus1);
            primary_name =itemView.findViewById(R.id.primary_name);
            _r1=itemView.findViewById(R.id._r1);
            discount=itemView.findViewById(R.id.discount);
        }

    }


}





