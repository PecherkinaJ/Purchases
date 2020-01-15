package ru.android.mypurchases;

import android.os.Environment;
import android.util.AtomicFile;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Saving {

    static final String LOG_TAG = "mylogs";


    // SD-card

    String exportDBtoSD() {
        String path = "";

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "data/ru.android.mypurchases/databases/DataTable";
                String backupDBPath = "/com.Purchases.backup/DataTable";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                File backUp1 = new File(sd, "/com.Purchases.backup/");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d("mylogs", "Copied to " + backupDB.toString());

                path = backUp1.toString();
            }

        } catch (Exception e) {
            Log.d("mylogs", "" + e.toString());
        }

        return path;
    }


    boolean importDBfromSD() {
        // TODO Auto-generated method stub
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "data/ru.android.mypurchases/databases/DataTable";
                String backupDBPath = "/com.Purchases.backup/DataTable";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.d(LOG_TAG, "Copied to " + backupDB.toString());
                return true;
            }
        }
        catch (Exception e) {
            Log.d(LOG_TAG, "" + e.toString());
            return false;
        }
        return false;
    }

}
