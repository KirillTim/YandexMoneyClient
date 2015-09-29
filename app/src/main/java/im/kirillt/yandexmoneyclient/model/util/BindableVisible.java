package im.kirillt.yandexmoneyclient.model.util;

import android.view.View;

public class BindableVisible extends BaseObservable {
    int value;
    public int get() {return  value;}
    public void set(int value) {
        if (value == View.VISIBLE || value == View.INVISIBLE || value == View.GONE) {
            if (this.value != value) {
                this.value = value;
                notifyChange();
            }
        } else {
            throw new IllegalArgumentException("not a View.visability value");
        }
    }
}
