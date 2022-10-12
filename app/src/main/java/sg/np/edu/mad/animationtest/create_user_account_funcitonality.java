package sg.np.edu.mad.animationtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
public class create_user_account_funcitonality extends AppCompatActivity {
    //this java file controls 'create_user_account.xml'

    private static final String TAG = "TAG";
    public final DBHandler dbHandler = new DBHandler(this);

    private int FindMaxIn(ArrayList<Integer> list){
        Log.d("ThatList", "" + list);
        Collections.sort(list);
        return list.get(list.size() - 1);
    }

    public void WriteToFirebaseFirestoreExtra(HashMap<String, Object> userData){
        AlertDialog.Builder registrationUnsuccessful = new AlertDialog.Builder(this);
        AlertDialog.Builder registrationSucceessful = new AlertDialog.Builder(this);
        registrationUnsuccessful
        .setTitle("Registration Unsuccessful")
        .setMessage("Registration has been unsuccessful. Please try again later")
        .setPositiveButton(
                "OK",
                (DialogInterface di, int i) -> {
                    di.dismiss();
                }
        )
        .setCancelable(false);

        registrationSucceessful
        .setTitle("Registration Successful")
        .setMessage("Registration has been successful. Please tap on the 'OK' button to be redirected back to the main sign in page")
        .setPositiveButton(
            "OK",
            (DialogInterface di, int i) -> {
                Intent thenRedirect = new Intent();
                thenRedirect.setClassName("sg.np.edu.mad.animationtest", "sg.np.edu.mad.animationtest.user_landing");
                startActivity(thenRedirect);
                finish();
            }
        )
        .setCancelable(false);

        RESTdb.collection("LoginInformation").document("LoginInformation")
        .set(userData, SetOptions.merge())
        .addOnSuccessListener(function -> {
            registrationSucceessful.create().show();
            Log.d("REGISTRATION SUCCESSFUL", "The system has sucessfully registered your new account. You will now be redirected to the main login page");

        })
        .addOnFailureListener(function -> {
            registrationUnsuccessful.create().show();
            Log.e("REGISTRATION FAILED", "Failed to register the user's account");
        });
    }

