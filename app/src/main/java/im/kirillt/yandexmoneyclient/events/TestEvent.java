package im.kirillt.yandexmoneyclient.events;

import android.util.Log;

/**
 * Created by kirill on 25.09.15.
 */
public class TestEvent {
    public void sleepTest() {
        for (int i = 0; i < 30; i++) {
            Log.i("sleepTest", i+"");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
