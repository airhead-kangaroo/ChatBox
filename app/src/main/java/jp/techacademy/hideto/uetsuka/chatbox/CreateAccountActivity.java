package jp.techacademy.hideto.uetsuka.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateAccountActivity extends AppCompatActivity {

    private AccountCreator accountCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        accountCreator = new AccountCreator(this);
    }

    public void createAccountBtnPushed(View v){
        accountCreator.readyCreateAccount();
        if(accountCreator.checkInputData()){
            accountCreator.createAccount();
        }
    }
}
