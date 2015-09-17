package im.kitillt.yandexmoneyclient;

import com.yandex.money.api.model.Error;

/**
 * Created by kirill on 17.09.15.
 */
public class AuthException extends Exception{
    public AuthException(Error authError) {
        super(authError.name());
    }
}
