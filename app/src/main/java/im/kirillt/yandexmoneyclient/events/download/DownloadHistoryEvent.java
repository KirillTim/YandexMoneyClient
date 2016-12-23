package im.kirillt.yandexmoneyclient.events.download;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yandex.money.api.methods.OperationHistory;
import com.yandex.money.api.model.Operation;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;
import im.kirillt.yandexmoneyclient.provider.operation.OperationCursor;
import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection;
//import im.kirillt.yandexmoneyclient.utils.ResponseReady;
import im.kirillt.yandexmoneyclient.utils.ResponseToContentValues;

public class DownloadHistoryEvent implements DownloadEvent {
    private Context context;
    private static int DEFAULT_RECORDS_DOWNLOAD = 30;

    public DownloadHistoryEvent(Context context) {
        this.context = context;
    }

    public void download() {
        try {
            YMCApplication.historyDownloadingStart();
            download(getLatestSavedOperationDate(), -1, -1);
        } catch (IOException e) {
            YMCApplication.historyDownloadingFinish();
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }

    private void download(DateTime from, int startRecord, int records) throws IOException {
        try {
            OperationHistory response = YMCApplication.client.execute(createRequest(from, startRecord, records));
            Log.i("download: ", "nextRecord:"+ (response.nextRecord == null ? 0 : response.nextRecord )+" response size: "+response.operations.size());
            if (!TextUtils.isEmpty(response.nextRecord)) {
                try {
                    download(from, Integer.valueOf(response.nextRecord), records);
                } catch (IOException e) {
                    YMCApplication.historyDownloadingFinish();
                    e.printStackTrace();
                    EventBus.getDefault().post(new AnyErrorEvent(e));
                }
            }
            for (Operation operation : response.operations) {
                if (operation.type == Operation.Type.OUTGOING_TRANSFER
                        || operation.type == Operation.Type.INCOMING_TRANSFER
                        || operation.type == Operation.Type.INCOMING_TRANSFER_PROTECTED) {
                    ResponseToContentValues.operation(operation).insert(context.getContentResolver());
                }
            }
            YMCApplication.historyDownloadingFinish();
            EventBus.getDefault().post(new SuccessHistoryEvent());
        } catch (Exception exception) {
            YMCApplication.historyDownloadingFinish();
            EventBus.getDefault().post(new AnyErrorEvent(exception));
        }
    }

    public DateTime getLatestSavedOperationDate() {
        OperationCursor cursor = new OperationSelection().operationidNot("").query(context.getContentResolver(), null, OperationColumns.DATETIME + " DESC");
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToNext()) {
            return new DateTime(new Date(cursor.getDatetime()));
        }
        return null;
    }

    private OperationHistory.Request createRequest(DateTime from, int startRecord, int records) {
        HashSet<OperationHistory.FilterType> types = new HashSet<>();
        types.add(OperationHistory.FilterType.DEPOSITION);
        types.add(OperationHistory.FilterType.PAYMENT);
        OperationHistory.Request.Builder builder = new OperationHistory.Request.Builder()
                .setTypes(types)
                .setDetails(true);
        if (from != null) {
            builder.setFrom(from.plusMillis(100));
        }
        if (startRecord >= 0) {
            builder.setStartRecord(String.valueOf(startRecord));
        }
        if (records >= 0) {
            builder.setRecords(records);
        } else {
            builder.setRecords(DEFAULT_RECORDS_DOWNLOAD);
        }
        return builder.create();
    }
}
