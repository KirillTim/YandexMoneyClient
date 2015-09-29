package im.kirillt.yandexmoneyclient.model.util;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import org.parceler.Parcel;
import org.parceler.Transient;

@Parcel
public class BaseObservable implements Observable {
    @Transient
    private PropertyChangeRegistry callbacks;

    public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
        if (this.callbacks == null) {
            this.callbacks = new PropertyChangeRegistry();
        }

        this.callbacks.add(listener);
    }

    public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
        if (this.callbacks != null) {
            this.callbacks.remove(listener);
        }
    }

    public synchronized void notifyChange() {
        if (this.callbacks != null) {
            this.callbacks.notifyCallbacks(this, 0, null);
        }
    }

    public void notifyPropertyChanged(int fieldId) {
        if (this.callbacks != null) {
            this.callbacks.notifyCallbacks(this, fieldId, null);
        }
    }
}
