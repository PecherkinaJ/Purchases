package ru.android.mypurchases;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class TablesBuilding extends Activity {

    Context context;

    TextView tvID, tvDate, tvGood, tvPrice;

    TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams rowParamsEG = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow row;

    int tableRowID;
    PopupMenu popup;

    int clickedRow;
    String comment;
    Boolean ifEdition = false;

    String goodText;

    DataBase DBobj;
    TableLayout table;

    ArrayList<String> arrayString = new ArrayList();
    ArrayList<String> arrayFloat = new ArrayList();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    Calendar myCalendar;


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


    TablesBuilding() {

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
        ID1.setTextSize(20);
        ID1.setPadding(25, 5, 25, 5);
        ID1.setTypeface(null, Typeface.BOLD_ITALIC);
        date1.setTextSize(20);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);
        good1.setTextSize(20);
        good1.setPadding(25, 5, 25, 5);
        good1.setTypeface(null, Typeface.BOLD_ITALIC);
        price1.setTextSize(20);
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
        tvID.setLayoutParams(rowParamsEG);
        tvDate.setLayoutParams(rowParamsEG);
        tvGood.setLayoutParams(rowParamsEG);
        tvPrice.setLayoutParams(rowParamsEG);

        tvID.setTextSize(1);
        tvDate.setTextSize(15);
        tvDate.setPadding(5, 5, 5, 20);
        tvGood.setTextSize(16);
        tvGood.setPadding(35, 5, 5, 20);
        tvPrice.setTextSize(16);
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

        row.setOnClickListener(onClickListenerEG);
    }

    View.OnClickListener onClickListenerEG = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            v.setBackgroundColor(Color.GRAY);
            showPopupMenuEG(v);
            tableRowID = table.indexOfChild(v);
        }
    };


    public void showPopupMenuEG(final View v) {
        popup = new PopupMenu(context, v);
        popup.inflate(R.menu.popup_menu);

        TableRow t = (TableRow) v;

        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();
        TextView secondTextView = (TextView) t.getChildAt(1);
        final String GoodText = secondTextView.getText().toString();

        clickedRow = Integer.parseInt(IDText1);
        Log.d("mylogs", "clicked row = " + clickedRow);

        if (ifEdition) {
            Menu popupMenu = popup.getMenu();
            popupMenu.findItem(R.id.MENU_DELETE).setVisible(false);
        }

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
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        table.removeViewAt(tableRowID);
                                        DBobj.DeleteFromDB(clickedRow, "mytable");
                                        Toast.makeText(context, "Удалено", Toast.LENGTH_LONG).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder ab = new AlertDialog.Builder(context);
                        ab.setMessage("Подтвердите удаление").setPositiveButton("Да", dialogClickListener)
                                .setNegativeButton("Нет", dialogClickListener).show();
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



/**********************************************************************/

    /** STATISTIC */

    public void HeadRow(String firstColName,
                        String secondColName) {

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        TextView ID1 = new TextView(context);
        TextView date1 = new TextView(context);
        ID1.setLayoutParams(rowParams);
        date1.setLayoutParams(rowParams);
        ID1.setTextSize(20);
        ID1.setPadding(25, 5, 25, 5);
        ID1.setTypeface(null, Typeface.BOLD_ITALIC);
        date1.setTextSize(20);
        date1.setPadding(25, 5, 25, 5);
        date1.setTypeface(null, Typeface.BOLD_ITALIC);

        ID1.setText(firstColName);
        date1.setText(secondColName);

        row.addView(ID1);
        row.addView(date1);
        table.addView(row);
    }



    public void GeneralTableInStatistic(String firstCol,
                                        String secCol,
                                        int tableRowNum){

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvDate = new TextView(context);

        tvID.setLayoutParams(rowParams);
        tvDate.setLayoutParams(rowParams);

        tvID.setTextSize(15);
        tvID.setPadding(5, 5, 5, 20);
        tvDate.setTextSize(15);
        tvDate.setPadding(5, 5, 5, 20);

        tvID.setText(firstCol);
        tvDate.setText(secCol);

        row.addView(tvID);
        row.addView(tvDate);

        table.addView(row, tableRowNum);
    }

    public void GeneralTableInStatistic(String firstCol,
                                        int tableRowNum){

        row = new TableRow(context);
        row.setLayoutParams(tableParams);

        tvID = new TextView(context);
        tvDate = new TextView(context);

        tvID.setLayoutParams(rowParams);
        tvDate.setLayoutParams(rowParams);

        tvID.setTextSize(15);
        tvID.setPadding(5, 5, 5, 20);
        tvDate.setTextSize(15);
        tvDate.setPadding(5, 5, 5, 20);

        tvID.setText(firstCol);

        row.addView(tvID);

        table.addView(row, tableRowNum);
    }


    public void TableForQueries(float flExpDown,
                                float flExpTop,
                                long startDateLong,
                                long endDateLong,
                                String svQuery,
                                String orderBy) {

        table.removeAllViews();
        HeadRow("id", "Дата:", "Продукт:", "Цена:", true);

        for (int i = 0; i < DBobj.getRowsCountStat_query(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy); i++) {
            Vector<String> getVectForQueries = DBobj.TableOfQueries(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy, i);
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
        tvDate.setTextSize(15);
        tvDate.setPadding(5, 5, 5, 20);
        tvGood.setTextSize(16);
        tvGood.setPadding(5, 5, 5, 20);
        tvPrice.setTextSize(16);
        tvPrice.setPadding(5, 5, 15, 20);
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

        row.setOnClickListener(onClickListenerStat);
        registerForContextMenu(row);
    }


    View.OnClickListener onClickListenerStat = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            v.setBackgroundColor(Color.GRAY);
            showPopupQueries(v, context);

            tableRowID = table.indexOfChild(v);
        }

    };


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

                    case R.id.MENU_DELETE:
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        table.removeViewAt(tableRowID);
                                        DBobj.DeleteFromDB(clickedRow, "mytable");
                                        Toast.makeText(context, "Удалено", Toast.LENGTH_LONG).show();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder ab = new AlertDialog.Builder(context);
                        ab.setMessage("Подтвердите удаление").setPositiveButton("Да", dialogClickListener)
                                .setNegativeButton("Нет", dialogClickListener).show();
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
        arrayFloat.clear();
        arrayString.clear();
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Дата:", "Общая стоимость:");

        for (int i = 0; i < DBobj.getRowsCountStat_everyday(startDateLong, endDateLong); i++) {
            Vector<String> getVectForEveryday = DBobj.EveryDayTable(i, startDateLong, endDateLong);
            String strDate = getVectForEveryday.get(0);
            String strCost = getVectForEveryday.get(1);
            GeneralTableInStatistic(strDate, strCost, 1);

            arrayString.add(strDate);
            arrayFloat.add(strCost);
        }

    }


    public void TableForEveryMonth(){
        arrayFloat.clear();
        arrayString.clear();
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Месяц:", "Потрачено:");
        for (int i=0; i<DBobj.getRowsCountStat_everymonth(); i++) {
            Vector <String> getVectForEverymonth = DBobj.EveryMonthTable(i);
            String strDate = getVectForEverymonth.get(0);
            String strCost = getVectForEverymonth.get(1);
            GeneralTableInStatistic(strDate, strCost, 1);

            arrayString.add(strDate);
            arrayFloat.add(strCost);
        }
    }


    public void TableForEveryPurch(long startDateLong, long endDateLong, String svQuery){
        arrayFloat.clear();
        arrayString.clear();
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Покупка:", "Потрачено:");

        for (int i = 0; i < DBobj.getRowsCountStat_everypurch(startDateLong, endDateLong, svQuery); i++) {
            Vector<String> getVectForEverypurch = DBobj.EveryPurchTable(i, startDateLong, endDateLong, svQuery);
            String strDate = getVectForEverypurch.get(0);
            String strCost = getVectForEverypurch.get(1);
            GeneralTableInStatistic(strDate, strCost, 1);

            arrayString.add(strDate);
            arrayFloat.add(strCost);
        }
    }


    public void TableForConcretePeriod(long startDateLong, long endDateLong){
        arrayFloat.clear();
        arrayString.clear();
        table.removeAllViews();
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        HeadRow("Общая стоимость:", "");

        String sum = DBobj.ConcretePeriodTable(startDateLong, endDateLong);
        Log.d("mylogs", "SUM = " + sum);
        if (sum != null) {GeneralTableInStatistic(sum, 1);}
        else {GeneralTableInStatistic("Нет данных", 1);}

    }



