package ru.android.mypurchases;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Saving {

    static final String LOG_TAG = "mylogs";


    // SD-card

    void importDBtoSD() {
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
                src.close();
                dst.close();
                Log.d("mylogs", "Copied to " + backupDB.toString());
            }

        } catch (Exception e) {
            Log.d("mylogs", "" + e.toString());
        }
    }


    void exportDBfromSD() {
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

}
