package im.kirillt.yandexmoneyclient.events.payment;

import com.yandex.money.api.methods.RequestPayment;
import com.yandex.money.api.methods.params.P2pTransferParams;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.fragments.PaymentFragment;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class PaymentRequestEvent {
    private PaymentFragment.PaymentModel model;
    private P2pTransferParams p2pTransferParams;

    public PaymentRequestEvent(PaymentFragment.PaymentModel model) {
        this.model = model;
        this.p2pTransferParams = new P2pTransferParams.Builder(model.who)
                //.setAmount(model.toBePaid)
                .setAmountDue(model.total)
                .setMessage(model.message)
                .setComment(model.comment)
                .setLabel(model.label)
                .build();
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
