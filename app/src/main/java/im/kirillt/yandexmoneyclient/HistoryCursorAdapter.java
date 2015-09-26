package im.kirillt.yandexmoneyclient;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.Date;

import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;
import im.kirillt.yandexmoneyclient.provider.operation.OperationCursor;
import im.kirillt.yandexmoneyclient.provider.operation.direction;
import im.kirillt.yandexmoneyclient.provider.operation.status;


public class HistoryCursorAdapter extends CursorAdapter {

    private Drawable icons[][] = new Drawable[3][3];//[direction: in/out/unknown][status: success/refused/in progress]

    public HistoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        //don't want to write if (CURRENT_API <= ...), so deprecated method is used
        icons[0][0] = context.getResources().getDrawable(R.drawable.ic_arrow_back_green_600_24dp);
        icons[0][1] = context.getResources().getDrawable(R.drawable.ic_arrow_back_orange_500_24dp);
        icons[0][2] = context.getResources().getDrawable(R.drawable.ic_arrow_back_red_900_24dp);
        for (int i = 0; i < 3; i++) {
            icons[1][i] = context.getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp);
            icons[2][i] = context.getResources().getDrawable(R.drawable.ic_crop_square_black_24dp);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_operation, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        OperationCursor operationCursor = new OperationCursor(cursor);
        LinearLayout header = (LinearLayout) view.findViewById(R.id.item_operation_header);
        LinearLayout expandedContainer = (LinearLayout) view.findViewById(R.id.item_operation_expanded_container);

        initHeader(header, expandedContainer, operationCursor);
        initExpandedContainer(expandedContainer, operationCursor);
    }

    private void initHeader(LinearLayout header, LinearLayout expandedContainer, OperationCursor cursor) {
        LinearLayout headerSmallDetails = (LinearLayout) header.findViewById(R.id.item_operation_small_details);
        ((TextView) header.findViewById(R.id.item_operation_small_title)).setText(cursor.getTitle());
        ((TextView) headerSmallDetails.findViewById(R.id.item_operation_small_amount))
                .setText(cursor.getAmount());
        ((TextView) headerSmallDetails.findViewById(R.id.item_operation_small_date))
                .setText(cursor.getDatetime()+"");
        initHeaderImage(header, cursor);
        expandedContainer.setVisibility(View.GONE);
        header.setOnClickListener(v -> {
            if (expandedContainer.getVisibility() == View.VISIBLE
                    || expandedContainer.getVisibility() == View.INVISIBLE) {
                expandedContainer.setVisibility(View.GONE);
                headerSmallDetails.setVisibility(View.VISIBLE);
            } else if (expandedContainer.getVisibility() == View.GONE) {
                expandedContainer.setVisibility(View.VISIBLE);
                headerSmallDetails.setVisibility(View.GONE);
            }
        });
    }

    private void initHeaderImage(LinearLayout header, OperationCursor cursor) {
        ImageView imageView = (ImageView)header.findViewById(R.id.item_operation_small_direction_image);
        //int dir = cursor.getInt(cursor.getColumnIndex(OperationColumns.DIRECTION));
        direction dir = cursor.getDirection();
        status st = cursor.getStatus();
        imageView.setImageDrawable( icons[dir.ordinal()][st.ordinal()]);
    }

    private void initExpandedContainer(LinearLayout container, OperationCursor cursor) {
        /*setTextOrVisibleGone((TextView)container.findViewById(R.id.item_operation_expanded_commission),
                getStringFromCursor(cursor, OperationColumns.FEE));
        setTextOrVisibleGone((TextView)container.findViewById(R.id.item_operation_expanded_status),
                getStringFromCursor(cursor, OperationColumns.STATUS));
        setTextOrVisibleGone((TextView)container.findViewById(R.id.item_operation_expanded_recipient),
                getStringFromCursor(cursor, OperationColumns.FEE));
        setTextOrVisibleGone((TextView)container.findViewById(R.id.item_operation_expanded_message),
                getStringFromCursor(cursor, OperationColumns.FEE));*/
        setTextOrVisibleGone(container, R.id.item_operation_expanded_date, cursor.getDatetime()+"");
        setTextOrVisibleGone(container, R.id.item_operation_expanded_amount, cursor.getAmount());
        TextView statusTV = (TextView)container.findViewById(R.id.item_operation_expanded_status);
        setTextOrVisibleGone(statusTV, cursor.getStatus().toString());
        container.findViewById(R.id.item_operation_expanded_status_header).setVisibility(statusTV.getVisibility());
        TextView recipientTV = (TextView)container.findViewById(R.id.item_operation_expanded_recipient);
        setTextOrVisibleGone(recipientTV, cursor.getRecipient());
        container.findViewById(R.id.item_operation_expanded_recipient_header).setVisibility(recipientTV.getVisibility());
        TextView messageTV = (TextView)container.findViewById(R.id.item_operation_expanded_message);
        setTextOrVisibleGone(messageTV, cursor.getMessage());
        container.findViewById(R.id.item_operation_expanded_message_header).setVisibility(messageTV.getVisibility());
        initButton(container, cursor);
    }

    private void initButton(LinearLayout expandedContainer, OperationCursor cursor) {
        //int dir = cursor.getInt(cursor.getColumnIndex(OperationColumns.DIRECTION));
        direction dir = cursor.getDirection();
        if (dir == direction.OUTGOING) {
            expandedContainer.findViewById(R.id.item_operation_expanded_repeat_button).setVisibility(View.VISIBLE);
            expandedContainer.findViewById(R.id.item_operation_payment_action_container).setVisibility(View.GONE);
        } else if (dir == direction.INCOMING) {
            expandedContainer.findViewById(R.id.item_operation_expanded_repeat_button).setVisibility(View.GONE);
            //if (cursor.getInt(cursor.getColumnIndex(OperationColumns.STATUS)) == status.IN_PROGRESS.ordinal()) {
            if (cursor.getStatus() == status.IN_PROGRESS) {
                expandedContainer.findViewById(R.id.item_operation_payment_action_container).setVisibility(View.VISIBLE);
            } else {
                expandedContainer.findViewById(R.id.item_operation_payment_action_container).setVisibility(View.GONE);
            }
        }
    }

/*    private String getStringFromCursor(Cursor cursor, String column) {
        try {
            return cursor.getString(cursor.getColumnIndex(column));
        } catch (Exception e) {
            return "";
        }
    }

    private int getIntFromCursor(Cursor cursor, String column) {
        try {
            return cursor.getInt(cursor.getColumnIndex(column));
        } catch (Exception e) {
            return 0;
        }
    }*/

    private void setTextOrVisibleGone(View root, int resourceId, String text) {
        setTextOrVisibleGone((TextView)root.findViewById(resourceId), text);
    }

    private void setTextOrVisibleGone(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setText(text);
        }
    }

}
