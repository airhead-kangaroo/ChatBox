package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Airhead-Kangaroo on 2017/05/27.
 */

public class EnterRoomDialogFlagment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flagment_enter_room,null);
        final RoomManagementController roomManagementController = new RoomManagementController(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("部屋入室")
                .setView(linearLayout)
                .setPositiveButton("入室", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText roomIdField = (EditText)linearLayout.findViewById(R.id.enterRoomRoomId);
                        EditText roomTokenField = (EditText)linearLayout.findViewById(R.id.enterRoomRoomToken);
                        String roomId = roomIdField.getText().toString();
                        String roomToken = roomTokenField.getText().toString();
                        roomManagementController.enterRoom(roomId,roomToken);
                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }
}
