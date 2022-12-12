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

    private Context context;
    final private TableLayout.LayoutParams tableParams;
    final private TableRow.LayoutParams rowParams;
    TableLayout table;

    public MyTable(Context context) {
        this.context = context;
        Parametres parametres = new Parametres();
        tableParams = parametres.getTableParametres();
        rowParams = parametres.getRowParametres();
        table = new TableLayout(context);
    }

    /*    void setTitle(String date, String good, String price); // general table
    void setTitle(String columnName);
    void setTitle(String columnName, String cost);*/

    void setHeadRow(String[] rowNames, boolean isFirstRowHidden) {
        TableRow row = new TableRow(context);
        row.setLayoutParams(rowParams);

        int rowNumber = rowNames.length;

        for (int i=0; i<rowNumber; i++){
            TextView textView = new TextView(context);
            textView.setLayoutParams(rowParams);
            textView.setTextSize(20);
            textView.setPadding(25, 5, 25, 5);
            textView.setTypeface(null, Typeface.BOLD_ITALIC);
            textView.setText(rowNames[i]);
            row.addView(textView);

            if (isFirstRowHidden && i==0)
                textView.setVisibility(View.GONE);
        }
        table.addView(row);

    }

    void drawCurrentData(List<ArrayList<String>> currentData, boolean isFirstRowHidden) {
        TableRow row = new TableRow(context);
        row.setLayoutParams(rowParams);

        for (int i=0; i<currentData.size(); i++) {
            ArrayList<String> array = currentData.get(i);
            for (int j=0; j<array.size(); j++) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(rowParams);
                textView.setTextSize(16);
                textView.setPadding(35, 5, 15, 20);
                textView.setText(array.get(j));
                row.addView(textView);

                if (isFirstRowHidden && j == 0)
                    textView.setVisibility(View.GONE);
            }
            table.addView(row);
        }

    }
}
