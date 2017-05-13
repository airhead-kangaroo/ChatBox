package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    private LoginLogic loginLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginLogic = new LoginLogic(this);


    }

    public void createAccountBtnPushed(View v){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void loginBtnPushed(View v) {
        loginLogic.abstractFieldData();
        if (loginLogic.checkFieldData()) {
            loginLogic.loginFirebaseAuth();
        }
    }
}
