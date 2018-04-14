package agency.vavien.vavientav;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Sedat
 * on 14.04.2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

}
