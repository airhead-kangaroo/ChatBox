package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class MyFirebaseAuth {

    private static MyFirebaseAuth myFirebaseAuth;
    private FirebaseAuth auth;
    private Activity activity;
    private String mailAddress;
    private String password;
    private String name;

    MyFirebaseAuth(Activity activity){
        auth = FirebaseAuth.getInstance();
        this.activity = activity;
    }


    //Todo:引数の改良（ビルダーパターン的な感じで？）
    //引数が両方ともStringなので、引数の順番間違いでエラーが起こる可能性。
    //後ほど要対策
    void createAccount(String mailAddress, String password, String name){
        this.mailAddress = mailAddress;
        this.password = password;
        this.name = name;
        auth.createUserWithEmailAndPassword(mailAddress,password).addOnCompleteListener(getCreateAccountCompleteListener());
    }

    void login(String mailAddress, String password){
        auth.signInWithEmailAndPassword(mailAddress, password).addOnCompleteListener(getLoginListener());
    }

    void logout(){
        auth.signOut();
        UserInfo.isLogedin = false;
    }



    private OnCompleteListener<AuthResult> getCreateAccountCompleteListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    auth.signInWithEmailAndPassword(mailAddress, password).addOnCompleteListener(getFirstLoginListener());
                } else {
                    Toast.makeText(activity, "アカウント作成に失敗しました", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getFirstLoginListener() {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                } else {
                    Toast.makeText(activity, "アカウント作成時のログイン処理に失敗しました", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getLoginListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    UserInfo.isLogedin = true;
                }else{
                    Toast.makeText(activity, "ログインに失敗しました", Toast.LENGTH_LONG).show();
                }
            }
        };
    }


}
