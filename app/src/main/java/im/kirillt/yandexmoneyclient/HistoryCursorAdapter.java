package im.kirillt.yandexmoneyclient;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.Date;

import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;


public class HistoryCursorAdapter extends CursorAdapter {

    public HistoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_operation_in_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView amount = (TextView) view.findViewById(R.id.amount);
        title.setText(cursor.getString(cursor.getColumnIndex(OperationColumns.TITLE)));
        long unixTime = cursor.getLong(cursor.getColumnIndex(OperationColumns.DATETIME));
        DateTime time = new DateTime(new Date(unixTime));
        date.setText(time.toString("yyyy-MM-dd HH:mm:ss"));
        amount.setText(cursor.getString(cursor.getColumnIndex(OperationColumns.AMOUNT)));
        //DateTime now = new DateTime(new Date(System.currentTimeMillis()/1000L));
    }
}
