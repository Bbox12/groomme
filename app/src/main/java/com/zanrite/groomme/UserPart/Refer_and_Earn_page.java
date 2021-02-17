package com.zanrite.groomme.UserPart;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.material.snackbar.Snackbar;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.BottomBar.Account_settings;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by parag on 28/02/18.
 */

public class Refer_and_Earn_page extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Refer_and_Earn_page.class.getSimpleName();
    private static final int REQUEST_INVITED = 101;
    private Toolbar toolbar;
    private PrefManager pref;
    private String _PhoneNo;
    private double My_lat = 0, My_long = 0;
    private ProgressBar progressBar;
    private String Refer_code;
    private EditText Refer_text;
    private ImageView WhatsApp, Messenger, Email, Message, Twitter, Facebook;
    private Button Refer;
    private String strShareMessage;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_earn);

        progressBar = findViewById(R.id.progressBarRefer);
        Refer_text = findViewById(R.id.referalCode);

        WhatsApp = findViewById(R.id.whatsapp);
        Messenger = findViewById(R.id.messenger);
        Email = findViewById(R.id.email);
        Message = findViewById(R.id.message);
        Twitter = findViewById(R.id.twitter);
        Facebook = findViewById(R.id.facebok);
        Refer = findViewById(R.id.buttonRefer);

        coordinatorLayout = findViewById(R.id.refer);
        toolbar = findViewById(R.id.toolbar_later_refer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails2();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE2);

 //       strShareMessage = "\nSign up with my code " + pref.getReferalCode() +" to avail disounts! Only on " + "https://play.google.com/store/apps/details?id=" + getPackageName();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            strShareMessage= "\nSign up with my code " + pref.getReferalCode() +
                    " to avail disounts! Only on " + (Html.fromHtml("https://groomme.page.link/download",Html.FROM_HTML_MODE_LEGACY));
        } else {
            strShareMessage= "\nSign up with my code " + pref.getReferalCode() +
                    " to avail disounts! Only on " +Html.fromHtml("https://groomme.page.link/download");
        }
        Refer_text.setText(pref.getReferalCode());


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Refer_and_Earn_page.this, UserHomeScreen.class);
                startActivity(i);
                finish();
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });


        WhatsApp.setOnClickListener(this);
        Message.setOnClickListener(this);
        Messenger.setOnClickListener(this);
        Email.setOnClickListener(this);
        Twitter.setOnClickListener(this);
        Facebook.setOnClickListener(this);
        Refer.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void vollyerror(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof AuthFailureError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ServerError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof NetworkError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ParseError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.whatsapp:
                sendAppMsg(v);
                break;
            case R.id.messenger:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,
                                strShareMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.message:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "");

                smsIntent.putExtra("sms_body", strShareMessage);
                startActivity(smsIntent);
                break;
            case R.id.email:
                sendEmail(strShareMessage);
                break;
            case R.id.twitter:
                try {
                    // Check if the Twitter app is installed on the phone.
                    getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Your text");
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Twitter is not installed on this device", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.facebok:
                sendAppFacebook(v);
                break;

            case R.id.buttonRefer:
                onInviteClicked();
                break;

            default:
                break;

        }
    }

    public void sendAppFacebook(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = strShareMessage;
        // change with required  application package

        intent.setPackage("com.facebook.katana");
        intent.putExtra(Intent.EXTRA_TEXT, text);//
        startActivity(Intent.createChooser(intent, text));
    }

    public void sendAppMsg(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = strShareMessage;
        // change with required  application package

        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, text);//
        startActivity(Intent.createChooser(intent, text));
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Send mail or SMS")
                .setMessage("Download Groom Me App and book salon at ease. Discounts available on share.")
                .setDeepLink(Uri.parse("groom://use/" + "=" + _PhoneNo + ":" + Refer_code))
                .setCustomImage(Uri.parse("http://139.59.38.160/Groom/App/icon/ic_launcher-web.png"))
                .setCallToActionText("Join")
                .build();
        startActivityForResult(intent, REQUEST_INVITED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //checking for our ColorSelectorActivity using request code

            case REQUEST_INVITED:
                if (resultCode == RESULT_OK) {

                    String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);


                    if (ids.length < 1) {
                        Toast.makeText(getApplicationContext(), "Please invite friends to join your community", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();

                        finish();


                    }

                    break;
                }

            default:
                break;
        }

    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    public void sendEmail(String s) {

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@groomme.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Download Groom Me");
        i.putExtra(Intent.EXTRA_TEXT, s);
        try {
            startActivity(Intent.createChooser(i, "Email us.."));
        } catch (android.content.ActivityNotFoundException ex) {

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "There are no email clients installed.", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Refer_and_Earn_page.this, UserHomeScreen.class);
            startActivity(i);
            finish();
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
        }
        return true;
    }

}
