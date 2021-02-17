package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.zanrite.groomme.Activities.SubscriptionActivity;
import com.zanrite.groomme.Models.SalonPriceModel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import java.util.ArrayList;

import static com.zanrite.groomme.Activities.StoreSlaonSpecialist.getScreenOrientation;


public class FirstAdapter extends RecyclerView.Adapter<FirstAdapter.ViewHolder> {

    private ArrayList<SalonPriceModel> mItems;
    private ArrayList<String> mService;
    private PrefManager pref;
    private int _ID = 0;
    private String _phoneNo;
    private RecyclerView _reviewRV;
    private LinearLayout L1;
    private Context mContext;
    private ProgressBar progressBar;
    private StaggeredGridLayoutManager mHorizontal;
    private CoordinatorLayout coordinatorLayout;
    private SecondAdapter hadapter;
    private LinearLayout ride_later,ride_now;


    public FirstAdapter(Context acontext, ArrayList<String> mService, ArrayList<SalonPriceModel> mItems) {
        this.mItems = mItems;
        this.mService = mService;
        this.mContext = acontext;


    }



    public void setPref(PrefManager pref1) {
        pref = pref1;
    }



    @Override
    public int getItemCount() {
        return mService == null ? 0 : mService.size();
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
                .inflate(R.layout.service_fragment_first_rv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        ArrayList<SalonPriceModel> mSecond = new ArrayList<>();
        mSecond.clear();
        viewHolder.hadapter1.setNestedScrollingEnabled(false);

        if (mService.get(position) != null) {


            for (int j = 0; j < mItems.size(); j++) {

                if (mItems.get(j).getService(position).contains(mService.get(position))) {
                    mSecond.add(mItems.get(j));
                }

                if(j==mItems.size()-1){
                        viewHolder.Name.setText(mService.get(position).toUpperCase());
                        hadapter = new SecondAdapter(mContext, mSecond);
                        LinearLayoutManager mHorizontal = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        mHorizontal.setAutoMeasureEnabled(false);
                        hadapter.notifyDataSetChanged();
                        hadapter.setPref(pref);
                        hadapter.setAdap(hadapter);
                        hadapter.setButton(ride_later,ride_now);
                        hadapter.setCoordinateLayout(coordinatorLayout);
                        viewHolder.hadapter1.setVisibility(View.VISIBLE);
                        viewHolder.hadapter1.setAdapter(hadapter);
                        viewHolder.hadapter1.setHasFixedSize(true);
                        viewHolder.hadapter1.setLayoutManager(mHorizontal);


                }
            }


        }
    }




    public int getSpan() {
        int orientation = getScreenOrientation(mContext);
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }

    public void setCoordinateLayout(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setButton(LinearLayout ride_later1, LinearLayout ride_now1) {
        ride_later=ride_later1;
        ride_now=ride_now1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView Name;
        private RecyclerView hadapter1;


        public ViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.my_service_all);
            hadapter1 = itemView.findViewById(R.id.all_fragment_rv2);


        }

    }

}






