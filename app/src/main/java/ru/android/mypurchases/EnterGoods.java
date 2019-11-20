package ru.android.mypurchases;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EnterGoods extends AppCompatActivity implements View.OnClickListener {

    EditText etGoods;
    EditText etPrice;
    EditText etDate;

    Button btnOK;
    Button btnCancel;

    TableLayout tablelay;

    Date dateToday, enterDate;
    String dateToday1;
    SimpleDateFormat sdf;
    Calendar myCalendar;

    DataBase DBobj;

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

        TBobj = new TablesBuilding();
        TBobj.HeadRow(EnterGoods.this, tablelay, " ID: ", "Дата:",
                "Продукт:", "Цена:", true);

        SQLiteDatabase db = DBobj.getReadableDatabase();
        DBobj.realizeCursors(db, EnterGoods.this, tablelay);

        //TBobj.DisplayExistingTable(this, tablelay, "1", "20.11.2019", "Проба", "1");
        //TBobj.DisplayExistingTable(this, tablelay);
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

                String date1 = etDate.getText().toString();
                String good = etGoods.getText().toString();
                Float price = Float.parseFloat(etPrice.getText().toString());
                long dateMS = 0;
                try {
                    Date date;
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(date1);
                    dateMS = date.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    enterDate = new SimpleDateFormat("dd.MM.yyyy").parse(etDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (dateToday.before(enterDate)) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введите корректную дату", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                /*
                if (ifEdition == false) {
                    fillingLayout();
                    break;
                } else if (ifEdition) {
                    EditTableRow();
                    ifEdition = false;
                }
                */

                DBobj.fillingDB("date", dateMS);
                DBobj.fillingDB("good", good);
                DBobj.fillingDB("price", price);
                DBobj.fillingDB("comment", "");

                SQLiteDatabase db = DBobj.getWritableDatabase();
                DBobj.proverka(db);

            case R.id.btnCancel:
                etGoods.setText("");
                etPrice.setText("");
                break;

            default:
                break;
        }
    }
}
