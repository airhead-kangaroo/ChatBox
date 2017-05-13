package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class LoginLogic {

    private Activity activity;
    private String mailAddress = "";
    private String password = "";
    private String name = "";
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;
    private static final String LOGIN_FEIL = "ログインに失敗しました。";

    LoginLogic(Activity activity){
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("処理中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        auth = FirebaseAuth.getInstance();
    }

    void abstractFieldData(){

        EditText mailAddressField = (EditText)activity.findViewById(R.id.loginMailField);
        EditText passwordField = (EditText)activity.findViewById(R.id.loginPasswordField);
        mailAddress = mailAddressField.getText().toString();
        password = passwordField.getText().toString();

    }

    boolean checkFieldData(){
        EnterFieldValidator validator = new EnterFieldValidator();
        validator.isMailAddressBlank(mailAddress);
        validator.isPasswordBlank(password);
        if(validator.getErrorCount() > 0){
            Toast.makeText(activity, validator.getErrorMsg(),Toast.LENGTH_LONG);
            return false;
        }else{
            return true;
        }
    }

    void setMailAddress(String mailAddress){
        this.mailAddress = mailAddress;
    }

    void setPassword(String password){
        this.password = password;
    }

    void setName(String name){
        this.name = name;
    }

    void loginFirebaseAuth(){
        auth.signInWithEmailAndPassword(mailAddress,password).addOnCompleteListener(getLoginListener());

    }

    void firstLogin(){
        auth.signInWithEmailAndPassword(mailAddress,password).addOnCompleteListener(getFirstLoginListener());
    }

    void saveName(String userName){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ApplicationInfo.SHARED_PREFERENCES_NAME_KEY, userName);
        editor.apply();
    }

    FirebaseUser getFirebaseUser(){
        return auth.getCurrentUser();
    }

    private OnCompleteListener<AuthResult> getLoginListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userRef = databaseReference.child(ApplicationInfo.FIREBASE_USER_PATH).child(user.getUid());
                    userRef.addListenerForSingleValueEvent(getValueEventListener());
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }else{
                    Toast.makeText(activity,LOGIN_FEIL,Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getFirstLoginListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userRef = databaseReference.child(ApplicationInfo.FIREBASE_USER_PATH).child(user.getUid());
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    userRef.setValue(data).addOnCompleteListener(getSaveUserNameListener());
                }else{
                    Toast.makeText(activity,LOGIN_FEIL,Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private ValueEventListener getValueEventListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map data = (Map)dataSnapshot.getValue();
                saveName(data.get("name").toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private OnCompleteListener getSaveUserNameListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    saveName(name);
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                }else{
                    Toast.makeText(activity,"ユーザー名の登録に失敗しました",Toast.LENGTH_LONG).show();
                }
            }
        };
    }


}
