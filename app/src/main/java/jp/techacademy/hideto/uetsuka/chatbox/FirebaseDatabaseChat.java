package jp.techacademy.hideto.uetsuka.chatbox;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/28.
 */

public class FirebaseDatabaseChat extends MyFirebaseDatabase {

    public static final String FIREBASE_CHAT_PATH = "chat";
    public static final String FIREBASE_CHAT_USER_ID_KEY = "userId";
    public static final String FIREBASE_CHAT_USER_NAME_KEY = "userName";
    public static final String FIREBASE_CHAT_CONVERSATION_KEY = "conversation";
    public static final String FIREBASE_CHAT_DATE_KEY = "date";


    FirebaseDatabaseChat(FirebaseMediator firebaseMediator){
        super(firebaseMediator);
    }

    void addMessage(String roomId, String userId, String userName,String conversation, String date){
        DatabaseReference chatRef = databaseReference.child(FIREBASE_CHAT_PATH).child(roomId).child(date);
        HashMap<String, String> data = new HashMap<>();
        data.put(FIREBASE_CHAT_USER_ID_KEY, userId);
        data.put(FIREBASE_CHAT_USER_NAME_KEY,userName);
        data.put(FIREBASE_CHAT_CONVERSATION_KEY, conversation);
        chatRef.setValue(data).addOnCompleteListener(getAddMessageListener());
    }

    void loadConversation(String roomId){
        DatabaseReference chatRef = databaseReference.child(FIREBASE_CHAT_PATH).child(roomId);
        chatRef.addChildEventListener(getChatListener());
    }


    private OnCompleteListener getAddMessageListener(){
        return new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    firebaseMediator.addMessageChatListener(true);
                }else{
                    firebaseMediator.addMessageChatListener(false);
                }
            }
        };
    }

    private ChildEventListener getChatListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap data = (HashMap)dataSnapshot.getValue();
                data.put(FIREBASE_CHAT_DATE_KEY, dataSnapshot.getKey());
                firebaseMediator.loadChatListener(data);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


}
