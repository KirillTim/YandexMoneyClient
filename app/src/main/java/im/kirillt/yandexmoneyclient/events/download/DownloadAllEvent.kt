package im.kirillt.yandexmoneyclient.events.download

import android.content.Context
import im.kirillt.yandexmoneyclient.YMCApplication


class DownloadAllEvent(context: Context) : DownloadEvent {
    private val downloadAccountInfoEvent: DownloadAccountInfoEvent
    private val downloadHistoryEvent: DownloadHistoryEvent

    init {
        downloadAccountInfoEvent = DownloadAccountInfoEvent(context, YMCApplication.getLogin())
        downloadHistoryEvent = DownloadHistoryEvent(context)
    }

    override fun download() {
        downloadAccountInfoEvent.download()
        downloadHistoryEvent.download()
    }
}
