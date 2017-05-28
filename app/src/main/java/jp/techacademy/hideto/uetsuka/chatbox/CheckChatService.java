package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/28.
 */

public class CheckChatService extends Service {

    private ArrayList<String> roomIds;
    private DatabaseReference databaseReference;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("checker", "チェック中");

        roomIds = new ArrayList<>();
        String userId = UserInfo.getUserId(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = databaseReference.child(FirebaseDatabaseUsers.FIREBASE_USERS_PATH).child(userId).child(FirebaseDatabaseUsers.FIREBASE_USERS_ROOMKEY);
        userRef.addChildEventListener(getRoomIdListener());



        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkNewMessage(){
        for(int i=0;i<roomIds.size();i++){
            DatabaseReference chatRef = databaseReference.child(FirebaseDatabaseChat.FIREBASE_CHAT_PATH).child(roomIds.get(i));
            chatRef.addChildEventListener(getNewChatCheckListener());
        }
    }

    private ChildEventListener getRoomIdListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                roomIds.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private ChildEventListener getNewChatCheckListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Map data = (HashMap)dataSnapshot.getValue();
                String userId = (String)data.get("userId");
                if(!userId.equals(UserInfo.getUserId(CheckChatService.this))){
                    String userName = (String)data.get("userName");
                    String message = (String)data.get("conversation");
                    String date = dataSnapshot.getKey();

                    Notification notification = new Notification.Builder(CheckChatService.this)
                            .setContentTitle("ChatBox New Message")
                            .setContentText(userName + "\n" + message + "\n" + date)
                            .setVibrate(new long[]{1000,100,1000,100,2000})
                            .setSmallIcon(android.R.drawable.sym_def_app_icon)
                            .build();

                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0,notification);

                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
