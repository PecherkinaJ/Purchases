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

    Button goodent;
    Button stat;
    Button futPurch;
    Button settings;

    Intent GoodIntent;
    Intent Statistic;
    Intent FuturePurchases;
    Intent Settings;
    Saving save;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean a = isStoragePermissionGranted();
        save = new Saving();
        //save.exportDBfromSD();

        GoodIntent = new Intent(this, EnterGoods.class);
        Statistic = new Intent(this, Statistic.class);
        FuturePurchases = new Intent(this, FuturePurchases.class);
        Settings = new Intent(this, Settings.class);

        goodent = findViewById(R.id.goodent);
        goodent.setOnClickListener(this);

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


    public void onDestroy() {
        super.onDestroy();
        //save.importDBtoSD();
    }


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG,"Permission is granted");
                File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/com.Purchases.backup");
                if (!dir.exists()) {
                    try {
                        dir.mkdirs();
                        Log.d(LOG_TAG, "File created " + dir);
                    } catch (Exception except){
                        Log.d(LOG_TAG, "exception=" + except);
                    }
                }
                return true;
            } else {
                Log.d(LOG_TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.d(LOG_TAG,"Permission is granted");
            File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/com.Purchases.backup");
            if (!dir.exists()) {
                try {
                    dir.mkdirs();
                    Log.d(LOG_TAG, "File created " + dir);
                } catch (Exception except) {
                    Log.d(LOG_TAG, "exception=" + except);
                }
            }
            return true;
        }
    }

}