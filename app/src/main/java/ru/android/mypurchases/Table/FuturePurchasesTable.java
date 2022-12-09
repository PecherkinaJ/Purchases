package ru.android.mypurchases.Table;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class FuturePurchasesTable extends CurrentTable implements MyTable {

    private Context context;

    public FuturePurchasesTable(Context context){
        super(context);
        this.context = context;
    }

    public void setTitle(String id, String date, String good, String price, Boolean hideFirstColumn) {

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
