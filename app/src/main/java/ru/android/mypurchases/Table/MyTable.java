package ru.android.mypurchases.Table;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.android.mypurchases.Parametres;

public class MyTable {

    Context context;
    private Parametres parametres = new Parametres();
    final TableLayout.LayoutParams tableParams;
    final TableRow.LayoutParams rowParams;

    public MyTable(Context context) {
        this.context = context;
        tableParams = parametres.getTableParametres();
        rowParams = parametres.getRowParametres();
    }

    /*    void setTitle(String date, String good, String price); // general table
    void setTitle(String columnName);
    void setTitle(String columnName, String cost);*/

    void setHeadRow() {
        TableRow row = new TableRow(context);
        row.setLayoutParams();

        TextView ID1 = new TextView(context);
        TextView date1 = new TextView(context);
        TextView good1 = new TextView(context);
        TextView price1 = new TextView(context);
        TextView com1 = new TextView(context);
        ID1.setLayoutParams(rowParams);
        date1.setLayoutParams(rowParams);
        good1.setLayoutParams(rowParams);
        price1.setLayoutParams(rowParams);
        com1.setLayoutParams(rowParams);
        ID1.setTextSize(20);
        ID1.setPadding(25, 5, 25, 5);
        ID1.setTypeface(null, Typeface.BOLD_ITALIC);
        date1.setTextSize(20);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);
        good1.setTextSize(20);
        good1.setPadding(25, 5, 25, 5);
        good1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setTextSize(20);
        price1.setPadding(25, 5, 25, 5);
        price1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        ID1.setText(IDColName);
        date1.setText(secondColName);
        good1.setText(thirdColName);
        price1.setText(fourthColName);

        row.addView(ID1);
        row.addView(date1);
        row.addView(good1);
        row.addView(price1);
        table.addView(row);
    }

    void drawCurrentData(List<ArrayList<String>> array) {

    }
}
