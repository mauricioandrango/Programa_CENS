package com.movil.cens.app.utils;

import android.app.Notification;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.movil.cens.app.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String CHANNEL_ID = "FIREBASE_APP";
   /* @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.i("FIREBASE CENS", "notificacion app");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.cens)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }*/


    /*@Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();

        //you can get your text message here.
        String text= data.get("message");
        Log.i(ConstantUtils.TAG_APP, text);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                // optional, this is to make beautiful icon
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.drawable.cens);

        Notification n = notificationBuilder.build();

        notificationBuilder.notify();
    /*You can read more on notification here:
    https://developer.android.com/training/notify-user/build-notification.html
    https://www.youtube.com/watch?v=-iog_fmm6mE
    */
   // }*/
}
