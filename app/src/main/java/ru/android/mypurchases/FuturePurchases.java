package ru.android.mypurchases;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Vector;

public class FuturePurchases extends AppCompatActivity implements View.OnClickListener {

    EditText etPurch;
    Button btnOK;

    DataBase DBobj;
    TablesBuilding TBobj;

    TableLayout tableFP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_purchases);

        etPurch = (EditText) findViewById(R.id.etPurch);

        tableFP = (TableLayout) findViewById(R.id.tableFP);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

        DBobj = new DataBase(this);

        TBobj = new TablesBuilding(FuturePurchases.this, DBobj, tableFP);
        TBobj.DisplayFutPurch();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(etPurch.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    "Введите данные", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TBobj.ifEdition) {
            String _newPurch = etPurch.getText().toString();
            DBobj.UpdateFP(_newPurch);

            TBobj.RecreatingRowFP();
            TBobj.ifEdition = false;

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Изменения сохранены", Toast.LENGTH_LONG);
            toast.show();

            etPurch.setText("");

        } else {
            String newPurch = etPurch.getText().toString();
            TBobj.AddToFP(newPurch);

            etPurch.setText("");
        }
    }


    public void EditionTable(){
            int clickedRow = TBobj.EditionalRow();
            String purchase = DBobj.GetDataToEditionFP(clickedRow);
            etPurch.setText(purchase);
    }
}
