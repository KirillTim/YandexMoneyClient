package im.kitillt.yandexmoneyclient.unit;


import com.yandex.money.api.model.Operation;
import com.yandex.money.api.model.PayeeIdentifierType;

import org.joda.time.DateTime;

import java.math.BigDecimal;

public class DefaultOperationBuilder {
    public static DateTime sampleDateTime1 = DateTime.parse("2011-07-11T20:43:00.000+04:00");
    public static DateTime sampleDateTime2 = new DateTime(2015, 9, 28, 20, 40, 14);
    public static Operation.Builder incomingOperationBuilder = new Operation.Builder()
            .setOperationId("1234")
            .setStatus(Operation.Status.SUCCESS)
            .setDirection(Operation.Direction.INCOMING)
            .setAmount(new BigDecimal(100.00d))
            .setDatetime(sampleDateTime1)
            .setTitle("Title")
            .setSender("54321")
            .setComment("comment")
            .setType(Operation.Type.DEPOSITION)
            .setDetails("Details");

    public static Operation.Builder outgoingOperationBuilder = new Operation.Builder()
            .setOperationId("4567")
            .setStatus(Operation.Status.IN_PROGRESS)
            .setDirection(Operation.Direction.OUTGOING)
            .setAmount(new BigDecimal(10.0))
            .setAmountDue(new BigDecimal(9.95))
            .setFee(new BigDecimal(0.05))
            .setDatetime(sampleDateTime1)
            .setRecipient("1234")
            .setRecipientType(PayeeIdentifierType.EMAIL)
            .setMessage("msg")
            .setType(Operation.Type.OUTGOING_TRANSFER);

}
