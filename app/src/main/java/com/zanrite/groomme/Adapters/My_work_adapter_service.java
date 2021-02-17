package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.PopulateSalons;
import com.zanrite.groomme.UserPart.PopulateServices;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class My_work_adapter_service extends RecyclerView.Adapter<My_work_adapter_service.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Total> mItems;
    protected List<Total> list;
    private LayoutInflater mshit;

    private Context mContext;
    private int Name_p;
    private String User;
    private String rView;
    private ArrayList<Total> mModel;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;


    public My_work_adapter_service(Context acontext, ArrayList<Total> mItems) {
        //mshit = LayoutInflater.from(acontext);
        this.mItems = mItems;
        this.mContext = acontext;



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

    public void setName(int name1) {

        Name_p=name1;

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

    public void setCoordinate(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView images_p;
        private TextView _name,_secondarytext,_discount;
        private CardView _c1;



        public ViewHolder(View itemView) {
            super(itemView);

            images_p = itemView.findViewById(R.id.service_pic);
            _name= (TextView) itemView.findViewById(R.id.primary_text);
            _secondarytext= (TextView) itemView.findViewById(R.id.secondary_text);
            _discount=itemView.findViewById(R.id.discount_1);
            _c1=itemView.findViewById(R.id._c1);
        }


    }

    @Override
    public My_work_adapter_service.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_work_card2, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final My_work_adapter_service.ViewHolder viewHolder, final int position) {
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
            Random rnd = new Random();
          //  int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
         //   viewHolder._c1.setCardBackgroundColor(color);
         //   viewHolder._c1.setRadius(8f);

            if (movie.getNoofItems(position) != 0 ) {
                viewHolder._secondarytext.setText(String.valueOf(movie.getNoofItems(position))+" Places");
            }else {
                viewHolder._secondarytext.setText("");
            }

            if(movie.getDiscount(position)!=0){
                viewHolder._discount.setVisibility(View.VISIBLE);
                DecimalFormat dft = new DecimalFormat("0");
                viewHolder._discount.setText( dft.format(movie.getDiscount(position))+"%\noff");
            }


           viewHolder.images_p.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   if(movie.getID(position)==1001){
                       Intent ou = new Intent(mContext, PopulateSalons.class);
                       pref.setID4(0);
                       mContext.startActivity(ou);
                       ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                       ((Activity)mContext).finish();
                   }else{

                           Intent ou = new Intent(mContext, PopulateServices.class);
                           ou.putExtra("from",movie.getID(position));
                           ou.putExtra("name",movie.getName(position));
                           mContext.startActivity(ou);
                           ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                           ((Activity)mContext).finish();

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

