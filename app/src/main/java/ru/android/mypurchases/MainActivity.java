package ru.android.mypurchases;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button goodent;
    Button stat;
    Button pref;
    Button futPurch;

    Intent GoodIntent;
    Intent Statistic;
    Intent Preferenses;
    Intent FuturePurchases;

    final String LOG_TAG = "myLogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoodIntent = new Intent(this, EnterGoods.class);
        Statistic = new Intent(this, Statistic.class);
        //Preferenses = new Intent(this, Preferenses.class);
        //FuturePurchases = new Intent(this, FuturePurchases.class);


        goodent = (Button) findViewById(R.id.goodent);
        goodent.setOnClickListener(this);

        /*pref = (Button) findViewById(R.id.pref);
        pref.setOnClickListener(this); */

        stat = (Button) findViewById(R.id.stat);
        stat.setOnClickListener(this);

        /*futPurch = (Button) findViewById(R.id.futPurch);
        futPurch.setOnClickListener(this);
*/
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goodent:
                startActivity(GoodIntent);
                break;
            case R.id.stat:
                startActivity(Statistic);
                break;
           /* case R.id.pref:
                startActivity(Preferenses);
                break;
            case R.id.futPurch:
                startActivity(FuturePurchases);
                break; */
            default:
                break;
        }
    }

}
