package tw.tony.fcm_test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  @Override
  protected void onStart() {
    super.onStart();

    Intent intent = getIntent();
    String msg = intent.getStringExtra("msg");
    if (msg!=null)
      Log.d("FCM", "msg:"+msg);

    FirebaseMessaging.getInstance().subscribeToTopic("tony_test");
//            .addOnCompleteListener( new OnCompleteListener<Void>() {
//              @Override
//              public void onComplete(@NonNull Task<Void> task) {
//                String msg ="msg_subscribed";
//                if (!task.isSuccessful()) {
//                  msg = "msg_subscribe_failed";
//                }
//                Log.d("tony-fcm", msg);
//                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//              }
//            });

  }

  public void btnSubOnClick(View view){
    FirebaseMessaging.getInstance().subscribeToTopic("tony_test");
    Toast.makeText(this, "訂閱 tony_test", Toast.LENGTH_SHORT).show();
  }

  public  void btnUnSubOnClick(View view){
    FirebaseMessaging.getInstance().unsubscribeFromTopic("tony_test");
    Toast.makeText(this, "取消訂閱 tony_test", Toast.LENGTH_SHORT).show();
  }
}
