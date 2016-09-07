package com.kalerkantho.Gcm;

import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.aapbd.utils.storage.PersistData;
import com.bangladesh_pratidin.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.MainActivity;
import com.kalerkantho.Utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wlaptop on 9/3/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    private Context con;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.

        con=getApplicationContext();

       if (AppConstant.openPush)
       {
           sendNotification(remoteMessage.getData().get("full_data"));
       }

        updateMyActivity(con,remoteMessage.getData().get("full_data"));


       // CustomNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("body"),"Test");
    }


    private void sendNotification(String messageTitle,String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500};

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    // Put the GCM message into a notification and post it.
    public void sendNotification(String msg) {

        String news_titl="",news_details="",news_id="",type="";


        try {
            JSONObject newsObj=new JSONObject(msg);
             news_titl=newsObj.optString("news_title");
             news_details=newsObj.optString("news_details");
             news_id=newsObj.optString("news_id");
             type=newsObj.optString("type");




        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent;

        if(type.equalsIgnoreCase("news"))
        {
            intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("content_id",news_id);
            intent.putExtra("is_favrt","0");

        }else
        {
            intent = new Intent(this, MainActivity.class);
        }




        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_small)
                .setContentTitle(news_titl)
                .setContentText(news_details)
                .setAutoCancel(true)
               // .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        if(PersistData.getBooleanData(getApplication(),AppConstant.notificationVibrateOn))
        {
            notificationBuilder.setVibrate(new long[] { 1000, 1000});
        }
        if(PersistData.getBooleanData(getApplication(),AppConstant.notificationSoundOn))
        {
            notificationBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

    static void updateMyActivity(Context context, String message) {
       // PersistData.setBooleanData(context, AppManager.PENDINGSTATUS,true);
        Intent intent = new Intent("unique_name");
        //put whatever data you want to send, if a
        // ny
        intent.putExtra("message", message);
        //send broadcast
        context.sendBroadcast(intent);
    }

}
