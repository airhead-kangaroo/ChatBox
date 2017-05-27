package jp.techacademy.hideto.uetsuka.chatbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomMemberListViewActivity extends AppCompatActivity {

    private RoomMemberAdapter adapter;
    private ListView listView;
    private RoomManagementController roomManagementController;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_user_list_view);
        roomId = getIntent().getStringExtra("roomId");

        roomManagementController = new RoomManagementController(this);
        adapter = new RoomMemberAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.roomUserListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userId = ((TextView)view.findViewById(R.id.roomUserUserId)).getText().toString();

            }
        });
    }

    void addAdapter(String userId, String userName){
        if(userId != UserInfo.getUserId(this)){
            //ここに下の処理を入れる
        }
        RoomMember member = new RoomMember();
        member.setUserId(userId);
        member.setUserName(userName);
        adapter.add(member);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        roomManagementController.getUserList(roomId);
    }
}
