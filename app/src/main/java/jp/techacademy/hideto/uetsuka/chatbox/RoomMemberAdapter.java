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
 * Created by Airhead-Kangaroo on 2017/05/27.
 */

public class RoomMemberAdapter extends BaseAdapter {

    private ArrayList<RoomMember> list;
    private Activity activity;
    private LayoutInflater inflater;

    RoomMemberAdapter(Activity activity){
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<RoomMember>();
    }

    void add(RoomMember roomMember){
        list.add(roomMember);
    }

    void clear(){
        list.clear();
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
            convertView = inflater.inflate(R.layout.listview_room_user,parent,false);
        }
        ((TextView)convertView.findViewById(R.id.roomUserUserName)).setText(list.get(position).getUserName());
        ((TextView)convertView.findViewById(R.id.roomUserUserId)).setText(list.get(position).getUserId());
        return convertView;
    }
}
