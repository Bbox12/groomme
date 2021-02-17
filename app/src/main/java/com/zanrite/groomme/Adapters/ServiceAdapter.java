package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.ServiceExpanded;
import com.zanrite.groomme.Models.servicemodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.UserPart.PopulateServices;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;

/**
 * Created by parag on 18/03/17.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<servicemodel> mService;
    private Context mContext;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager pref;

    public ServiceAdapter(Context aContext, ArrayList<servicemodel> mService) {
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
        private LinearLayout card_team;





        public ViewHolder(View itemView) {
            super(itemView);

            img_profilo = (ImageView) itemView.findViewById(R.id.img_profilo);
            Detail = itemView.findViewById(R.id.lvExp);
            name =  itemView.findViewById(R.id.name);
            card_team=itemView.findViewById(R.id.card_team);


        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.servicervadpater, viewGroup, false);
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

        viewHolder.card_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pref.getResposibility()==2) {
                    Intent o = new Intent(mContext, ServiceExpanded.class);
                    pref.setServiceID(album_pos.getID(position));
                    pref.setPrimaryimage(album_pos.getPrimaryimage(position));
                    pref.setPrimaryservice(album_pos.getPrimaryservice(position));
                    mContext.startActivity(o);
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else{
                    Intent o = new Intent(mContext, PopulateServices.class);
                    o.putExtra("from",album_pos.getID(position));
                    mContext.startActivity(o);
                    ((Activity) mContext).finish();
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }
            }
        });






    }



    @Override
    public int getItemCount() {
        return mService.size();
    }

}

