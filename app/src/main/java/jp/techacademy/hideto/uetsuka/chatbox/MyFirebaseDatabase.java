package jp.techacademy.hideto.uetsuka.chatbox;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Airhead-Kangaroo on 2017/05/14.
 */

public class MyFirebaseDatabase {

    protected DatabaseReference databaseReference;

    MyFirebaseDatabase(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
