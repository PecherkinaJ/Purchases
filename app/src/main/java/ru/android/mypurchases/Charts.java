package ru.android.mypurchases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Charts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        Statistic statClass = new Statistic();

        if (statClass.posOfSpin == 3) {}
        else if (statClass.posOfSpin == 4) {}
        else if (statClass.posOfSpin == 5) {}
    }
}
