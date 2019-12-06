package ru.android.mypurchases;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Vector;


public class TablesBuilding extends Activity {

    Context context;

    TextView tvID, tvDate, tvGood, tvPrice;

    TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow row;

    int tableRowID;
    PopupMenu popup;

    int clickedRow;
    String comment;
    Boolean ifEdition = false;

    String goodText;

    DataBase DBobj;
    TableLayout table;


    TablesBuilding(DataBase _db,
                   Context context,
                   TableLayout table,
                   String IDColName,
                   String secondColName,
                   String thirdColName,
                   String fourthColName,
                   Boolean hidingFirstColumn) {
        DBobj = _db;
        this.table = table;
        this.context = context;
        HeadRow(IDColName, secondColName, thirdColName, fourthColName, hidingFirstColumn);
        DisplayTableEG();
    }


    TablesBuilding(Context context,
                   DataBase _db,
                   TableLayout table) {
        DBobj = _db;
        this.table = table;
        this.context = context;
    }


    /* ENTER GOODS */

    public void HeadRow(String IDColName,
                        String secondColName,
                        String thirdColName,
                        String fourthColName,
                        Boolean hidingFirstColumn) {

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        TextView ID1 = new TextView(context);
        TextView date1 = new TextView(context);
        TextView good1 = new TextView(context);
        TextView price1 = new TextView(context);
        TextView com1 = new TextView(context);
        ID1.setLayoutParams(rowParams);
        date1.setLayoutParams(rowParams);
        good1.setLayoutParams(rowParams);
        price1.setLayoutParams(rowParams);
        com1.setLayoutParams(rowParams);
        ID1.setTextSize(23);
        ID1.setPadding(25, 5, 25, 5);
        ID1.setTypeface(null, Typeface.BOLD_ITALIC);
        date1.setTextSize(23);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);
        good1.setTextSize(23);
        good1.setPadding(25, 5, 25, 5);
        good1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setTextSize(23);
        price1.setPadding(25, 5, 25, 5);
        price1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        ID1.setText(IDColName);
        date1.setText(secondColName);
        good1.setText(thirdColName);
        price1.setText(fourthColName);

        row.addView(ID1);
        row.addView(date1);
        row.addView(good1);
        row.addView(price1);
        table.addView(row);

