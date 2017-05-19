package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class MyFirebaseAuth {

    private FirebaseAuth auth;
    private FirebaseMediator firebaseMediator;
    private String mailAddress;
    private String password;

    public enum ListenerInfo{normalLogin, firstLogin, createAccount}

    MyFirebaseAuth(FirebaseMediator firebaseMediator){
        auth = FirebaseAuth.getInstance();
        this.firebaseMediator = firebaseMediator;
    }

    String getCurrentUser(){
        if(auth.getCurrentUser() != null){
            return auth.getCurrentUser().getUid();
        }else{
            return null;
        }
    }

    String getUserId(){
        return auth.getCurrentUser().getUid();
    }

    void createAccount(String mailAddress, String password){
        this.mailAddress = mailAddress;
        this.password = password;
        auth.createUserWithEmailAndPassword(mailAddress,password).addOnCompleteListener(getCreateAccountCompleteListener());
    }

    void login(String mailAddress, String password){
        auth.signInWithEmailAndPassword(mailAddress, password).addOnCompleteListener(getLoginListener());
    }

    void logout(){
        auth.signOut();
    }



    private OnCompleteListener<AuthResult> getCreateAccountCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.signInWithEmailAndPassword(mailAddress, password).addOnCompleteListener(getFirstLoginListener());
                } else {
                    firebaseMediator.firebaseAuthCreateAccountListener(false);
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getFirstLoginListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseMediator.firebaseAuthFirstLoginListener(true);

                } else {
                    firebaseMediator.firebaseAuthFirstLoginListener(false);
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getLoginListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseMediator.firebaseAuthLoginListener(true);

                } else {
                    firebaseMediator.firebaseAuthLoginListener(false);
                }
            }
        };
    }


}
