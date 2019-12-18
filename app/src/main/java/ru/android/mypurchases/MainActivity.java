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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button goodent;
    Button stat;
    Button futPurch;

    Intent GoodIntent;
    Intent Statistic;
    Intent FuturePurchases;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isStoragePermissionGranted();
        importDBfromSD();

        GoodIntent = new Intent(this, EnterGoods.class);
        Statistic = new Intent(this, Statistic.class);
        FuturePurchases = new Intent(this, FuturePurchases.class);


        goodent = (Button) findViewById(R.id.goodent);
        goodent.setOnClickListener(this);

        stat = (Button) findViewById(R.id.stat);
        stat.setOnClickListener(this);

        futPurch = (Button) findViewById(R.id.futPurch);
        futPurch.setOnClickListener(this);
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
            default:
                break;
        }
    }


    public void onDestroy() {
        super.onDestroy();
            isStoragePermissionGranted();
            exportDBtoSD();
    }


    public void importDBfromSD() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String backupDBPath = "com.Purchases.backup/myDB";
                String currentDBPath = "data/ru.android.mypurchases/databases/DataTable";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                Log.d(LOG_TAG, "trasfered? " + dst.transferFrom(src, 0, src.size()));
                src.close();
                dst.close();
                Log.d("mylogs", "Copied to " + backupDB.toString());
            }

        } catch (Exception e) {
            Log.d("mylogs", "" + e.toString());
        }
    }



    private void exportDBtoSD() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "data/ru.android.mypurchases/databases/DataTable";
                String backupDBPath = "com.Purchases.backup/myDB";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d(LOG_TAG, "Copied from " + backupDB.toString());

            }
        } catch (Exception e) {

            Log.d(LOG_TAG, "" + e.toString());

        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(LOG_TAG,"Permission is granted");
                File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/"+ "/com.Purchases.backup");
                if (!dir.exists()) {
                    try {
                        dir.mkdirs();
                        Log.d(LOG_TAG, "File created " + dir);
                    } catch (Exception except){
                        Log.d(LOG_TAG, "exception="+except);
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
            File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/"+ "/com.Purchases.backup");
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