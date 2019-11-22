package ru.android.mypurchases;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TablesBuilding extends Activity {

    Context context;

    TextView tvID, tvDate, tvGood, tvPrice;

    TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
    TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
    TableRow row;

    DataBase DBobj;

    PopupMenu popup;


    public void HeadRow(Context context,
                        TableLayout table,
                        String IDColName,
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


    public void DisplayExistingTable(final Context context,
                                     final TableLayout table,
                                     String ID,
                                     String Date,
                                     String Good,
                                     String Price,
                                     Boolean hidingFirstColumn) {

        this.context = context;

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

        tvID.setTextSize(15);
        tvID.setPadding(5, 5, 25, 5);
        tvDate.setTextSize(20);
        tvDate.setPadding(25, 5, 25, 5);
        tvGood.setTextSize(20);
        tvGood.setPadding(25, 5, 25, 5);
        tvPrice.setTextSize(20);
        tvPrice.setPadding(25, 5, 25, 5);

        tvID.setText(ID);
        tvDate.setText(Date);
        tvGood.setText(Good);
        tvPrice.setText(Price);

        row.addView(tvID);
        row.addView(tvDate);
        row.addView(tvGood);
        row.addView(tvPrice);

        table.addView(row, 1);
        if (hidingFirstColumn) tvID.setVisibility(View.GONE);

        row.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                v.setBackgroundColor(Color.GRAY);
                showPopupMenu(v);
                return true;
            }
        });
        registerForContextMenu(row);

       /* popup.setOnDismissListener(new android.widget.PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(android.widget.PopupMenu menu) {
                v.setBackgroundColor(Color.parseColor("#9AFFD180"));
                v.setBackgroundColor(Color.alpha(70));
            }
        });*/
    }


    public void showPopupMenu(final View v) {
        popup = new PopupMenu(context, v);
        popup.inflate(R.menu.popup_menu);

        TableRow t = (TableRow) v;

        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();

        int clickedRow = Integer.parseInt(IDText1);
//        DBobj.ShowCommentPopup(clickedRow);


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.MENU_EDIT:
                        /*ifEdition = true;
                        etDate.setText(dateText);
                        etGoods.setText(goodText);
                        etPrice.setText(priceText);*/
                        break;

                    case R.id.MENU_DELETE:
                        /*tablelay.removeView(v);
                        DeleteTableRow();*/
                        break;

                    case R.id.MENU_COMMENT:
                        //addComment(EnterGoods.this);


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


}
