package im.kirillt.yandexmoneyclient.provider.account;

import im.kirillt.yandexmoneyclient.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Account info
 */
public interface AccountModel extends BaseModel {

    /**
     * Get the {@code accountnumber} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getAccountnumber();

    /**
     * Get the {@code accountusername} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getAccountusername();

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    String getBalance();

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    String getBalancehold();

    /**
     * avatar image file location
     * Can be {@code null}.
     */
    @Nullable
    String getAvatar();
}
