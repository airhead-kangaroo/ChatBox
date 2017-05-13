package jp.techacademy.hideto.uetsuka.chatbox;

import android.widget.Toast;

/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class EnterFieldValidator {

    private String errorMsg;
    private int errorCount;

    public EnterFieldValidator(){
        errorMsg = "";
        errorCount = 0;
    }

    boolean isMailAddressBlank(String mailAddress){
        if(mailAddress.equals("") || mailAddress.length() == 0){
            errorMsg += "メールアドレスを入力してください。\n";
            errorCount += 1;
            return true;
        }else{
            return false;
        }
    }

    boolean isPasswordBlank(String password){
        if(password.equals("") || password.length() == 0){
            errorMsg += "パスワードを入力してください。\n";
            errorCount += 1;
            return true;
        }else{
            return false;
        }
    }

    boolean isPasswordConfirmClear(String password, String confirmPassword){
        if(!confirmPassword.equals(password)){
            errorMsg += "パスワードが一致しません。\n";
            errorCount += 1;
            return true;
        }else{
            return false;
        }
    }

    boolean isPasswordTooShort(String password){
        if(password.length() < 8){
            errorCount += 1;
            errorMsg += "パスワードは8文字以上です。";
            return true;
        }else{
            return false;
        }
    }

    boolean isNameBlank(String name){
        if(name.equals("") || name.length() == 0){
            errorMsg += "アカウント名を入力してください。\n";
            errorCount += 1;
            return true;
        }else{
            return false;
        }
    }

    //Todo : 後ほど要件等
    //他とそろえるために、不正ならばtrueを返す。ただ、関数名がわかりにくいし、ifですべて条件をクリアするとfalseはわかりにくいか？
    //！でmailAddress.contains("@")にすべきか？要件等
    //Todo : 正規表現に置き換える
    boolean isTextNotMailAddress(String mailAddress){
        if(mailAddress.contains("@") &&
                mailAddress.contains(".") &&
                mailAddress.indexOf("@") != 0 &&
                mailAddress.indexOf("@") != mailAddress.length() -1 &&
                mailAddress.indexOf(".") != 0 &&
                mailAddress.indexOf(".") != mailAddress.length()){
            return false;
        }else{
            errorMsg += "メールアドレスが正しくありません。\n";
            errorCount += 1;
            return true;
        }
    }

    int getErrorCount(){
        return errorCount;
    }

    String getErrorMsg(){
        if(errorMsg.equals("") || errorMsg.length() == 0){
            return errorMsg;
        }else{
            errorMsg = errorMsg.substring(0,errorMsg.length() - 1);
        }
        return errorMsg;
    }
}
