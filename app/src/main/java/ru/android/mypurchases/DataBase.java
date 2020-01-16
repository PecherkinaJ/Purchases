package ru.android.mypurchases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;


public class DataBase extends SQLiteOpenHelper {

    String logs = "MYLOGS";
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    SQLiteDatabase db;
    int rowID;


    public DataBase(Context context) {
        super(context, "DataTable", null, 8);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table futPurchTable ("
                + "id integer primary key autoincrement,"
                + "purchase string,"
                + "comment string" + ");");

        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "date long,"
                + "good string,"
                + "price float,"
                + "comment string" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


/* ENTER GOODS */

    public int getRowsCountEG() {
        long dateToday = System.currentTimeMillis();
        long weekAgo = dateToday - 7 * 1000 * 60 * 60 * 24;
        Cursor cursor = db.rawQuery("SELECT id, date, good, price FROM mytable " +
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
        Cursor c = db.rawQuery("SELECT id, date, good, price FROM mytable " +
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
        ContentValues cv = new ContentValues();
        cv.put("date", insertData);
        cv.put("good", insertGood);
        cv.put("price", insertPrice);
        cv.put("comment", comment);
        this.getWritableDatabase().insert("mytable", null, cv);
    }


    public Vector UpdatingTable() {
        Cursor c = db.query("mytable", null, null, null, null, null, null);

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

        rowID = clickedRow;

        Cursor c = db.rawQuery("SELECT id, comment FROM mytable WHERE ID = " + rowID, null);

        String commentText = new String();
        if (c.moveToFirst()) {
            commentText = c.getString(c.getColumnIndex("comment"));
        }
        c.close();
        return commentText;
    }


    public void UpdateComment(int clickedRow, String task) {
        ContentValues cv = new ContentValues();
        rowID = clickedRow;
        cv.put("comment", task);
        int updCount = db.update("mytable", cv, "id = " + rowID, null);
        Log.d(logs, "updated rows count = " + updCount + ", добавлено: " + task);
    }


    public void UpdateDB(long date, String good, float price) {
        ContentValues cv = new ContentValues();
        cv.put("date", date);
        cv.put("good", good);
        cv.put("price", price);
        db.update("mytable", cv, "id = " + rowID, null);
    }


    public Vector RecreatedRow(){
        Vector<String> updatedDBRow= new Vector<>();
        Cursor c = db.rawQuery("SELECT * FROM mytable WHERE ID = " + rowID, null);
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


    public void DeleteFromDB(int clickedRow, String DBtable) {
        rowID = clickedRow;
        int delCount = db.delete(DBtable, "id = " + rowID, null);
        Log.d(logs, "deleted rows count = " + delCount);
    }


    public Vector GetDataToEdition(int clickedRow) {
        rowID = clickedRow;
        Vector<String> editVector = new Vector<>();
        Cursor c = db.rawQuery("SELECT id, date, good, price FROM mytable WHERE id = " + rowID, null);
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



    /* STATISTICS */

    public int getRowsCountStat_query(float flExpDown,
                                      float flExpTop,
                                      long startDateLong,
                                      long endDateLong,
                                      String svQuery,
                                      String orderBy) {
        String minPrice, maxPrice, minDate, maxDate;

        if (flExpDown != 0) {minPrice = " PRICE >=" + flExpDown + " AND ";} else {minPrice = "";}
        if (flExpTop != 0) {maxPrice = " PRICE <=" + flExpTop + " AND ";} else {maxPrice = "";}
        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong + " AND ";} else {maxDate = "";}

        Cursor c = db.rawQuery("SELECT * FROM mytable WHERE " +
                minPrice + maxPrice + minDate + maxDate +
                " GOOD LIKE '%" + svQuery + "%' " +
                " ORDER BY " + orderBy + " , date", null);

        int count = c.getCount();
        c.close();

        Log.d("mylogs", "" + minPrice + " " + maxPrice + minDate + maxDate);

        return count;
    }

    public Vector TableOfQueries(float flExpDown,
                                 float flExpTop,
                                 long startDateLong,
                                 long endDateLong,
                                 String svQuery,
                                 String orderBy,
                                 int num){

        Vector<String> vectQuery = new Vector<>();

        String minPrice, maxPrice, minDate, maxDate;

        if (flExpDown != 0) {minPrice = " PRICE >=" + flExpDown + " AND ";} else {minPrice = "";}
        if (flExpTop != 0) {maxPrice = " PRICE <=" + flExpTop + " AND ";} else {maxPrice = "";}
        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong + " AND ";} else {maxDate = "";}

        Cursor c = db.rawQuery("SELECT * FROM mytable WHERE " +
                minPrice + maxPrice + minDate + maxDate +
                " GOOD LIKE '%" + svQuery + "%' " +
                " ORDER BY " + orderBy + " , date", null);

        if (c.moveToPosition(num)) {
            long a = c.getLong(c.getColumnIndex("date"));
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
            calendar.setTimeInMillis(a);

            String strID = c.getString(c.getColumnIndex("id"));
            String strDate = sdf.format(calendar.getTime());
            String strGood = c.getString(c.getColumnIndex("good"));
            String strPrice = c.getString(c.getColumnIndex("price"));
            String strComm = c.getString(c.getColumnIndex("comment"));

            vectQuery.add(0, strID);
            vectQuery.add(1, strDate);
            vectQuery.add(2, strGood);
            vectQuery.add(3, strPrice);
            vectQuery.add(4, strComm);
        }
        return vectQuery;
    }



    public int getRowsCountStat_everyday(long startDateLong, long endDateLong) {
        String minDate, maxDate;

        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong;} else {maxDate = "";}
        Cursor c = db.rawQuery("SELECT SUM(PRICE) AS summarize, date, " +
                " strftime('%d.%m.%Y', DATE(date / 1000, 'unixepoch', 'localtime')) as totalInDate" +
                " FROM mytable " +
                " WHERE " + minDate + maxDate +
                " GROUP BY totalInDate ORDER BY date", null);
        int count = c.getCount();
        c.close();
        return count;
    }

    public Vector EveryDayTable(int num, long startDateLong, long endDateLong){

        Vector<String> everyDayVector = new Vector<>();

        String minDate, maxDate;

        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong;} else {maxDate = "";}

        Cursor c = db.rawQuery("SELECT SUM(PRICE) AS summarize, date, " +
                " strftime('%d.%m.%Y', DATE(date / 1000, 'unixepoch', 'localtime')) as totalInDate" +
                " FROM mytable " +
                " WHERE " + minDate + maxDate +
                " GROUP BY totalInDate ORDER BY date", null);

        if (c.moveToPosition(num)) {
            everyDayVector.add(0, c.getString(c.getColumnIndex("totalInDate")));
            everyDayVector.add(1, c.getString(c.getColumnIndex("summarize")));
        }

        return everyDayVector;
    }



    public int getRowsCountStat_everymonth() {
        Cursor c = db.rawQuery("SELECT SUM(price) as summarize, date, " +
                " strftime('%m.%Y', DATE(date / 1000, 'unixepoch', 'localtime')) " +
                " AS months " +
                " FROM mytable " +
                " GROUP BY months ORDER BY date", null);
        int count = c.getCount();
        c.close();
        return count;
    }

    public Vector EveryMonthTable(int num){

        Vector<String> everyDayVector = new Vector<>();

        Cursor c = db.rawQuery("SELECT SUM(price) as summarize, date, " +
                " strftime('%m.%Y', DATE(date / 1000, 'unixepoch', 'localtime')) " +
                " AS months " +
                " FROM mytable " +
                " GROUP BY months ORDER BY date", null);

        if (c.moveToPosition(num)) {
            everyDayVector.add(0, c.getString(c.getColumnIndex("months")));
            everyDayVector.add(1, c.getString(c.getColumnIndex("summarize")));
        }

        return everyDayVector;
    }



    public int getRowsCountStat_everypurch(long startDateLong, long endDateLong, String svQuery) {
        String minDate, maxDate;

        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong;} else {maxDate = "";}

        Cursor c = db.rawQuery("SELECT good, date, sum(price) AS summarize from mytable" +
                " where " + minDate + maxDate +
                " AND good LIKE '%" + svQuery + "%' " +
                " GROUP BY good ORDER BY summarize", null);
        int count = c.getCount();
        //Log.d("mylogs", "number of every purchase vector = " + count);
        c.close();
        return count;
    }

