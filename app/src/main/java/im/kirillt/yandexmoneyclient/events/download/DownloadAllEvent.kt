package im.kirillt.yandexmoneyclient.events.download

import android.content.Context


class DownloadAllEvent(context: Context) : DownloadEvent {
    private val downloadAccountInfoEvent: DownloadAccountInfoEvent
    private val downloadHistoryEvent: DownloadHistoryEvent

    init {
        downloadAccountInfoEvent = DownloadAccountInfoEvent(context)
        downloadHistoryEvent = DownloadHistoryEvent(context)
    }

    override fun download() {
        downloadAccountInfoEvent.download()
        downloadHistoryEvent.download()
    }
}
