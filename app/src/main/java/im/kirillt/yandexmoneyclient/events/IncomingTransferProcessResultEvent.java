package im.kirillt.yandexmoneyclient.events;

public class IncomingTransferProcessResultEvent {
    public String result;
    public boolean res;
    public IncomingTransferProcessResultEvent(String result, boolean res) {
        this.result = result;
        this.res = res;
    }
}
