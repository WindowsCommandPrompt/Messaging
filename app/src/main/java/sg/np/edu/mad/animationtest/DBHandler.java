package sg.np.edu.mad.animationtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "UserAccount.db";
    public static String ACCOUNTS = "UserAccounts";
    public static String COLUMN_USERNAME = "Username";
    public static String COLUMN_PASSWORD = "Password";
    public static String COLUMN_ID = "Id";
    public static String COLUMN_DESCRIPTION = "Description";
    public static String COLUMN_NAME = "Name";
    public static String COLUMN_FOLLOWEDWHO = "FollowedWho";
    public static int DATABASE_VERSION = 1;

    //The database will have a total of
    @Override
    public void onCreate(SQLiteDatabase DB){
        //FollowedWho STORES AN ARRAY, convert the entire ArrayList into a string, and then convert it back
        String command = "CREATE TABLE UserAccounts " +
                         "("
                             + "Id INTEGER,"
                             + "Name TEXT,"
                             + "Description TEXT,"
                             + "FollowedWho TEXT,"
                             + "Username TEXT,"
                             + "Password TEXT" +
                         ")";

        DB.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB,  int oldVersion, int newVersion){
        DB.execSQL("DROP TABLE IF EXISTS UserAccounts");
        onCreate(DB);
    }

    //pass in the user object. Extract the username and the password of the user object accordingly
    public void AddUser(User user){
        Log.d("Adding", "Adding the user to the database");
        SQLiteDatabase SQLDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", user.id);
        values.put("Name", user.name);
        values.put("Description", user.description);
        //convert the entire string to array...
        //Convert.ToObject([Type], [Value]) would sound better
        //Convert.ToObject(String, user.followedWho);
        values.put("FollowedWho", user.followedWho.toString());
        values.put("Username", user.username);
        values.put("Password", user.password);
        SQLDB.insert("UserAccounts", null, values);
        SQLDB.close();
    }

    public ArrayList<User> GetAllUsers(){
        ArrayList<User> tempUserList = new ArrayList<>();
        //String query = String.format("SELECT * FROM UserAccounts WHERE Username = %s", username);
        String query1 = "SELECT * FROM UserAccounts";
        SQLiteDatabase SQLDB = this.getReadableDatabase();
        Cursor pointer = SQLDB.rawQuery(query1, null);
        User returnUser = new User();
        if (pointer.getCount() != 0) {
            if (pointer.moveToFirst()) {
                //sequence, left-most column to right-most column -> id, name, description, followed, followedWho, username, password
                returnUser.setId(Integer.parseInt(pointer.getString(0)));
                returnUser.setName(pointer.getString(1));
                returnUser.setDescription(pointer.getString(2));
                returnUser.setFollowedWho((new ArrayList<String>(Arrays.asList(pointer.getString(3)))));
                returnUser.setUsername(pointer.getString(4));
                returnUser.setPassword(pointer.getString(5));
                tempUserList.add(returnUser);
                return tempUserList;
            }
        }
        SQLDB.close();
        return tempUserList;
    }

    public void UpdateUser(ArrayList<String> updatedFollowList, String identifier){
        SQLiteDatabase SQLDB = this.getWritableDatabase();
        String query = "UPDATE UserAccounts SET FollowedWho = " +  updatedFollowList + " WHERE Username = " + identifier;
        SQLDB.execSQL(query);
        SQLDB.close();
    }

    //SQLDB only holds one data
    public void ClearDatabase(){
        SQLiteDatabase SQLDB = this.getWritableDatabase();
        SQLDB.execSQL("DELETE FROM UserAccounts");
        SQLDB.close();
    }

    //DBHandler class constructor
    public DBHandler(Context c){
        super(c, "UserAccounts", null, 1);
    }
}
