//package im.kitillt.yandexmoneyclient.unit;
//
//import android.content.ContentResolver;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import org.joda.time.DateTime;
//import org.joda.time.DateTimeZone;
//import org.junit.AfterClass;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//
//import im.kirillt.yandexmoneyclient.MainActivity;
//import im.kirillt.yandexmoneyclient.events.download.DownloadHistoryEvent;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationContentValues;
//import im.kirillt.yandexmoneyclient.provider.operation.OperationSelection;
//import im.kirillt.yandexmoneyclient.utils.ResponseToContentValues;
//
//import static junit.framework.Assert.assertEquals;
//
//@RunWith(AndroidJUnit4.class)
//public class DownloadHistory {
//    @Rule
//    public ActivityTestRule<MainActivity> activityRule =
//            new ActivityTestRule<>(MainActivity.class);
//
//    @AfterClass
//    public void clearOperationsDB() {
//        new OperationSelection().operationidNot("").delete(activityRule.getActivity().getContentResolver());
//    }
//
//    @Test
//    public void getLastSavedOperationDateTest() {
//        ContentResolver contentResolver = activityRule.getActivity().getContentResolver();
//        new OperationSelection().operationidNot("").delete(contentResolver);
//        DateTime now = DateTime.now();
//        ResponseToContentValues.operation(DefaultOperationBuilder.incomingOperationBuilder.
//                setDatetime(now).createOperation()).insert(contentResolver);
//        ResponseToContentValues.operation(DefaultOperationBuilder.incomingOperationBuilder
//                .setTitle("test2").setDatetime(now.minusDays(1)).createOperation()).insert(contentResolver);
//        DownloadHistoryEvent event = new DownloadHistoryEvent(activityRule.getActivity());
//        try {
//            //Such reflection, very love it, WOW!
//            Method method = event.getClass().getDeclaredMethod("getLatestSavedOperationDate", new Class[]{});
//            DateTime result = (DateTime) method.invoke(event, (Object[])null);
//            assertEquals(now.toDateTime(DateTimeZone.getDefault()), result.toDateTime(DateTimeZone.getDefault()));
//        } catch (NoSuchMethodException e) { //can't use Exception1|Exception2|... e notation because of targetAPI
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
//}
