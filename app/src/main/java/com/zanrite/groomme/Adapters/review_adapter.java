package com.zanrite.groomme.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;

import java.util.ArrayList;

/**
 * Created by parag on 31/01/17.
 */
public class review_adapter extends RecyclerView.Adapter<review_adapter.ViewHolder> {

    // The items to display in your RecyclerView
    ArrayList<Total> mItems;



    private Context mContext;

    public review_adapter(Context acontext, ArrayList<Total> mItems) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {


        public EditText User;
        public  TextView My_review;
        private RatingBar Rate_t,Rate_s;



        public ViewHolder(View itemView) {
            super(itemView);


            User= (EditText) itemView.findViewById(R.id.reviewer);
            My_review= (TextView) itemView.findViewById(R.id.reviews);
            Rate_t= (RatingBar) itemView.findViewById(R.id.rate_t);
            Rate_s= (RatingBar) itemView.findViewById(R.id.rate_s);


        }


    }

    @Override
    public review_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(review_adapter.ViewHolder viewHolder, final int position) {

        Total movie = mItems.get(position);
        if (!TextUtils.isEmpty(movie.getName(position))) {
            viewHolder.User.setText(movie.getName(position));
            viewHolder.My_review.setText(movie.getReviews(position));
            viewHolder.Rate_t.setRating(Float.valueOf(movie.getRate_s(position)));
            viewHolder.Rate_s.setRating(Float.valueOf(movie.getRate_t(position)));

        } else {
            // status is empty, remove from view
            viewHolder.User.setVisibility(View.GONE);
        }


    }




    @Override
    public int getItemCount() {
        return mItems.size();
    }



}

