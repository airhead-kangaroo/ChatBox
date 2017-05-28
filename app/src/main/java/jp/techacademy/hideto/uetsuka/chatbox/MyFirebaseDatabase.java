package jp.techacademy.hideto.uetsuka.chatbox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class MyFirebaseDatabase {

    protected DatabaseReference databaseReference;
    protected FirebaseMediator firebaseMediator;

    public enum ListenerInfo{getUserName,setUserName,createRoom,loadRoomsData,lockRoomResult, enterRoomFail, enterRoomSuccess, deleteRoom, loadChat, addChat}

    MyFirebaseDatabase(FirebaseMediator firebaseMediator){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.firebaseMediator = firebaseMediator;
    }
}
