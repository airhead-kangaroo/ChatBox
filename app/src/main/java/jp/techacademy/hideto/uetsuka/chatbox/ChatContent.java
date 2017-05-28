package jp.techacademy.hideto.uetsuka.chatbox;

/**
 * Created by Airhead-Kangaroo on 2017/05/28.
 */

public class ChatContent {

    private String userName;
    private String userId;
    private String chatContent;
    private String date;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
