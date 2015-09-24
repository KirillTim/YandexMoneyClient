package im.kirillt.yandexmoneyclient.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by kirill on 24.09.15.
 */
public abstract class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //do nothing
    }

    @Override
    abstract public void afterTextChanged(Editable editable);
}
