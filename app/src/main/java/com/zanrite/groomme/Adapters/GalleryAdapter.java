package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Models.servicemodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;

/**
 * Created by parag on 18/03/17.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<servicemodel> mService;
    private Context mContext;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager pref;

    public GalleryAdapter(Context aContext, ArrayList<servicemodel> mService) {
        this.mService = mService;
        this.mContext=aContext;

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setCor(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView img_profilo;
        private ExpandableListView Detail;






        public ViewHolder(View itemView) {
            super(itemView);

            img_profilo = (ImageView) itemView.findViewById(R.id.img_profilo);
            Detail = itemView.findViewById(R.id.lvExp);
            name =  itemView.findViewById(R.id.name);


        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.galleryvadpater, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final servicemodel album_pos = mService.get(position);


        if (album_pos.getPrimaryservice(position)!=null) {
            viewHolder.name.setText(album_pos.getPrimaryservice(position));

        }
        if (album_pos.getPrimaryimage(position) != null) {
            Picasso.Builder builder = new Picasso.Builder(mContext);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(album_pos.getPrimaryimage(position)).into(viewHolder.img_profilo);

        }


    }



    @Override
    public int getItemCount() {
        return mService.size();
    }

}

