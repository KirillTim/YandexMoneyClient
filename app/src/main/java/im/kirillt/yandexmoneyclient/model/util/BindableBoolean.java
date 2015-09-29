package im.kirillt.yandexmoneyclient.model.util;

import org.parceler.Parcel;

@Parcel
public class BindableBoolean extends BaseObservable {
    boolean value;

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        if (this.value != value) {
            this.value = value;
            notifyChange();
        }
    }
}