    public Vector EveryPurchTable(int num,
                                  long startDateLong,
                                  long endDateLong,
                                  String svQuery){

        Vector<String> everyDayVector = new Vector<>();

        String minDate, maxDate;

        if (startDateLong != 0) {minDate = " date >= " + startDateLong + " AND ";} else {minDate = "";}
        if (endDateLong != 0) {maxDate = " date <= " + endDateLong;} else {maxDate = "";}

        Cursor c = db.rawQuery("SELECT good, date, sum(price) AS summarize from mytable" +
                " where " + minDate + maxDate +
                " AND good LIKE '%" + svQuery + "%' " +
                " GROUP BY good ORDER BY summarize", null);

        if (c.moveToPosition(num)) {
            everyDayVector.add(0, c.getString(c.getColumnIndex("good")));
            everyDayVector.add(1, c.getString(c.getColumnIndex("summarize")));
        }

        return everyDayVector;
    }



    /* FUTURE PURCHASES */

    public void FillingFutPurch(String insertGood, String comment){
        ContentValues cv = new ContentValues();
        cv.put("purchase", insertGood);
        cv.put("comment", comment);
        this.getWritableDatabase().insert("futPurchTable", null, cv);
    }


    public int getRowsCountStat_futurepurch() {
        Cursor c = db.rawQuery("SELECT * FROM futPurchTable", null);
        int count = c.getCount();
        c.close();
        return count;
    }


