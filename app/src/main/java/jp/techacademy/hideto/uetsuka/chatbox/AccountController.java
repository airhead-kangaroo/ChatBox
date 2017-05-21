package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/18.
 */

public class AccountController implements FirebaseListener{

    private Activity activity;
    private FirebaseMediator firebaseMediator;

    AccountController(Activity activity){
        this.activity = activity;
        firebaseMediator = new FirebaseMediator(activity, this);
    }

    void checkLoginState(){
        String userId = UserInfo.getUserId(activity);
        if(userId == null){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }else{
            UserInfo.setUserId(activity, userId);
        }
    }

    boolean isLogedin(){
      if(firebaseMediator.getUser() != null){
          return true;
      }else{
          return false;
      }
    }

    void login(){
        EditText mailAddressField = (EditText)activity.findViewById(R.id.loginMailField);
        String mailAddress = mailAddressField.getText().toString();
        EditText passwordField = (EditText)activity.findViewById(R.id.loginPasswordField);
        String password = passwordField.getText().toString();
        if(validatorEntryField(mailAddress,password)){
            firebaseMediator.login(mailAddress,password);
        }
    }


    void logout(){
        firebaseMediator.logout();
        UserInfo.setUserId(activity, null);
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    void createAccount(){
        EditText mailAddressField = (EditText) activity.findViewById(R.id.createAccountMailField);
        String mailAddress = mailAddressField.getText().toString();
        EditText passwordField = (EditText) activity.findViewById(R.id.createAccountPasswordField);
        String password = passwordField.getText().toString();
        EditText passwordConfirmField = (EditText) activity.findViewById(R.id.createAccountConfirmPasswordField);
        String passwordConfirm = passwordConfirmField.getText().toString();
        if(validatorEntryField(mailAddress,password,passwordConfirm)){
            firebaseMediator.createAccount(mailAddress,password);
        }



    }

    private boolean validatorEntryField(String mailAddress, String password, String confirmPassword){
        EntryFieldValidator validator = new EntryFieldValidator();
        validator.isMailAddressBlank(mailAddress);
        validator.isPasswordTooShort(password);
        validator.isPasswordBlank(password);
        validator.isPasswordConfirmClear(password,confirmPassword);
        validator.isTextNotMailAddress(mailAddress);
        return validatorValidate(validator);
    }

    private boolean validatorEntryField(String mailAddress, String password){
        EntryFieldValidator validator = new EntryFieldValidator();
        validator.isMailAddressBlank(mailAddress);
        validator.isPasswordBlank(password);
        return validatorValidate(validator);
    }

    private boolean validatorValidate(EntryFieldValidator validator){
        int errorCount = validator.getErrorCount();
        if(errorCount > 0){
            Toast.makeText(activity,validator.getErrorMsg(),Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result) {
        switch (info){
            case normalLogin:
                if(result){
                    Toast.makeText(activity, "ログインしました",Toast.LENGTH_SHORT).show();
                    String userId = firebaseMediator.getUser();
                    UserInfo.setUserId(activity, userId);
                    activity.finish();
                }else{
                    Toast.makeText(activity, "ログインに失敗しました",Toast.LENGTH_SHORT).show();
                    EditText password = (EditText)activity.findViewById(R.id.loginPasswordField);
                    EditText mailAddress = (EditText)activity.findViewById(R.id.loginMailField);
                    password.setText("");
                    mailAddress.setText("");
                }
                break;
            case firstLogin:
                if(result){
                    Toast.makeText(activity, "アカウント作成完了！ようこそ！！",Toast.LENGTH_SHORT).show();
                    String userId = firebaseMediator.getUser();
                    UserInfo.setUserId(activity, userId);
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }else{
                    Toast.makeText(activity, "初回ログインに失敗しました",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data) {

    }

    @Override
    public void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result) {
        switch (info){
            case setUserName:
                if(result){
                    String userId = firebaseMediator.getUser();
                    UserInfo.setUserId(activity, userId);
                }else{
                    Toast.makeText(activity, "ユーザー名登録に失敗しました",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo info, Map<String, String> data) {

    }
}
