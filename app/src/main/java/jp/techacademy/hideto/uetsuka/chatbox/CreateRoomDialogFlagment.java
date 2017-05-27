package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Airhead-Kangaroo on 2017/05/21.
 */

public class CreateRoomDialogFlagment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flagment_create_room,null);
        final RoomManagementController roomManagementController = new RoomManagementController(getActivity());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("部屋生成")
                .setView(layout)
                .setPositiveButton("生成",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //EditText roomNameField = (EditText)layout.findViewById(R.id.createRoomName);
                                EditText roomIdField = (EditText)layout.findViewById(R.id.createRoomId);
                                EditText roomTokenField = (EditText)layout.findViewById(R.id.createRoomToken);
                                EditText roomCapacityField = (EditText)layout.findViewById(R.id.roomCapaity);
                                //String roomName = roomNameField.getText().toString();
                                String roomId = roomIdField.getText().toString();
                                String roomToken = roomTokenField.getText().toString();
                                String roomCapacity = roomCapacityField.getText().toString();
                                roomManagementController.createRoom(roomId,roomToken,roomCapacity);

                            }
                        })
                .setNegativeButton("キャンセル",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .create();
    }
}
