package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/21.
 */

public class RoomManagementController implements FirebaseListener{

    private Activity activity;
    private FirebaseMediator firebaseMediator;
    private String roomNanme;
    private String roomId;
    private String roomToken;
    private String roomCapacity;

    RoomManagementController(Activity activity){
        this.activity = activity;
        firebaseMediator = new FirebaseMediator(activity,this);
    }

    void setRoomName(String roomName){
        this.roomNanme = roomName;
    }

    void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    void setRoomToken(String roomToken) {
        this.roomToken = roomToken;
    }

    public void setRoomCapacity(String roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    void createRoom(){
        if(validateEntryField()){
            firebaseMediator.createRoom(roomNanme,roomId,roomToken, roomCapacity);
        }
    }

    boolean validateEntryField(){
        EntryFieldValidator validator = new EntryFieldValidator();
        validator.isRoomNameBlank(roomNanme);
        validator.isRoomIdBlank(roomId);
        validator.isRoomTokenBlank(roomToken);
        validator.isRoomIdTooShort(roomId);
        validator.isRoomTokenTooShort(roomToken);
        validator.isRoomCapacityBlank(roomCapacity);
        validator.isRoomCapacityTooSmall(roomCapacity);
        validator.isRoomCapacityTooLarge(roomCapacity);
        if(validator.getErrorCount() > 0){
            Toast.makeText(activity, validator.getErrorMsg(),Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    void loadRoomsData(String userId){
        firebaseMediator.loadRoomsData(userId);
    }

    @Override
    public void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result) {

    }

    @Override
    public void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data) {
        switch (info){
            case createRoom:
                if(data != null){
                    if(firebaseMediator.addRoom(UserInfo.getUserId(activity),data)){
                        Toast.makeText(activity,"部屋を生成しました。部屋IDは" + data + "です", Toast.LENGTH_LONG).show();
                        firebaseMediator.addMember(UserInfo.getUserId(activity), data);
                        //intentで部屋入室処理
                    }else{
                        Toast.makeText(activity, "部屋を生成しました。部屋IDは" + data + "です。部屋入室処理を行ってください", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(activity, "部屋生成に失敗しました", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result) {

    }

    @Override
    public void firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo info, Map<String, String> data) {
        switch (info){
            case loadRoomsData:
                if(activity instanceof RoomManagementActivity){
                    for(Map.Entry<String,String> entry :data.entrySet()){
                        HashMap<String, String> dataForListView = new HashMap<>();
                        dataForListView.put("roomName", entry.getKey());
                        if(entry.getValue().equals("master")){
                            dataForListView.put("roomProperty", "管理者");
                        }else{
                            dataForListView.put("roomProperty", "ゲスト");
                        }
                        ((RoomManagementActivity) activity).addDataToAdapter(dataForListView);
                    }

        }


        }
    }
}
