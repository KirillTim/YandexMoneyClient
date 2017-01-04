package im.kirillt.yandexmoneyclient.events

data class WebAuthResultEvent(val login: String, val token: String, val errorDescription: String)