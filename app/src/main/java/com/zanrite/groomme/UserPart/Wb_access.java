package com.zanrite.groomme.UserPart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.HashMap;


public class Wb_access extends Activity {

    private String postUrl;
    private double My_lat,My_long;
    private String Car_type;
    private PrefManager pref;
    private String otp;
    MyCountDownTimer myCountDownTimer;
    private ProgressDialog mProgressDialog;
    private boolean first=false;
    private String from;

    public class MyCountDownTimer extends CountDownTimer {



        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }



        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {
            if (!Wb_access.this.isFinishing()) {
                if(mProgressDialog!=null) {
                    mProgressDialog.dismiss();
                }
                if(!first) {
                    if(from==null) {
                        first = true;
                        if (!Wb_access.this.isFinishing()) {
                            new AlertDialog.Builder(Wb_access.this, R.style.AlertDialogTheme)
                                    .setTitle("Thank you for booking")
                                    .setCancelable(false)
                                    .setMessage("Your booking has been registered! We will confirm your booking soon.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(pref.getResposibility()==1) {
                                                Intent i = new Intent(Wb_access.this, UserHomeScreen.class);
                                                startActivity(i);
                                                finish();
                                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                            }else{
                                                Intent i = new Intent(Wb_access.this, SalonHomePage.class);
                                                startActivity(i);
                                                finish();
                                                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                            }
                                            dialog.cancel();
                                        }
                                    })

                                    .show();
                        }
                    }else{
                        Intent i = new Intent(Wb_access.this, SalonHomePage.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }
                }
            }

        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        String _phoneNo = user.get(PrefManager.KEY_MOBILE2);
        String _userName = user.get(PrefManager.KEY_NAME2);

        from=pref.getUserPhoneNo();
        if (!Wb_access.this.isFinishing() ) {

                    mProgressDialog = new ProgressDialog(Wb_access.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    if(pref.getResposibility()==1) {
                        mProgressDialog.setTitle("Communicating salon");
                    }else{
                        mProgressDialog.setTitle("Communicating customer");
                    }
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();


        }
        if(postUrl==null){
            String msg;
            if(from==null) {
                msg = _userName +" Please open Groom me app and get details";
                postUrl = "http://139.59.38.160/Groom/App/latlongFCM.php?otp=" + String.valueOf(pref.getOrderID()) + "&msg=" + msg + "&to=" + pref.getSalonPhone();
            }else{
                msg ="OTP "+String.valueOf(pref.getOrderID())+"  appointment has been accepted";
                postUrl = "http://139.59.38.160/Groom/App/latlongFCM.php?otp=" + String.valueOf(pref.getOrderID()) + "&msg=" + msg + "&to=" + pref.getUserPhoneNo();

            }
        }

        setContentView(R.layout.web_access);
        WebView webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                myCountDownTimer = new MyCountDownTimer(5000, 500);
                myCountDownTimer.start();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(postUrl);





    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!Wb_access.this.isFinishing() && (mProgressDialog != null)) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        if(myCountDownTimer!=null) {
            myCountDownTimer.cancel();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!Wb_access.this.isFinishing() && (mProgressDialog != null)) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }

        if(myCountDownTimer!=null) {
            myCountDownTimer.cancel();
        }
    }
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            //finish();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return false;

        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!, No Internet Connection found.", Toast.LENGTH_SHORT).show();
            // finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
          //  Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();

            finish();
            super.onPageFinished(view, url);

        }
    }

}
