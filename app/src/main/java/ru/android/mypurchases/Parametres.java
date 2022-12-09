package ru.android.mypurchases;

import android.widget.TableLayout;
import android.widget.TableRow;

public final class Parametres {
    private TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
    private TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);

    public TableLayout.LayoutParams getTableParametres(){
        return tableParams;
    }

    public TableRow.LayoutParams getRowParametres(){
        return rowParams;
    }
}
