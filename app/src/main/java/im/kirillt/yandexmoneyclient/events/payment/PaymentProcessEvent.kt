package im.kirillt.yandexmoneyclient.events.payment

import com.yandex.money.api.methods.ProcessPayment

import de.greenrobot.event.EventBus
import im.kirillt.yandexmoneyclient.YMCApplication
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent

class PaymentProcessEvent(internal val requestId: String) {

    fun process() {
        try {
            val response = YMCApplication.client.execute(ProcessPayment.Request(requestId))
            EventBus.getDefault().post(PaymentProcessResultEvent(response));
        } catch (e: Exception) {
            e.printStackTrace()
            EventBus.getDefault().post(AnyErrorEvent(e))
        }

    }
}
