package sg.np.edu.mad.animationtest;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BlankLayout extends RecyclerView.ViewHolder{

    LinearLayout llb;

    public BlankLayout(@NonNull View itemView) {
        super(itemView);
        llb = ((LinearLayout) itemView.findViewById(R.id.blankLayout));
    }
}
