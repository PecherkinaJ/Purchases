package ru.android.mypurchases;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TablesBuilding extends AppCompatActivity {

    TableLayout table1;
    TableRow row1;
    LinearLayout linlay1;
    TextView tv;


    public void HeadRow(TableLayout table, TableRow row, TextView tv, String firstColumnName) {

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        row.setLayoutParams(tableParams);

        tv.setLayoutParams(rowParams);
        tv.setText(firstColumnName);

        row.addView(tv);
        table.addView(row);

       /* TextView date1 = new TextView(this);
        TextView good1 = new TextView(this);
        TextView price1 = new TextView(this);
        TextView com1 = new TextView(this);
        date1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        good1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        price1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        com1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        date1.setTextSize(23);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);
        good1.setTextSize(23);
        good1.setPadding(25, 5, 25, 5);
        good1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setTextSize(23);
        price1.setPadding(25, 5, 25, 5);
        price1.setTypeface(null, Typeface.BOLD_ITALIC);

        date1.setText(" Дата:");
        good1.setText("Продукт:");
        price1.setText("Цена:");
        com1.setText("Коммент"); */

    }

    public void  DisplayTable() {

    }
}
