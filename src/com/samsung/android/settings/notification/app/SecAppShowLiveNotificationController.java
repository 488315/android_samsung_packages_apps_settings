package com.samsung.android.settings.notification.app;

import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecAppShowLiveNotificationController extends NotificationPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public NotificationBackend mBackend;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "app_show_live_notification";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        List arrayList;
        if (!super.isAvailable() || "com.samsung.android.app.aodservice".equals(this.mAppRow.pkg)) {
            return false;
        }
        this.mBackend.getClass();
        Log.d("NotificationBackend", "getAllowedOngoingActivityAppList:");
        try {
            arrayList = NotificationBackend.sINM.getAllowedOngoingActivityAppList();
        } catch (Exception e) {
            Log.d("NotificationBackend", "exception in getAllowedOngoingActivityAppList: ", e);
            arrayList = new ArrayList();
        }
        boolean contains = arrayList.contains(this.mAppRow.pkg);
        Log.d(
                "SecAppShowLiveNotificationController",
                " allowed to show for: " + this.mAppRow.pkg + ": " + contains);
        return contains;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (this.mBackend == null) {
            return true;
        }
        Log.d("SecAppShowLiveNotificationController", " new value: " + obj);
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i, "setAllowOngoingActivity: pkg=", str, " uid=", " enabled="),
                booleanValue,
                "NotificationBackend");
        try {
            NotificationBackend.sINM.setAllowOngoingActivity(str, i, booleanValue);
        } catch (Exception e) {
            Log.d("NotificationBackend", "exception in set live notification: ", e);
        }
        StringBuilder sb = new StringBuilder(" pkg: ");
        sb.append(this.mAppRow.pkg);
        sb.append(": isOngoingActivityAllowed: ");
        NotificationBackend.AppRow appRow2 = this.mAppRow;
        sb.append(NotificationBackend.isOngoingActivityAllowed(appRow2.uid, appRow2.pkg));
        Log.d("SecAppShowLiveNotificationController", sb.toString());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        ((SwitchPreferenceCompat) preference)
                .setChecked(NotificationBackend.isOngoingActivityAllowed(i, str));
    }
}
