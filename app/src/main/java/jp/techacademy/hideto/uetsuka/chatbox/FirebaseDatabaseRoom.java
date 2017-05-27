package jp.techacademy.hideto.uetsuka.chatbox;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Airhead-Kangaroo on 2017/05/21.
 */

public class FirebaseDatabaseRoom extends MyFirebaseDatabase {

    public static final String FIREBASE_ROOMS_PATH = "rooms";
    public static final String FIREBASE_ROOMS_MEMBERSKEY = "members";
    public static final String FIREBASE_ROOMS_LOCKKEY = "islock";
    public static final String FIREBASE_ROOMS_USERSKEY = "users";
    public static final String FIREBASE_ROOMS_NUMBER_OF_MEMBERKEY = "numberOfMember";
    public static final String FIREBASE_ROOMS_ROOM_TOKEN_KEY = "roomToken";
    public static final String FIREBASE_ROOMS_ROOM_CAPACITY_KEY = "roomCapacity";
    public static final String ROOM_LOCED = "1";
    public static final String ROOM_OPEN = "0";

    FirebaseDatabaseRoom(FirebaseMediator firebaseMediator){
        super(firebaseMediator);
    }

    void createRoom(String userSetRoomId, String roomToken, String roomCapacity, String userId, String userName){
        Random random = new Random();
        int num = random.nextInt(9000000) + 1000000;
        String roomId = userSetRoomId + String.valueOf(num);
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId);
        Map<String,String> data = new HashMap<>();
        data.put(FIREBASE_ROOMS_ROOM_TOKEN_KEY, roomToken);
        data.put(FIREBASE_ROOMS_ROOM_CAPACITY_KEY, roomCapacity);
        data.put(FIREBASE_ROOMS_LOCKKEY, ROOM_OPEN);
        data.put(FIREBASE_ROOMS_NUMBER_OF_MEMBERKEY, "1");
        roomRef.setValue(data).addOnCompleteListener(getCreateRoomListener(roomId,userId, userName));
    }

    void deleteRoom(String roomId, FirebaseMediator.DELETE_MODE mode){
        if(mode == FirebaseMediator.DELETE_MODE.INIT){
            DatabaseReference roomUserRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_USERSKEY);
            roomUserRef.addListenerForSingleValueEvent(getUsersNameForDeleteRoomListener(roomId));
        }else if(mode == FirebaseMediator.DELETE_MODE.AFTER_USER_DELETE){
            DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId);
            roomRef.removeValue().addOnCompleteListener(getRoomDeleteCompleteListener());
        }

    }

    void getUserList(String roomId){
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_USERSKEY);
        roomRef.addListenerForSingleValueEvent(getUserListListener(roomId));
    }



    void lockRoom(String roomId){
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_LOCKKEY);
        roomRef.setValue(ROOM_LOCED).addOnCompleteListener(getLockResultListener());
    }

    void enterRoom(String roomId, String roomToken, String userId, String userName){
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId);
        roomRef.addListenerForSingleValueEvent(getEnterRoomEventListener(roomId,roomToken,userId, userName));
    }




    private OnCompleteListener getCreateRoomListener(final String roomId, final String userId, final String userName){

        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseCreateRoomListener(true, roomId, userId, userName);
                    DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_USERSKEY).child("0");
                    roomRef.setValue(userId);
                }else{
                    firebaseMediator.firebaseDatabaseCreateRoomListener(false, null,null,null);
                }
            }
        };
    }

    private OnCompleteListener getLockResultListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseLockRoomListener(true);
                }else{
                    firebaseMediator.firebaseDatabaseLockRoomListener(false);
                }
            }
        };
    }

    private OnCompleteListener getRoomDeleteCompleteListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseRoomDeleteCompleteListener(true);
                }else{
                    firebaseMediator.firebaseDatabaseRoomDeleteCompleteListener(false);
                }
            }
        };
    }

    private ValueEventListener getUsersNameForDeleteRoomListener(final String roomId){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List list = (ArrayList) dataSnapshot.getValue();
                for(int i=0;i<list.size();i++){
                    firebaseMediator.firebaseDatabaseUserIdForDeleteRoomListener(roomId,(String)list.get(i));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private ValueEventListener getUserListListener(final String roomId){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List list = (ArrayList)dataSnapshot.getValue();
                for(int i=0;i<list.size();i++){
                    firebaseMediator.firebaseDatabaseGetUserListListener(roomId, (String)list.get(i));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private ValueEventListener getEnterRoomEventListener(final String roomId, final String roomToken, final String userId, final String userName){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map data = (HashMap)dataSnapshot.getValue();
                if(data == null){
                    firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("部屋IDが正しくありません");
                }else{
                    String registeredToken = (String)data.get(FIREBASE_ROOMS_ROOM_TOKEN_KEY);
                    int roomCapacity = Integer.parseInt((String)data.get(FIREBASE_ROOMS_ROOM_CAPACITY_KEY));
                    int numberOfMember = Integer.parseInt((String)data.get(FIREBASE_ROOMS_NUMBER_OF_MEMBERKEY));
                    String isLock = (String) data.get(FIREBASE_ROOMS_LOCKKEY);
                    if (!registeredToken.equals(roomToken)){
                        firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("トークンが正しくありません");
                    }else if(isLock.equals("1")){
                        firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("この部屋はロックされています");
                    }else if(numberOfMember >= roomCapacity){
                        firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("この部屋は定員オーバーです");
                    }else if(registeredToken.equals(roomToken) && isLock.equals("0")){
                        DatabaseReference roomUserRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_USERSKEY).child(String.valueOf(numberOfMember));
                        roomUserRef.setValue(userId);
                        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_NUMBER_OF_MEMBERKEY);
                        roomRef.setValue(String.valueOf(numberOfMember + 1));
                        firebaseMediator.firebaseDatabaseRoomEnterRoomListener(roomId,userId,userName);
                    }else{
                        firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("システムエラーにより入室できませんでした");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseMediator.firebaseDatabaseEnterRoomFailuerListener("部屋IDが正しくありません");
            }
        };
    }
}
