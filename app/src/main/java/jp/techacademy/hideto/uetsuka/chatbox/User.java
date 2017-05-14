package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class User {

    private Activity activity;
    private String userId;

    User(Activity activity){
        this.activity = activity;
    }

    void createAccount(){
        AccountCreator creator = new AccountCreator(activity);
        creator.readyCreateAccount();
        if(creator.checkInputData()){
            creator.createAccount();
        }
    }

    void loginAccount(){
        LoginLogic logic = new LoginLogic(activity);
        logic.abstractFieldData();
        if(logic.checkFieldData()){
            logic.login();
        }
    }

    boolean isLogedin(){
        return UserInfo.isLogedin;
    }




}
