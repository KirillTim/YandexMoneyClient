package im.kitillt.yandexmoneyclient.provider.operation;

/**
 * Possible values for the {@code paymenttype} column of the {@code operation} table.
 */
public enum paymentType {
    /**
     * 
     */
    PAYMENT_SHOP,

    /**
     * 
     */
    OUTGOING_TRANSFER,

    /**
     * 
     */
    INCOMING_TRANSFER,

    /**
     * 
     */
    INCOMING_TRANSFER_PROTECTED,

    /**
     * 
     */
    DEPOSITION,

    /**
     * 
     */
    UNKNOWN,

}