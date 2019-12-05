package ru.android.mypurchases;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class FuturePurchases extends AppCompatActivity implements View.OnClickListener {

    EditText etPurch;
    Button btnOK;

    DataBase DBobj;
    TablesBuilding TBobj;

    TableLayout tableFP;

    int tableRowID, clickedRow;
    String IDText;

    Long todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_purchases);

        etPurch = (EditText) findViewById(R.id.etPurch);

        tableFP = (TableLayout) findViewById(R.id.tableFP);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

        DBobj = new DataBase(this);
        TBobj = new TablesBuilding(this, DBobj, tableFP);
    }

    @Override
    public void onClick(View v) {

    }
}
