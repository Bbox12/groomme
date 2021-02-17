package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.ArrayList;

/**
 * Created by parag on 18/03/17.
 */
public class homepageTeamdapter extends RecyclerView.Adapter<homepageTeamdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Album> mService;
    private Context mContext;
    private int selectedPosition=-1;
    private int _from=0;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;

    public homepageTeamdapter(Context aContext, ArrayList<Album> mService) {
        this.mService = mService;
        this.mContext=aContext;

    }

    public void setFrom(int i) {
        _from=i;
    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }

    public void setCoordinate(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageThumbnail;
        private TextView Detail;
        private TextView info;
        private AppCompatCheckBox _chck;






        public ViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = (ImageView) itemView.findViewById(R.id.img_profilo);
            Detail = itemView.findViewById(R.id.name);
            info =  itemView.findViewById(R.id.service);
            _chck=itemView.findViewById(R.id._chk);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_team_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Album album_pos = mService.get(position);


        if (!album_pos.getName(position).equalsIgnoreCase("null")) {
            viewHolder.Detail.setText(album_pos.getName(position));
            viewHolder.info.setVisibility(View.VISIBLE);
            viewHolder.info.setText(album_pos.getCategory(position));

        }
        if (album_pos.getThumbnailUrl(position) != null) {
            Picasso.Builder builder = new Picasso.Builder(mContext);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(album_pos.getThumbnailUrl(position)).into(viewHolder.imageThumbnail);

        }
        viewHolder.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_from==0) {
                    viewHolder._chck.setVisibility(View.VISIBLE);
                    viewHolder._chck.setChecked(true);
                }else{
                    if(_from!=2) {
                        if (pref.getResposibility() == 1) {
                            pref.createLogin(album_pos.getmobileno(position), "");
                            Intent o = new Intent(mContext, SalonHomePage.class);
                            mContext.startActivity(o);
                            ((Activity) mContext).finish();
                            ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                        }
                    }
                }
            }
        });

        if (album_pos.isSelected(position)) {
            viewHolder._chck.setChecked(true);
            selectedPosition = position;
        } else {
            viewHolder._chck.setChecked(false);
            viewHolder._chck.setVisibility(View.GONE);
        }

        viewHolder._chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    album_pos.setSelected(true);
                    if (selectedPosition >= 0) {
                        mService.get(selectedPosition).setSelected(false);
                        notifyItemChanged(selectedPosition);
                    }
                    selectedPosition = position;
                } else {
                    album_pos.setSelected(false);
                    viewHolder._chck.setVisibility(View.GONE);
                }
            }
        });



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
    public int getItemCount() {
        return mService.size();
    }


}

