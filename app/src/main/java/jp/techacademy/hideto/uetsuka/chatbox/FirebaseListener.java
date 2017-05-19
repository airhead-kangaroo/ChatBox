package jp.techacademy.hideto.uetsuka.chatbox;

/**
 * Created by Airhead-Kangaroo on 2017/05/18.
 */

public interface FirebaseListener {

    void firebaseAuthListener(MyFirebaseAuth.ListenerInfo info, boolean result);
    void firebaseDataBaseListener(MyFirebaseDatabase.ListenerInfo info, String data);
    void firebaseDatabaseResultListener(MyFirebaseDatabase.ListenerInfo info, boolean result);
}
