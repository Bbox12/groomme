package com.zanrite.groomme.FCM;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zanrite.groomme.Activities.SalonHomePage;
import com.zanrite.groomme.UserPart.UserHomeScreen;
import com.zanrite.groomme.helpers.Config_URL;
import com.zanrite.groomme.helpers.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by parag on 06/02/17.
 */
public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private String imageUrl;
    private PrefManager pref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        pref = new PrefManager(getApplicationContext());
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {

                    JSONObject json = new JSONObject(remoteMessage.getData().toString());
                    handleDataMessage(json);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            Intent pushNotification = new Intent(Config_URL.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String timestamp = data.getString("timestamp");
            if(!data.isNull("image")){
                if(!TextUtils.isEmpty(data.getString("image"))){
                    imageUrl = data.getString("image");
                }
            }



            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(Config_URL.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                if(pref.getResposibility()==2){
                    Intent resultIntent = new Intent(getApplicationContext(), SalonHomePage.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("reached", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(resultIntent);

                }else{
                    Intent resultIntent = new Intent(getApplicationContext(), UserHomeScreen.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("reached", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(resultIntent);
                }

            } else {

                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                Intent resultIntent;
                if(pref.getResposibility()==2){
                     resultIntent = new Intent(getApplicationContext(), SalonHomePage.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("reached", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(resultIntent);

                }else{
                     resultIntent = new Intent(getApplicationContext(), UserHomeScreen.class);
                    resultIntent.putExtra("message", message);
                    resultIntent.putExtra("reached", true);
                    resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(resultIntent);
                }
                if (imageUrl==null) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {

                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }


            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }


    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }


    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}