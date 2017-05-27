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
    public static final String FIREBASE_USERS_PROPERTYKEY = "property";
    public static final String FIREBASE_USERS_NAMEKEY = "name";
    public static final String FIREBASE_USERS_ROOMMASTER = "master";
    public static final String FIREBASE_USERS_ROOMGUEST = "guest";
    public enum PROPERTY{MASTER,GUEST}
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

    void getUserName(String roomId, String userId){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY).child(roomId);
        userRef.addListenerForSingleValueEvent(getUserNameValueEventListener(userId));
    }

    void addRoom(String roomId, String userId, String userName, PROPERTY property){
        DatabaseReference userRoomRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY).child(roomId);
        Map<String, String> data = new HashMap<>();
        switch (property){
            case MASTER:
                data.put(FIREBASE_USERS_PROPERTYKEY,FIREBASE_USERS_ROOMMASTER);
                break;
            case GUEST:
                data.put(FIREBASE_USERS_ROOMKEY, FIREBASE_USERS_ROOMGUEST);
        }
        data.put(FIREBASE_USERS_NAMEKEY, userName);
        userRoomRef.setValue(data).addOnCompleteListener(getAddRoomListener(roomId));

    }

    void loadRoomsData(String userId){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY);
        userRef.addChildEventListener(getLoadRoomsDataListener());
    }

    void deleteRoom(String roomId, String userId){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY).child(roomId);
        userRef.removeValue().addOnCompleteListener(getRoomDeleteListener(roomId));
    }

    void enterRoom(String roomId, String userId,String userName){
        DatabaseReference userRef = databaseReference.child(FIREBASE_USERS_PATH).child(userId).child(FIREBASE_USERS_ROOMKEY).child(roomId);
        Map<String,String> data = new HashMap<>();
        data.put(FIREBASE_USERS_NAMEKEY, userName);
        data.put(FIREBASE_USERS_PROPERTYKEY, FIREBASE_USERS_ROOMGUEST);
        userRef.setValue(data).addOnCompleteListener(getenterRoomResultListener(roomId));
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

    private OnCompleteListener getenterRoomResultListener(final String roomId){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseUsersEneterRoomResultListener(true,roomId);
                }else{
                    firebaseMediator.firebaseDatabaseUsersEneterRoomResultListener(false, roomId);
                }
            }
        };
    }

    private OnCompleteListener getAddRoomListener(final String roomId){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseUsersAddRoomMemberListener(true, roomId);
                }else{
                    firebaseMediator.firebaseDatabaseUsersAddRoomMemberListener(false, roomId);
                }
            }
        };
    }

    private OnCompleteListener getRoomDeleteListener(final String roomId){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseUsersDeleteRoomListener(roomId);
                }
            }
        };
    }

    private ValueEventListener getUserNameValueEventListener(final String userId){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap data = (HashMap)dataSnapshot.getValue();
                String userName = (String)data.get("name");
                firebaseMediator.firebaseDatabaseGetUserNameListener(userId, userName);
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
                Map<String, String> loadData;
                Map<String, String> sendData = new HashMap<>();
                loadData = (HashMap)dataSnapshot.getValue();
                String property = loadData.get("property");
                sendData.put(dataSnapshot.getKey(), property);
                firebaseMediator.firebaseDatabaseLoadRoomsDataListener(sendData);
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
