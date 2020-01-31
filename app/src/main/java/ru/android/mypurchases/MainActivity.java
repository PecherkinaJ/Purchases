package ru.android.mypurchases;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button goodEnt;
    Button stat;
    Button futPurch;

    Intent GoodIntent;
    Intent Statistic;
    Intent FuturePurchases;
    Intent Additional;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoodIntent = new Intent(this, EnterGoods.class);
        Statistic = new Intent(this, Statistic.class);
        FuturePurchases = new Intent(this, FuturePurchases.class);
        Additional = new Intent(this, Additional.class);

        goodEnt = findViewById(R.id.goodent);
        goodEnt.setOnClickListener(this);

        stat = findViewById(R.id.stat);
        stat.setOnClickListener(this);

        futPurch = findViewById(R.id.futPurch);
        futPurch.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodent:
                startActivity(GoodIntent);
                break;
            case R.id.stat:
                startActivity(Statistic);
                break;
            case R.id.futPurch:
                startActivity(FuturePurchases);
                break;
            case R.id.fab:
                startActivity(Additional);
            default:
                break;
        }
    }




}