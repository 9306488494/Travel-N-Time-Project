package com.travelandtime;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.travelandtime.Social.Community;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.travelandtime.R.drawable.large_logo;

/**
 * Created by Yeshveer on 6/6/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }
    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //message will contain the Push Message
        String message = remoteMessage.getData().get("message");
        String value = remoteMessage.getData().get("value");
Log.e("value............",value);

        //imageUri will contain URL of the image to be displayed with Notification
        String imageUri = remoteMessage.getData().get("image");
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.

        bitmap = getBitmapfromUrl(imageUri);
        sendNotification(message, bitmap,value,message);// sendNotification(message, bitmap, TrueOrFlase);
    }



    private void sendNotification(String messageBody, Bitmap image,String value,String message) {

        // value 1 for mainActivity,2 for trip, 3 fro profile, 4 for community
        if(value.equals("1"))
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("value", value);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0   , intent,
                    PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_web_client_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationBuilder = new NotificationCompat.Builder(this,channelId)
                        .setBadgeIconType(R.color.drawable_red)/*Notification icon image*/
                        .setSmallIcon(R.drawable.smallicon)
                        .setColor(getApplication().getColor(R.color.drawable_red))
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image))/*Notification with Image*/



                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }


            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        else if(value.equals("2"))
        {
            Intent intent = new Intent(this, Trip.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("value", value);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0   , intent,
                    PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_web_client_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationBuilder = new NotificationCompat.Builder(this,channelId)
                        .setBadgeIconType(R.color.drawable_red)/*Notification icon image*/
                        .setSmallIcon(R.drawable.smallicon)
                        .setColor(getApplication().getColor(R.color.drawable_red))
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image))/*Notification with Image*/
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }


            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        else if(value.equals("3"))
        {
            Intent intent = new Intent(this, MyProfile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("value", value);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0   , intent,
                    PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_web_client_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationBuilder = new NotificationCompat.Builder(this,channelId)
                        .setBadgeIconType(R.color.drawable_red)/*Notification icon image*/
                        .setSmallIcon(R.drawable.smallicon)
                        .setColor(getApplication().getColor(R.color.drawable_red))
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image))/*Notification with Image*/
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }


            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
        else if(value.equals("4"))
        {
            Intent intent = new Intent(this, Community.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("value", value);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0   , intent,
                    PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_web_client_id);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                notificationBuilder = new NotificationCompat.Builder(this,channelId)
                        .setBadgeIconType(R.color.drawable_red)/*Notification icon image*/
                        .setSmallIcon(R.drawable.smallicon)
                        .setColor(getApplication().getColor(R.color.drawable_red))
                        .setContentTitle(messageBody)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .bigPicture(image))/*Notification with Image*/
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
            }

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Channel human readable title",
                        NotificationManager.IMPORTANCE_DEFAULT);

                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);
            }


            assert notificationManager != null;
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }




    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}