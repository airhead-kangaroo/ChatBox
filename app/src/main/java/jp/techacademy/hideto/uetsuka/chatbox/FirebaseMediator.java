package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/18.
 */

public class FirebaseMediator {

    private Activity activity;
    private MyFirebaseAuth myFirebaseAuth;
    private FirebaseListener firebaseListener;
    private FirebaseDatabaseUsers firebaseDatabaseUsers;
    private FirebaseDatabaseRoom firebaseDatabaseRoom;
    public enum DELETE_MODE{INIT, AFTER_USER_DELETE};

    public static final String USERS_PATH = "users";

    FirebaseMediator(Activity activity, FirebaseListener firebaseListener){
        this.activity = activity;
        this.firebaseListener = firebaseListener;
    }

    //-------------------------------------------------------
    //以下FirebaseAuth系の処理
    //-------------------------------------------------------

    String getUser(){
        getMyFirebaseAuth();
        return myFirebaseAuth.getCurrentUser();
    }


    void login(String mailAddress, String password){
        getMyFirebaseAuth();
        myFirebaseAuth.login(mailAddress, password);
    }

    void logout(){
        getMyFirebaseAuth();
        myFirebaseAuth.logout();
    }

    void createAccount(String mailAddress, String password){
        getMyFirebaseAuth();
        myFirebaseAuth.createAccount(mailAddress,password);
    }

    void firebaseAuthLoginListener(boolean isLoginSuccess){
        if(isLoginSuccess){
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.normalLogin, true);
        }else {
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.normalLogin, false);
        }
    }

    void firebaseAuthCreateAccountListener(boolean isSuccess){
        if(isSuccess) {
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.createAccount, true);
        }else{
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.createAccount, false);
        }
    }

    void firebaseAuthFirstLoginListener(boolean isSuccess){
        if(isSuccess){
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.firstLogin, true);
        }else{
            firebaseListener.firebaseAuthListener(MyFirebaseAuth.ListenerInfo.firstLogin, false);
        }
    }

    private void getMyFirebaseAuth(){
        if(myFirebaseAuth == null){
            myFirebaseAuth = new MyFirebaseAuth(this);
        }
    }


    //-------------------------------------------------------
    //以下FirebaseDatabase系の処理
    //-------------------------------------------------------

    //------------[FirebaseDatabaseUser]の処理-----------------


    void loadRoomsData(String userId){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.loadRoomsData(userId);
    }

    void createRoom(String roomId,String roomToken, String roomCapacity, String userId, String userName){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.createRoom(roomId,roomToken, roomCapacity, userId, userName);
    }

    //todo: room側にもメンバー追加処理
    void addRoom(String userId, String roomId, String userName){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.addRoom(roomId,userId,userName, FirebaseDatabaseUsers.PROPERTY.GUEST);
    }

    //------------[FirebaseDatabaseRoom]の処理-----------------


    void deleteRoom(String roomId, DELETE_MODE mode){
        if(mode == DELETE_MODE.INIT){
            getFirebaseDatabaseRoom();
            firebaseDatabaseRoom.deleteRoom(roomId, DELETE_MODE.INIT);
        }else if(mode == DELETE_MODE.AFTER_USER_DELETE){
            getFirebaseDatabaseRoom();
            firebaseDatabaseRoom.deleteRoom(roomId, DELETE_MODE.AFTER_USER_DELETE);
        }

    }

    void lockRoom(String roomId){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.lockRoom(roomId);
    }

    void enterRoom(String roomId, String roomToken, String userId, String userName){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.enterRoom(roomId,roomToken,userId, userName);
    }

    void getUserList(String roomId){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.getUserList(roomId);
    }

    //------------[FirebaseDatabase]のリスナー処理-----------------


    void firebaseDatabaseGetUserNameListener(String userId, String userName){
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("userName", userName);
        firebaseListener.firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo.getUserName, data);
    }

    void firebaseDatabaseSetUserNameListener(boolean isSuccess){
        if(isSuccess){
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.setUserName, true);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.setUserName, false);
        }
    }

    void firebaseDatabaseCreateRoomListener(boolean isSuccess, String roomId, String userId, String userName){
        if(isSuccess){
            getFirebaseDatabaseUsers();
            firebaseDatabaseUsers.addRoom(roomId, userId, userName, FirebaseDatabaseUsers.PROPERTY.MASTER);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.createRoom, false);
        }
    }

    void firebaseDatabaseLoadRoomsDataListener(Map<String,String> data){
        firebaseListener.firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo.loadRoomsData,data);
    }

    void firebaseDatabaseLockRoomListener(boolean isSuccess){
        if(isSuccess){

            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.lockRoomResult,true);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.lockRoomResult,false);
        }
    }

    void firebaseDatabaseRoomEnterRoomListener(String roomId, String userId, String userName){
            getFirebaseDatabaseUsers();
            firebaseDatabaseUsers.enterRoom(roomId,userId,userName);
    }

    void firebaseDatabaseUsersEneterRoomResultListener(boolean isSuccess, String roomId){
        if(isSuccess){
            firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.enterRoomSuccess,roomId);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.enterRoomFail,false);
        }
    }

    void firebaseDatabaseEnterRoomFailuerListener(String message){
        firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.enterRoomFail, message);
    }

    void firebaseDatabaseUserIdForDeleteRoomListener(String roomId, String userId){
        if(userId != null || roomId != "" || roomId.length() != 0){
            getFirebaseDatabaseUsers();
            firebaseDatabaseUsers.deleteRoom(roomId, userId);
        }
    }

    void firebaseDatabaseUsersDeleteRoomListener(String roomId){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.deleteRoom(roomId,DELETE_MODE.AFTER_USER_DELETE);
    }

    void firebaseDatabaseUsersAddRoomMemberListener(boolean isSuccess, String roomId){
        if(isSuccess){
            firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.createRoom, roomId);
        }else{
            firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.createRoom, null);
        }
    }

    void firebaseDatabaseRoomDeleteCompleteListener(boolean isSuccess){
        if(isSuccess){
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.deleteRoom, true);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.deleteRoom, false);
        }
    }

    void firebaseDatabaseGetUserListListener(String roomId,String userId){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.getUserName(roomId, userId);
    }


    //-------------------------------------------------------
    //以下インスタンス取得のための処理
    //-------------------------------------------------------

    private void getFirebaseDatabaseUsers(){
        if(firebaseDatabaseUsers == null){
            firebaseDatabaseUsers = new FirebaseDatabaseUsers(this);
        }
    }

    private void getFirebaseDatabaseRoom(){
        if(firebaseDatabaseRoom == null){
            firebaseDatabaseRoom = new FirebaseDatabaseRoom(this);
        }
    }


}
