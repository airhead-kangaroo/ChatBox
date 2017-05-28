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
 * Created by Airhead-Kangaroo on 2017/05/28.
 */

public class ChatContentAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<ChatContent> list;
    private LayoutInflater inflater;

    ChatContentAdapter(Activity activity){
        this.activity = activity;
        list = new ArrayList<>();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void add(ChatContent chatContent){
        list.add(chatContent);
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
        if(list.get(position).getUserId().equals(UserInfo.getUserId(activity))){
            if(convertView == null){
                convertView = inflater.inflate(R.layout.listview_chatcontent_self,parent,false);
            }

            ((TextView)convertView.findViewById(R.id.chatContentUserName)).setText(list.get(position).getUserName());
            ((TextView)convertView.findViewById(R.id.chatContentUserId)).setText(list.get(position).getUserId());
            ((TextView)convertView.findViewById(R.id.chatContentConversation)).setText(list.get(position).getChatContent());
            ((TextView)convertView.findViewById(R.id.chatContentDate)).setText(list.get(position).getDate());


        }else{
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.listview_chatcontent_other, parent, false);
            }
            ((TextView)convertView.findViewById(R.id.chatContentUserName)).setText(list.get(position).getUserName());
            ((TextView)convertView.findViewById(R.id.chatContentUserId)).setText(list.get(position).getUserId());
            ((TextView)convertView.findViewById(R.id.chatContentConversation)).setText(list.get(position).getChatContent());
            ((TextView)convertView.findViewById(R.id.chatContentDate)).setText(list.get(position).getDate());

        }
        return convertView;
    }
}
