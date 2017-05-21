package jp.techacademy.hideto.uetsuka.chatbox;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Airhead-Kangaroo on 2017/05/21.
 */

public class RoomManagementAdapter extends BaseAdapter {

    private ArrayList<RoomList> list;
    private Activity activity;
    private LayoutInflater inflater;

    RoomManagementAdapter(Activity activity){
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<RoomList>();
    }

    void add(RoomList newList){
        list.add(newList);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.room_management_listview,parent,false);
        }
        ((TextView)convertView.findViewById(R.id.roomManagementRoomName)).setText(list.get(position).getRoomName());
        ((TextView)convertView.findViewById(R.id.roomManagementProperty)).setText(list.get(position).getRoomProperty());
        return convertView;
    }
}
