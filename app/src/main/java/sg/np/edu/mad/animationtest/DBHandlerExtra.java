package sg.np.edu.mad.animationtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandlerExtra extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase DB) {
        String command = "CREATE TABLE TempData" +
                         "("
                            + "TempString TEXT,"
                            + "TempStringDataSize TEXT" +
                         ")";

        DB.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS TempData");
        onCreate(DB);
    }

    public void AddInformation(String item, String itemDataSize) {
        Log.d("ADDEDTEMPSTRING", "Added temp string within the database");
        Log.d("VALUEOFITEM", item);
        Log.d("VALUEOFITEMDATASIZE", itemDataSize);
        SQLiteDatabase SQLDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TempString", item);
        values.put("TempStringDataSize", itemDataSize);
        SQLDB.insert("TempData", null, values);
        Log.d("INSERTEDVARIABLEVALUE", "" + values.get("TempString"));
        SQLDB.close();
    }

    public String[] GetInformation(){
        SQLiteDatabase SQLDB = this.getReadableDatabase();
        String query = "SELECT * FROM TempData";
        Cursor pointer = SQLDB.rawQuery(query, null);
        String[] returnResult = new String[]{ };
        if (pointer.getCount() != 0){
            if (pointer.moveToFirst()){
                returnResult = new String[] { pointer.getString(0), pointer.getString(1) };
            }
        }
        else {
            //Let the programmer know that the SQLDB database does not contain any results...
            Log.i("DBHandlerExtraNORESULTS", "DBHandlerResult class which is responsible for managing the data that is contained within ");
        }
        return returnResult; //Might return a string array that does not contain any valuable information inside
    }

    public void ClearDatabase(){
        SQLiteDatabase SQLDB = this.getWritableDatabase();
        SQLDB.execSQL("DELETE FROM TempData");
        SQLDB.close();
    }

    //DBHandler class constructor
    public DBHandlerExtra(Context c){
        super(c, "TempData", null, 1);
    }
}