    // use the 'FirebaseFirestore' to write to the Firebase storage...
    public static FirebaseFirestore RESTdb = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_account);

        //Prepare to receive the string from the firebase
        Intent retrieval = getIntent();
        String data = retrieval.getStringExtra("DataString");
        String dataLen = retrieval.getStringExtra("DataStringLen");
        String rawDataStrLen = retrieval.getStringExtra("DataRawStringLen");
        ArrayList<User> userArrayList = user_landing.ConvertToUser(data, dataLen, rawDataStrLen);
        ArrayList<Integer> idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> descriptionList = new ArrayList<>();

        for (int i = 0; i < userArrayList.size(); i++) {
            idList.add(userArrayList.get(i).id);
        }
        for (int i = 0; i < userArrayList.size(); i++) {
            nameList.add(userArrayList.get(i).name);
        }
        for (int i = 0; i < userArrayList.size(); i++) {
            descriptionList.add(userArrayList.get(i).description);
        }
        user_landing ul = new user_landing();

        Log.d("IDARR", "" + idList);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AtomicReference<String> tempString = new AtomicReference<>();
        tempString.set(user_landing.PasswordGenerator_x20length());
        AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
        String restorePrevPasswordInput = ((TextView) findViewById(R.id.setPasswordFieldText)).getText().toString();
        alertDialogBuilder1
        .setTitle("Password generated successfully!")
        .setMessage(String.format("The password generated is: \n%s", tempString.get()) + "\nDo you want to keep this password? ")
        .setPositiveButton(
            "YES, KEEP IT",
            (DialogInterface di, int i) -> {
                ((EditText) findViewById(R.id.setPasswordFieldText)).setText(tempString.get());
                di.dismiss();
            }
        )
        .setNegativeButton(
            "NO, GENERATE ANOTHER ONE",
            (DialogInterface di, int i) -> {
                di.dismiss();
                tempString.set(user_landing.PasswordGenerator_x20length());
                Log.d("sdf", "" + tempString.get());
                alertDialogBuilder1.setMessage(String.format("The password generated is: \n%s", tempString.get()) + "\nDo you want to keep this password? ");
                alertDialogBuilder1.create().show();
            }
        )
        .setCancelable(false);

        ((TextView) findViewById(R.id.autoGeneratePassword)).setOnClickListener(function -> {
            tempString.set(user_landing.PasswordGenerator_x20length());
            alertDialogBuilder1.create().show();
        });

        (findViewById(R.id.confirmationSignUpButton)).setOnClickListener(andThen -> {
            EditText usernameField = (findViewById(R.id.setUsernameFieldText));
            String usernameInput = usernameField.getText().toString(); //Extract the username string from the username field
            EditText passwordField = (findViewById(R.id.setPasswordFieldText));
            String passwordInput = passwordField.getText().toString(); //Extract the password string from the password field
            Toast message = Toast.makeText(this,
                    String.format("Password length is too long, it must be less than 30 characters.\nCurrent length: %s", usernameInput.length()),
                    Toast.LENGTH_LONG
            );
            if (usernameInput.length() != 0 && passwordInput.length() != 0) {
                if (ul.hasDuplicate(usernameInput, data, dataLen, null)) {
                    //if there is a duplicate....
                    alertDialogBuilder
                        .setTitle("Duplicate user error")
                        .setMessage(usernameInput + " is already taken. Please try again with another username.")
                        .setPositiveButton(
                            "Understood",
                            (DialogInterface di, int i) -> {
                                di.dismiss();
                            }
                        );
                    AlertDialog messageBox = alertDialogBuilder.create();
                    messageBox.show();
                }
                else {
                    Log.d("USERNAMEINPUT", usernameInput);
                    if (usernameInput.matches("[~!@#$%^&*()+`-|:<>?;'./]")) { //Check for illegal characters. It should not contain any symbol other than
                        Log.e("ILLEGAL CHARACTER", "Found an illegal character in your username");
                        AlertDialog.Builder illegalCharacterFound = new AlertDialog.Builder(this);
                        illegalCharacterFound
                        .setTitle("An illegal character was found")
                        .setMessage("An illegal character was found in your username")
                        .setPositiveButton(
                            "OK",
                            (DialogInterface di, int i) -> {
                                di.dismiss();
                            }
                        );
                        illegalCharacterFound.create().show();
                    }
                    else {
                        Log.d("NOILLEGALCHARACTER", "There were no illegal characters found in your username");
                        if (passwordInput.length() <= 30) {
                            String newName, newDescription;
                            ArrayList<String> newFollowedWhoList = new ArrayList<>();
                            boolean newFollowedStatus = false;
                            newFollowedWhoList.add(null);
                            int newID = FindMaxIn(idList) + 1;
                            Log.d("newID", "" + newID);
                            //Generate name (No duplicate) and then generate description (No duplicate)
                            String integerSegmentForName = "";
                            String integerSegmentForDescription = "";
                            boolean nameIsExistent = false;
                            boolean descriptionIsExistent = false;
                            do {
                                Log.d("DOWHILELOOP1ENTRY", "Entering the first do-while loop to generate the integer section for the name integer segment");
                                nameIsExistent = false;
                                do {
                                    integerSegmentForName += new Random().nextInt(10);
                                } while (integerSegmentForName.length() < 10);
                                for (int i = 0; i < nameList.size(); i++) { //Array of name directly from the firebase
                                    Log.d("FORLOOP1ENTRY", "Entering the first for-loop to generate the integer segment");
                                    if (nameList.get(i).substring(nameList.get(i).lastIndexOf('-') + 1).equals(integerSegmentForName)) {
                                        Log.d("DuplicateStringFound", "A duplicate string has been found. The system will now generate another string");
                                        integerSegmentForName = "";
                                        nameIsExistent = true;
                                        break;
                                    } else {
                                        Log.d("ITEM", "" + nameList.get(i).substring(nameList.get(i).lastIndexOf('-') + 1));
                                        Log.d("ELSE", "Entered the else clause");
                                    }
                                }
                                //Else the for-loop end naturally....with the variable 'itIsExistent' still being 'false'
                            } while (nameIsExistent);
                            newName = "Name-" + integerSegmentForName;
                            //Do the same for description
                            do {
                                descriptionIsExistent = false;
                                do {
                                    integerSegmentForDescription += new Random().nextInt(10);
                                }
                                while (integerSegmentForDescription.length() < 10);
                                for (int i = 0; i < descriptionList.size(); i++) {
                                    if (descriptionList.get(i).substring(descriptionList.get(i).lastIndexOf('-') + 1).equals(integerSegmentForDescription)) {
                                        integerSegmentForDescription = "";
                                        descriptionIsExistent = true;
                                        break;
                                    }
                                }
                            } while (descriptionIsExistent);
                            newDescription = "Description-" + integerSegmentForDescription;
                            //int id, String name, String description, String username, String password, Boolean followed, ArrayList<String> followedWho
                            User anotherUser = new User(newID, newName, newDescription, usernameInput, passwordInput, newFollowedStatus, newFollowedWhoList);
                            Log.d("NEWID", "" + newID);
                            Log.d("NEWNAME", newName);
                            Log.d("NEWDESCRIPTION", newDescription);
                            Log.d("NEWUSERNAME", usernameInput);
                            Log.d("NEWPASSWORD", passwordInput);
                            Log.d("NEWFOLLOWEDSTATUS", "" + newFollowedStatus);
                            Log.d("NEWFOLLOWEDWHOLIST", "" + newFollowedWhoList);
                            HashMap<String, User> a = new HashMap<>();
                            a.put("User" + newID, anotherUser);
                            Toast.makeText(this, "New account has been created....", Toast.LENGTH_SHORT);
                        }
                        else {
                            //Password length is too long!
                            message.cancel();
                            message.setText(String.format("Password length is too long, it must be less than 30 characters.\nCurrent length: %s", usernameInput.length()));
                            message.show();
                        }
                    }
                }
            }
            else {
                Toast.makeText(this, "Please enter a username and / or password", Toast.LENGTH_SHORT).show();
            }
        });

        //What if the user does not want to sign up anymore, and want to return to the previous page
        (findViewById(R.id.cancelSignUpButton)).setOnClickListener(andThen -> {
            alertDialogBuilder
            .setTitle("Confirm exit?")
            .setMessage("Are you sure you want to quit this process, all the values will be lost the moment you exit this page")
            .setPositiveButton(
                "YES",
                (DialogInterface di, int i) -> {
                    Intent teleporter = new Intent();
                    teleporter.setClassName(this, "sg.np.edu.mad.animationtest.user_landing");
                    startActivity(teleporter);
                    finish();
                }
            )
            .setNegativeButton(
                "NO",
                (DialogInterface di, int i) -> {
                    di.dismiss();
                }
            );

            //make the dialog box appear
            AlertDialog messageBox = alertDialogBuilder.create();
            messageBox.show();
        });
    }
}
