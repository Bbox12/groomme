package com.zanrite.groomme.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.zanrite.groomme.Adapters.review_adapter;
import com.zanrite.groomme.Models.Total;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReviewsFragment extends Fragment {

    private static final String TAG = ReviewsFragment.class.getSimpleName();
    private String Get_review_;
    private String rating_i;
    private double rating_c;
    private double rating_s;
    private RecyclerView review_parlour;
    private ProgressBar progressBar_i,progressBar_c,progressBar_s;
    private double pro_i;
    private double pro_c;
    private double pro_s;
    private TextView rate_i;
    private TextView rate_c;
    private TextView rate_s;
    private Button review_send;
    private ProgressBar progressBar;
    private boolean isInternetPresent;
    private String UNIQUE;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private PrefManager pref;
    private String USER;
    private String Mobile;
    private String FLAG;
    private double overallrating=0;
    private CardView card_review;
    private  TextView noRides;
    private String _phoneNo;


    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V= inflater.inflate(R.layout.reviewfragment, container, false);
        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = (CoordinatorLayout) V.findViewById(R.id
                .coordinatorLayout_r);
        review_parlour= (RecyclerView) V.findViewById(R.id.parlour_review);
        rate_i= (TextView) V.findViewById(R.id.rate_i2);
        rate_c= (TextView) V.findViewById(R.id.rate_c2);
        rate_s= (TextView) V.findViewById(R.id.rate_s2);
        progressBar_i= (ProgressBar) V.findViewById(R.id.progressbar_i);
        progressBar_c= (ProgressBar)V.findViewById(R.id.progressBar_c);
        progressBar_s= (ProgressBar) V.findViewById(R.id.progressBar_s);
        progressBar =V.findViewById(R.id.progressBar_p);
        card_review=V.findViewById(R.id.card_review);
        noRides=V.findViewById(R.id.no_rides);


        return  V;
    }


    @Override
    public void onResume() {
        super.onResume();
       noRides.setVisibility(View.VISIBLE);
        card_review.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final ArrayList<Total> mParlours = new ArrayList<Total>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_REQUEST_MOBILE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);


                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray contacts = jsonObj.getJSONArray("users");


                            JSONArray items = jsonObj.getJSONArray("review");
                            if (items.length() != 0) {
                                for (int i = 0; i < items.length(); i++) {
                                    JSONObject c = items.getJSONObject(i);
                                    if (!c.isNull("Name")) {
                                        Total item = new Total();
                                        item.setRate_s(c.getString("rating_c"));
                                        item.setRate_t(c.getString("rating_s"));
                                        item.setName(c.getString("Name"));
                                        item.setReviews(c.getString("SalonReview"));
                                        mParlours.add(item);
                                    }

                                }
                            }




                            // looping through All Contacts
                            if (contacts.length() > 0) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);
                                    overallrating=c.getDouble("serviceRating");

                                }

                            }

                            JSONArray review = jsonObj.getJSONArray("sumreview");



                            // looping through All Contacts
                            if (review.length() > 0) {
                                for (int i = 0; i < review.length(); i++) {
                                    JSONObject c = review.getJSONObject(i);
                                    rating_c=c.getDouble("rating_c");
                                    rating_s=c.getDouble("rating_s");

                                }

                            }

                            if(overallrating==0){
                             noRides.setVisibility(View.VISIBLE);
                             card_review.setVisibility(View.GONE);
                             progressBar.setVisibility(View.VISIBLE);
                         }else{
                             noRides.setVisibility(View.GONE);
                             card_review.setVisibility(View.VISIBLE);
                             progressBar.setVisibility(View.GONE);
                             progressBar_i.setProgress((int) overallrating);
                             rate_i.setText(String.valueOf(overallrating));
                             rate_c.setText(String.valueOf(rating_c));
                             rate_s.setText(String.valueOf(rating_s));
                             progressBar_c.setProgress((int) rating_c);
                             progressBar_s.setProgress((int) rating_s);
                         }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                         if(mParlours.size()!=0){
                             review_parlour.setVisibility(View.VISIBLE);
                             review_adapter adapter = new review_adapter(getActivity(), mParlours);
                             adapter.notifyDataSetChanged();
                             review_parlour.setAdapter(adapter);
                             LinearLayoutManager horizontalLayoutManagaer_r
                                     = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                             review_parlour.setLayoutManager(horizontalLayoutManagaer_r);

                         }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
               // vollyError(error);

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", _phoneNo);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }
}