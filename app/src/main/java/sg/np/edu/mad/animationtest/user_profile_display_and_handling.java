package sg.np.edu.mad.animationtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class user_profile_display_and_handling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        AtomicReference<ArrayList<String>> tempList = new AtomicReference<>(); //stores the followedWho array
        AtomicReference<String> usernameString = new AtomicReference<>();

        DBHandler dbHandler = new DBHandler(this);

        //Redirect to the message button
        ((Button) findViewById(R.id.messageButton)).setOnClickListener(thenFunctionAs -> {
            Intent transporter = new Intent();
            //go to another activity....
            transporter.setClassName("sg.np.edu.mad.ipptready.user_profile_display_and_handling", "sg.np.edu.mad.ipptready.message_controller");
            startActivity(transporter);
            finish();
        });

        //Get the name, username, etc.
        Intent results = getIntent();
        String username = results.getStringExtra("InternalUsername");
        String description = results.getStringExtra("InternalDescription");
        String name = results.getStringExtra("InternalName");
        String[] followedWhoList = results.getStringArrayExtra("ForwardFollowedWhoList");

        //Check for array membership
        List<String> fullFollowedWhoList = Arrays.stream(followedWhoList).collect(Collectors.toList());

        for (int i = 0; i < fullFollowedWhoList.size(); i++) {
            if (fullFollowedWhoList.get(i).contains(username)) {
                ((Button) findViewById(R.id.followUnfollowButton)).setText("UNFOLLOW");
                break;
            }
            ((Button) findViewById(R.id.followUnfollowButton)).setText("FOLLOW");
        }

        //Clean the array for further handling
        //Remove the '[' symbol at the first element and the ']' symbol at the last element
        ArrayList<String> translate = new ArrayList<String>();

        translate.add(fullFollowedWhoList.get(0).replace(Character.toString(fullFollowedWhoList.get(0).charAt(0)), ""));
        translate.add(fullFollowedWhoList.get(1));
        translate.add(fullFollowedWhoList.get(fullFollowedWhoList.size() - 1).replace(Character.toString(fullFollowedWhoList.get(fullFollowedWhoList.size() - 1).charAt(fullFollowedWhoList.get(fullFollowedWhoList.size() - 1).length() - 1)), ""));

        Log.d("OUTCOME", "" + translate);

        ((TextView) findViewById(R.id.userProfileDescriptionHolder)).setText(description);
        ((TextView) findViewById(R.id.userProfileNameHolder)).setText(name);
        ((TextView) findViewById(R.id.userProfileAbstractNameHolder)).setText(username);

        FirebaseFirestore RESTDB = FirebaseFirestore.getInstance();
        DocumentReference records = RESTDB.collection("LoginInformation").document("LoginInformation");

        //If the user attempts to follow or unfollow the user, then it will trigger a function which would in turn update the information in the database in such a way that it will append the username into the ArrayList that is bounded to the
        ((Button) findViewById(R.id.followUnfollowButton)).setOnClickListener(thenFunctionAs -> {
            //get the username of the profile.
            usernameString.set((((TextView) findViewById(R.id.userProfileNameHolder)).getText()).toString());
            if (((Button) findViewById(R.id.followUnfollowButton)).getText().toString().equals("UNFOLLOW")){ //to unfollow the user, just simply remove the user from the array list
                String targetUsername = usernameString.get();
                //Remove the item from the list..
                if (translate.size() == 1) {
                    translate.remove(username);
                    translate.add(username);
                    Log.d("AFTER-1ISNULL", "" + translate);
                }
                else {
                    translate.remove(username);
                    Log.d("AFTER-1NOTNULL", "" + translate);
                }
                Toast.makeText(this, "Successfully followed user", Toast.LENGTH_SHORT).show();
                ((Button) findViewById(R.id.followUnfollowButton)).setText("FOLLOW");
            }
            else if (((Button) findViewById(R.id.followUnfollowButton)).getText().toString().equals("FOLLOW")){
                String targetUsername = usernameString.get();
                //Check if the array contains an element that is named 'null'
                if (translate.size() == 1 && translate.contains(null)){
                    translate.remove(0); //remove that element and replace it with the element itself.
                    //Add the username into the list...
                    translate.add(username);
                }
                else {
                    //Add the username into the list...
                    translate.add(username);
                }
                Toast.makeText(this, "Successfully followed user", Toast.LENGTH_SHORT).show();
                ((Button) findViewById(R.id.followUnfollowButton)).setText("UNFOLLOW");
            }
        });

        //The button that will return the user back to the recycler's view
        ((LinearLayout) findViewById(R.id.goBack)).setOnClickListener(function -> {
            //Pass the information back into that activity
            Intent intent = new Intent(sg.np.edu.mad.animationtest.user_profile_display_and_handling.this, sg.np.edu.mad.animationtest.recycler_view_interface.class);
            intent.putExtra("SendBackNameList", results.getStringArrayExtra("ReturnNameList"));
            intent.putExtra("SendBackDescriptionList", results.getStringArrayExtra("ReturnDescriptionList"));
            intent.putExtra("SendBackUserList", results.getStringArrayExtra("ReturnUserList"));
            intent.putExtra("SendBackCurrUsername", results.getStringExtra("ReturnCurrUsername"));
            intent.putExtra("SendBackCurrDescription", results.getStringExtra("ReturnCurrDescription"));
            intent.putExtra("SendBackFollowedWhoList", followedWhoList);
            startActivity(intent);
            finish();
        });

        //tempList.get() gives the entire array....
        //usernameString.get() gives the username identifier
        dbHandler.UpdateUser(tempList.get(), usernameString.get());

        //Allow the user to be able to sign out of the account
        ((LinearLayout) findViewById(R.id.signOut)).setOnClickListener(function -> {
            AlertDialog.Builder messageBuilder = new AlertDialog.Builder(this);
            messageBuilder
            .setTitle("Confirm sign out?")
            .setMessage("Are you sure you would like to sign out of this application? You will be required to sign in again later")
            .setPositiveButton(
                "YES",
                (DialogInterface di, int i) -> {
                    //DELETE THE INFORMATION FROM THE SQLITE DB

                    //Where do you want to go?
                    Intent intent = new Intent();
                    intent.setClassName("sg.np.edu.mad.animationtest.user_profile_display_and_handling", "sg.np.edu.mad.animationtest.user_landing");
                    startActivity(intent);
                    finish();
                }
            )
            .setNegativeButton(
                "NO",
                (DialogInterface di, int i) -> {
                    di.dismiss();
                }
            )
            .setCancelable(false);

        });
    }
}
