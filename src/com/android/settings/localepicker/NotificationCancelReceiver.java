package com.android.settings.localepicker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NotificationCancelReceiver extends BroadcastReceiver {
    public NotificationController getNotificationController(Context context) {
        return NotificationController.getInstance(context);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String string = intent.getExtras().getString("app_locale");
        int i = intent.getExtras().getInt("notification_id", -1);
        NotificationInfo notificationInfo =
                getNotificationController(context).mDataManager.getNotificationInfo(string);
        int i2 = notificationInfo != null ? notificationInfo.mNotificationId : -1;
        Log.i("NotificationCancelReceiver", "Locale notification is swiped away.");
        if (i2 == i) {
            LocaleNotificationDataManager localeNotificationDataManager =
                    getNotificationController(context).mDataManager;
            NotificationInfo notificationInfo2 =
                    localeNotificationDataManager.getNotificationInfo(string);
            localeNotificationDataManager.putNotificationInfo(
                    string,
                    new NotificationInfo(
                            notificationInfo2.mUidCollection,
                            notificationInfo2.mNotificationCount,
                            notificationInfo2.mDismissCount + 1,
                            notificationInfo2.mLastNotificationTimeMs,
                            notificationInfo2.mNotificationId));
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(context, 1897, new Pair[0]);
        }
    }
}
