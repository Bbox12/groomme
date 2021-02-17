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
import com.zanrite.groomme.UserPart.GooglemapApp;
import com.zanrite.groomme.helpers.LruBitmapCache;

import java.util.ArrayList;
import java.util.Random;

public class _CommodityAdapter extends RecyclerView.Adapter<_CommodityAdapter.ViewHolder> {

    private ArrayList<Total> mItems;
    private Context mContext;
    private ImageLoader imageLoader;
    private int _from = 0;
    private CoordinatorLayout coordinatorLayout;
    private String _phoneNo;


    public _CommodityAdapter(Context aContext, ArrayList<Total> mItems) {
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


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.commodityrv, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Total album_pos = mItems.get(position);

        if (album_pos.getName(position) != null && !TextUtils.isEmpty(album_pos.getName(position))) {
            viewHolder.Name.setText(album_pos.getName(position));
        }
     //   Random rnd = new Random();
     //   int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
      //  viewHolder._c1.setCardBackgroundColor(color);
      //  viewHolder._c1.setRadius(8f);


        String url = album_pos.getThumbnailUrl(position).replaceAll(" ", "%20");
        imageLoader = LruBitmapCache.getInstance(mContext)
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(viewHolder.Thumbnail,
                R.mipmap.ic_noimage, R.mipmap
                        .ic_noimage));
        viewHolder.Thumbnail.setImageUrl(url, imageLoader);

        viewHolder._c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent o = new Intent(mContext, GooglemapApp.class);
                o.putExtra("from",album_pos.getID(position));
                o.putExtra("category",album_pos.getName(position));
                mContext.startActivity(o);
                ((Activity) mContext).finish();
                ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView Name;
        private NetworkImageView Thumbnail;
        private CardView _c1;

        public ViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.primary_text);
            Thumbnail = itemView.findViewById(R.id.service_pic);
            _c1=itemView.findViewById(R.id._c1);
        }

    }


}






