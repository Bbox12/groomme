package com.zanrite.groomme.Booking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.squareup.picasso.Picasso;
import com.zanrite.groomme.Fragments.SalonServiceShow;
import com.zanrite.groomme.R;
import com.zanrite.groomme.helpers.PrefManager;

import java.util.HashMap;

public class SelectePaymentOption extends AppCompatActivity implements View.OnClickListener {

    private PrefManager pref;
    private String _phoneNo;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private TextView _credit,_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentoptionselect);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        progressBar = findViewById(R.id.progressBarSplash);
        coordinatorLayout = findViewById(R.id.cor_home_main);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        _credit=findViewById(R.id.credit);
        _credit.setOnClickListener(this);

        _price=findViewById(R.id._price);
        _price.setText("R"+pref.getTotal());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelectePaymentOption.this, CheckoutTime.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
        });


        if(pref.getHeaderImage()!=null){
            ImageView Top1 = findViewById(R.id.top1);
            Picasso.Builder builder = new Picasso.Builder(SelectePaymentOption.this);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(pref.getHeaderImage()).into(Top1);
        }
    }

    @Override
    public void onClick(View view) {
       if(view.getId()==R.id.credit){
           Intent i = new Intent(SelectePaymentOption.this, Payment.class);
           startActivity(i);
           finish();
           overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_black, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(pref.getServiceAt()==1) {
                Intent i = new Intent(SelectePaymentOption.this, CheckoutTime.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else{
                Intent i = new Intent(SelectePaymentOption.this, HomeserviceSelected.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }

        }
        return true;
    }
}
