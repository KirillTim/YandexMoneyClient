package im.kirillt.yandexmoneyclient.events.payment

import com.yandex.money.api.methods.RequestPayment
import com.yandex.money.api.methods.params.P2pTransferParams
import de.greenrobot.event.EventBus
import im.kirillt.yandexmoneyclient.YMCApplication
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent
import im.kirillt.yandexmoneyclient.model.PaymentInfo
import java.math.BigDecimal


class PaymentRequestEvent(model: PaymentInfo) {
    private val p2pTransferParams: P2pTransferParams

    init {
        val builder = P2pTransferParams.Builder(model.who.get())
                .setAmountDue(BigDecimal(model.amountTotal.get()))
                .setMessage(model.message.get())
                .setLabel("Alternative Yandex Money Client")
        if (model.codePro.get()) {
            builder.setCodepro(true)
            builder.setExpirePeriod(Integer.valueOf(model.codeproTime.get()))
        }
        this.p2pTransferParams = builder.create()
    }

    fun request() {
        try {
            val response = YMCApplication.client.execute(RequestPayment.Request.newInstance(p2pTransferParams))
            EventBus.getDefault().post(PaymentRequestResultEvent(response));
        } catch (e: Exception) {
            e.printStackTrace()
            EventBus.getDefault().post(AnyErrorEvent(e))
        }

    }
}
