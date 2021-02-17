package com.zanrite.groomme.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.Activities.StoreSlaonSpecialist;
import com.zanrite.groomme.Login.SignUp;
import com.zanrite.groomme.Models.Slots;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SpecialistServiceRv extends RecyclerView.Adapter<SpecialistServiceRv.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Slots> mItems;
    private Context mContext;
    private ArrayList<String> _main = new ArrayList<String>();
    private Button submit1;
    private ProgressBar progressBar;
    private ImageView specialistimage;
    private EditText specialistdetails;
    private PrefManager pref;
    private EditText specialistname;
    private EditText specialistservice;


    public SpecialistServiceRv(Context aContext, ArrayList<Slots> mItems) {
        this.mItems = mItems;
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

    @Override
    public SpecialistServiceRv.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeslotrv, viewGroup, false);
        SpecialistServiceRv.ViewHolder viewHolder = new SpecialistServiceRv.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SpecialistServiceRv.ViewHolder viewHolder, final int position) {
        final Slots album_pos = mItems.get(position);
        if (album_pos.getSlots(position) != null && !TextUtils.isEmpty(album_pos.getSlots(position))) {
            viewHolder.slots.setText(album_pos.getSlots(position));
        }

       viewHolder.slots.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(b){
                  _main.add(String.valueOf(album_pos.getSlots(position)));
                   String commaSeparatedValues = TextUtils.join(",", _main);
                  specialistservice.setText(commaSeparatedValues);
               }else{
                   for (int i = 0; i < _main.size(); i++) {

                       if (_main.get(i).contains(album_pos.getSlots(position))) {
                           _main.remove(i);
                           String commaSeparatedValues = TextUtils.join(",", _main);
                           specialistservice.setText(commaSeparatedValues);
                           break;
                       }

                   }
               }
           }
       });

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    String commaSeparatedValues = TextUtils.join(",", _main);
                if(!TextUtils.isEmpty(specialistservice.getText().toString()) && !TextUtils.isEmpty(specialistname.getText().toString())) {
                        new CreateNewUser().execute(specialistservice.getText().toString());

                }else{
                    specialistname.setError("Can not be empty");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setButton(Button submit2) {
        submit1=submit2;
    }

    public void setprogressBar(ProgressBar progressBar1) {
        progressBar=progressBar1;
    }

    public void setspecialistimage(ImageView specialistimage1) {
        specialistimage=specialistimage1;
    }

    public void setspecialistdetails(EditText specialistdetails1) {
        specialistdetails=specialistdetails1;
    }

    public void setpref(PrefManager pref1) {
        pref=pref1;
    }

    public void setspecialistname(EditText specialistname1) {
        specialistname=specialistname1;
    }

    public void setspecialistservice(EditText specialistservice1) {
        specialistservice=specialistservice1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        private RelativeLayout _rl;
        private AppCompatCheckBox slots;

        public ViewHolder(View itemView) {
            super(itemView);

            _rl = itemView.findViewById(R.id.rl);
            slots = itemView.findViewById(R.id.slots);
        }

    }

    class CreateNewUser extends AsyncTask<String, Integer, String> {


        private long totalSize;
        private boolean success=false;
        private String filePath;
        private String _phoneNo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            pref = new PrefManager(mContext);
            HashMap<String, String> user = pref.getUserDetails();
            _phoneNo = user.get(PrefManager.KEY_MOBILE);
            if (specialistimage.getDrawable() != null) {
                specialistimage.setBackground(null);
                Bitmap bitmap2 = ((BitmapDrawable) specialistimage.getDrawable()).getBitmap();
                final File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date()) + "2";
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 90, bytes);
                File destination2 = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + "2" + ".jpg");
                FileOutputStream fo;
                try {
                    destination2.createNewFile();
                    fo = new FileOutputStream(destination2);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath = destination2.getPath();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




        }



        protected String doInBackground(String... args) {
            return uploadFile(args[0]);
        }

        private String uploadFile(String arg) {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
            try {

                File sourceFile = new File(filePath);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("image", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                        .addFormDataPart("detail",   specialistdetails.getText().toString())
                        .addFormDataPart("user",_phoneNo)
                        .addFormDataPart("name", specialistname.getText().toString())
                        .addFormDataPart("service",arg)
                        .addFormDataPart("ID", String.valueOf(pref.getSpecialistID()))

                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.CREW_DETAIL)
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
               // Toast.makeText(mContext,"Slow connection. Please try again.",Toast.LENGTH_SHORT).show();
            }


            return res;

        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            progressBar.setVisibility(View.GONE);
            if (success) {
                pref.setisSpecialist(1);
                ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                alert.showDialog((Activity)mContext, "Succesfully stored information.",false);

            } else {
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog((Activity)mContext, "Failed to store information! Please try another time.",true);

            }
        }

    }
    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);

                dialog1.setContentView(R.layout.custom_dialog_failed);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        }
    }
    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate){
            if(!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                pref.setisSpecialist(1);
                dialog1.setContentView(R.layout.custom_dialog_success);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent o = new Intent(mContext, SalonHomePage.class);
                        mContext.startActivity(o);
                        ((Activity)mContext).finish();
                        ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                        dialog1.dismiss();
                    }
                });


                dialog1.show();
            }
        }
    }
}





