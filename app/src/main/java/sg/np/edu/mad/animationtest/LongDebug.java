package sg.np.edu.mad.animationtest;

import android.util.Log;

public class LongDebug {
    public static void Warn(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.w(tag, msg.substring(start, end));
        }
    }

    public static void Info(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.i(tag, msg.substring(start, end));
        }
    }


}
