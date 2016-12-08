package ssjk.cafein;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;

/**
 * Created by wqe13 on 2016-11-24.
 */

public class SuggestionsDatabase{

    public static final String DB_SUGGESTION = "SUGGESTION_DB";
    public final static String TABLE_SUGGESTION = "TABLE_SUGGESTION";
    public final static String FIELD_ID = "_id";
    public final static String FIELD_SUGGESTION = "FIELD_SUGGESTION";
    private static String DB_PATH = "/data/data/ssjk.cafein/databases/";

    private static String DB_NAME = "cafesd1.db";

    SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);

    public SuggestionsDatabase(Context context){

    }
  /*  private Helper helper;

    public SuggestionsDatabase(Context context) {

        helper = new Helper(context, DB_SUGGESTION, null, 1);
        db = helper.getWritableDatabase();
    }
/*
    public long insertSuggestion(String text)
    {
        ContentValues values = new ContentValues();
        values.put(FIELD_SUGGESTION, text);
        return db.insert(TABLE_SUGGESTION, null, values);
    }
*/
    public Cursor getSuggestions(String text)
    {
        return db.query(TABLE_SUGGESTION, new String[] {FIELD_ID, FIELD_SUGGESTION},
                FIELD_SUGGESTION+" LIKE '"+ text +"%'", null, null, null, null);
    }

/*
    private class Helper extends SQLiteOpenHelper
    {

        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TABLE_SUGGESTION+" ("+
                    FIELD_ID+" integer primary key autoincrement, "+FIELD_SUGGESTION+" text);");
            Log.d("SUGGESTION", "DB CREATED");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }*/

}