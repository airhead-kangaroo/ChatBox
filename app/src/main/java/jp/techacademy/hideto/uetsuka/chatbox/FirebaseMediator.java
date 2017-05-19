package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Airhead-Kangaroo on 2017/05/18.
 */

public class FirebaseMediator {

    private Activity activity;
    private MyFirebaseAuth myFirebaseAuth;
    private FirebaseListener firebaseListener;
    private FirebaseDatabaseUsers firebaseDatabaseUsers;

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

    private void getFirebaseDatabaseUsers(){
        if(firebaseDatabaseUsers == null){
            firebaseDatabaseUsers = new FirebaseDatabaseUsers(this);
        }
    }
}
