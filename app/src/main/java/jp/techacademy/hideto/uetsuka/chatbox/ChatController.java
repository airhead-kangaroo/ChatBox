package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/28.
 */

public class ChatController implements FirebaseListener{

    private Activity activity;
    private FirebaseMediator mediator;

    ChatController(Activity activity){
        this.activity = activity;
        mediator = new FirebaseMediator(activity,this);
    }

    void addMessage(String roomId, String conversation){
        String date = DateFormat.format("yyyy年MM月dd日 HH:mm:ss", Calendar.getInstance()).toString();
        mediator.addMessage(roomId,UserInfo.getUserId(activity),UserInfo.getUserName(activity),conversation, date);
    }

    void loadConversation(String roomId){
        mediator.loadConversation(roomId);
    }


    @Override
    public void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result) {

    }

    @Override
    public void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data) {

    }

    @Override
    public void firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo info, Map<String, String> data) {
        switch (info){
            case loadChat:
                if(activity instanceof ChatActivity){
                    String userId = data.get("userId");
                    String userName = data.get("userName");
                    String conversation = data.get("conversation");
                    String date = data.get("date");
                    ((ChatActivity) activity).addDataToAdapter(userId,userName,conversation,date);
                }


        }
    }

    @Override
    public void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result) {
        switch (info){
            case addChat:
                if(result){
                    if(activity instanceof ChatActivity){
                        ((ChatActivity)activity).onResume();
                    }
                    //会話更新時の処理
                }else{
                    Toast.makeText(activity,"チャット投稿に失敗しました",Toast.LENGTH_LONG).show();
                }
        }
    }
}
