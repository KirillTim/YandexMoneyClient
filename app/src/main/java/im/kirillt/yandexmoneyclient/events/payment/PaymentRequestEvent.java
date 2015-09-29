package im.kirillt.yandexmoneyclient.events.payment;

import com.yandex.money.api.methods.RequestPayment;
import com.yandex.money.api.methods.params.P2pTransferParams;

import java.io.IOException;
import java.math.BigDecimal;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.model.PaymentInfo;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class PaymentRequestEvent {
    private PaymentInfo model;
    private P2pTransferParams p2pTransferParams;

    public PaymentRequestEvent(PaymentInfo model) {
        this.model = model;
        P2pTransferParams.Builder builder = new P2pTransferParams.Builder(model.who.get())
                .setAmountDue(new BigDecimal(model.amountTotal.get()))
                .setMessage(model.message.get())
                .setLabel("Alternative Yandex Money Client");
        if (model.codePro.get()) {
            builder.setCodepro(true);
            builder.setExpirePeriod(Integer.valueOf(model.codeproTime.get()));
        }
        this.p2pTransferParams = builder.build();
    }

    public void request() {
        try {
            YMCApplication.auth2Session.enqueue(RequestPayment.Request.newInstance(p2pTransferParams), new ResponseReady<RequestPayment>() {
                @Override
                protected void failure(Exception exception) {
                    exception.printStackTrace();
                    EventBus.getDefault().post(new AnyErrorEvent(exception));
                }

                @Override
                protected void response(RequestPayment response) {
                    EventBus.getDefault().post(new PaymentRequestResultEvent(response));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }
}
