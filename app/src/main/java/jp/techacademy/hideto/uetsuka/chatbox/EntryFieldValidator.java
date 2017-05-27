package jp.techacademy.hideto.uetsuka.chatbox;

import android.widget.Toast;

/**
 * Created by Airhead-Kangaroo on 2017/05/13.
 */

public class EntryFieldValidator {

    private String errorMsg;
    private int errorCount;
    private static final int PASSWORD_MIN_TEXTLENGTH = 8;
    private static final int ROOMID_MIN_TEXTLENGTH = 6;
    private static final int ROOMTOKEN_MIN_TEXTLENGTH = 6;

    EntryFieldValidator(){
        errorMsg = "";
        errorCount = 0;
    }

    boolean isNameBlank(String name){
        return isBlank(name, "アカウント名");
    }

    boolean isMailAddressBlank(String mailAddress){
        return isBlank(mailAddress, "メールアドレス");
    }

    boolean isPasswordBlank(String password){
        return isBlank(password, "パスワード");
    }

    boolean isPasswordConfirmClear(String password, String confirmPassword){
        return textMatch(password,confirmPassword,"パスワード");
    }

    boolean isPasswordTooShort(String password){
        return isTextTooShort(password, PASSWORD_MIN_TEXTLENGTH, "パスワード");
    }

    boolean isRoomNameBlank(String roomName){
        return isBlank(roomName, "部屋名");
    }

    boolean isRoomIdBlank(String roomId){
        return isBlank(roomId, "部屋ID");
    }

    boolean isRoomTokenBlank(String roomToken){
        return isBlank(roomToken, "トークン");
    }

    boolean isRoomIdTooShort(String roomId){
        return isTextTooShort(roomId,ROOMID_MIN_TEXTLENGTH,"部屋ID");
    }

    boolean isRoomTokenTooShort(String roomToken){
        return isTextTooShort(roomToken, ROOMTOKEN_MIN_TEXTLENGTH, "トークン");
    }

    boolean isRoomCapacityBlank(String roomCapacity){
        return isBlank(roomCapacity, "制限人数");
    }

    boolean isRoomCapacityTooSmall(String roomCapacity){
        return isNumberTooSmall(roomCapacity,1, "制限人数");
    }

    boolean isRoomCapacityTooLarge(String roomCapacity){
        return isNumberTooLarge(roomCapacity, 10, "制限人数");
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
            errorCount++;
            return true;
        }
    }


    private boolean isBlank(String text, String fieldName){
        if(text.equals("") || text.length() == 0 || text == null){
            errorMsg += fieldName + "を入力してください\n";
            errorCount++;
            return false;
        }else {
            return true;
        }
    }

    private boolean textMatch(String text1, String text2, String fieldName){
        if(!text1.equals(text2)){
            errorMsg += fieldName + "が一致しません\n";
            errorCount++;
            return false;
        }else{
            return true;
        }
    }

    private boolean isTextTooShort(String text, int textLength, String fieldName){
        if(text.length() < textLength){
            errorMsg += fieldName + "は" + String.valueOf(textLength) + "文字以上です\n";
            errorCount++;
            return false;
        }else {
            return true;
        }
    }

    private boolean isNumberTooSmall(int number, int length, String fieldName){
        if(number < length){
            errorMsg += fieldName + "は" + String.valueOf(length) + "以上です\n";
            errorCount++;
            return false;
        }else{
            return true;
        }
    }

    private boolean isNumberTooSmall(String number, int length, String fieldName){
        if(Integer.parseInt(number) < length){
            errorMsg += fieldName + "は" + String.valueOf(length) + "以上です\n";
            errorCount++;
            return false;
        }else{
            return true;
        }
    }

    private boolean isNumberTooLarge(int number, int length, String fieldName){
        if(number > length){
            errorMsg += fieldName + "は" + String.valueOf(length) + "以下です\n";
            errorCount++;
            return false;
        }else{
            return true;
        }
    }

    private boolean isNumberTooLarge(String number, int length, String fieldName){
        if(Integer.parseInt(number) > length){
            errorMsg += fieldName + "は" + String.valueOf(length) + "以下です\n";
            errorCount++;
            return false;
        }else{
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
