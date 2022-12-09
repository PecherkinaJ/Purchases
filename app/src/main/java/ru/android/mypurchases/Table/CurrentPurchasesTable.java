package ru.android.mypurchases.Table;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class CurrentPurchasesTable extends CurrentTable implements MyTable {

    public CurrentPurchasesTable(Context context){
        super(context);
    }

    public void setTitle(String date, String good, String price) {

    }

    @Override
    public void setTitle() {

    }

    @Override
    public void displayCurrentData(List<ArrayList<String>> array) {

    }

    void displayAddedRow(List<String> newRow) {

    }
}
