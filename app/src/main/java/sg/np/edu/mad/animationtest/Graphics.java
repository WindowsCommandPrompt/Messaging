package sg.np.edu.mad.animationtest;

import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import android.view.*;

public class Graphics extends AppCompatActivity {

    private WindowManager wm;

    public Graphics(WindowManager wm){
        this.wm = wm;
    }

    public int[] ScreenDimensions() {
        DisplayMetrics measurements = new DisplayMetrics();
        this.wm.getDefaultDisplay().getMetrics(measurements);
        int height = measurements.heightPixels;
        int width = measurements.widthPixels;
        return new int[] { width, height };
    }

    public static class Width{
        private int width;

        private Width(){

        }

        public int getWidth(){

        }
    }

    public static class Height {
        private int height;

        private Height(){

        }

        public int getHeight(){

        }
    }

    public static class CustomPinPointing {

    }
}
