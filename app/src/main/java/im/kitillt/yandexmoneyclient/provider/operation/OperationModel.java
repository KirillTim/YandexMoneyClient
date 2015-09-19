package im.kitillt.yandexmoneyclient.provider.operation;

import im.kitillt.yandexmoneyclient.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * P2P payment operation.
 */
public interface OperationModel extends BaseModel {

    /**
     * Get the {@code operationid} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getOperationid();

    /**
     * Get the {@code status} value.
     * Cannot be {@code null}.
     */
    @NonNull
    status getStatus();

    /**
     * Get the {@code direction} value.
     * Cannot be {@code null}.
     */
    @NonNull
    direction getDirection();

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    String getAmount();

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    String getAmountdue();

    /**
     * real type: BigDecimal
     * Cannot be {@code null}.
     */
    @NonNull
    String getFee();

    /**
     * unix time here, real type: DateTime
     */
    long getDatetime();

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code sender} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getSender();

    /**
     * Get the {@code recipient} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getRecipient();

    /**
     * Get the {@code payeeidentifiertype} value.
     * Cannot be {@code null}.
     */
    @NonNull
    payeeIdentifierType getPayeeidentifiertype();

    /**
     * Get the {@code message} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMessage();

    /**
     * Get the {@code comment} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getComment();

    /**
     * Get the {@code codepro} value.
     */
    boolean getCodepro();

    /**
     * Get the {@code protectioncode} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getProtectioncode();

    /**
     * unix time here, real type: DateTime
     */
    long getExpires();

    /**
     * unix time here, real type: DateTime
     */
    long getAnswerdatetime();

    /**
     * Get the {@code label} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getLabel();

    /**
     * Get the {@code details} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getDetails();

    /**
     * Get the {@code repeatable} value.
     */
    boolean getRepeatable();

    /**
     * Get the {@code favorite} value.
     */
    boolean getFavorite();

    /**
     * Get the {@code paymenttype} value.
     * Cannot be {@code null}.
     */
    @NonNull
    paymentType getPaymenttype();
}
