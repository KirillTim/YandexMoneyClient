package im.kirillt.yandexmoneyclient.events.payment

import com.yandex.money.api.methods.ProcessPayment

data class PaymentProcessResultEvent(val response: ProcessPayment)
