package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
    private ProgressDialog progressDialog;

    RoomManagementController(Activity activity){
        this.activity = activity;
        firebaseMediator = new FirebaseMediator(activity,this);
    }

    void createRoom(String roomId, String roomToken, String roomCapacity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("処理中...");
        progressDialog.show();
        if(validateEntryFieldForRoomCreate(roomId, roomToken, roomCapacity)){
            firebaseMediator.createRoom(roomId,roomToken, roomCapacity, UserInfo.getUserId(activity), UserInfo.getUserName(activity));
        }
    }

    //todo : Validatorを性能ごとにクラスで分け、factoryパターンで生成するようにする
    boolean validateEntryFieldForRoomCreate(String roomId, String roomToken, String roomCapacity){
        EntryFieldValidator validator = new EntryFieldValidator();
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

    //todo: リスナー処理を入れ、両方が終了したらトースト出す
    //ゲストのユーザーが持っている部屋情報をどうやって消すかが課題。
    void deleteRoom(String roomName){
        firebaseMediator.deleteRoom(roomName, FirebaseMediator.DELETE_MODE.INIT);
    }

    void lockRoom(String roomId){
        firebaseMediator.lockRoom(roomId);
    }

    void enterRoom(String roomId, String roomToken){
        EntryFieldValidator validator = new EntryFieldValidator();
        validator.isRoomIdBlank(roomId);
        validator.isRoomTokenBlank(roomToken);
        if(validator.getErrorCount() > 0){
            Toast.makeText(activity, validator.getErrorMsg(),Toast.LENGTH_LONG).show();
        }else {
            firebaseMediator.enterRoom(roomId,roomToken,UserInfo.getUserId(activity),UserInfo.getUserName(activity));
        }
    }

    void getUserList(String roomId){
        firebaseMediator.getUserList(roomId);
    }

    @Override
    public void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result) {

    }

    @Override
    public void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data) {
        switch (info){
            case createRoom:
                progressDialog.dismiss();
                if(data != null){
                    Toast.makeText(activity,"部屋を生成しました。部屋IDは" + data + "です", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(activity, "部屋を生成しました。部屋IDは" + data + "です。部屋入室処理を行ってください", Toast.LENGTH_LONG).show();
                    }
                break;
            case enterRoomFail:
                Toast.makeText(activity, data, Toast.LENGTH_LONG).show();
                break;
            case enterRoomSuccess:
                Intent intent = new Intent(activity, ChatActivity.class);
                intent.putExtra("roomId",data);
                activity.startActivity(intent);
                break;
        }
    }

    @Override
    public void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result) {
        switch (info){
            case lockRoomResult:
                if(result){
                    Toast.makeText(activity, "部屋をロックしました", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(activity, "部屋ロックに失敗しました", Toast.LENGTH_SHORT).show();
                }
                break;
            case enterRoomFail:
                Toast.makeText(activity,"部屋入室に失敗しました", Toast.LENGTH_SHORT).show();
                break;
            case createRoom:
                if(!result) {
                    Toast.makeText(activity,"部屋生成に失敗しました",Toast.LENGTH_LONG).show();
                }
                break;
            case deleteRoom:
                if(result){
                    Toast.makeText(activity,"部屋を消去しました",Toast.LENGTH_SHORT).show();
                    activity.finish();
                }else{
                    Toast.makeText(activity,"部屋消去に失敗しました",Toast.LENGTH_SHORT).show();
                }

        }

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
                break;
            case getUserName:
                if(activity instanceof RoomMemberListViewActivity){
                    String userId = data.get("userId");
                    String userName = data.get("userName");
                    ((RoomMemberListViewActivity)activity).addAdapter(userId,userName);
                }


        }
    }
}
