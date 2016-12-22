/*
package im.kirillt.yandexmoneyclient.events;

import android.content.ContentResolver;
import android.content.Context;

import com.yandex.money.api.methods.IncomingTransferAccept;
import com.yandex.money.api.methods.IncomingTransferReject;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.provider.operation.OperationColumns;
import im.kirillt.yandexmoneyclient.provider.operation.OperationContentValues;
import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection;
import im.kirillt.yandexmoneyclient.provider.operation.status;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class IncomingTransferProcessEvent {
    private String operationId;
    private String code;
    private boolean accept;
    private ContentResolver contentResolver;
    private Context context;

    public IncomingTransferProcessEvent(Context context, String operationId, String code, boolean accept) {
        this.operationId = operationId;
        this.code = code;
        this.accept = accept;
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public void process() {
        if (accept) {
            processAccept();
        } else {
            processReject();
        }
    }

    private void processAccept() {
        try {

        YMCApplication.auth2Session.enqueue(new IncomingTransferAccept.Request(operationId, code), new ResponseReady<IncomingTransferAccept>() {
            @Override
            protected void failure(Exception exception) {
                EventBus.getDefault().post(new AnyErrorEvent(exception));
            }

            @Override
            protected void response(IncomingTransferAccept response) {
                String msg = "";
                OperationContentValues contentValues = new OperationContentValues();
                if (response.status == IncomingTransferAccept.Status.SUCCESS) {
                    msg = "Success";
                    contentValues.putStatus(status.SUCCESS);
                } else if (response.status == IncomingTransferAccept.Status.REFUSED) {
                    msg = "Invalid code, "+response.protectionCodeAttemptsAvailable+" attempts available";
                    if (response.protectionCodeAttemptsAvailable == 0) {
                        contentValues.putStatus(status.REFUSED);
                    }
                } else if (response.status == IncomingTransferAccept.Status.UNKNOWN) {
                    msg = "Unknown result, try to update";
                }
                EventBus.getDefault().post(new IncomingTransferProcessResultEvent(msg, true));
                contentResolver.update(OperationColumns.CONTENT_URI, contentValues.values(),
                        new OperationSelection().operationid(operationId).sel(), new String[]{operationId});
            }
        });
        } catch (Exception e) {
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }

    private void processReject() {
        try {
            YMCApplication.auth2Session.enqueue(new IncomingTransferReject.Request(operationId), new ResponseReady<IncomingTransferReject>() {
                @Override
                protected void failure(Exception exception) {
                    EventBus.getDefault().post(new AnyErrorEvent(exception));
                }

                @Override
                protected void response(IncomingTransferReject response) {
                    EventBus.getDefault().post(new IncomingTransferProcessResultEvent(response.status.toString(),false));
                    contentResolver.update(OperationColumns.CONTENT_URI, new OperationContentValues().putStatus(status.REFUSED).values(),
                            new OperationSelection().operationid(operationId).sel(), new String[]{operationId});
                }
            });
        } catch (Exception e) {
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }

    }
}
*/
