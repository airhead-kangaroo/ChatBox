package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class AccountCreator {

    private Activity activity;
    private String mailAddress;
    private String password;
    private String confirmPassword;
    private String name;
    private ProgressDialog progressDialog;
    private FirebaseAuth auth;



    AccountCreator(Activity activity){
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("処理中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    void readyCreateAccount(){
        EditText mailAddressField = (EditText)this.activity.findViewById(R.id.createAccountMailField);
        mailAddress = mailAddressField.getText().toString();
        EditText passwordField = (EditText)this.activity.findViewById(R.id.createAccountPasswordField);
        password =  passwordField.getText().toString();
        EditText confirmPasswordField = (EditText)this.activity.findViewById(R.id.createAccountConfirmPasswordField);
        confirmPassword = confirmPasswordField.getText().toString();
        EditText nameField = (EditText)this.activity.findViewById(R.id.createAccountNameField);
        name = nameField.getText().toString();
    }

    boolean checkInputData(){
        EnterFieldValidator validator = new EnterFieldValidator();
        validator.isMailAddressBlank(mailAddress);
        validator.isTextNotMailAddress(mailAddress);
        validator.isPasswordBlank(password);
        validator.isPasswordConfirmClear(password, confirmPassword);
        validator.isPasswordTooShort(password);
        validator.isNameBlank(name);
        if(validator.getErrorCount() > 0){
            Toast.makeText(activity,validator.getErrorMsg(), Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    void createAccount(){
        progressDialog.show();
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mailAddress,password).addOnCompleteListener(getCreateAccountCompleteListener());
    }

    private OnCompleteListener<AuthResult> getCreateAccountCompleteListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    auth.signInWithEmailAndPassword(mailAddress, password).addOnCompleteListener(getLoginListener());
                }else{
                    Toast.makeText(activity, "アカウント作成に失敗しました", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        };
    }

    private OnCompleteListener<AuthResult> getLoginListener(){
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    LoginLogic loginLogic = new LoginLogic(activity);
                    loginLogic.setMailAddress(mailAddress);
                    loginLogic.setPassword(password);
                    loginLogic.setName(name);
                    loginLogic.firstLogin();

                }else{
                    Toast.makeText(activity, "アカウント作成に失敗しました", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
        };
    }



}
