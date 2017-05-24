package jp.techacademy.hideto.uetsuka.chatbox;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Airhead-Kangaroo on 2017/05/21.
 */

public class FirebaseDatabaseRoom extends MyFirebaseDatabase {

    public static final String FIREBASE_ROOMS_PATH = "rooms";
    public static final String FIREBASE_ROOMS_MEMBERSKEY = "members";
    public static final String ROOM_LOCED = "1";
    public static final String ROOM_OPEN = "0";
    private String roomId;

    FirebaseDatabaseRoom(FirebaseMediator firebaseMediator){
        super(firebaseMediator);
    }

    void createRoom(String roomName, String roomId, String roomToken, String roomCapacity){
        Random random = new Random();
        int num = random.nextInt(9000000) + 1000000;
        this.roomId = roomId + String.valueOf(num);
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(this.roomId);
        Map<String,String> data = new HashMap<>();
        data.put("roomName", roomName);
        data.put("roomToken", roomToken);
        data.put("roomCapacity", roomCapacity);
        data.put("islock", ROOM_OPEN);
        roomRef.setValue(data).addOnCompleteListener(getCreateRoomListener());
    }

    //todo:メンバー属性を追加。名前など
    //部屋管理者用処理
    void addMember(String userId, String roomId){
            DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomId).child(FIREBASE_ROOMS_MEMBERSKEY).child("0");
            roomRef.setValue(userId);
    }

    //ゲスト用処理
    void addMember(String userId, String roomId, String roomToken){
        //処理追加　制限人数チェック　トークンチェック
    }

    void deleteRoom(String roomName){
        DatabaseReference roomRef = databaseReference.child(FIREBASE_ROOMS_PATH).child(roomName);
        roomRef.removeValue();
    }




    private OnCompleteListener getCreateRoomListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.firebaseDatabaseCreateRoomListener(true, roomId);
                }else{
                    firebaseMediator.firebaseDatabaseCreateRoomListener(false, null);
                }
            }
        };
    }
}
