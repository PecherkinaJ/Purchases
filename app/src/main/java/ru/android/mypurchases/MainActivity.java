package ru.android.mypurchases;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button goodEnt;
    Button stat;
    Button futPurch;
    Button settings;

    Intent GoodIntent;
    Intent Statistic;
    Intent FuturePurchases;
    Intent Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoodIntent = new Intent(this, EnterGoods.class);
        Statistic = new Intent(this, Statistic.class);
        FuturePurchases = new Intent(this, FuturePurchases.class);
        Settings = new Intent(this, Settings.class);

        goodEnt = findViewById(R.id.goodent);
        goodEnt.setOnClickListener(this);

        stat = findViewById(R.id.stat);
        stat.setOnClickListener(this);

        futPurch = findViewById(R.id.futPurch);
        futPurch.setOnClickListener(this);

        settings = findViewById(R.id.settings);
        settings.setOnClickListener(this);
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
            case R.id.settings:
                startActivity(Settings);
            default:
                break;
        }
    }




}