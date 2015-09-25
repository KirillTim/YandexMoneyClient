package im.kirillt.yandexmoneyclient.events.payment;

import com.yandex.money.api.methods.ProcessPayment;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import im.kirillt.yandexmoneyclient.YMCApplication;
import im.kirillt.yandexmoneyclient.events.AnyErrorEvent;
import im.kirillt.yandexmoneyclient.utils.ResponseReady;

public class PaymentProcessEvent {
    final String requestId;

    public PaymentProcessEvent(String requestId) {
        this.requestId = requestId;
    }

    public void process() {
        try {
            YMCApplication.auth2Session.enqueue(new ProcessPayment.Request(requestId), new ResponseReady<ProcessPayment>() {
                @Override
                protected void failure(Exception exception) {
                    EventBus.getDefault().post(new AnyErrorEvent(exception));
                }

                @Override
                protected void response(ProcessPayment response) {
                    EventBus.getDefault().post(new PaymentProcessResultEvent(response));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            EventBus.getDefault().post(new AnyErrorEvent(e));
        }
    }
}
