package ru.android.mypurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DataBase extends SQLiteOpenHelper {

    String logs = "MYLOGS";
    ContentValues cv = new ContentValues();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    TablesBuilding TBobj = new TablesBuilding();


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


    public void DisplayExistingTable(SQLiteDatabase db, Context context, TableLayout table) {

        long dateToday = System.currentTimeMillis();
        long weekAgo = dateToday - 604800000;
        Cursor c = db.rawQuery("SELECT * FROM myPurch " +
                " WHERE date >= " + weekAgo +
                " AND date <= " + dateToday, null);

        if (c.moveToLast()) {
            do {
                long a = c.getLong(c.getColumnIndex("date"));
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
                calendar.setTimeInMillis(a);

                String strID = c.getString(c.getColumnIndex("id"));
                String strDate = sdf.format(calendar.getTime());
                String strGood = c.getString(c.getColumnIndex("good"));
                String strPrice = c.getString(c.getColumnIndex("price"));

                TBobj.DisplayExistingTable(context, table, strID, strDate, strGood, strPrice, true);

            } while (c.moveToPrevious());
        }
        c.close();
    }


    public void FillingDB(long insertData, String insertGood, float insertPrice, String comment) {
        cv.put("date", insertData);
        cv.put("good", insertGood);
        cv.put("price", insertPrice);
        cv.put("comment", comment);
        this.getWritableDatabase().insert("myPurch", null, cv);
    }


    public void UpdatingTable(SQLiteDatabase db, Context context, TableLayout table) {
        Cursor c = db.query("mypurch", null, null, null, null, null, null);

        if (c.moveToLast()) {
            do {
                long a = c.getLong(c.getColumnIndex("date"));
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
                calendar.setTimeInMillis(a);

                String strID = c.getString(c.getColumnIndex("id"));
                String strDate = sdf.format(calendar.getTime());
                String strGood = c.getString(c.getColumnIndex("good"));
                String strPrice = c.getString(c.getColumnIndex("price"));

                TBobj.DisplayExistingTable(context, table, strID, strDate, strGood, strPrice, true);

            } while (c.isClosed());
        }
        c.close();
    }


    public void ShowCommentPopup(int clickedRow) {
        Cursor c = this.getWritableDatabase().rawQuery("SELECT * FROM MYTABLE WHERE ID = " + clickedRow, null);

        if (c.moveToFirst()) {
            do {
                String IDText = c.getString(c.getColumnIndex("id"));

                long a = c.getLong(c.getColumnIndex("date"));
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
                calendar.setTimeInMillis(a);
                String dateText = sdf.format(calendar.getTime());

                String goodText = c.getString(c.getColumnIndex("good"));
                String priceText = c.getString(c.getColumnIndex("price"));

                Log.d(logs, "id = " + IDText + ", date = " + dateText + ", good = " + goodText + ", price = " + priceText);

            } while (c.moveToNext());
        }
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