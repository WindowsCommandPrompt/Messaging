package sg.np.edu.mad.animationtest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.util.function.*;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.firestore.*;

public class user_landing extends AppCompatActivity {

    public  ArrayList<User> TEMPUSERLIST = null;
    public final String TAG = "TAG";
    public final String USERNAME = "Username";
    public final String PASSWORD = "Password";
    public static ArrayList<User> userList = new ArrayList<>();
    public static ArrayList<String> passwordList = new ArrayList<>(); //pass this array list into the method named 'PasswordGenerator_x20'
    public static ArrayList<String> usernameList = new ArrayList<>();

    public void setTempUserList(ArrayList<User> uList){
        this.TEMPUSERLIST = uList;
    }

    public ArrayList<User> getTempUserList(){
        return this.TEMPUSERLIST;
    }

    public static user_landing BASEOBJ = new user_landing();
    public static user_landing ENTRIES = new user_landing();

    //generate a password...
    private static void PasswordGenerator_x20length(ArrayList<String> password) {
        final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SYMBOLS = "~!@#$%^&*()+`-|:<>?;'./";
        final String STRING_POOL = LOWER_CASE_LETTERS + UPPER_CASE_LETTERS + DIGITS + SYMBOLS;
        Random generator = new Random();
        String element = "";
        int passwordLength = Math.abs(generator.nextInt(30));
        do{
            int index = Math.abs(generator.nextInt(STRING_POOL.length() - 1));
            char target = STRING_POOL.charAt(index);
            element += target;
        }
        while (element.length() < 20);
        password.add(element);
    }

    public static String PasswordGenerator_x20length(){
        final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String SYMBOLS = "~!@#$%^&*()+`-|:<>?;'./";
        final String STRING_POOL = LOWER_CASE_LETTERS + UPPER_CASE_LETTERS + DIGITS + SYMBOLS;
        Random generator = new Random();
        String element = "";
        int passwordLength = Math.abs(generator.nextInt(30));
        do{
            int index = Math.abs(generator.nextInt(STRING_POOL.length() - 1));
            char target = STRING_POOL.charAt(index);
            element += target;
        }
        while (element.length() < 20);
        return element;
    }

    //this method will always return one character from the string.
    private static char ReturnCharacterInterval(){
        final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
        final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String DIGITS = "0123456789";
        final String STRING_POOL = LOWER_CASE_LETTERS + UPPER_CASE_LETTERS + DIGITS;
        return STRING_POOL.charAt((new Random().nextInt(STRING_POOL.length() - 1)));
    }

    private static void UsernameGenerator_x100Quantity(ArrayList<String> username){
        ArrayList<String> descriptiveListForGeneralPositive = new ArrayList<String>(
                Arrays.asList(
                        "Powerful",
                        "Invincible",
                        "Fearless",
                        "Untouchable",
                        "Undestructable",
                        "Impregnable",
                        "Inviolable",
                        "Unassailable",
                        "Solid",
                        "Rugged",
                        "Tough",
                        "Vigorous",
                        "Sturdy",
                        "Robust",
                        "Substantial",
                        "Durable",
                        "Energetic",
                        "Speedy",
                        "Wise",
                        "Euphoric",
                        "Inspirational",
                        "Bright",
                        "Thoughtful",
                        "Diligent",
                        "Amiable",
                        "Hardworking",
                        "Studious",
                        "Clever",
                        "Brilliant",
                        "EasyGoing",
                        "Mindful",
                        "Adventurous",
                        "Enchanting",
                        "Wise",
                        "Exceptional",
                        "Determined",
                        "Thoughtful",
                        "Enthusiastic"
                )
        );
        ArrayList<String> nameListPerson = new ArrayList<String>(
                Arrays.asList(
                        "Professional",
                        "Wiz",
                        "User",
                        "Wizard",
                        "Killer",
                        "Wiper"
                )
        );
        ArrayList<String> nameListAnimal = new ArrayList<String>(
                Arrays.asList(
                        "Impala",
                        "Jaguar",
                        "Cheetah",
                        "Lion",
                        "Tiger"
                )
        );
        if ((new Random()).nextInt(2) == 0){
            //take from the nameListPerson
            username.add(    descriptiveListForGeneralPositive.get((Math.abs((new Random()).nextInt(descriptiveListForGeneralPositive.size() - 1)))) +
                             nameListPerson.get(Math.abs((new Random()).nextInt(nameListPerson.size() - 1))) + "_" +
                             ((Math.abs((new Random()).nextInt(3)) == 0)
                                     ? ReturnCharacterInterval()
                                     : (Math.abs((new Random()).nextInt(3)) == 1)
                                            ? 2 * ReturnCharacterInterval()
                                            : (Math.abs((new Random()).nextInt(3)) == 2)
                                                    ? 3 * ReturnCharacterInterval()
                                                    : (Math.abs((new Random()).nextInt(3)) == 3)
                                                            ? 4 * ReturnCharacterInterval()
                                                            : "")
            );
        }
        else if ((new Random()).nextInt(2) == 1){
            //take from the nameListAnimal
            username.add(    descriptiveListForGeneralPositive.get((Math.abs((new Random()).nextInt(descriptiveListForGeneralPositive.size() - 1)))) +
                             nameListAnimal.get(Math.abs((new Random()).nextInt(nameListAnimal.size() - 1))) + "_" +
                            ((Math.abs((new Random()).nextInt(3)) == 0)
                                    ? ReturnCharacterInterval()
                                    : (Math.abs((new Random()).nextInt(3)) == 1)
                                            ? 2 * ReturnCharacterInterval()
                                            : (Math.abs((new Random()).nextInt(3)) == 2)
                                                    ? 3 * ReturnCharacterInterval()
                                                    : (Math.abs((new Random()).nextInt(3)) == 3)
                                                            ? 4 * ReturnCharacterInterval()
                                                            : "")
            );
        }
    }

