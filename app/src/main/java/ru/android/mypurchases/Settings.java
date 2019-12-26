package ru.android.mypurchases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    Button btnExport;
    Button btnImport;

    TextView tvExport;
    TextView tvImport;

    Saving save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        switch (v.getId()){
            case R.id.btnExport:
                String pathExp = save.exportDBtoSD();
                Toast.makeText(this, "Сохранено по пути " + pathExp, Toast.LENGTH_LONG).show();
                break;
            case R.id.btnImport:
                save.importDBfromSD();
                Toast.makeText(this, "База данных восстановлена", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

}