/*************************************************************************************/

    /** FUTURE PURCHASES */

    public void AddNewElementFP(){
        final EditText taskEditText = new EditText(context);
        AlertDialog dialogNewFP = new AlertDialog.Builder(context)
                .setTitle("Новая покупка")
                .setView(taskEditText)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEditText.getText().toString();
                        if (TextUtils.isEmpty(taskEditText.getText())) {
                            Toast.makeText(context, "Поле пусто", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AddToFP(task);
                            Toast.makeText(context, "Элемент добавлен", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .setNeutralButton("Далее", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String task = taskEditText.getText().toString();

                        if (TextUtils.isEmpty(taskEditText.getText())) {
                            Toast.makeText(context, "Поле пусто", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            AddToFP(task);
                            Toast.makeText(context, "Элемент добавлен", Toast.LENGTH_SHORT).show();
                        }
                        AddNewElementFP();
                    }
                })
                .create();
        dialogNewFP.show();
    }


    public void AddToFP(String futureGood){
        DBobj.FillingFutPurch(futureGood, "false");

        Vector<String> newData = DBobj.UpdatingFP();
        String newStrID = newData.get(0);
        String newstrPurch = newData.get(1);
        String newstrCom = newData.get(2);
        Log.d("mylogs", "new data:" + newStrID + " " + newstrPurch + " " + newstrCom);
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

        tvGood.setTextSize(20);
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

        final FuturePurchases FP = (FuturePurchases) context;

        if (ifEdition) {
            Menu popupMenu = popup.getMenu();
            popupMenu.findItem(R.id.MENU_DELETE).setVisible(false);
        }
        else {
            Menu popupMenu = popup.getMenu();
            popupMenu.findItem(R.id.MENU_DELETE).setVisible(true);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.MENU_BOUGHT:
                        BoughtPurch();
                        break;

                    case R.id.MENU_EDIT:
                        ifEdition = true;
                        String purchase = DBobj.GetDataToEditionFP(clickedRow);
                        EditElementFP(purchase);
                        break;

                    case R.id.MENU_DELETE:
                        table.removeViewAt(tableRowID);
                        DBobj.DeleteFromDB(clickedRow, "futPurchTable");
                        break;

                    default:
                        break;
                }
                return false;
            }

        });

        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                v.setBackgroundColor(4);
            }
        });
        popup.show();

    }


    private void EditElementFP(String s){
        final EditText taskEditText = new EditText(context);
        taskEditText.setText(s);
        AlertDialog dialogEditFP = new AlertDialog.Builder(context)
                .setTitle("Редактирование покупки")
                .setView(taskEditText)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = taskEditText.getText().toString();

                        if (TextUtils.isEmpty(taskEditText.getText())) {
                            Toast.makeText(context, "Поле пусто", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DBobj.UpdateFP(task);
                            RecreatingRowFP();
                            Toast.makeText(context, "Изменения сохранены", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
        dialogEditFP.show();
        ifEdition = false;
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
                            Toast.makeText(context, "Не добавлено. Введите правильную сумму", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {
                            goodText = DBobj.BoughtGood(clickedRow);
                            DBobj.FillingDB(todayDate, goodText , taskFloat, "");
                            Log.d("mylogs", "\nдобавлено: \ngood = " + goodText + ", \n price = " + task);
                            DBobj.DeleteFromDB(clickedRow, "futPurchTable");
                            table.removeViewAt(tableRowID);
                            Toast.makeText(context, "Элемент добавлен", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Отмена", null)
                .create();
        dialogPrice.show();
    }


    public void RecreatingRowFP(){
        Vector<String> recreatedDB = DBobj.RecreatedRowFP(clickedRow);
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

            if (strComment.matches("false")) {
                Log.d("mylogs", "CLICKED ROW = " + strPurch + " MUST BECOME CROSSED OUT");
                DBobj.UpdateFPcomment("true");
                RecreatingRowFP();
            } else {
                v.setBackgroundColor(Color.alpha(100));
                Log.d("mylogs", "CLICKED ROW = " + strPurch + " MUST BECOME NORMAL");
                DBobj.UpdateFPcomment("false");
                RecreatingRowFP();
            }
        }

    };

}