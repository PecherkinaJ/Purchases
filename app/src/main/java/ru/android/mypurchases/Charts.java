package ru.android.mypurchases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Charts extends AppCompatActivity {

    HorizontalBarChart barChart;

    ArrayList<String> arrayString = new ArrayList<>();
    ArrayList<String> arrayFloat = new ArrayList<>();

    ArrayList<BarEntry> arrayFloatBarEntry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        barChart = (HorizontalBarChart) findViewById(R.id.barChart);

        arrayString = getIntent().getStringArrayListExtra("arrayString");
        arrayFloat = getIntent().getStringArrayListExtra("arrayFloat");

        if (arrayString != null) {
            for (int i = 0; i < arrayFloat.size(); i++) {
                float abc = Float.parseFloat(arrayFloat.get(i));
                arrayFloatBarEntry.add(new BarEntry(i, abc));
            }
            BuildingCharts(arrayFloatBarEntry, arrayString, "");
        }
    }


    private void BuildingCharts(ArrayList floatArray, ArrayList stringArray, String label) {
        BarDataSet set = new BarDataSet(floatArray, label);
        BarData data = new BarData(set);
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.setData(data);
        barChart.animateY(3000);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(stringArray));

        barChart.invalidate();
    }

}