    public DBHandler dbHandler = new DBHandler(this);
    public DBHandlerExtra dbHandlerExtraForTempDataStorage = new DBHandlerExtra(this);

    public int GenerateNumOfFollowers(){
        Random generator = new Random();
        //number generated from the random number generator will exclude the value included in the bound. Therefore
        return Math.abs(generator.nextInt((userList.size()) + 1));
    }

    public int GenerateRandomIndex(){
        Random generator = new Random();
        //excludes the overall size of the data stored within the database.....
        return Math.abs(generator.nextInt((userList.size())) - 1);
    }

    //substitute int numOfFollowers with GenerateNumofFollowers() : int
    public String[] AppendFollowerList(){
        ArrayList<String> usernameStringList = new ArrayList<>();
        ArrayList<String> tempHolder = new ArrayList<>();
        if (GenerateNumOfFollowers() != 0) {
            do {
                usernameStringList.add(userList.get(GenerateRandomIndex()).username);
            }
            while (usernameStringList.size() < GenerateNumOfFollowers());
            for (String s : new HashSet<String>(usernameStringList)){
                //remove duplicates
                tempHolder.add(s);
            }
            return tempHolder.toArray(new String[]{ });
        }
        else {
            return new String[]{ };
        }
    }

    public ArrayList<User> AppendIntoActualArray(){
        for (int i = 0; i < userList.size(); i++){
            userList.get(i).followedWho = new ArrayList<String>(Arrays.asList(AppendFollowerList()));
            if (userList.get(i).followedWho.contains(userList.get(i).username)){
                userList.get(i).followedWho.remove(userList.get(i).username);
            }
        }
        return userList;
    }

    //dangerous, use this method with care
    public static void DeleteEntiresFirebaseFirestore(){
        RESTdb.collection("LoginInformation").document("LoginInformation")
        .delete()
        .addOnSuccessListener((Void)->{
            Log.d("SDF", "Database successfully deleted!");
        })
        .addOnFailureListener((Void)->{
            Log.d("SDF", "Database failed to delete. Please try again...");
        });
    }

    //dangerous, use this method with care
    private void WriteToFirebaseFirestore(HashMap<String, User> userData) {
        RESTdb.collection("LoginInformation").document("LoginInformation").get().addOnSuccessListener(process -> {
            Log.d("INITIALLENGTH", "" + process.getData().size());
            if (process.getData().size() < 20) {
                Log.d("Function entry", "Starting to write");
                RESTdb.collection("LoginInformation").document("LoginInformation")
                .set(userData)
                .addOnSuccessListener((Void) -> {
                    Log.d("DocumentUpdateStatus", "Data append successful");
                })
                .addOnFailureListener((Void) -> {
                    Log.d("DocumentUpdateStatus", "Data append failed");
                });
            }
        });
    }

    // use the 'FirebaseFirestore' to write to the Firebase storage...
    public static FirebaseFirestore RESTdb = FirebaseFirestore.getInstance();

    //check for whether the username exists in the database, false if it does not exist
    //Firestorage (root) -> collection node -> document node
    public ArrayList<Object> isAnEntryInDatabase(String username, String password, String string, String dataStrLen, String dataRawStrLen){
        ArrayList<Object> verificationStatus = new ArrayList<>();
        Log.d("isAnEntryInDatabase", "Function begins execution....");
        Log.d("Size", "" + ConvertToUser(string, dataStrLen, dataRawStrLen).size());
        ArrayList<User> userList = ConvertToUser(string, dataStrLen, dataRawStrLen);
        for (int i = 0; i < userList.size(); ++i){
            Log.d("ResultUsername", userList.get(i).username);
            Log.d("ResultPassword", userList.get(i).password);
            if (userList.get(i).username.equals(username) && userList.get(i).password.equals(password)){
                Log.d("Entered here", "Has entered this clause");
                verificationStatus.add(true);
                verificationStatus.add(true);
                verificationStatus.add(i);
                break;
            }
        }
        for (Object o : verificationStatus){
            Log.d("INTERNAL", "" + o);
        }
        return verificationStatus;
    }

