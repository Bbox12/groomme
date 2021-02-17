package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.LruBitmapCache;

import java.util.ArrayList;
import java.util.List;


public class SelfieAdapter extends RecyclerView.Adapter<SelfieAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Total> mItems;
    protected List<Total> list;
    private LayoutInflater mshit;

    private Context mContext;
    private String Name_p;
    private String User;
    private String rView;
    private ArrayList<Total> mModel;


    public SelfieAdapter(Context acontext, ArrayList<Total> mItems) {
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

    public void setName(String name) {

        Name_p=name;

    }

    public void User(String user) {
        User=user;
    }

    public void Service_name(String s) {

        rView=s;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView images_p;
        private TextView _name,_address;



        public ViewHolder(View itemView) {
            super(itemView);

            images_p = itemView.findViewById(R.id.service_pic);

        }


    }

    @Override
    public SelfieAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.selfierv, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SelfieAdapter.ViewHolder viewHolder, final int position) {
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

        }
    }






    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

