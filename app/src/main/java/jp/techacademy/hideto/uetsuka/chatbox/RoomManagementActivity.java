package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_management);

        adapter = new RoomManagementAdapter(this);
        list = (ListView)findViewById(R.id.roomManagementListView);
        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("処理中...");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomManagementActivity.this, RoomManagementOptionActivity.class);
                intent.putExtra("roomName", ((TextView)view.findViewById(R.id.roomManagementRoomName)).getText().toString());
                intent.putExtra("roomProperty", ((TextView)view.findViewById(R.id.roomManagementProperty)).getText().toString());
                startActivity(intent);
            }
        });

        roomManagementController = new RoomManagementController(this);
        adapter.clear();
        //roomManagementController.loadRoomsData(UserInfo.getUserId(this));
    }

    void addDataToAdapter(HashMap<String, String> data){
        RoomList roomList = new RoomList();
        roomList.setRoomName(data.get("roomName"));
        roomList.setRoomProperty(data.get("roomProperty"));
        adapter.add(roomList);
        list.setAdapter(adapter);
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        adapter.clear();
        roomManagementController.loadRoomsData(UserInfo.getUserId(this));
    }
}
