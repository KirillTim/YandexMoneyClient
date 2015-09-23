package im.kitillt.yandexmoneyclient.events.result;

/**
 * Created by kirill on 20.09.15.
 */
public class AnyErrorEvent {
    private Exception exception;

    public AnyErrorEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        return exception.getLocalizedMessage();
    }
}
