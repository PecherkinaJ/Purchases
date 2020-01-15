package ru.android.mypurchases;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    Button btnExport;
    Button btnImport;

    TextView tvExport;
    TextView tvImport;

    Saving save;

    final String LOG_TAG = "myLogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        isStoragePermissionGranted();

        save = new Saving();

        btnExport = (Button) findViewById(R.id.btnExport);
        btnExport.setOnClickListener(this);
        btnImport = (Button) findViewById(R.id.btnImport);
        btnImport.setOnClickListener(this);

        tvExport = (TextView) findViewById(R.id.tvExport);
        tvImport = (TextView) findViewById(R.id.tvImport);

        tvExport.setTextSize(17);
        tvImport.setTextSize(17);
        tvExport.setGravity(View.TEXT_ALIGNMENT_CENTER);

        tvExport.setText("Вы можете сделать доступную копию базы данных, нажав на эту кнопку:");
        tvImport.setText("Восстановить сохраненную базу данных. ВНИМАНИЕ: текущая база данных будет заменена на импортированную!");
    }

    @Override
    public void onClick(View v) {
        boolean a = isStoragePermissionGranted();

        switch (v.getId()){
            case R.id.btnExport:
                if (a) {
                    String pathExp = save.exportDBtoSD();
                    Toast.makeText(this, "Сохранено по пути " + pathExp, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Нет доступа к файловой системе. Разрешите запись файлов", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnImport:
                if (a && save.importDBfromSD()) {
                    //save.importDBfromSD();
                    Toast.makeText(this, "База данных восстановлена", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this, "Нет доступа к файловой системе либо файла не существует", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
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
