package ru.android.mypurchases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Charts extends AppCompatActivity {

    HashMap<String, Float> hashMap;

    DataBase DBobj;
    Statistic StatObj;
    TablesBuilding TBobj;

    ArrayList <String> DateArray;
    ArrayList<Float> PriceDayArray;

    ArrayList <String> MonthArray;
    ArrayList <Float> MonthWastingsArray;

    ArrayList <String> GoodsArray;
    ArrayList <Float> PriceGoodArray;

    HorizontalBarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        DBobj = new DataBase(this);
        StatObj = new Statistic();
        TBobj = new TablesBuilding();
        hashMap = TBobj.HashMapForGraph();

        Log.d("mylogs", "hashmap in Charts = " + hashMap);

        barChart = (HorizontalBarChart) findViewById(R.id.barChart);

        if (StatObj.posOfSpin == 3) {
            //EveryDay();
            BuildingCharts(PriceDayArray, DateArray, "Стоимость по дням");
        }
        else if (StatObj.posOfSpin == 4) {
            //EveryMonth();
            BuildingCharts(PriceGoodArray, GoodsArray, "Стоимость по месяцам");
        }
        else if (StatObj.posOfSpin == 5) {
            EveryPurch();
            BuildingCharts(MonthWastingsArray, MonthArray, "Стоимость по покупкам");
        }
    }

   /* private void EveryDay() {
        for (int i=0; i<DBobj.getRowsCountStat_everyday(StatObj.startDateLong, StatObj.endDateLong); i++) {
            Vector <String> getVectForEveryday = DBobj.EveryDayTable(i, StatObj.startDateLong, StatObj.endDateLong);
            String strDate = getVectForEveryday.get(0);
            float strCost = Float.parseFloat(getVectForEveryday.get(1));

            DateArray.add(strDate);
            PriceDayArray.add(strCost);
        }
    }

    private void EveryMonth(){
        for (int i=0; i<DBobj.getRowsCountStat_everymonth(); i++) {
            Vector <String> getVectForEverymonth = DBobj.EveryMonthTable(i);
            String strDate = getVectForEverymonth.get(0);
            float strCost = Float.parseFloat(getVectForEverymonth.get(1));

            MonthArray.add(strDate);
            MonthWastingsArray.add(strCost);
        }
    }*/

    private void EveryPurch(){
        for (String key : hashMap.keySet()) {
            GoodsArray.add(key);
            Log.d("mylogs", "array = " + GoodsArray);
        }
        for (float value : hashMap.values()) {
            PriceDayArray.add(value);
        }
    }


    private void BuildingCharts(ArrayList arrayName1, ArrayList arrayName2, String label) {
        BarDataSet set = new BarDataSet(arrayName1, label);
        BarData data = new BarData(set);
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
        barChart.animateY(3000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(arrayName2));

        barChart.invalidate();
    }

}
