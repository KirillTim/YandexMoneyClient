package im.kirillt.yandexmoneyclient.events.download

import com.yandex.money.api.methods.AccountInfo

data class SuccessAccountInfoEvent(val response: AccountInfo) : SuccessDownloadEvent
