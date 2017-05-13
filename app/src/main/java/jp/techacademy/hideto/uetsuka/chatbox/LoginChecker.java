package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class LoginChecker {

    private FirebaseUser user;
    private Activity activity;
    private String userId;
    private FirebaseAuth auth;

    LoginChecker(Activity activity){
        this.activity = activity;
        this.auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    boolean isLogedin(){
        if(user == null){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            return false;
        }else{
            userId = user.getUid();
            return true;
        }
    }

    String getUserId(){
        return userId;
    }

    void logout(){
        auth.signOut();
        user = null;
        Toast.makeText(activity,"ログアウトしました", Toast.LENGTH_LONG).show();
    }
}
