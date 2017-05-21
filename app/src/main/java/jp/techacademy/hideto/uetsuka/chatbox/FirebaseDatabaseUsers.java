package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class FirebaseDatabaseUsers extends MyFirebaseDatabase {

    public static final String FIREBASE_USERS_PATH = "users";
    public static final String FIREBASE_USERS_ROOMKEY = "rooms";
    public static final String FIREBASE_USERS_ROOMMASTERKEY = "master";
    public static final String FIREBASE_USERS_ROOMGUESTKEY = "guest";
    private String name;
    private Activity activity;


    FirebaseDatabaseUsers(FirebaseMediator firebaseMediator){
        super(firebaseMediator);
    }

    void saveUserData(String userId, String name){
        this.name = name;
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId);
        Map<String, String> data = new HashMap<>();
        data.put("name",name);
        userRef.setValue(data).addOnCompleteListener(getSaveValueListener());
    }

    void getUserName(String userId){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId);
        userRef.addListenerForSingleValueEvent(getUserNameValueEventListener());
    }

    //todo: issuccesfulはリスナー登録でないとダメか確認し、必要であればリスナーを追加
    boolean addRoom(String userId, String roomId){
        DatabaseReference userRoomRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY).child(roomId);
        if(userRoomRef.setValue(FIREBASE_USERS_ROOMMASTERKEY).isSuccessful()){
            return true;
        }else {
            return false;
        }
    }

    void loadRoomsData(String userId){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY);
        userRef.addChildEventListener(getLoadRoomsDataListener());
    }


    private OnCompleteListener getSaveValueListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseSetUserNameListener(true);
                }else{
                    firebaseMediator.firebaseDatabaseSetUserNameListener(false);
                }
            }
        };
    }

    private ValueEventListener getUserNameValueEventListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map data = (HashMap)dataSnapshot.getValue();
                firebaseMediator.firebaseDatabaseGetUserIdListener(data.get("name").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private ChildEventListener getLoadRoomsDataListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, String> data = new HashMap<>();
                data.put(dataSnapshot.getKey(), (String)dataSnapshot.getValue());
                firebaseMediator.firebaseDatabaseLoadRoomsDataListener(data);
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





}
