package im.kitillt.yandexmoneyclient.events.download;

import android.content.Context;
import android.text.TextUtils;

import com.yandex.money.api.methods.OperationHistory;
import com.yandex.money.api.model.Operation;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import de.greenrobot.event.EventBus;
import im.kitillt.yandexmoneyclient.YMCApplication;
import im.kitillt.yandexmoneyclient.events.download.DownloadEvent;
import im.kitillt.yandexmoneyclient.events.result.AnyErrorEvent;
import im.kitillt.yandexmoneyclient.events.result.SuccessHistoryEvent;
import im.kitillt.yandexmoneyclient.provider.operation.OperationColumns;
import im.kitillt.yandexmoneyclient.provider.operation.OperationCursor;
import im.kitillt.yandexmoneyclient.provider.operation.OperationSelection;
import im.kitillt.yandexmoneyclient.utils.ResponseReady;
import im.kitillt.yandexmoneyclient.utils.ResponseToContentValues;

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
        YMCApplication.auth2Session.enqueue(createRequest(from, startRecord, records), new ResponseReady<OperationHistory>() {
            @Override
            protected void failure(Exception exception) {
                YMCApplication.historyDownloadingFinish();
                exception.printStackTrace();
                EventBus.getDefault().post(new AnyErrorEvent(exception));
            }

            @Override
            protected void response(OperationHistory response) {
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
                    ResponseToContentValues.operation(operation).insert(context.getContentResolver());
                }
                YMCApplication.historyDownloadingFinish();
                EventBus.getDefault().post(new SuccessHistoryEvent());
            }
        });
    }

    private DateTime getLatestSavedOperationDate() {
        OperationCursor cursor = new OperationSelection().operationidNot("").query(context.getContentResolver(), null, OperationColumns.DATETIME + " DESC");
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToNext()) {
            return new DateTime(new Date(cursor.getDatetime() * 1000L));
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
            builder.setFrom(from);
        }
        if (startRecord >= 0) {
            builder.setStartRecord(String.valueOf(startRecord));
        }
        if (records >= 0) {
            builder.setRecords(records);
        } else {
            builder.setRecords(DEFAULT_RECORDS_DOWNLOAD);
        }
        return builder.createRequest();
    }
}
