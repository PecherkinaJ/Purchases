package ru.android.mypurchases;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardEnter;
    CardView cardStat;
    CardView cardFutpurch;

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

        cardEnter = findViewById(R.id.card_view1);
        cardEnter.setOnClickListener(this);

        cardStat = findViewById(R.id.card_view2);
        cardStat.setOnClickListener(this);

        cardFutpurch = findViewById(R.id.card_view3);
        cardFutpurch.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.card_view1:
                startActivity(GoodIntent);
                break;
            case R.id.card_view2:
                startActivity(Statistic);
                break;
            case R.id.card_view3:
                startActivity(FuturePurchases);
                break;
            case R.id.fab:
                startActivity(Additional);
            default:
                break;
        }
    }




}