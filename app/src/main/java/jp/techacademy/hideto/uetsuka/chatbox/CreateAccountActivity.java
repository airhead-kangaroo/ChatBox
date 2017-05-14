package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CreateAccountActivity extends AppCompatActivity {

    private AccountCreator accountCreator;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        //User user = User.getInstance(this);
        setTitle("アカウント作成");

    }

    public void createAccountBtnPushed(View v){
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        user.createAccount();
    }
}
