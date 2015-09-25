package im.kirillt.yandexmoneyclient.events.payment;

import com.yandex.money.api.methods.ProcessPayment;

public class PaymentProcessResultEvent {
    public final ProcessPayment response;

    public PaymentProcessResultEvent(ProcessPayment response) {
        this.response = response;
    }
}
