package jp.techacademy.hideto.uetsuka.chatbox;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    private String roomId;
    private ChatContentAdapter adapter;
    private ListView listView;
    private ChatController chatController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatController = new ChatController(this);

        roomId = getIntent().getStringExtra("roomId");
        adapter = new ChatContentAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.chatContentListView);

    }

    void addDataToAdapter(String userId, String userName, String converation, String date){
        ChatContent chatContent = new ChatContent();
        chatContent.setUserId(userId);
        chatContent.setUserName(userName);
        chatContent.setChatContent(converation);
        chatContent.setDate(date);

        adapter.add(chatContent);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatController.loadConversation(roomId);
        adapter.clear();
    }

    void sendText(View v){
        InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        EditText et = (EditText) findViewById(R.id.chatBoxEditText);
        String conversation = et.getText().toString();
        et.setText("");
        chatController.addMessage(roomId,conversation);
    }

    void sendImage(View v){
        String date = DateFormat.format("yyyy/MM/dd HH:mm:ss", Calendar.getInstance()).toString();
        Toast.makeText(this,date,Toast.LENGTH_LONG).show();
    }
}
