package ru.android.mypurchases;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Vector;

public class FuturePurchases extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton addNew;

    DataBase DBobj;
    TablesBuilding TBobj;

    TableLayout tableFP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_purchases);

        tableFP = (TableLayout) findViewById(R.id.tableFP);

        addNew = (FloatingActionButton) findViewById(R.id.addNew);
        addNew.setOnClickListener(this);

        DBobj = new DataBase(this);

        TBobj = new TablesBuilding(FuturePurchases.this, DBobj, tableFP);
        TBobj.DisplayFutPurch();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNew:
                TBobj.AddNewElementFP();
        }
    }

}