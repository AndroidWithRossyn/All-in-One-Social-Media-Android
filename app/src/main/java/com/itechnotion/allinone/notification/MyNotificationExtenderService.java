package com.itechnotion.allinone.notification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.itechnotion.allinone.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import java.math.BigInteger;

/**
 * Created by admin on 2/12/2018.
 */

public class MyNotificationExtenderService extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {


            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Red on Android 5.0+ devices.
                Bitmap icon = BitmapFactory.decodeResource(MyApplication.context.getResources(),
                        R.mipmap.ic_launcher);
                builder.setLargeIcon(icon);
                return builder.setColor(new BigInteger("9FE20B", 16).intValue());
            }
        };

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return true;
    }
}
