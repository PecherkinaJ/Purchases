package ru.android.mypurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;


public class DataBase extends SQLiteOpenHelper {

    String logs = "MYLOGS";
    ContentValues cv = new ContentValues();
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    SQLiteDatabase db;
    int rowID;


    public DataBase(Context context) {
        super(context, "DataTable", null, 1);
        db = this.getWritableDatabase();
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


    public int getRowsCount() {
        long dateToday = System.currentTimeMillis();
        long weekAgo = dateToday - 7 * 1000 * 60 * 60 * 24;
        Cursor cursor = db.rawQuery("SELECT id, date, good, price FROM myPurch " +
                " WHERE date >= " + weekAgo +
                " AND date <= " + dateToday, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Vector DisplayExistingTable(int num) {

        Vector<String> DBRow = new Vector<>(4);

        long dateToday = System.currentTimeMillis();
        long weekAgo = dateToday - 7 * 1000 * 60 * 60 * 24;
        Cursor c = db.rawQuery("SELECT id, date, good, price FROM myPurch " +
                " WHERE date >= " + weekAgo +
                " AND date <= " + dateToday + " ORDER BY date", null);

        if (c.moveToPosition(num)) {
            long a = c.getLong(c.getColumnIndex("date"));
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(a);

            String strID = c.getString(c.getColumnIndex("id"));
            String strDate = sdf.format(calendar.getTime());
            String strGood = c.getString(c.getColumnIndex("good"));
            String strPrice = c.getString(c.getColumnIndex("price"));

            DBRow.add(strID);
            DBRow.add(strDate);
            DBRow.add(strGood);
            DBRow.add(strPrice);
        }
        c.close();
        return DBRow;
    }


    public void FillingDB(long insertData, String insertGood, float insertPrice, String comment) {
        cv.put("date", insertData);
        cv.put("good", insertGood);
        cv.put("price", insertPrice);
        cv.put("comment", comment);
        this.getWritableDatabase().insert("myPurch", null, cv);
    }


    public Vector UpdatingTable() {
        Cursor c = db.query("mypurch", null, null, null, null, null, null);

        Vector <String> newValueArray = new Vector<>(4);

        if (c.moveToLast()) {
            long a = c.getLong(c.getColumnIndex("date"));
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(a);

            String strID = c.getString(c.getColumnIndex("id"));
            String strDate = sdf.format(calendar.getTime());
            String strGood = c.getString(c.getColumnIndex("good"));
            String strPrice = c.getString(c.getColumnIndex("price"));

            newValueArray.add(strID);
            newValueArray.add(strDate);
            newValueArray.add(strGood);
            newValueArray.add(strPrice);
        }
        c.close();
        return newValueArray;
    }


    public String ShowCommentPopup(int clickedRow) {
    //public String ShowCommentPopup(int clickedRow) {
        Log.d("mylogs", "id = " + clickedRow);

        rowID = clickedRow;

        Cursor c = db.rawQuery("SELECT id, comment FROM myPurch WHERE ID = " + rowID, null);

        String commentText = new String();
        if (c.moveToFirst()) {
            commentText = c.getString(c.getColumnIndex("comment"));
        }
        c.close();
        return commentText;
    }


    public void UpdateComment(int clickedRow, String task) {
        rowID = clickedRow;
        cv.put("comment", task);
        int updCount = db.update("myPurch", cv, "id = " + rowID, null);
        Log.d(logs, "updated rows count = " + updCount + ", добавлено: " + task);
    }

    public void UpdateDB(long date, String good, float price) {
        cv.put("date", date);
        cv.put("good", good);
        cv.put("price", price);
        db.update("myPurch", cv, "id = " + rowID, null);
    }

    public Vector RecreatedRow(){
        Vector<String> updatedDBRow= new Vector<>();
        Cursor c = db.rawQuery("SELECT * FROM myPurch WHERE ID = " + rowID, null);
        if (c.moveToFirst()) {
            String IDText = c.getString(c.getColumnIndex("id"));

            long a = c.getLong(c.getColumnIndex("date"));
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(a);
            String dateText = sdf.format(calendar.getTime());

            String goodText = c.getString(c.getColumnIndex("good"));
            String priceText = c.getString(c.getColumnIndex("price"));

            updatedDBRow.add(0, IDText);
            updatedDBRow.add(1, dateText);
            updatedDBRow.add(2, goodText) ;
            updatedDBRow.add(3, priceText);
        }
        c.close();
        return updatedDBRow;
    }


    public void DeleteFromDB(int clickedRow) {
        rowID = clickedRow;
        int delCount = db.delete("myPurch", "id = " + rowID, null);
        Log.d(logs, "deleted rows count = " + delCount);
    }


    public Vector GetDataToEdition(int clickedRow) {
        rowID = clickedRow;
        Vector<String> editVector = new Vector<>();
        Cursor c = db.rawQuery("SELECT id, date, good, price FROM myPurch WHERE id = " + rowID, null);
        if (c.moveToFirst()) {
            long a = c.getLong(c.getColumnIndex("date"));
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(a);

            String strID = c.getString(c.getColumnIndex("id"));
            String strDate = sdf.format(calendar.getTime());
            String strGood = c.getString(c.getColumnIndex("good"));
            String strPrice = c.getString(c.getColumnIndex("price"));

            editVector.add(strID);
            editVector.add(strDate);
            editVector.add(strGood);
            editVector.add(strPrice);
        }
        return editVector;
    }

    protected void proverka() {
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