package sg.np.edu.mad.animationtest;

import android.util.Log;

public class DebugUtil {

    private DebugUtil(){

    }

    public static void WarnDefault(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.w(tag, msg.substring(start, end));
        }
    }

    public static void InfoDefault(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.i(tag, msg.substring(start, end));
        }
    }

    public static void ErrorDefault(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.e(tag, msg.substring(start, end));
        }
    }

    public static void VerboseDefault(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.v(tag, msg.substring(start, end));
        }
    }

    public static void DebugDefault(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.d(tag, msg.substring(start, end));
        }
    }

    public static final class LogCatMessageBuilder{

    }
}
