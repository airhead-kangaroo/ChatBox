package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class AccountCreator {

    private Activity activity;
    private EditText mailAddress;
    private EditText password;
    private EditText confirmPassword;
    private EditText name;


    AccountCreator(Activity activity){
        this.activity = activity;
        mailAddress = (EditText)this.activity.findViewById(R.id.createAccountMailField);
        password = (EditText)this.activity.findViewById(R.id.createAccountPasswordField);
        confirmPassword = (EditText)this.activity.findViewById(R.id.createAccountConfirmPasswordField);
        name = (EditText)this.activity.findViewById(R.id.createAccountNameField);
    }

    void readyCreateAccount(){
        mailAddress = (EditText)this.activity.findViewById(R.id.createAccountMailField);
        password = (EditText)this.activity.findViewById(R.id.createAccountPasswordField);
        confirmPassword = (EditText)this.activity.findViewById(R.id.createAccountConfirmPasswordField);
        name = (EditText)this.activity.findViewById(R.id.createAccountNameField);
    }

    boolean checkInputData(){
        EnterFieldValidator validator = new EnterFieldValidator();
        validator.isMailAddressBlank(mailAddress.getText().toString());
        validator.isTextNotMailAddress(mailAddress.getText().toString());
        validator.isPasswordBlank(password.getText().toString());
        validator.isPasswordConfirmClear(password.getText().toString(), confirmPassword.getText().toString());
        validator.isNameBlank(name.getText().toString());
        if(validator.getErrorCount() > 0){
            Toast.makeText(activity,validator.getErrorMsg(), Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    void createAccount(){

    }

}
