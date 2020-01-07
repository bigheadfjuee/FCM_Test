package tw.tony.fcm_test;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by tony on 2016/7/5.
 */
public class MyInstanceIDService extends FirebaseInstanceIdService {
  @Override
  public void onTokenRefresh() {
    super.onTokenRefresh();
    String token = FirebaseInstanceId.getInstance().getToken();
    Log.d("FCM", "Token:"+token);
  }
}
