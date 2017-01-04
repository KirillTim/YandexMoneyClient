//package im.kitillt.yandexmoneyclient.unit;
//
//
//import android.content.ContentResolver;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.yandex.money.api.model.Operation;
//import com.yandex.money.api.model.PayeeIdentifierType;
//
//import org.joda.time.DateTime;
//import org.joda.time.DateTimeZone;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.math.BigDecimal;
//
//import im.kirillt.yandexmoneyclient.MainActivity;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationContentValues;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationCursor;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection;
//import im.kirillt.yandexmoneyclient.provider.operation.direction;
//import im.kirillt.yandexmoneyclient.provider.operation.payeeIdentifierType;
//import im.kirillt.yandexmoneyclient.provider.operation.paymentType;
//import im.kirillt.yandexmoneyclient.provider.operation.status;
//import im.kirillt.yandexmoneyclient.utils.ResponseToContentValues;
//
//
//import static junit.framework.Assert.assertEquals;
//
//@RunWith(AndroidJUnit4.class)
//public class OperationToCursor {
//    @Rule
//    public ActivityTestRule<MainActivity> activityRule =
//            new ActivityTestRule<>(MainActivity.class);
//
//
//    @Before
//    public void cleanOperationsTable() {
//        ContentResolver contentResolver = activityRule.getActivity().getContentResolver();
//        new OperationSelection().operationidNot("").delete(contentResolver);
//    }
//
//    @Test
//    public void incomingOperationToCursorTest() {
//        Operation incomingOperation = DefaultOperationBuilder.incomingOperationBuilder.createOperation();
//        ContentResolver contentResolver = activityRule.getActivity().getContentResolver();
//        OperationContentValues contentValues = ResponseToContentValues.operation(incomingOperation);
//        contentValues.insert(contentResolver);
//        OperationCursor cursor = new OperationSelection().operationid(incomingOperation.operationId).query(contentResolver);
//        cursor.moveToFirst();
//        assertEquals(cursor.getOperationid(), incomingOperation.operationId);
//        assertEquals(cursor.getStatus(), status.SUCCESS);
//        assertEquals(cursor.getDirection(), direction.INCOMING);
//        assertEquals(new BigDecimal(cursor.getAmount()), incomingOperation.amount);
//        System.out.println(new DateTime(cursor.getDatetime()).toString());
//        System.out.println("long: " + cursor.getDatetime());
//        assertEquals(new DateTime(cursor.getDatetime()).toDateTime(DateTimeZone.getDefault()), incomingOperation.datetime.toDateTime(DateTimeZone.getDefault()));
//        assertEquals(cursor.getTitle(), incomingOperation.title);
//        assertEquals(cursor.getSender(), incomingOperation.sender);
//        assertEquals(cursor.getRecipient(), "");
//        assertEquals(cursor.getMessage(), "");
//        assertEquals(cursor.getComment(), incomingOperation.comment);
//        assertEquals(cursor.getPaymenttype(), paymentType.DEPOSITION);
//        assertEquals(cursor.getDetails(), incomingOperation.details);
//        assertEquals(cursor.getCodepro(), false);
//    }
//
//    @Test
//    public void incomingOperationWithProtectionCodeToCursorTest() {
//        ContentResolver contentResolver = activityRule.getActivity().getContentResolver();
//        Operation codeProOperation = DefaultOperationBuilder.incomingOperationBuilder
//                .setCodepro(true)
//                .setProtectionCode("1234")
//                .setExpires(DefaultOperationBuilder.sampleDateTime2)
//                .createOperation();
//        OperationContentValues operationContentValues = ResponseToContentValues.operation(codeProOperation);
//        operationContentValues.insert(contentResolver);
//        OperationCursor cursor = new OperationSelection().operationid(codeProOperation.operationId).query(contentResolver);
//        cursor.moveToFirst();
//        assertEquals(cursor.getCodepro(), true);
//        assertEquals(cursor.getProtectioncode(), codeProOperation.protectionCode);
//        assertEquals(new DateTime(cursor.getExpires()).toDateTime(DateTimeZone.getDefault()), codeProOperation.expires.toDateTime(DateTimeZone.getDefault()));
//        assertEquals(cursor.getAnswerdatetime(), null);
//
//    }
//
//    @Test
//    public void outgoingOperationTest() {
//        ContentResolver contentResolver = activityRule.getActivity().getContentResolver();
//        Operation operation = DefaultOperationBuilder.outgoingOperationBuilder.createOperation();
//        OperationContentValues operationContentValues = ResponseToContentValues.operation(operation);
//        operationContentValues.insert(contentResolver);
//        OperationCursor cursor = new OperationSelection().operationid(operation.operationId).query(contentResolver);
//        assertEquals(new BigDecimal(cursor.getAmount()), operation.amount);
//        assertEquals(new BigDecimal(cursor.getAmountdue()), operation.amountDue);
//        assertEquals(new BigDecimal(cursor.getFee()), operation.fee);
//        assertEquals(cursor.getRecipient(), operation.recipient);
//        assertEquals(cursor.getMessage(), operation.message);
//        assertEquals(cursor.getPayeeidentifiertype(), payeeIdentifierType.EMAIL);
//        assertEquals(cursor.getCodepro(), false);
//    }
//
//}
