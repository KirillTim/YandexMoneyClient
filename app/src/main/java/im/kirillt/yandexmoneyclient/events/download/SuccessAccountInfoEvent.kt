package im.kirillt.yandexmoneyclient.events.download

import com.yandex.money.api.methods.AccountInfo

data class SuccessAccountInfoEvent(val login: String, val response: AccountInfo) : SuccessDownloadEvent
