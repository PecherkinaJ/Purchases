package ru.android.mypurchases;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Statistic extends AppCompatActivity implements View.OnClickListener {

    DataBase DBobj;
    TablesBuilding TBobj;

    final String LOG_TAG = "myLogs";

    TextView tvTotal;

    String stotalMonthSum;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String dateToday;
    Integer curMonth;
    Integer curYear;

    String ifContains;
    String ifContainsPrevMon;

    SearchView svSearch;
    Spinner spinSort;
    int posOfSpin;

    EditText etDateBuy;
    EditText etDateBuyEnd;
    EditText etMoreExpensive;
    EditText etLessExpensive;

    Button btnShowTable;
    Button btnShowGraph;

    String[] data = {"Дата", "Продукт", "Цена", "Сумма за каждый день", "Сумма по месяцам", "Сумма по покупкам"};

    String orderBy = null;

    Calendar myCalendar;

    TableLayout tableTotal;

    ArrayList<String> DateArray;
    ArrayList <Float> PriceDayArray;
    ArrayList <String> GoodsArray;
    ArrayList <Float> PriceGoodArray;
    ArrayList <String> MonthArray;
    ArrayList <Float> MonthWastingsArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        DBobj = new DataBase(this);
        TBobj = new TablesBuilding(this, DBobj, tableTotal);

        tvTotal = (TextView) findViewById(R.id.tvTotal);

        svSearch = (SearchView) findViewById(R.id.svSearch);

        spinSort = (Spinner) findViewById(R.id.spinSort);


        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener calDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                etDateBuy.setText(sdf.format(myCalendar.getTime()));            }

        };

        final DatePickerDialog.OnDateSetListener calDateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                etDateBuyEnd.setText(sdf.format(myCalendar.getTime()));            }

        };

        etDateBuy = (EditText) findViewById(R.id.etDateBuy);
        etDateBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Statistic.this, calDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etDateBuyEnd = (EditText) findViewById(R.id.etDateBuyEnd);
        etDateBuyEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Statistic.this, calDateEnd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etMoreExpensive = (EditText) findViewById(R.id.etMoreExpensive);
        etLessExpensive = (EditText) findViewById(R.id.etLessExpensive);

        ArrayAdapter<String> adapterG = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSort.setAdapter(adapterG);
        spinSort.setPrompt("Title");
        spinSort.setSelection(0);
        spinSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                posOfSpin = position;
                if (position == 0 || position == 1 || position == 2) {btnShowGraph.setEnabled(false);}
                else {btnShowGraph.setEnabled(true);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btnShowTable = (Button) findViewById(R.id.btnShowTable);
        btnShowTable.setOnClickListener(this);

        btnShowGraph = (Button) findViewById(R.id.btnShowGraph);
        btnShowGraph.setOnClickListener(this);

        tableTotal = (TableLayout) findViewById(R.id.tableTotal);

        dateToday = sdf.format(new Date(System.currentTimeMillis()));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowTable:
                Queries();


            default:
                break;


        }

    }

    String strDate1;
    String strDate2;
    float flExpDown;
    float flExpTop;
    String svQuery;

    public void Queries(){

        if (posOfSpin == 0) {
            orderBy = "DATE";
        } else if (posOfSpin == 1) {
            orderBy = "GOOD";
        } else if (posOfSpin == 2) {
            orderBy = "PRICE";
        }

        if (TextUtils.isEmpty(etMoreExpensive.getText().toString())) {
            flExpDown = 0;
        } else {
            flExpDown = Float.parseFloat(etMoreExpensive.getText().toString());
        }

        if (TextUtils.isEmpty(etLessExpensive.getText().toString())) {
            flExpTop = 9999999;
        } else {
            flExpTop = Float.parseFloat(etLessExpensive.getText().toString());
        }


        if (TextUtils.isEmpty(etDateBuy.getText().toString())) {
            strDate1 = "01.01.2010";
        } else {
            strDate1 = etDateBuy.getText().toString();
        }

        if (TextUtils.isEmpty(etDateBuyEnd.getText().toString())) {
            strDate2 = dateToday;
        } else {
            strDate2 = etDateBuyEnd.getText().toString();
        }

        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate1);
            endDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long startDateLong = startDate.getTime();
        Long endDateLong = endDate.getTime() + 86399000;

        svQuery = svSearch.getQuery().toString();

        TBobj.StatTableQueries(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy, true);
    }

}
