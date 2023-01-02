package com.itechnotion.allinone.notification;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.onesignal.OneSignal;

/**
 * Created by admin on 2/10/2018.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        context = this;
    }
}
