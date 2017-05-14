package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LoginLogic loginLogic;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //user = User.getInstance(this);


    }

    public void createAccountBtnPushed(View v){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void loginBtnPushed(View v) {
        if(!user.isLogedin()){
            user.loginAccount();
        }else{
            Toast.makeText(this, "既にログインしています。ログインしなおすには一度ログアウトしてください。", Toast.LENGTH_LONG).show();
        }
    }
}
