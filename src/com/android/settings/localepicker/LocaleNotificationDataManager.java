package com.android.settings.localepicker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocaleNotificationDataManager {
    public Context mContext;

    public void clearLocaleNotificationMap() {
        this.mContext.getSharedPreferences("locale_notification", 0).edit().clear().apply();
    }

    public final NotificationInfo getNotificationInfo(String str) {
        Gson gson = new Gson();
        String string =
                this.mContext
                        .getSharedPreferences("locale_notification", 0)
                        .getString(str, ApnSettings.MVNO_NONE);
        if (string.isEmpty()) {
            return null;
        }
        return (NotificationInfo) gson.fromJson(string, NotificationInfo.class);
    }

    public final void putNotificationInfo(String str, NotificationInfo notificationInfo) {
        String json = new Gson().toJson(notificationInfo);
        SharedPreferences.Editor edit =
                this.mContext.getSharedPreferences("locale_notification", 0).edit();
        edit.putString(str, json);
        edit.apply();
    }
}
