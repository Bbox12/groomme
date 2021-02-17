package com.zanrite.groomme.Fragments;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.github.andreilisun.swipedismissdialog.OnSwipeDismissListener;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDirection;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Target;
import com.zanrite.groomme.Activities.SalonGalleryImages;
import com.zanrite.groomme.Adapters.GalleryAdapter;
import com.zanrite.groomme.Models.servicemodel;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.AppController;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.LruBitmapCache;
import com.zanrite.groomme.helpers.PrefManager;
import com.zanrite.groomme.helpers.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment  {

    private Button submit;
    private LinearLayout servicelinarlayout;
    private TextView no_rides;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView serviceRv;
    private PrefManager pref;
    private Target target;
    private String _phoneNo;


    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);


        // Inflate the layout for this fragment
        View V= inflater.inflate(R.layout.galleryfragment, container, false);
        submit=V.findViewById(R.id.submit);
        servicelinarlayout=V.findViewById(R.id.servicelinarlayout);
        no_rides=V.findViewById(R.id.no_rides);
        coordinatorLayout = V.findViewById(R.id
                .cor_home_main);
        serviceRv=V.findViewById(R.id.serviceRv);
        serviceRv.setNestedScrollingEnabled(false);
         if(pref.getResposibility()==1){
             submit.setVisibility(View.GONE);
         }



        return  V;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();
        prepareListData();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(getActivity(), SalonGalleryImages.class);
                startActivity(o);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });

    }



    private void prepareListData() {
        final ArrayList<servicemodel> mService = new ArrayList<servicemodel>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GETGALLEY,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {




                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray services = jsonObj.getJSONArray("users");

                            for (int i = 0; i < services.length(); i++) {
                                JSONObject c = services.getJSONObject(i);
                                servicemodel item = new servicemodel();
                                item.setPrimaryimage(c.getString("pic"));
                                item.setPrimaryservice(c.getString("detail"));
                                item.setID(c.getInt("ID"));
                                mService.add(item);
                            }



                            if (mService.size() != 0) {
                                no_rides.setVisibility(View.GONE);
                                servicelinarlayout.setVisibility(View.VISIBLE);
                                GalleryAdapter sAdapter = new GalleryAdapter(getActivity(), mService);
                                sAdapter.notifyDataSetChanged();
                                sAdapter.setCor(coordinatorLayout);
                                serviceRv.setAdapter(sAdapter);
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                serviceRv.setLayoutManager(mLayoutManager);
                                serviceRv.addOnItemTouchListener(
                                        new RecyclerTouchListener(getActivity(), serviceRv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));

                                                        open_logout(mService.get(position).getPrimaryimage(position));
                                                    }

                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));
                            }else {
                                no_rides.setVisibility(View.VISIBLE);
                                servicelinarlayout.setVisibility(View.GONE);
                            }



                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                vollyError(error);

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",_phoneNo);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    private void open_logout(String _filePath) {

        if(!getActivity().isFinishing()  ) {

            SwipeDismissDialog.Builder builder = new SwipeDismissDialog.Builder(getActivity());

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View dialogView = inflater.inflate(R.layout.full_image, null);

            // Set the custom layout as alert dialog view
            builder.setView(dialogView);


            NetworkImageView _Profile = dialogView.findViewById(R.id.full_image_view);
            ImageLoader imageLoader = LruBitmapCache.getInstance(getActivity())
                    .getImageLoader();
            imageLoader.get(_filePath, ImageLoader.getImageListener(_Profile,
                    R.mipmap.ic_launcher, R.mipmap
                            .ic_launcher));
            _Profile.setImageUrl(_filePath, imageLoader);
            // Create the alert dialog
            final SwipeDismissDialog dialog = builder.setOnSwipeDismissListener(new OnSwipeDismissListener() {
                @Override
                public void onSwipeDismiss(View view, SwipeDismissDirection direction) {

                }
            })
                    .build();


            dialog.show();
        }

    }


    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof AuthFailureError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ServerError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof NetworkError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ParseError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getActivity(). finish();
                            getActivity().overridePendingTransition(0, 0);
                            startActivity(getActivity().getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }



    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public int getSpan() {
        int orientation = getScreenOrientation(getActivity());
        if (isTV(getActivity())) return 2;
        if (isTablet(getActivity()))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }
}
