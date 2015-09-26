package im.kirillt.yandexmoneyclient.events.download;

import com.yandex.money.api.methods.AccountInfo;

public class SuccessAccountInfoEvent implements SuccessDownloadEvent {
    public final AccountInfo response;

    public SuccessAccountInfoEvent() {
        response = null;
    }

    public SuccessAccountInfoEvent(AccountInfo response) {
        this.response = response;
    }
}
