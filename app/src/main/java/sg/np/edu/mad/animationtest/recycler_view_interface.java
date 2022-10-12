package sg.np.edu.mad.animationtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.stream.Collectors;

public class recycler_view_interface extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recycler_view_other_users_exploration);

        Intent getStringArrays = getIntent();
        //Receive both arrays.
        String[] result = getStringArrays.getStringArrayExtra("ForwardFollowedWhoList");
        String[] result1 = getStringArrays.getStringArrayExtra("ForwardUsersList");
        String[] result2 = getStringArrays.getStringArrayExtra("ForwardNameList");
        String[] result3 = getStringArrays.getStringArrayExtra("ForwardDescriptionList");
        String result4 = getStringArrays.getStringExtra("Name"); //Get the name
        String result5 = getStringArrays.getStringExtra("Description"); //Get the description

        Log.d("FOLLOWEDWHOLISTCONTENTS?", result != null ? result.length > 0 ? "Length of followedWho list is greater than 0" : "followedWhoList is empty": "The list from output 1 is not even sent over to this java file");
        Log.d("FULLUSERLISTCONTENTS?", result1 != null ? result1.length > 0 ? "Length of users list is greater than 0" : "users list is empty": "The list from output 2 is not even sent over to this java file");

        //getIntent from the user_profile_display_and_handling.java file.
        Intent restoreItems = getIntent();
        String[] restoredNameList = restoreItems.getStringArrayExtra("SendBackNameList");
        String[] restoredDescriptionList = restoreItems.getStringArrayExtra("SendBackDescriptionList");
        String[] restoredUserList = restoreItems.getStringArrayExtra("SendBackUserList");
        String[] restoredFollowedWhoList = restoreItems.getStringArrayExtra("SendBackFollowedWhoList");
        String restoredCurrUsername = restoreItems.getStringExtra("SendBackCurrUsername");
        String restoredCurrDescription = restoreItems.getStringExtra("SendBackCurrDescription");

        int targetIndex = 0;

        //Find the index position of the current user....
        if (result2 != null && result4 != null) {
            targetIndex = Arrays.stream(result2).collect(Collectors.toList()).indexOf(result4);
        }
        else if (restoredNameList != null && restoredCurrUsername != null){
            targetIndex = Arrays.stream(restoredNameList).collect(Collectors.toList()).indexOf(restoredCurrUsername);
        }
        else {

        }

        //Bind to the recycler view adapter here
        RecyclerView rv = findViewById(R.id.mainRecyclerViewUserExploration);
        RecyclerViewAdapter rva = new RecyclerViewAdapter(this,
                result1 != null ? result1 : restoredUserList,
                result != null ? result : restoredFollowedWhoList,
                result2 != null ? result2 : restoredNameList,
                result3 != null ? result3 : restoredDescriptionList,
                targetIndex,
                result4 != null ? result4 : restoredCurrUsername,
                result5 != null ? result5 : restoredCurrDescription);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(llm);
        rv.setAdapter(rva);

        //Add an event listener that allows the user to be able to quit the recycler's view and return to the main index view
        ((LinearLayout) findViewById(R.id.quitRecyclerView)).setOnClickListener(function -> {
            Intent intent = new Intent(sg.np.edu.mad.animationtest.recycler_view_interface.this, sg.np.edu.mad.animationtest.user_main.class);
            intent.putExtra("ReturnName", restoredCurrUsername != null ? restoredCurrUsername : result4);
            intent.putExtra("ReturnDescription", restoredCurrDescription != null ? restoredCurrDescription : result5);
            //Return back the nameList when not needed
            intent.putExtra("ReturnNameList", restoredNameList != null ? restoredNameList : result2);
            intent.putExtra("ReturnUserList", restoredUserList != null ? restoredUserList : result1);
            intent.putExtra("ReturnDescriptionList", restoredDescriptionList != null ? restoredDescriptionList : result3);
            intent.putExtra("ReturnFollowedWhoList", restoredFollowedWhoList != null ? restoredFollowedWhoList : result);
            startActivity(intent); //Go back to the user_main.java source file
            finish();
        });
    }
}