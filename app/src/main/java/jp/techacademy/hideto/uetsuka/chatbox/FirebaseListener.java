package jp.techacademy.hideto.uetsuka.chatbox;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Airhead-Kangaroo on 2017/05/18.
 */

public interface FirebaseListener {

    void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result);
    void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data);
    void firebaseDataBaseHashmapListener(MyFirebaseDatabase.ListenerInfo info, Map<String,String> data);
    void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result);
}
