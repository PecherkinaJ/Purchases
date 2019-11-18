package ru.android.mypurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBase extends SQLiteOpenHelper {

    String logs = "MYLOGS";
    ContentValues cv = new ContentValues();


    public DataBase(Context context) {
        super(context, "DataTable", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table futPurchTable ("
                + "id integer primary key autoincrement,"
                + "purchase string,"
                + "comment string" + ");");

        db.execSQL("create table myPurch ("
                + "id integer primary key autoincrement,"
                + "date long,"
                + "good string,"
                + "price float,"
                + "comment string" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void realizeCursors(Cursor c) {

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int dateColIndex = c.getColumnIndex("date");
            int goodColIndex = c.getColumnIndex("good");
            int priceColIndex = c.getColumnIndex("price");
            int comColIndex = c.getColumnIndex("comment");

            do {

            } while (c.moveToLast());
        }
    }


    public void fillingDB(String DBfield, long insertData) {
        cv.put(DBfield, insertData);
        Log.d(logs, "inserted: "+insertData);
    }

    public void fillingDB(String DBfield, String insertData) {
        cv.put(DBfield, insertData);
        Log.d(logs, "inserted: "+insertData);
    }

    public void fillingDB(String DBfield, Float insertData) {
        cv.put(DBfield, insertData);
    }

    protected void proverka(SQLiteDatabase db) {
        Log.d(logs, "--- Rows in mytable: ---");
        Cursor c = db.query("myPurch", null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("date");
            int nameColIndex = c.getColumnIndex("good");
            int emailColIndex = c.getColumnIndex("price");

            do {
                Log.d(logs,
                        "date = " + c.getInt(idColIndex) +
                                ", good = " + c.getString(nameColIndex) +
                                ", price = " + c.getString(emailColIndex));
            } while (c.moveToNext());
        } else
            Log.d(logs, "0 rows");
        c.close();
    }

}