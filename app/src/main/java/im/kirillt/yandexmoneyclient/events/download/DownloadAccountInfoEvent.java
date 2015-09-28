package im.kirillt.yandexmoneyclient.events.download;

import android.content.Context;

import com.yandex.money.api.methods.AccountInfo;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.provider.account.AccountColumns;
import im.kirillt.yandexmoneyclient.provider.account.AccountContentValues;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class DownloadAccountInfoEvent implements DownloadEvent {
    private Context context;
    private String login = null;

    public DownloadAccountInfoEvent(Context context, String login) {
        this.context = context;
        this.login = login;
    }

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
                    AccountContentValues contentValues = new AccountContentValues();
                    contentValues.putBalance(response.balance.toString());
                    if (login != null) {
                        contentValues.putAccountusername(login);
                    }
                    if (response.balanceDetails != null) {
                        contentValues.putBalancehold(response.balanceDetails.hold.toString());
                    }
                    if (response.avatar != null) {
                        contentValues.putAvatar(response.avatar.url);
                    }

                    context.getContentResolver().
                            update(AccountColumns.CONTENT_URI, contentValues.values(), null, null);
                    YMCApplication.accountDownloadingFinish();
                    EventBus.getDefault().post(new SuccessAccountInfoEvent(response));
                }
            });
        } catch (IOException e) {
            YMCApplication.accountDownloadingFinish();
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }
}
