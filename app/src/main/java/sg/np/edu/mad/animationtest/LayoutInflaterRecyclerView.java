package sg.np.edu.mad.animationtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LayoutInflaterRecyclerView extends RecyclerView.ViewHolder {
    TextView tv1, tv2, tv3;
    LinearLayout ll;
    public LayoutInflaterRecyclerView(@NonNull View itemView) {
        super(itemView);
        tv1 = ((TextView) itemView.findViewById(R.id.nameHolder));
        tv2 = ((TextView) itemView.findViewById(R.id.descriptionHolder));
        tv3 = ((TextView) itemView.findViewById(R.id.abstractNameHolder));
        ll = ((LinearLayout) itemView.findViewById(R.id.baseLinearLayout));
    }
}
