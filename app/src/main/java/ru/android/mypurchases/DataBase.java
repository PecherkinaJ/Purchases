package ru.android.mypurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DataBase extends SQLiteOpenHelper {

    String logs = "MYLOGS";
    ContentValues cv = new ContentValues();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    TablesBuilding TBobj;


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

    //public void realizeCursors(Cursor c) {
    public void realizeCursors(SQLiteDatabase db, Context context, TableLayout table) {

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("MYPURCH", null, null, null, null, null, null);

        TBobj = new TablesBuilding();
        //TBobj.DisplayExistingTable(context, table, "id", "date", "good", "price", true);

        if (c.moveToFirst()) {
            do {
                long a = c.getLong(c.getColumnIndex("date"));
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
                calendar.setTimeInMillis(a);

                String strID = c.getString(c.getColumnIndex("id"));
                String strDate = sdf.format(calendar.getTime());
                String strGood = c.getString(c.getColumnIndex("good"));
                String strPrice = c.getString(c.getColumnIndex("price"));

                TBobj.DisplayExistingTable(context, table, strID, strDate, strGood, strPrice, true);

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
        Log.d(logs, "inserted: "+insertData);
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