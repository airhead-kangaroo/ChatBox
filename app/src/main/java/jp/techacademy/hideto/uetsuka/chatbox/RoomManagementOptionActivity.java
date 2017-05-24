package jp.techacademy.hideto.uetsuka.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoomManagementOptionActivity extends AppCompatActivity {
    private String roomName;
    private RoomManagementController roomManagementController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_management_option);
        UserInfo.isLogin(this);
        roomManagementController = new RoomManagementController(this);
        roomName = getIntent().getStringExtra("roomName");
        String roomProperty = getIntent().getStringExtra("roomProperty");
        setButtonState(roomProperty);


    }

    private void setButtonState(String roomProperty){
        Button deleteRoomBtn = (Button)findViewById(R.id.deleteRoom);
        Button lockRoomBtn = (Button)findViewById(R.id.lockRoom);
        Button dragOutMemberBtn = (Button)findViewById(R.id.dragOutMember);
        Button restartChatBtn = (Button)findViewById(R.id.restartChat);
        if(roomProperty.equals("ゲスト")){
            deleteRoomBtn.setEnabled(false);
            lockRoomBtn.setEnabled(false);
            dragOutMemberBtn.setEnabled(false);
            restartChatBtn.setEnabled(true);
        }
    }

    void deleteRoom(View v){
        roomManagementController.deleteRoom(roomName, UserInfo.getUserId(this));
    }

    void lockRoom(View v){

    }

    void dragOutMember(){

    }

    void reStartChat(View v){

    }



}
