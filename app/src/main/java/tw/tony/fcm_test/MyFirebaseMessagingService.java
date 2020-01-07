package tw.tony.fcm_test;

/**
 * Created by tony on 2016/7/5.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
  static String msg;



  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);

    Log.d("FCM", "onMessageRecived: " + remoteMessage.getFrom());
    Log.d("FCM", "onMessageRecived: " + remoteMessage);

    msg = "onMessageRecived:\n";

    // 如果是 notification 格式，而 app 又在背景時，是 fcm 元件會自動產生通知
    // 但 app 在前景時，通知的內容是空的
    String type =  remoteMessage.getMessageType();

    if (type != null ) // Tony: getMessageType 只能用來判所是否 notification 格式
        msg = msg + "noficiation(getBody):" + remoteMessage.getNotification().getBody();
    // Tony 不知為何，在這裡取不到內容！？


    // data 格式要這樣判斷，不能用 getMessageType
    if (remoteMessage.getData().size() > 0) {
        msg = msg + "data(body): " + remoteMessage.getData().get("body");

      // Tony : data 格式的要自已處理通知
        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
      }


    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(new Runnable() {
      public void run() {
        Toast.makeText(getApplicationContext(), msg , Toast.LENGTH_LONG).show();
      }
    });


  }
  private void sendNotification(String messageTitle,String messageBody) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);

    long[] pattern = {500,500,500,500,500};

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
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

}
