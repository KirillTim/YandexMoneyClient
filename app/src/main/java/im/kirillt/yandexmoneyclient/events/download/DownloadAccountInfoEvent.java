package im.kirillt.yandexmoneyclient.events.download;

import android.content.Context;

import com.yandex.money.api.methods.AccountInfo;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.provider.account.AccountSelection;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;
import im.kirillt.yandexmoneyclient.utils.ResponseToContentValues;

public class DownloadAccountInfoEvent implements DownloadEvent {
    private Context context;

    public DownloadAccountInfoEvent(Context context) {
        this.context = context;
    }

    public void download() {
        try {
            YMCApplication.accountDownloadingStart();
            YMCApplication.auth2Session.enqueue(new AccountInfo.Request(), new ResponseReady<AccountInfo>() {
                @Override
                protected void failure(Exception exception) {
                    YMCApplication.accountDownloadingFinish();
                    exception.printStackTrace();
                    EventBus.getDefault().post(new AnyErrorEvent(exception));
                }

                @Override
                protected void response(AccountInfo response) {
                    //TODO: rewrite it better way
                    new AccountSelection().accountnumberNot(response.account).delete(context.getContentResolver());
                    ResponseToContentValues.account(response).insert(context.getContentResolver());
                    YMCApplication.accountDownloadingFinish();
                    EventBus.getDefault().post(new SuccessAccountInfoEvent());
                }
            });
        } catch (IOException e) {
            YMCApplication.accountDownloadingFinish();
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }
}