        if (hidingFirstColumn) ID1.setVisibility(View.GONE);
    }


    public void DisplayTableEG() {
        for (int i=0; i<DBobj.getRowsCountEG(); i++) {
            Vector<String> vectorRows = DBobj.DisplayExistingTable(i);
            String strID = vectorRows.get(0);
            String strDate = vectorRows.get(1);
            String strGood = vectorRows.get(2);
            String strPrice = vectorRows.get(3);
            DisplayExistingTable(strID, strDate, strGood, strPrice, true, 1);
        }
    }


    public void DisplayExistingTable(String ID,
                                     String Date,
                                     String Good,
                                     String Price,
                                     Boolean hidingFirstColumn,
                                     int rowNumber) {

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvDate = new TextView(context);
        tvGood = new TextView(context);
        tvPrice = new TextView(context);
        tvID.setLayoutParams(rowParams);
        tvDate.setLayoutParams(rowParams);
        tvGood.setLayoutParams(rowParams);
        tvPrice.setLayoutParams(rowParams);

        tvID.setTextSize(1);
        tvDate.setTextSize(17);
        tvDate.setPadding(5, 5, 5, 20);
        tvGood.setTextSize(18);
        tvGood.setPadding(5, 5, 5, 20);
        tvPrice.setTextSize(18);
        tvPrice.setPadding(35, 5, 15, 20);
        tvPrice.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        tvID.setText(ID);
        tvDate.setText(Date);
        tvGood.setText(Good);
        tvPrice.setText(Price);

        row.addView(tvID);
        row.addView(tvDate);
        row.addView(tvGood);
        row.addView(tvPrice);

        table.addView(row, rowNumber);
        if (hidingFirstColumn) tvID.setVisibility(View.GONE);

        row.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.GRAY);
                showPopupMenuEG(v);

                tableRowID = table.indexOfChild(v);
                return true;
            }
        });
        registerForContextMenu(row);

    }


    public void showPopupMenuEG(final View v) {
        popup = new PopupMenu(context, v);
        popup.inflate(R.menu.popup_menu);

        TableRow t = (TableRow) v;

        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();

        clickedRow = Integer.parseInt(IDText1);
        Log.d("mylogs", "clicked row = " + clickedRow);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.MENU_EDIT:
                        ifEdition = true;
                        EnterGoods EG = (EnterGoods)context;
                        EG.EditionTable();
                        break;

                    case R.id.MENU_DELETE:
                        table.removeViewAt(tableRowID);
                        DBobj.DeleteFromDB(clickedRow, "myPurch");
                        break;

                    case R.id.MENU_COMMENT:
                        comment = DBobj.ShowCommentPopup(clickedRow);
                        AddComment();


                        break;

                    default:
                        return false;
                }
                return false;
            }

        });

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                v.setBackgroundColor(Color.parseColor("#9AFFD180"));
                v.setBackgroundColor(Color.alpha(70));
            }
        });
        popup.show();

    }


    public void AddComment() {
        final EditText taskEditText = new EditText(context);

        taskEditText.setText(comment);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Комментарий")
                .setMessage("Добавьте комментарий в поле ниже")
                .setView(taskEditText)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEditText.getText().toString();
                        DBobj.UpdateComment(clickedRow, task);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
        dialog.show();
    }


    public int EditionalRow() {
        return clickedRow;
    }


    public void RecreatingRow(){
        Vector<String> recreatedDB = DBobj.RecreatedRow();
        table.removeViewAt(tableRowID);
        String strID = recreatedDB.get(0);
        String strDate = recreatedDB.get(1);
        String strGood = recreatedDB.get(2);
        String strPrice = recreatedDB.get(3);
        DisplayExistingTable(strID, strDate, strGood, strPrice, true, tableRowID);
    }



    /* STATISTIC */

    public void HeadRow(String firstColName,
                        String secondColName) {

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        TextView ID1 = new TextView(context);
        TextView date1 = new TextView(context);
        ID1.setLayoutParams(rowParams);
        date1.setLayoutParams(rowParams);
        ID1.setTextSize(23);
        ID1.setPadding(25, 5, 25, 5);
        ID1.setTypeface(null, Typeface.BOLD_ITALIC);
        date1.setTextSize(23);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);

        ID1.setText(firstColName);
        date1.setText(secondColName);

        row.addView(ID1);
        row.addView(date1);
        table.addView(row);
    }


    public void QueryTableForStat(float flExpDown,
                                  float flExpTop,
                                  long startDateLong,
                                  long endDateLong,
                                  String svQuery,
                                  String orderBy) {
        table.removeAllViews();
        HeadRow("id", "Дата:", "Продукт:", "Цена:", true);
        for (int i=0; i<DBobj.getRowsCountStat_query(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy); i++) {
            Vector <String> getVectForQueries = DBobj.TableOfQueries(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy, i);
            String strID = getVectForQueries.get(0);
            String strDate = getVectForQueries.get(1);
            String strGood = getVectForQueries.get(2);
            String strPrice = getVectForQueries.get(3);
            String strComm = getVectForQueries.get(4);
            StatTableQueries(strID, strDate, strGood, strPrice, strComm, true, 1);
        }
    }


    public void StatTableQueries(String ID,
                                 String Date,
                                 String Good,
                                 String Price,
                                 String Comment,
                                 boolean hidingFirstColumn,
                                 int tableRowNum){

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvDate = new TextView(context);
        tvGood = new TextView(context);
        tvPrice = new TextView(context);
        tvID.setLayoutParams(rowParams);
        tvDate.setLayoutParams(rowParams);
        tvGood.setLayoutParams(rowParams);
        tvPrice.setLayoutParams(rowParams);

        tvID.setTextSize(1);
        tvDate.setTextSize(17);
        tvDate.setPadding(5, 5, 5, 20);
        tvGood.setTextSize(18);
        tvGood.setPadding(5, 5, 5, 20);
        tvPrice.setTextSize(18);
        tvPrice.setPadding(35, 5, 15, 20);
        tvPrice.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

        tvID.setText(ID);
        tvDate.setText(Date);
        tvGood.setText(Good);
        tvPrice.setText(Price);

        row.addView(tvID);
        row.addView(tvDate);
        row.addView(tvGood);
        row.addView(tvPrice);

        table.addView(row, tableRowNum);
        if (hidingFirstColumn) tvID.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(Comment)) {
            tvDate.setTypeface(null, Typeface.BOLD_ITALIC);
            tvGood.setTypeface(null, Typeface.BOLD_ITALIC);
            tvPrice.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        row.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.GRAY);
                showPopupQueries(v, context);

                tableRowID = table.indexOfChild(v);
                return true;
            }
        });
        registerForContextMenu(row);


    }

    public void showPopupQueries(final View v, final Context context) {
        popup = new PopupMenu(context, v);
        popup.inflate(R.menu.popup_stat_query);

        TableRow t = (TableRow) v;

        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();

        clickedRow = Integer.parseInt(IDText1);
        Log.d("mylogs", "clicked row = " + clickedRow);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.MENU_COMMENT:
                        comment = DBobj.ShowCommentPopup(clickedRow);
                        ShowComment();
                        break;

                    default:
                        return false;
                }
                return false;
            }

        });

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                v.setBackgroundColor(Color.parseColor("#9AFFD180"));
                v.setBackgroundColor(Color.alpha(70));
            }
        });
        popup.show();

    }


    public void ShowComment() {
        final EditText taskEditText = new EditText(context);
        taskEditText.setText(comment);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Комментарий")
                .setView(taskEditText)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEditText.getText().toString();
                        DBobj.UpdateComment(clickedRow, task);
                        Vector<String> recreatedDB = DBobj.RecreatedRow();
                        table.removeViewAt(tableRowID);
                        String strID = recreatedDB.get(0);
                        String strDate = recreatedDB.get(1);
                        String strGood = recreatedDB.get(2);
                        String strPrice = recreatedDB.get(3);
                        StatTableQueries(strID, strDate, strGood, strPrice, task, true, tableRowID);
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
        dialog.show();
    }


    public void TableForEveryDay(long startDateLong, long endDateLong){
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Дата:", "Общая стоимость:");
        for (int i=0; i<DBobj.getRowsCountStat_everyday(startDateLong, endDateLong); i++) {
            Vector <String> getVectForEveryday = DBobj.EveryDayTable(i, startDateLong, endDateLong);
            String strDate = getVectForEveryday.get(0);
            String strCost = getVectForEveryday.get(1);
            StatTableEverySmth(strDate, strCost, 1);
        }
    }


    public void TableForEveryMonth(){
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Месяц:", "Потрачено:");
        for (int i=0; i<DBobj.getRowsCountStat_everymonth(); i++) {
            Vector <String> getVectForEverymonth = DBobj.EveryMonthTable(i);
            String strDate = getVectForEverymonth.get(0);
            String strCost = getVectForEverymonth.get(1);
            StatTableEverySmth(strDate, strCost, 1);
        }
    }


    public void TableForEveryPurch(long startDateLong, long endDateLong){
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Месяц:", "Потрачено:");
        for (int i=0; i<=DBobj.getRowsCountStat_everypurch(startDateLong, endDateLong); i++) {
            Vector <String> getVectForEverypurch = DBobj.EveryPurchTable(i, startDateLong, endDateLong);
            String strDate = getVectForEverypurch.get(0);
            String strCost = getVectForEverypurch.get(1);
            StatTableEverySmth(strDate, strCost, 1);
        }
    }


    public void StatTableEverySmth(String firstCol,
                               String secCol,
                               int tableRowNum){

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvDate = new TextView(context);

        tvID.setLayoutParams(rowParams);
        tvDate.setLayoutParams(rowParams);

        tvID.setTextSize(17);
        tvID.setPadding(5, 5, 5, 20);
        tvDate.setTextSize(17);
        tvDate.setPadding(5, 5, 5, 20);

        tvID.setText(firstCol);
        tvDate.setText(secCol);

        row.addView(tvID);
        row.addView(tvDate);

        table.addView(row, tableRowNum);
    }


    /* FUTURE PURCHASES */

    public void AddToFP(String futureGood){
        DBobj.FillingFutPurch(futureGood, "false");

        Vector<String> newData = DBobj.UpdatingFP();
        String newStrID = newData.get(0);
        String newstrPurch = newData.get(1);
        String newstrCom = newData.get(2);
        TableForFP(newStrID, newstrPurch, newstrCom, 0);
    }


    public void DisplayFutPurch(){
        int a = DBobj.getRowsCountStat_futurepurch();
        for (int i=0; i<a; i++){
            Vector <String> futPurch = DBobj.ShowFutPurch(i);
            String firstCol = futPurch.get(0);
            String secCol = futPurch.get(1);
            String comment = futPurch.get(2);
            Log.d("mylogs", "DB: \nPurchase = " + futPurch.get(1));
            TableForFP(firstCol, secCol, comment, 0);
        }
    }


    public void TableForFP(String firstCol,
                           String secCol,
                           String comment,
                           int tableRowNum){

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvGood = new TextView(context);

        tvID.setLayoutParams(rowParams);
        tvGood.setLayoutParams(rowParams);

        tvGood.setTextSize(22);
        tvGood.setPadding(5, 5, 5, 30);

        tvID.setText(firstCol);
        tvGood.setText(secCol);

        row.addView(tvID);
        row.addView(tvGood);

        table.addView(row, tableRowNum);
        table.setColumnStretchable(1, true);
        tvID.setVisibility(View.GONE);

        if (comment.matches("true")) {
            tvGood.setTypeface(null, Typeface.ITALIC);
            tvGood.setPaintFlags(tvGood.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvGood.setTextColor(Color.parseColor("#50000000"));
        }

        row.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.GRAY);
                showPopupMenuFP(v);

                tableRowID = table.indexOfChild(v);
                return true;
            }
        });
        registerForContextMenu(row);

        row.setOnClickListener(onClickListener);
    }


    public void showPopupMenuFP(final View v) {

        final PopupMenu popup = new PopupMenu(context, v);
        popup.inflate(R.menu.popup_futputch);

        TableRow t = (TableRow) v;

        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();
        Log.d("mylogs", "ID = " + IDText1);

        clickedRow = Integer.parseInt(IDText1);

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.MENU_BOUGHT:
                        BoughtPurch();
                        break;

                    case R.id.MENU_EDIT:
                        ifEdition = true;
                        FuturePurchases FP = (FuturePurchases) context;
                        FP.EditionTable();
                        break;

                    case R.id.MENU_DELETE:
                        table.removeViewAt(tableRowID);
                        DBobj.DeleteFromDB(clickedRow, "futPurchTable");
                        break;

                    default:
                        return false;
                }
                return false;
            }

        });

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                //UpdatingTable();
            }
        });
        popup.show();

    }


    private void BoughtPurch() {

        final long todayDate = new Date(System.currentTimeMillis()).getTime();

        final EditText taskEditText = new EditText(context);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        AlertDialog dialogPrice = new AlertDialog.Builder(context)
                .setTitle("Сумма покупки")
                .setMessage("Введите сумму покупки в поле ниже")
                .setView(taskEditText)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEditText.getText().toString();
                        float taskFloat = Float.parseFloat(task);

                        if (TextUtils.isEmpty(taskEditText.getText())) {
                            Toast.makeText(getApplicationContext(), "Не добавлено. Введите правильную сумму", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            goodText = DBobj.BoughtGood(clickedRow);
                            DBobj.FillingDB(todayDate, goodText , taskFloat, "");
                            Log.d("mylogs", "\nдобавлено: \ngood = " + goodText + ", \n price = " + task);
                            DBobj.DeleteFromDB(clickedRow, "futPurchTable");
                            table.removeViewAt(tableRowID);
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
        dialogPrice.show();
    }


    public void RecreatingRowFP(){
        Vector<String> recreatedDB = DBobj.RecreatedRowFP();
        table.removeViewAt(tableRowID);
        String strID = recreatedDB.get(0);
        String strDate = recreatedDB.get(1);
        String strCom = recreatedDB.get(2);
        TableForFP(strID, strDate, strCom, tableRowID);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tableRowID = table.indexOfChild(v);
            TableRow t = (TableRow) v;

            TextView idTextView = (TextView) t.getChildAt(0);
            final String strID = idTextView.getText().toString();
            clickedRow = Integer.parseInt(strID);

            Vector<String> FPvector = DBobj.TrueFalseComment(clickedRow);
            String strPurch = FPvector.get(0);
            String strComment = FPvector.get(1);
            Log.d("mylogs", "CLICKED ROW " + clickedRow + " HAD COMMENT = " + strComment);

            if (!strComment.matches("true")) {
                Log.d("mylogs", "CLICKED ROW = " + strPurch + " MUST BECOME CROSSED OUT");
                DBobj.UpdateFP(strPurch, "true");
            } else {
                v.setBackgroundColor(Color.alpha(100));
                Log.d("mylogs", "CLICKED ROW = " + strPurch + " MUST BECOME NORMAL");
                DBobj.UpdateFP(strPurch, "false");
            }
            RecreatingRowFP();
        }

    };

}