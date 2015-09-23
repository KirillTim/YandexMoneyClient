package im.kirillt.yandexmoneyclient.provider.operation;

import im.kirillt.yandexmoneyclient.provider.base.BaseModel;

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
     * Can be {@code null}.
     */
    @Nullable
    String getAmountdue();

    /**
     * real type: BigDecimal
     * Can be {@code null}.
     */
    @Nullable
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
     * Can be {@code null}.
     */
    @Nullable
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
     * Can be {@code null}.
     */
    @Nullable
    String getProtectioncode();

    /**
     * unix time here, real type: DateTime
     * Can be {@code null}.
     */
    @Nullable
    Long getExpires();

    /**
     * unix time here, real type: DateTime
     * Can be {@code null}.
     */
    @Nullable
    Long getAnswerdatetime();

    /**
     * Get the {@code label} value.
     * Can be {@code null}.
     */
    @Nullable
    String getLabel();

    /**
     * Get the {@code details} value.
     * Can be {@code null}.
     */
    @Nullable
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
     * only *_TRANSFER_* values is used
     * Cannot be {@code null}.
     */
    @NonNull
    paymentType getPaymenttype();
}