    public boolean hasDuplicate(String username, String string, String dataStrLen, String dataRawStrLen){
        boolean existence = false;
        ArrayList<User> a = ConvertToUser(string, dataStrLen, dataRawStrLen);
        for (User u : a){
            if (u.username.equals(username)){
                existence = true;
            }
        }
        return existence;
    }

    //This method is only used to log extremely long messages
    public static void DebugLog(String tag, String msg){
        int maxLogSize = 1000;
        for(int i = 0; i <= msg.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > msg.length() ? msg.length() : end;
            Log.d(tag, msg.substring(start, end));
        }
    }

    //Convert the ExtractAllFromDatabase() into an HashMap that stores User objects
    //unpackage the object and form a user
    public static ArrayList<User> ConvertToUser(String string, String dataStrLen, String dataRawStrLen) {
        Log.d("ConvertingToUser", "Converting to user..");
        Log.d("NumOfEntries", dataStrLen);
        Log.d("NumRawEntries", dataRawStrLen == null ? "dataRawStrLen has been passed in as null, please supply it with a value." : dataRawStrLen);
        ArrayList<Integer> idList = new ArrayList<Integer>();
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> descriptionList = new ArrayList<String>();
        ArrayList<String> usernameList = new ArrayList<String>();
        ArrayList<String> passwordList = new ArrayList<String>();
        ArrayList<Boolean> followedList = new ArrayList<Boolean>();
        ArrayList<ArrayList<String>> followedWhoList = new ArrayList<ArrayList<String>>();
        String[] a = string.split("="); //split
        String intermediate = "";
        String tertiary = "";
        String beyondTertiary = "";
        for (int i = 0; i < a.length; i++) {
            intermediate += a[i];
        }
        String[] b = intermediate.split("\\{");
        for (int i = 0; i < b.length; i++) {
            tertiary += b[i];
        }
        String step = tertiary.replace("password", "").replace("name", "").replace("description", "").replace("followedWho", "").replace("followed", "").replace("id", "");
        String[] c = step.split("\\}");
        for (int i = 0; i < c.length; i++) {
            beyondTertiary += c[i];
        }
        String step2 = beyondTertiary.replace("user", "");
        DebugLog("STEP2", step2);
        String[] d = step2.split(" ");
        String step3 = "";
        for (int i = 0; i < d.length; i++) {
            Log.d("SplitStringStage4", "" + d[i]);
            step3 += d[i];
        }
        DebugLog("TAGMESSAGE", "" + step3); //NO problem till this point
        String[] e = step3.split(",");
        ArrayList<String> innerArray = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < e.length; i++) {
            DebugLog("SplitStringStage9", e[i]);
            if (e[i].startsWith("User")){ //Password go here
                passwordList.add(
                e[i]
                .replace("User", "")
                .replace(
                ((Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").contains(Character.toString(e[i].charAt(5)))) && (Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").contains(Character.toString(e[i].charAt(4))))
                ? e[i].substring(4, 6)
                : Character.toString(e[i].charAt(4)))
                , "")
                );
            }
            if ( //followedWho list go here   FIXME: INCONSISTENT ARRAY LENGTH FOR FOLLOWEDWHO
                (
                    (
                        //IMPORTANT -> '[]' OR '[Username_key?]' instead of '[Username1_key?, Username2_key?]
                        (
                            //Each username has a underscore character inside
                            //length of the string after and including the underscore character greater than 1
                            (e[i].contains("_") && (e[i].substring(e[i].lastIndexOf("_")).length() > 1))
                            //might end with the terminating character
                            ? e[i].endsWith("]") ||
                            //last character is 0 to 9 OR
                            (e[i].substring(e[i].lastIndexOf("_")).charAt(1) >= '0' && e[i].substring(e[i].lastIndexOf("_")).charAt(1) <= '9') ||
                            (
                                //last character is any small letter alphabet from 'a' to 'z' OR
                                (e[i].substring(e[i].lastIndexOf("_")).charAt(1) >= 'a' && e[i].substring(e[i].lastIndexOf("_")).charAt(1) <= 'z') ||
                                //last character is any capital letter alphabet from 'A' to 'Z'
                                (e[i].substring(e[i].lastIndexOf("_")).charAt(1) >= 'A' && e[i].substring(e[i].lastIndexOf("_")).charAt(1) <= 'Z')
                            )
                            //if length is not 1 then check whether the string ends of with an underscore character
                            //BUT WHAT IF the element is just simply '[]'???
                            : e[i].endsWith("_") || e[i].contains("[]")
                        )
                    )
                )
            ) {
                //If e[i].startsWith("[") then add a breakpoint, terminate when the loop meets a string that contains "]" inside
                //Keep adding the items that is between strings that have met the e[i].startsWith("]") condition and the e[i].endsWith("]") condition
                innerArray.add(e[i]);
            }
            if (i - 1 >= 0) { //Check the last character whether does it equate to a single underscore character, if not check the character to the right of the underscore
                if (e[i].contains("_") && (e[i-1].equals("true") || e[i-1].equals("false"))){
                    usernameList.add(e[i]);
                }
                //DO NOT USE OVERLAPPING CONDITIONS
                if (e[i].contains("Name-")) {
                    nameList.add(e[i]);
                }
                if (e[i].contains("Description-")) {
                    descriptionList.add(e[i]);
                }
                if (e[i].equals("true") || e[i].equals("false")) {
                    followedList.add(Boolean.parseBoolean(e[i]));
                }
                if (e[i].length() <= 2 && (e[i].charAt(0) >= '0' && e[i].charAt(0) <= '9')){
                    idList.add(Integer.parseInt(e[i]));
                }
            }
        }
        //Remove sandwiched usernames
        for (int j = 0; j < innerArray.size(); ++j){
            //Perform string manipulation and slicing '(internal array string grouping)'
            if (j + 1 < innerArray.size() && j - 1 >= 0){
                if (innerArray.get(j+1).contains("[") && innerArray.get(j-1).contains("]")){
                    innerArray.remove(j);
                }
            }
        }
        //Remove the last element from the array
        innerArray.remove(innerArray.size() - 1);
        for (int i = 0; i < innerArray.size(); i++){
            Log.d("FILTEREDELEMENTS", innerArray.get(i)); //Filtered elements return the correct result
            if (innerArray.get(i).startsWith("[")){
                followedWhoList.add(new ArrayList<>()); //Insert empty lists accordingly
            }
        }
        //innerArray.remove(innerArray.size() - 1); //Remove the last element from the array
        DebugLog("INNERARRAY", "" + innerArray);
        Log.d("INNERARRAYSIZEBEFORE", "" + innerArray.size());
        Log.d("CURRLENPASSWORD", "" + passwordList.size());
        Log.d("CURRLENNAME", "" + nameList.size());
        Log.d("CURRLENFOLLOWED", "" + followedList.size());
        Log.d("CURRLENDESCRIPTION", "" + descriptionList.size());
        Log.d("CURRLENID", "" + idList.size());
        Log.d("CURRLENUSERNAME", "" + usernameList.size());
        Log.d("CURRLENFOLLOWEDWHO", "" + followedWhoList.size());
        int j = 0;int startingIndex = 0;int endingIndex = 0;boolean onlyOneElement = false;
        //Until here everything is correct
        for (; ; ) {
            if (j < followedWhoList.size()) {
                Log.d("REPEATING", "The process is currently repeating");
                for (int i = 0; i < innerArray.size(); i++) {
                    if (innerArray.get(i).contains("[") && innerArray.get(i).contains("]")) {
                        Log.d("STARTINGINDEXNEW", "" + startingIndex);
                        Log.d("ENDINGINDEXNEW", "" + endingIndex);
                        startingIndex = i = endingIndex;
                        break;
                    }
                    if (innerArray.get(i).contains("[") && !innerArray.get(i).contains("]")) {
                        Log.d("STARTINGINDEX", "" + startingIndex);
                        startingIndex = i;
                    }
                    if (innerArray.get(i).contains("]")) {
                        endingIndex = i;
                        Log.d("ENDINGINDEX", "" + endingIndex);
                        break;
                    }
                }
                if (startingIndex == endingIndex) { //might be '[]' or '[Username]'
                    if ((innerArray.get(startingIndex).contains("[") && innerArray.get(startingIndex).contains("]")) && !innerArray.get(startingIndex).equals("[]")) {
                        onlyOneElement = true;
                    }
                }
                //Create a boundary (range)
                if (onlyOneElement || (startingIndex != endingIndex)) {
                    if (!onlyOneElement) {
                        do {
                            Log.d("BoundaryEntry", "If Statement Entry!");
                            Log.d("CURRVALJ", "" + j);
                            Log.d("LOADEDELEMENT", "" + innerArray.get(startingIndex));
                            followedWhoList.get(j).add(
                                innerArray.get(startingIndex)
                            );
                            ++startingIndex; //Move on to next element
                        } while (startingIndex <= endingIndex);
                    }
                    else {
                        followedWhoList.get(j).add(
                            innerArray.get(startingIndex)
                        );
                    }
                    //WHEN DONE: UPON SUCCESSFUL DELETION
                    startingIndex = 0; //initialize again to prepare for deletion
                    if (!onlyOneElement) {
                        do {
                            Log.d("INDEX", "" + startingIndex);
                            Log.d("RemovedElement", innerArray.get(startingIndex));
                            //Delete the element from the innerArray
                            innerArray.remove(startingIndex);
                            endingIndex--;
                        }
                        while (endingIndex >= 0);
                    }
                    else {
                        innerArray.remove(innerArray.get(startingIndex));
                    }
                }
                else { //Only for '[]'
                    innerArray.remove(innerArray.get(startingIndex)); //remove that element
                    //if just an empty array, add a null then skip that cell
                    followedWhoList.get(j).add(null);
                }
                j++;
                startingIndex = 0; //initialize
                endingIndex = 0; //initialize
                onlyOneElement = false;
                Log.d("ENDINGINDEXINITIALIZATION", "" + endingIndex);
            }
            else {
                Log.d("FINALINNERARRAYLENGTH", "" + innerArray.size()); //must be 0
                Log.d("BREAK", "Broke out of infinite loop");
                break;
            }
        }
        Log.d("FINALARRAYSIZE", "" + followedWhoList.size());
        DebugLog("FINALARRAYOUTCOME", "" + followedWhoList);
        ArrayList<User> userUserList = new ArrayList<User>();
        if (idList.size() == followedList.size() && idList.size() == descriptionList.size() && idList.size() == nameList.size() && idList.size() == passwordList.size()  && idList.size() == usernameList.size()  && idList.size() == followedWhoList.size()) {
            //Share the same for loop if only the lengths of all the arrays involved are exactly equal to one another.
            for (int i = 0; i < followedWhoList.size(); i++) {
                userUserList.add(
                    new User(
                        idList.get(i),
                        nameList.get(i),
                        descriptionList.get(i),
                        usernameList.get(i),
                        passwordList.get(i),
                        followedList.get(i),
                        followedWhoList.get(i)
                    )
                );
            }
        }
        else {
            Log.e("FATAL EXCEPTION!", "Unbalanced array lengths");
        }
        Log.d("ReturnTheUserList", "Returning the array from the method....");
        BASEOBJ.setTempUserList(userUserList);
        return userUserList;
    }

    private void Intermediary(){
        DocumentReference df = RESTdb.collection("LoginInformation").document("LoginInformation");
        df.get().addOnSuccessListener(process-> {
            final HashMap<String, Object> a = ((HashMap<String, Object>)process.getData()); //all the data stored in a variable named a..
            Log.d("Data size", "" + a.size()); //99
            Log.d("Current Data", "" + a);
            ((TextView) findViewById(R.id.tempString)).setText(a.toString()); //The database goes here
            ((TextView) findViewById(R.id.databaseStringLength)).setText(Integer.toString(a.size()));
        })
        .addOnFailureListener(process -> {
            AlertDialog.Builder extractionFailure = new AlertDialog.Builder(this);
            extractionFailure
            .setTitle("Failed to retrieve required data")
            .setMessage("The system has failed to retrieve the required data from the database. Please")
            .setNeutralButton(
                "OK",   //Insert actual string or string resource over here.... R.string.??
                (DialogInterface di, int i) -> {
                    di.dismiss();
                }
            )
            .setCancelable(false);
            Toast.makeText(this, "Unable to get information from remote database", Toast.LENGTH_SHORT).show();
        });
    }

    private void ConvertToChatHistory(String result, String dataStrLen){
        String primary = "";
        String secondary = "";
        String tertiary = "";
        String beyondTertiary = "";

    }

    private void GenerateHistory(ArrayList<User> userArrayList){
        Log.d("GENERATEHISTORYFUNCTIONENTRY", "Entering the method named ");
        DocumentReference df = RESTdb.collection("LoginInformation").document("LoginInformation");
        DocumentReference df1 = RESTdb.collection("ChatInformation").document("ChatHistory");
        df1.get()
        .addOnSuccessListener(rootProcess -> {
            if (rootProcess.getData().size() < 20) {
                ArrayList<HashMap<String, ArrayList<ChatHistory>>> a = new ArrayList<>();
                HashMap<String, ArrayList<ChatHistory>> chatHistory = new HashMap<>(); //String is the user
                int attempt = 0; //try count
                int index = 0; //current position within the array
                int outcome;
                do {
                    //Create a random number generator that is responsible for generating whether the user has chat with that user
                    outcome = new Random().nextInt(2);
                    String result = "";
                    int outboundHourFromSender = 0; int inboundHourToSender = 0; int outboundMinuteFromSender = 0; int inboundMinuteToSender = 0; int outboundSecondFromSender = 0; int inboundSecondToSender = 0; //int values for senders
                    int outboundHourFromRecipient = 0; int inboundHourToRecipient = 0; int outboundMinuteFromRecipient = 0; int inboundMinuteToRecipient = 0; int outboundSecondFromRecipient = 0; int inboundSecondToRecipient = 0; //int values for recipients
                    do {
                        result = userArrayList.get(new Random().nextInt(userArrayList.size() - 1)).username;
                    }
                    while (!userArrayList.get(new Random().nextInt(userArrayList.size() - 1)).username.equals(result));
                    //boolean chunk no.1
                    //check if the hours are the same
                    //check if the minutes are the same
                    //hours and minutes are the same, then check check if
                    //hours is the same, but minutes not equal
                    //boolean chunk no.2
                    //Perform cross comparison between boolean chunk no.1 and boolean chunk no.2
                    do {
                        outboundHourFromSender = Math.abs(new Random().nextInt(24));      //hour segment from sender to receiver
                        inboundHourToSender = Math.abs(new Random().nextInt(24));         //hour segment when the sender reads the message sent to him from the recipient
                        outboundMinuteFromSender = Math.abs(new Random().nextInt(60));    //minute segment from sender to receiver
                        inboundMinuteToSender = Math.abs(new Random().nextInt(60));       //minute segment when the sender reads the message sent to him from the recipient
                        outboundSecondFromSender = Math.abs(new Random().nextInt(60));    //second segment from sender to receiver
                        inboundSecondToSender = Math.abs(new Random().nextInt(60));       //second segment when the sender reads the message from the recipient
                        //BELOW IS TO RECEIVER SIDE
                        outboundHourFromRecipient = Math.abs(new Random().nextInt(24));   //hour segment when the recipient sends a message back to the sender
                        inboundMinuteToRecipient = Math.abs(new Random().nextInt(60));    //minute segment when the recipient reads a message that has been sent to him from the sender
                        outboundMinuteFromRecipient = Math.abs(new Random().nextInt(60)); //minute segment from recipient back to the sender
                        inboundHourToRecipient = Math.abs(new Random().nextInt(24));      //hour segment when the recipient reads the message
                        outboundSecondFromRecipient = Math.abs(new Random().nextInt(60)); //second segment from recipient back to sender
                        inboundSecondToRecipient = Math.abs(new Random().nextInt(60));    //second segment when the recipient reads the message
                    }
                    while (
                            ( //boolean chunk no.1
                                outboundHourFromSender == inboundHourToSender           //check if the hours are the same
                                ? outboundMinuteFromSender == inboundMinuteToSender     //check if the minutes are the same
                                    ? outboundSecondFromSender < inboundSecondToSender  //hours and minutes are the same, then check check if
                                    : outboundMinuteFromSender < inboundMinuteToSender && outboundSecondFromSender < inboundSecondToSender //hours is the same, but minutes not equal
                                : outboundHourFromSender < inboundHourToSender && outboundMinuteFromSender < inboundMinuteToSender && outboundSecondFromSender < inboundSecondToSender
                            ) &&
                            ( //boolean chunk no.2
                                outboundHourFromRecipient == inboundHourToRecipient
                                ? outboundMinuteFromRecipient == inboundMinuteToRecipient
                                    ? outboundSecondFromRecipient < inboundSecondToRecipient
                                    : outboundMinuteFromRecipient < inboundMinuteToRecipient && outboundSecondFromRecipient < inboundSecondToRecipient
                                : outboundHourFromRecipient < inboundHourToRecipient && outboundMinuteFromRecipient < inboundMinuteToRecipient && outboundSecondFromRecipient < inboundSecondToRecipient
                            ) &&
                            //Perform cross comparison between boolean chunk no.1 and boolean chunk no.2
                            //Shown in boolean chunk no.3 and boolean chunk no.4
                            (
                                outboundHourFromSender == outboundHourFromRecipient
                                ? outboundMinuteFromSender == outboundMinuteFromRecipient && outboundSecondFromSender < outboundSecondFromRecipient
                                : outboundHourFromSender < outboundHourFromRecipient && outboundMinuteFromSender < outboundMinuteFromRecipient && outboundSecondFromSender < outboundSecondFromRecipient
                            ) &&
                            (
                                inboundHourToSender == inboundHourToRecipient
                                ? inboundMinuteToSender == outboundMinuteFromRecipient && outboundSecondFromSender < outboundSecondFromRecipient
                                : outboundHourFromSender < outboundHourFromRecipient && outboundMinuteFromSender < outboundMinuteFromRecipient && outboundSecondFromSender < outboundSecondFromRecipient
                            )
                            //Do a cross check between boolean chunk no.3 and boolean chunk no.4

                    );
                    //Key of each hashmap is the chat history identifier (whose chat history does that belong to)
                    chatHistory.put(userArrayList.get(index).username, new ArrayList<ChatHistory>(Arrays.asList(
                        new ChatHistory(
                            result, //Who receives the messsage
                            //Key of the HashMap is ChatHistory.MessageStats.MessageEntity object
                            new ArrayList<HashMap<ChatHistory.MessageStats.MessageEntity, String>>(Arrays.asList(
                                new HashMap<ChatHistory.MessageStats.MessageEntity, String>(
                                    Collections.singletonMap(new ChatHistory.MessageStats.MessageEntity(
                                            Math.abs(new Random().nextInt(2)) == 0 ? userArrayList.get(index).username : result,
                                            "sdf"
                                    ), "")
                                )
                            )), //list of messages containing who sent the message, and the message itself
                            new ChatHistory.MessageStats(
                                new ChatHistory.MessageStats.MessageStatsTimeFormat(outboundHourFromSender, outboundMinuteFromSender, outboundSecondFromRecipient).returnTimeAsFullString(),  //Time sent by sender
                                new ChatHistory.MessageStats.MessageStatsTimeFormat(inboundHourToSender, inboundMinuteToSender, inboundSecondToSender).returnTimeAsFullString(),  //Time read by sender
                                new ChatHistory.MessageStats.MessageStatsTimeFormat(outboundHourFromRecipient, outboundMinuteFromRecipient, outboundSecondFromRecipient).returnTimeAsFullString(),  //Time sent by the receiver
                                new ChatHistory.MessageStats.MessageStatsTimeFormat(inboundHourToRecipient, inboundMinuteToRecipient, inboundSecondToRecipient).returnTimeAsFullString()   //Time that has been read by the receiver
                            )
                        )
                    )));
                    attempt++;
                    index++;
                }
                while (attempt < rootProcess.getData().size());
                /*df1.set(chatHistory)
                .addOnSuccessListener(internalProcess -> {
                    Log.d("CHATHISTORYINITIALIZATIONSUCCESS", "Successfully passed the value into the database");
                });*/
            }
            else {
                return;
            }
        })
        .addOnFailureListener(process -> {
            Log.e("CHATHISTORYRETRIEVALFAILURE", "Failed to retrieve the required chat history data from the remote database. Please try again later");
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d("POSTCREATE", "The activity has been created");
        TextView textView = (TextView) findViewById(R.id.tempString);

        Log.d("CONTENTS", "" + textView.getText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("ONCREATE", "The activity is being created");
        //Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        //Repeat this process for 20 times
        do {
            PasswordGenerator_x20length(passwordList);
        }
        while (passwordList.size() <= 20);

        //Repeat this process for 20 times....
        do {
            UsernameGenerator_x100Quantity(usernameList);
        }
        while (usernameList.size() <= 20);

        //Generate 100 user data and append it into the database.
        int tag = 1;
        do {
            Random generator = new Random();
            int descriptionIntegerSegment = Math.abs(generator.nextInt());
            int nameIntegerSegment = Math.abs(generator.nextInt());
            String name = "Name-" + nameIntegerSegment;
            String description = "Description-" + descriptionIntegerSegment;
            //put all the information in a user object.
            userList.add(new User
                (
                    tag,
                    name,
                    description,
                    usernameList.get(tag - 1),
                    passwordList.get(tag - 1),
                    false,
                    new ArrayList<>() //add a blank ArrayList
                )
            );
            tag++;
        }
        while (tag <= 20);

        Log.d(TAG, "Code reached this checkpoint: Checkpoint 1");

        //run AppendFollowerList()
        for (int i = 0; i < AppendIntoActualArray().size(); i++) {
            Log.d(USERNAME, (userList.get(i).username));
            Log.d(PASSWORD, (userList.get(i).followedWho) + "\n========================================================================");
        }

        //append the list of objects directly into Google Firebase...
        HashMap<String, User> userData = new HashMap<>();
        for (int i = 0; i < userList.size(); i++) {
            userData.put("User " + i, userList.get(i)); //the whole document stores a map, consisting of a key that has been declared as a string and the value which has been declared as a userObject
        }

        //READ LOG.D to check for password existance.
        for (int i = 0; i < userList.size(); i++) {
            Log.d("ID", "" + userList.get(i).id);
            Log.d(USERNAME, (userList.get(i).username));
            Log.d(PASSWORD, (userList.get(i).followedWho) + "\n========================================================================");
        }

        //Add into the database...
        WriteToFirebaseFirestore(userData);

        //run through the entire SQLite database again......
        if (dbHandler.GetAllUsers().size() == 0) {
            Log.d("Redirect to login", "Going to user login activity");
            setContentView(R.layout.practical5_loginpage); //Set this content view if SharedPreferences contains NO DATA
            Intermediary(); //Load the login information into an invisible textview
        }
        else {
            Log.d(TAG, "Else statement executed");
            //straight away go to user_main.java
            Intent transporter = new Intent();
            transporter.setClassName("sg.np.edu.mad.animationtest", "sg.np.edu.mad.animationtest.user_main");
            startActivity(transporter);
            finish();
        }

        Log.d("DETAILS", "" + ((TextView) findViewById(R.id.tempString)).getText().toString());

        Button myLoginButton = (Button) findViewById(R.id.myLoginButton);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //Create a new user account
        TextView newUser = (TextView) findViewById(R.id.createNewUser);

        if (newUser != null && myLoginButton != null) {
            //if the user taps on the 'New User?' text
            newUser.setOnTouchListener((View v, MotionEvent m) -> {
                //go to another java class
                String result = ((TextView) findViewById(R.id.tempString)).getText().toString();
                String result1 = ((TextView) findViewById(R.id.databaseStringLength)).getText().toString();
                String result2 = Integer.toString(((TextView) findViewById(R.id.tempString)).getText().length()); //raw length
                Intent andThenRedirect = new Intent(user_landing.this, create_user_account_funcitonality.class);
                andThenRedirect.putExtra("DataString", result); //data string
                andThenRedirect.putExtra("DataStringLen", result1); //20
                andThenRedirect.putExtra("DataRawStringLen", result2);
                startActivity(andThenRedirect);
                finish();
                //Toast.makeText(this, "This feature is not available as of now", Toast.LENGTH_SHORT).show();
                return m.isButtonPressed(R.id.createNewUser);
            });

            alertDialog
            .setTitle("User authentication has failed")
            .setMessage("Either your username or your password is not correct. Please try again.")
            .setPositiveButton(
                    "Create another account",
                    (DialogInterface di, int i) -> {
                        di.dismiss();
                        //then redirect to another activity...
                        Intent andThenRedirect = new Intent(user_landing.this, create_user_account_funcitonality.class);
                        startActivity(andThenRedirect);
                        finish();
                        //Toast.makeText(this, "This feature is not available as of now", Toast.LENGTH_SHORT).show();
                    }
            )
            .setNegativeButton(
                    "Try again",
                    (DialogInterface di, int i) -> {
                        di.dismiss();
                    }
            )
            .setCancelable(false);

            myLoginButton.setOnClickListener(ThenFunction -> {
                Log.d("DatabaseFullStringLen", "" + ((TextView) findViewById(R.id.tempString)).getText().length());
                DebugLog("DatabaseFullString", ((TextView) findViewById(R.id.tempString)).getText().toString());
                Log.d("DatabaseEntries", ((TextView) findViewById(R.id.databaseStringLength)).getText().toString());
                String result = ((TextView) findViewById(R.id.tempString)).getText().toString();
                String result1 = ((TextView) findViewById(R.id.databaseStringLength)).getText().toString();
                String result2 = Integer.toString(((TextView) findViewById(R.id.tempString)).getText().length());

                //Add the information accordingly
                if (dbHandlerExtraForTempDataStorage.GetInformation().length != 0) {
                    Log.i("NOENTRIES", "There are currently no entries within the \"TempData\" database itself");
                }
                //dbHandlerExtraForTempDataStorage.ClearDatabase();
                Log.d("BLAH", "" + dbHandlerExtraForTempDataStorage.GetInformation());
                GenerateHistory(ConvertToUser(result, result1, result2)); //Load the chat history into an invisible textview for deserialization.

                //Take in the password.
                EditText passwordTextField = (EditText) findViewById(R.id.passwordField);
                //then take in the username
                EditText usernameTextField = (EditText) findViewById(R.id.usernameField);
                Log.d(TAG, "The code reached here");
                if (passwordTextField.getText().length() != 0 && usernameTextField.getText().length() != 0) {
                    ArrayList<Object> finalObjArr = isAnEntryInDatabase(usernameTextField.getText().toString(), passwordTextField.getText().toString(), result, result1, result2);
                    Log.d("Account verification", "Verification started....");
                    Log.d("USERNAMECHECKSTATUS", "" + finalObjArr.get(0));
                    Log.d("PASSWORDCHECKSTATUS", "" + finalObjArr.get(1));
                    if (((Boolean) finalObjArr.get(0)) && (Boolean) finalObjArr.get(1)) {
                        //if the username exist then check for the password.
                        ArrayList<String> abc = new ArrayList<String>();
                        ArrayList<String> abc1 = new ArrayList<>();
                        ArrayList<String> abc2 = new ArrayList<>();
                        for (int i = 0; i < ConvertToUser(result, result1, result2).size(); i++) {
                            //Add all users into the list.
                            abc.add(ConvertToUser(result, result1, result2).get(i).username);
                            abc1.add(ConvertToUser(result, result1, result2).get(i).name);
                            abc2.add(ConvertToUser(result, result1, result2).get(i).description);
                        }
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "User login is successful at this point");
                        //Add the user object into the SQLDB..
                        Intent andThenRedirect = new Intent(sg.np.edu.mad.animationtest.user_landing.this, sg.np.edu.mad.animationtest.user_main.class);
                        andThenRedirect.putExtra("Name", ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).name);
                        andThenRedirect.putExtra("Description", ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).description);
                        andThenRedirect.putExtra("FollowedWhoList", ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).followedWho.toArray(new String[]{}));
                        andThenRedirect.putExtra("FullUserList", abc.toArray(new String[]{}));
                        andThenRedirect.putExtra("FullNameList", abc1.toArray(new String[]{}));
                        andThenRedirect.putExtra("FullDescriptionList", abc2.toArray(new String[]{}));
                        //Form a user object....
                        User user = new User(
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).id,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).name,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).description,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).username,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).password,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).followed,
                                ConvertToUser(result, result1, result2).get((Integer) finalObjArr.get(2)).followedWho
                        );
                        dbHandler.AddUser(user);
                        //tell user_landing.java to pass the task to user_main.java
                        startActivity(andThenRedirect);
                        finish();
                    } else {
                        Log.d("BOTHWRONG", "Both username and the password are wrong");
                        alertDialog.create().show();
                    }
                } else {
                    //make a toast message that tells the user that the username and the password field has been left blank
                    Toast.makeText(this, "Please enter your username and / or password", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}