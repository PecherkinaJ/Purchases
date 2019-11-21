package ru.android.mypurchases;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

public class PopupMenu {
    DataBase DBobj;

    public void PopupInEnterGoods(View v, Context con) {
        DBobj = new DataBase(con);
        android.support.v7.widget.PopupMenu popup = new android.support.v7.widget.PopupMenu(con, v);
        popup.getMenu().add("Комментарий");
        popup.getMenu().add("Редактировать элемент");
        popup.getMenu().add("Удалить элемент");

        TableRow t = (TableRow) v;
        TextView firstTextView = (TextView) t.getChildAt(0);
        final String IDText1 = firstTextView.getText().toString();
        Log.d("mylogs", "ID = " + IDText1);

        int clickedRow = Integer.parseInt(IDText1);

        DBobj.ShowCommentPopup(clickedRow);
    }
}
