package ru.android.mypurchases;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.Vector;


public class EnterGoods extends AppCompatActivity implements View.OnClickListener {

    EditText etGoods;
    EditText etPrice;
    EditText etDate;

    Button btnOK;
    Button btnCancel;

    TableLayout tablelay;
    TableRow row;

    Date dateToday, enterDate;
    String dateToday1;
    SimpleDateFormat sdf;
    Calendar myCalendar;

    DataBase DBobj;
    //SQLiteDatabase db;

    TablesBuilding TBobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_goods);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        etGoods = (EditText) findViewById(R.id.etGoods);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etDate = (EditText) findViewById(R.id.etDate);

        tablelay = (TableLayout) findViewById(R.id.tablelay);

        sdf = new SimpleDateFormat("dd.MM.yyyy");
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener calDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };
        dateToday1 = sdf.format(new Date(System.currentTimeMillis()));
        try {
            dateToday = sdf.parse(dateToday1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EnterGoods.this, calDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        DBobj = new DataBase(this);

        TBobj = new TablesBuilding(DBobj, this, tablelay, " ID: ", "Дата:",
                "Продукт:", "Цена:", true);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:

                if ( (TextUtils.isEmpty(etGoods.getText().toString()) ||
                        TextUtils.isEmpty(etPrice.getText().toString())  ||
                        TextUtils.isEmpty(etDate.getText().toString())) ) {
                    Toast.makeText(getApplicationContext(),
                            "Введите все данные", Toast.LENGTH_SHORT).show();
                    return;
                }

                String good = etGoods.getText().toString();
                Float price = Float.parseFloat(etPrice.getText().toString());
                long dateMS = 0;
                try {
                    enterDate = new SimpleDateFormat("dd.MM.yyyy").parse(etDate.getText().toString());
                    dateMS = enterDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (dateToday.before(enterDate)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите корректную дату", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if (TBobj.ifEdition == true) {
                    String _date = etDate.getText().toString();
                    String _good = etGoods.getText().toString();
                    Float _price = Float.parseFloat(etPrice.getText().toString());
                    long _dateMS = 0;
                    try {
                        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(_date);
                        _dateMS = date.getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DBobj.UpdateDB(_dateMS, _good, _price);

                    TBobj.RecreatingRow();

                    TBobj.ifEdition = false;
                } else {


                    DBobj.FillingDB(dateMS, good, price, "");
                    Vector<String> newData = DBobj.UpdatingTable();
                    String newStrID = newData.get(0);
                    String newstrDate = newData.get(1);
                    String newstrGood = newData.get(2);
                    String newstrPrice = newData.get(3);
                    TBobj.DisplayExistingTable(newStrID, newstrDate, newstrGood, newstrPrice, true, 1);

                    DBobj.proverka();
                }

            case R.id.btnCancel:
                etGoods.setText("");
                etPrice.setText("");
                break;

            default:
                break;
        }

    }

    public void EditionTable() {

        if (TBobj.ifEdition) {
            int clickedRow = TBobj.EditionalRow();
            Vector<String> editVector = DBobj.GetDataToEdition(clickedRow);
            etDate.setText(editVector.get(1));
            etGoods.setText(editVector.get(2));
            etPrice.setText(editVector.get(3));
        }
    }


}
