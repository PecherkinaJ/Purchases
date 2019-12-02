package ru.android.mypurchases;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
                showPopupMenuEG(v, table, context);

                tableRowID = table.indexOfChild(v);
                return true;
            }
        });
        registerForContextMenu(row);

    }


    public void showPopupMenuEG(final View v, final TableLayout table, final Context context) {
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
                        //ifEdition =  false;
                        break;

                    case R.id.MENU_DELETE:
                        DeleteTableRow(table);
                        DBobj.DeleteFromDB(clickedRow);
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

    public void DeleteTableRow(TableLayout table) {
        table.removeViewAt(tableRowID);
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

    public void StatTableQueries(float flExpDown,
                                 float flExpTop,
                                 long startDateLong,
                                 long endDateLong,
                                 String svQuery,
                                 String orderBy,
                                 boolean hidingFirstColumn){
        Vector <String> getVectForQueries = DBobj.TableOfQueries(flExpDown, flExpTop, startDateLong, endDateLong, svQuery, orderBy);

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

        /*tvID.setText(ID);
        tvDate.setText(Date);
        tvGood.setText(Good);
        tvPrice.setText(Price);*/

        row.addView(tvID);
        row.addView(tvDate);
        row.addView(tvGood);
        row.addView(tvPrice);

        //table.addView(row, rowNumber);
        if (hidingFirstColumn) tvID.setVisibility(View.GONE);

        row.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.GRAY);
                //showPopupMenuEG(v, table, context);

                tableRowID = table.indexOfChild(v);
                return true;
            }
        });
        registerForContextMenu(row);

    }

}