    public Vector ShowFutPurch(int num){

        Vector<String> futPurchVector = new Vector<>();

        Cursor c = db.rawQuery("SELECT * FROM futPurchTable", null);

        if (c.moveToPosition(num)) {
            futPurchVector.add(0, c.getString(c.getColumnIndex("id")));
            futPurchVector.add(1, c.getString(c.getColumnIndex("purchase")));
            futPurchVector.add(2, c.getString(c.getColumnIndex("comment")));
        }

        return futPurchVector;
    }


    public Vector UpdatingFP() {
        Cursor c = db.query("futPurchTable", null, null, null, null, null, null);

        Vector <String> newValueArray = new Vector<>();

        if (c.moveToLast()) {
            String strID = c.getString(c.getColumnIndex("id"));
            String strGood = c.getString(c.getColumnIndex("purchase"));
            String strCom = c.getString(c.getColumnIndex("comment"));

            newValueArray.add(0, strID);
            newValueArray.add(1, strGood);
            newValueArray.add(2, strCom);
        }
        return newValueArray;
    }


    public Vector RecreatedRowFP(int clickedRow){
        rowID = clickedRow;
        Vector<String> updatedDBRow = new Vector<>();
        Cursor c = db.rawQuery("SELECT * FROM futPurchTable WHERE ID = " + rowID, null);
        if (c.moveToFirst()) {
            String IDText = c.getString(c.getColumnIndex("id"));
            String purchText = c.getString(c.getColumnIndex("purchase"));
            String comText = c.getString(c.getColumnIndex("comment"));

            updatedDBRow.add(0, IDText) ;
            updatedDBRow.add(1, purchText);
            updatedDBRow.add(2, comText);
        }
        c.close();
        return updatedDBRow;
    }


    public String GetDataToEditionFP(int clickedRow) {
        rowID = clickedRow;
        Cursor c = db.rawQuery("SELECT * FROM futPurchTable WHERE id = " + rowID, null);
        String strGood = "";
        if (c.moveToFirst()) {
            strGood = c.getString(c.getColumnIndex("purchase"));
        }
        return strGood;
    }


    public void UpdateFP(String purchase) {
        ContentValues ccv = new ContentValues();
        ccv.put("purchase", purchase);
        db.update("futPurchTable", ccv, "id = " + rowID, null);
    }


    public void UpdateFPcomment(String comment) {
        ContentValues ccv = new ContentValues();
        ccv.put("comment", comment);
        db.update("futPurchTable", ccv, "id = " + rowID, null);
    }


    public String BoughtGood(int clickedRow){
        String bought = "";
        rowID = clickedRow;

        Cursor c = db.rawQuery("SELECT id, purchase FROM futPurchTable WHERE id = " + rowID, null);
        if (c.moveToFirst()) {
            bought = c.getString(c.getColumnIndex("purchase"));
        }
        return bought;
    }


    Vector TrueFalseComment(int clickedRow){
        rowID = clickedRow;
        Vector <String> comment = new Vector<>();

        Cursor c = db.rawQuery("SELECT id, purchase, comment FROM futPurchTable WHERE id = " + clickedRow, null);
        if (c.moveToFirst()) {
            comment.add(c.getString(c.getColumnIndex("purchase")));
            comment.add(c.getString(c.getColumnIndex("comment")));
        }
        return comment;
    }

}