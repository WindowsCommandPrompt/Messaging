package sg.np.edu.mad.animationtest;

/*  welp...
 *
 *  import {
 *      android.content.DialogInterface,
 *      android.content.Intent,
 *      . . .
 *      . . .
 *  }
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

public class user_main extends AppCompatActivity {

    public DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        //this java file handles user_page.xml

        user_landing referenceTo = new user_landing();

        //Read all information from the SQLDB, if there is any.
        User u = dbHandler.GetAllUsers().get(0);

        //When the user just logs in
        Intent whiteHole = getIntent();
        String name = whiteHole.getStringExtra("Name");
        String description = whiteHole.getStringExtra("Description");

        //Prepare to receive the information from 'recycler_view_interface.java' source code file
        Intent restoredDetails = getIntent();
        String restoredName = restoredDetails.getStringExtra("ReturnName");
        String restoredDescription = restoredDetails.getStringExtra("ReturnDescription");
        String[] restoredNameList = restoredDetails.getStringArrayExtra("ReturnNameList");
        String[] restoredUserList = restoredDetails.getStringArrayExtra("ReturnUserList");
        String[] restoredDescriptionList = restoredDetails.getStringArrayExtra("ReturnDescriptionList");
        String[] restoredFollowedWhoList = restoredDetails.getStringArrayExtra("ReturnFollowedWhoList");

        if (name != null && description != null) {
                ((TextView) findViewById(R.id.nameTextBox)).setText(name);
            ((TextView) findViewById(R.id.descriptionTextBox)).setText(description);
        }
        else if (restoredName != null && restoredDescription != null) {
            ((TextView) findViewById(R.id.nameTextBox)).setText(restoredName);
            ((TextView) findViewById(R.id.descriptionTextBox)).setText(restoredDescription);
        }
        else {
            ((TextView) findViewById(R.id.nameTextBox)).setText(u.name);
            ((TextView) findViewById(R.id.descriptionTextBox)).setText(u.description);
        }

        String[] output1 = whiteHole.getStringArrayExtra("FollowedWhoList");
        String[] output2 = whiteHole.getStringArrayExtra("FullUserList");
        String[] output3 = whiteHole.getStringArrayExtra("FullNameList");
        String[] output4 = whiteHole.getStringArrayExtra("FullDescriptionList");

        if (name == null || description == null){
            //Call firebase again.
            FirebaseFirestore RESTdb = FirebaseFirestore.getInstance();
            DocumentReference df = RESTdb.collection("LoginInformation").document("LoginInformation");
            df.get().addOnSuccessListener(process-> {
                final HashMap<String, Object> a = (HashMap<String, Object>) process.getData();
                String dataEntries = "" + a.size();
                String dataRawStrLen = "" + a.toString().length();
                //Recreate output1, output2, output3, and lastly output4.
                ((TextView) findViewById(R.id.storeTempFirebaseData)).setText(a.toString());
                ((TextView) findViewById(R.id.storeTempFirebaseDataLength)).setText(dataEntries);
                ((TextView) findViewById(R.id.storeTempFirebaseDataLengthRaw)).setText(dataRawStrLen);
            });
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        ((LinearLayout) findViewById(R.id.signOut)).setOnClickListener(
            thenFunctionAs -> {
                alertDialog
                .setTitle("Confirm Log out?")
                .setMessage("You will be required to sign in again if you decide to sign out of the system. Do you wish to proceed?")
                .setPositiveButton(
                    "YES",
                    (DialogInterface di, int i) -> {
                        dbHandler.ClearDatabase(); //the database only contains one record....
                        Intent transporter = new Intent(sg.np.edu.mad.animationtest.user_main.this, sg.np.edu.mad.animationtest.user_landing.class);
                        startActivity(transporter);
                        finish();
                    }
                )
                .setNegativeButton(
                    "No",
                    (DialogInterface di, int i) -> {
                        di.dismiss();
                    }
                );
                alertDialog.create().show();
            }
        );

        //$("LinearLayout#exploreOtherUsers").onclick(() => { });
        ((LinearLayout) findViewById(R.id.exploreOtherUsers)).setOnClickListener(thenFunctionAs -> {
            String result = ((TextView) findViewById(R.id.storeTempFirebaseData)).getText().toString();
            String result1 = ((TextView) findViewById(R.id.storeTempFirebaseDataLength)).getText().toString();
            String result2 = ((TextView) findViewById(R.id.storeTempFirebaseDataLengthRaw)).getText().toString();
            Log.d("TRANSFER1", "" + result);
            Log.d("TRANSFER2", "" + result1);
            Log.d("TRANSFER3", "" + result2);
            String[] followedWhoArr = null; String[] usersList = null; String[] nameList = null; String[] descriptionList = null;
            if (result.length() > 0 && result1.length() > 0 && result2.length() > 0) {
                ArrayList<User> userList = user_landing.ConvertToUser(result, result1, result2);
                ArrayList<String> usersListRaw = new ArrayList<>();
                ArrayList<String> nameListRaw = new ArrayList<>();
                ArrayList<String> descriptionListRaw = new ArrayList<>();
                for (User user : userList) {
                    usersListRaw.add(user.username);
                }
                for (User user : userList) {
                    nameListRaw.add(user.name);
                }
                for (User user : userList) {
                    descriptionListRaw.add(user.description);
                }
                for (User user : userList) {
                    if (user.name.equals(((TextView) findViewById(R.id.nameTextBox)).getText().toString())) {
                        followedWhoArr = user.followedWho.toArray(new String[]{});
                    }
                }
                usersList = usersListRaw.toArray(new String[]{});
                nameList = nameListRaw.toArray(new String[]{});
                descriptionList = descriptionListRaw.toArray(new String[]{});
            }
            //Redirect to the recycler's view
            Intent transporter = new Intent(sg.np.edu.mad.animationtest.user_main.this, sg.np.edu.mad.animationtest.recycler_view_interface.class);
            transporter.putExtra("ForwardFollowedWhoList", output1 != null ? output1 : restoredFollowedWhoList != null ? restoredFollowedWhoList : followedWhoArr);
            transporter.putExtra("ForwardUsersList", output2 != null ? output2 : restoredUserList != null ? restoredUserList : usersList);
            transporter.putExtra("ForwardNameList", output3 != null ? output3 : restoredNameList != null ? restoredNameList : nameList);
            transporter.putExtra("ForwardDescriptionList", output4 != null ? output4 : restoredDescriptionList != null ? restoredDescriptionList : descriptionList);
            transporter.putExtra("Name", ((TextView) findViewById(R.id.nameTextBox)).getText().toString());
            //Send the description over to the 'recycler_view_interface.java' source code file
            transporter.putExtra("Description", ((TextView) findViewById(R.id.descriptionTextBox)).getText().toString());
            startActivity(transporter);
            finish();
        });

        ((Button) findViewById(R.id.changeProfile)).setOnClickListener(function -> {
            String result = ((TextView) findViewById(R.id.storeTempFirebaseData)).getText().toString();
            Intent redirection = new Intent();
            redirection.setClassName("sg.np.edu.mad.animationtest", "sg.np.edu.mad.animationtest.ChangePersonalParticulars");
            redirection.putExtra("Username", u.username);
            redirection.putExtra("DataStr", result);
            redirection.putExtra("PasswordForVerification", u.password);
            startActivity(redirection);
            finish();
        });
    }
}
