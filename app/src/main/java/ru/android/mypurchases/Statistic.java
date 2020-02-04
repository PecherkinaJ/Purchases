package ru.android.mypurchases;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Statistic extends AppCompatActivity implements View.OnClickListener {

    DataBase DBobj;
    TablesBuilding TBobj;

    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    String dateToday;

    SearchView svSearch;
    Spinner spinSort;
    public int posOfSpin;

    EditText etDateBuy;
    EditText etDateBuyEnd;
    EditText etMoreExpensive;
    EditText etLessExpensive;

    Button btnShowTable;
    Button btnShowGraph;

    String[] data = {"Дата", "Продукт", "Цена", "Сумма за каждый день", "Сумма по месяцам", "Сумма по покупкам", "Итог за выбранный период"};

    String orderBy = null;

    Calendar myCalendar;

    TableLayout tableTotal;

    ArrayList<String> arrayString = new ArrayList();
    ArrayList<String> arrayFloat = new ArrayList();

    String strDate1;
    String strDate2;
    float flExpDown = 0;
    float flExpTop = 0;
    String svQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

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
        etMoreExpensive.setFocusable(false);
        etMoreExpensive.setFocusableInTouchMode(true);

        etLessExpensive = (EditText) findViewById(R.id.etLessExpensive);
        etLessExpensive.setFocusable(false);
        etLessExpensive.setFocusableInTouchMode(true);

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
                if (position == 0 || position == 1 || position == 2) {
                    btnShowGraph.setEnabled(false);
                    enableSearchView(svSearch, true);
                    enableSearchView(etDateBuy, true);
                    enableSearchView(etDateBuyEnd, true);
                    enableSearchView(etMoreExpensive, true);
                    enableSearchView(etLessExpensive, true);
                }
                else {btnShowGraph.setEnabled(true);}

                if (position == 3) {
                    enableSearchView(svSearch, false);
                    enableSearchView(etMoreExpensive, false);
                    enableSearchView(etLessExpensive, false);
                    enableSearchView(etDateBuy, true);
                    enableSearchView(etDateBuyEnd, true);
                }

                if (position == 4) {
                    enableSearchView(svSearch, false);
                    enableSearchView(etDateBuy, false);
                    enableSearchView(etDateBuyEnd, false);
                    enableSearchView(etMoreExpensive, false);
                    enableSearchView(etLessExpensive, false);
                }

                if (position == 5) {
                    enableSearchView(svSearch, true);
                    enableSearchView(etDateBuy, true);
                    enableSearchView(etDateBuyEnd, true);
                    enableSearchView(etMoreExpensive, false);
                    enableSearchView(etLessExpensive, false);
                }
                if (position == 6) {
                    btnShowGraph.setEnabled(false);
                    enableSearchView(svSearch, false);
                    enableSearchView(etMoreExpensive, false);
                    enableSearchView(etLessExpensive, false);
                    enableSearchView(etDateBuy, true);
                    enableSearchView(etDateBuyEnd, true);
                }

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

        DBobj = new DataBase(this);
        TBobj = new TablesBuilding(this, DBobj, tableTotal);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowTable:
                switch(posOfSpin) {
                    case 3:
                        TotalForEveryDay();
                        break;
                    case 4:
                        TotalForEveryMonth();
                        break;
                    case 5:
                        TotalForEveryPurch();
                        break;
                    case 6:
                        ConcretePeriod();
                        break;
                    default:
                        Queries();
                }
                break;

            case R.id.btnShowGraph:
                onClick(btnShowTable);
                arrayFloat = TBobj.arrayFloat;
                arrayString = TBobj.arrayString;
                Log.d("mylogs", "Arrays: " + arrayString + "\n" + arrayFloat);

                Intent charts = new Intent(Statistic.this, Charts.class);
                charts.putExtra("arrayString", arrayString);
                charts.putExtra("arrayFloat", arrayFloat);
                startActivity(charts);
                break;

            default:
                break;
        }
    }



    public void Queries(){

        if (posOfSpin == 0) {
            orderBy = "DATE";
        } else if (posOfSpin == 1) {
            orderBy = "GOOD";
        } else if (posOfSpin == 2) {
            orderBy = "PRICE";
        }

        String min = etMoreExpensive.getText().toString();
        String max = etLessExpensive.getText().toString();

        if (!min.isEmpty()) flExpDown = Float.parseFloat(etMoreExpensive.getText().toString());
        else if (min.isEmpty()) flExpDown = 0;


        if (!max.isEmpty()) flExpTop = Float.parseFloat(etLessExpensive.getText().toString());
        else if (max.isEmpty()) flExpTop = 0;


        strDate1 = etDateBuy.getText().toString();
        strDate2 = etDateBuyEnd.getText().toString();

        Date startDate = new Date();
        Date endDate = new Date();

        try {
            startDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate1);
            endDate = new SimpleDateFormat("dd.MM.yyyy").parse(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Long startDateLong = (long) 0;
        Long endDateLong = (long) 0;

        if (!strDate1.isEmpty()) startDateLong = startDate.getTime();
        else if (strDate1.isEmpty()) startDateLong = (long) 0;

        if (!strDate2.isEmpty()) endDateLong = endDate.getTime() + 60*60*24*1000 - 1000;
        else if (strDate2.isEmpty()) endDateLong = (long) 0;

        svQuery = svSearch.getQuery().toString();

        //Log.d("mylogs", "Цены в пределах: " + flExpDown + " и " + flExpTop);
        //Log.d("mylogs", "Даты в пределах: ." + strDate1 + ". и ." + strDate2 + ".");
        //Log.d("mylogs", "Даты в LONG: ." + startDateLong + ". и ." + endDateLong + ".");

        TBobj.TableForQueries(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy);
    }


    public void TotalForEveryDay(){
        strDate1 = etDateBuy.getText().toString();

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

        Long startDateLong;
        Long endDateLong = (long) 0;

        if (!strDate1.isEmpty()) startDateLong = startDate.getTime();
        else startDateLong = (long) 0;

        if (!strDate2.isEmpty()) endDateLong = endDate.getTime() + (60*60*24*1000 - 1000);
        else if (strDate2.isEmpty()) endDateLong = (long) 0;

        TBobj.TableForEveryDay(startDateLong, endDateLong);
    }


    public void TotalForEveryMonth(){
        TBobj.TableForEveryMonth();
    }


    public void TotalForEveryPurch(){
        String strDate1;
        String strDate2;

        strDate1 = etDateBuy.getText().toString();
        Log.d("mylogs", "dates = " + strDate1);

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

        Long startDateLong = (long) 0;
        Long endDateLong = (long) 0;

        if (!strDate1.isEmpty()) startDateLong = startDate.getTime();
        else startDateLong = (long) 0;

        if (!strDate2.isEmpty()) endDateLong = endDate.getTime() + (60*60*24*1000 - 1000);
        else if (strDate2.isEmpty()) endDateLong = (long) 0;

        Log.d("mylogs", "dates = " + startDateLong + " and " + endDateLong);

        svQuery = svSearch.getQuery().toString();

        TBobj.TableForEveryPurch(startDateLong, endDateLong, svQuery);
    }


    private void ConcretePeriod(){
        strDate1 = etDateBuy.getText().toString();

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

        Long startDateLong;
        Long endDateLong = (long) 0;

        if (!strDate1.isEmpty()) startDateLong = startDate.getTime();
        else startDateLong = (long) 0;

        if (!strDate2.isEmpty()) endDateLong = endDate.getTime() + (60*60*24*1000 - 1000);
        else if (strDate2.isEmpty()) endDateLong = (long) 0;

        TBobj.TableForConcretePeriod(startDateLong, endDateLong);
    }


    private void enableSearchView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                enableSearchView(child, enabled);
            }
        }
    }

}