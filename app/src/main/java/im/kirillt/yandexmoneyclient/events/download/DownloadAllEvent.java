package im.kirillt.yandexmoneyclient.events.download;

import android.content.Context;


public class DownloadAllEvent implements DownloadEvent{
    private DownloadAccountInfoEvent downloadAccountInfoEvent;
    private DownloadHistoryEvent downloadHistoryEvent;

    public DownloadAllEvent(Context context) {
        downloadAccountInfoEvent = new DownloadAccountInfoEvent(context);
        downloadHistoryEvent = new DownloadHistoryEvent(context);
    }

    public void download() {
        downloadAccountInfoEvent.download();
        downloadHistoryEvent.download();
    }
}
