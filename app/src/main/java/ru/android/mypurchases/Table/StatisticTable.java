package ru.android.mypurchases.Table;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StatisticTable extends CurrentTable implements MyTable {

    public StatisticTable(Context context){
        super(context);
    }

    public void setTitle(String id, String date, String good, String price, Boolean hideFirstColumn) {

    }

    @Override
    public void setTitle() {

    }

    @Override
    public void displayCurrentData(List<ArrayList<String>> array) {

    }

    void displayAddedRow(List<ArrayList<String>> array) {

    }

}
