package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;

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
        if(!isSuccess){
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

    void getUserName(String userId){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.getUserName(userId);
    }

    void setUserName(String userName, String userId){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.saveUserData(userName,userId);
    }

    void createRoom(String roomName,String roomId,String roomToken, String roomCapacity){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.createRoom(roomName,roomId,roomToken, roomCapacity);
    }

    //todo: room側にもメンバー追加処理
    boolean addRoom(String userId, String roomId){
        getFirebaseDatabaseUsers();
        if(firebaseDatabaseUsers.addRoom(userId,roomId)){
            return true;
        }else{
            return false;
        }
    }

    void addMember(String userId, String roomId){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.addMember(userId,roomId);
    }

    void addMember(String userId, String roomId, String roomToken){
        getFirebaseDatabaseRoom();
        firebaseDatabaseRoom.addMember(userId,roomId,roomToken);
    }

    void loadRoomsData(String userId){
        getFirebaseDatabaseUsers();
        firebaseDatabaseUsers.loadRoomsData(userId);
    }


    void firebaseDatabaseGetUserIdListener(String data){
        firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.getUserName, data);
    }

    void firebaseDatabaseSetUserNameListener(boolean isSuccess){
        if(isSuccess){
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.setUserName, true);
        }else{
            firebaseListener.firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo.setUserName, false);
        }
    }

    void firebaseDatabaseCreateRoomListener(boolean isSuccess, String roomId){
        if(isSuccess){
            firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.createRoom, roomId);
        }else{
            firebaseListener.firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo.createRoom, null);
        }
    }

    void firebaseDatabaseLoadRoomsDataListener(Map<String,String> data){
        firebaseListener.firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo.loadRoomsData,data);
    }


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
