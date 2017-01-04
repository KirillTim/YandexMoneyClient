package im.kirillt.yandexmoneyclient.events.payment

import com.yandex.money.api.methods.RequestPayment

data class PaymentRequestResultEvent(val response: RequestPayment)
