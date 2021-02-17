package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Activities.SalonGalleryImages;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.Models.Album;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by parag on 18/03/17.
 */
public class adapter_team extends RecyclerView.Adapter<adapter_team.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Album> mService;
    private Context mContext;
    private int _from=0;
    private PrefManager pref;
    private int _id=0;
    private int _leave=0;
    private String _PhoneNo;

    public adapter_team(Context aContext, ArrayList<Album> mService) {
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

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView overflow;
        private ImageView imageThumbnail;
        private TextView Detail;
        private TextView info,_available;
        private SwitchCompat _availability;






        public ViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = (ImageView) itemView.findViewById(R.id.pics);
            Detail = itemView.findViewById(R.id.name);
            info =  itemView.findViewById(R.id.service);
            overflow =  itemView.findViewById(R.id.overflow);
            _available =  itemView.findViewById(R.id.available);
            _availability=itemView.findViewById(R.id.monswitch);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.team_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Album album_pos = mService.get(position);
        pref = new PrefManager(mContext);
        HashMap<String, String> user = pref.getUserDetails();
         _PhoneNo = user.get(PrefManager.KEY_MOBILE);


        if (!album_pos.getDetail(position).equalsIgnoreCase("null")) {
            viewHolder.Detail.setText(album_pos.getName(position));
            viewHolder.info.setVisibility(View.VISIBLE);
            viewHolder.info.setText(album_pos.getDetail(position));

        } else {
            // status is empty, remove from view
            viewHolder.Detail.setText("Add More");
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

        if(album_pos.getAvailable(position)==0){
            viewHolder._available.setText("On Leave");
            viewHolder._available.setTextColor(mContext.getResources().getColor(R.color.quantum_googredA700));
            viewHolder._availability.setChecked(false);
        }else{
            viewHolder._available.setText("Available");
            viewHolder._available.setTextColor(mContext.getResources().getColor(R.color.green));
            viewHolder._availability.setChecked(true);
        }


        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.setSpecialistID(album_pos.getID(position));
                showPopupMenu(viewHolder.overflow);
            }
        });

        viewHolder._availability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    _leave=1;
                    viewHolder._available.setText("Available");
                }else{
                    _leave=0;
                    viewHolder._available.setText("On Leave");
                }
                pref.setSpecialistID(album_pos.getID(position));
                new LeaveUser().execute();
            }
        });


    }



    @Override
    public int getItemCount() {
        return mService.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    Intent o = new Intent(mContext, StoreSlaonSpecialistEdit.class);
                    mContext.startActivity(o);
                    ((Activity)mContext).finish();
                    ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    return true;
                case R.id.action_delete:
                   new CreateNewUser().execute();
                    return true;
                default:        pref.setSpecialistID(0);
                break;
            }
            return false;
        }
    }


    class CreateNewUser extends AsyncTask<Void, Integer, String> {


        private long totalSize;
        private boolean success=false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {



                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ID", String.valueOf(pref.getSpecialistID()))
                        .addFormDataPart("mobile", _PhoneNo)

                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.CREW_DELETE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();

                String[] pars=res.split("error");
                if(pars[1].contains("false")){
                    success = true;}else{
                    success=false;
                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                    Toast.makeText(mContext, "Slow connection. Please try again..",Toast.LENGTH_SHORT).show();

                }
            }


            return res;

        }


        protected void onPostExecute(String file_url) {

            if (success) {
                pref.setSpecialistID(0);
                Toast.makeText(mContext,"Success",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Failed to store information! Please try another time.",Toast.LENGTH_SHORT).show();

            }
            ((Activity)mContext). finish();
            ((Activity)mContext).overridePendingTransition(0, 0);
            ((Activity)mContext).startActivity(((Activity) mContext).getIntent());
        }

    }

    class LeaveUser extends AsyncTask<Void, Integer, String> {


        private long totalSize;
        private boolean success=false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {



                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("ID", String.valueOf(pref.getSpecialistID()))
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("available", String.valueOf(_leave))
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.CREW_LEAVE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();

                String[] pars=res.split("error");
                if(pars[1].contains("false")){
                    success = true;}else{
                    success=false;
                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                   Toast.makeText(mContext,"Slow connection. Please try again.",Toast.LENGTH_SHORT).show();
                }
            }


            return res;

        }


        protected void onPostExecute(String file_url) {

            if (success) {
                pref.setSpecialistID(0);
              //  Toast.makeText(mContext,"Success",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Failed to store information! Please try another time.",Toast.LENGTH_SHORT).show();

            }
        }

    }
}

