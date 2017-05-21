package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomManagementActivity extends AppCompatActivity{

    private RoomManagementController roomManagementController;
    private RoomManagementAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_management);

        adapter = new RoomManagementAdapter(this);
        list = (ListView)findViewById(R.id.roomManagementListView);
        adapter.notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RoomManagementActivity.this, ((TextView)view.findViewById(R.id.roomManagementRoomName)).getText().toString(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(this, );
//                intent.putExtra("roomName", ((TextView)view.findViewById(R.id.roomManagementRoomName)).getText().toString());
//                startActivity(intent);
            }
        });

        roomManagementController = new RoomManagementController(this);
        String userId = UserInfo.getUserId(this);
        if(userId != null){
            roomManagementController.loadRoomsData(UserInfo.getUserId(this));
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


    }

    void addDataToAdapter(HashMap<String, String> data){
        RoomList roomList = new RoomList();
        roomList.setRoomName(data.get("roomName"));
        roomList.setRoomProperty(data.get("roomProperty"));
        adapter.add(roomList);
        list.setAdapter(adapter);
    }

}
