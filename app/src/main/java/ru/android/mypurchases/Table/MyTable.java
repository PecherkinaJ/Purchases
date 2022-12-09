package ru.android.mypurchases.Table;

import java.util.ArrayList;
import java.util.List;

public interface MyTable {

/*    void setTitle(String date, String good, String price); // general table
    void setTitle(String columnName);
    void setTitle(String columnName, String cost);*/

    void setTitle();

    void displayCurrentData(List<ArrayList<String>>array);
}
