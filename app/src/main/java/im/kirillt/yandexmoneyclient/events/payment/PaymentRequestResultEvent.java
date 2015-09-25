package im.kirillt.yandexmoneyclient.events.payment;

import com.yandex.money.api.methods.RequestPayment;

public class PaymentRequestResultEvent {
    public final RequestPayment response;

    public PaymentRequestResultEvent(RequestPayment response) {
        this.response = response;
    }

}
