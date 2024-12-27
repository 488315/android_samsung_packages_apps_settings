package com.samsung.android.settings.notification;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecAppAlertRadioPreferenceController extends NotificationPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final NotificationSettings.DependentFieldListener mDependentFieldListener;
    public final Vibrator mVibrator;

    public SecAppAlertRadioPreferenceController(
            Context context,
            NotificationBackend notificationBackend,
            NotificationSettings.DependentFieldListener dependentFieldListener) {
        super(context, notificationBackend);
        this.mDependentFieldListener = dependentFieldListener;
        this.mVibrator = (Vibrator) context.getSystemService("vibrator");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "app_alert_importance";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return super.isAvailable();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        NotificationBackend.AppRow appRow = this.mAppRow;
        String str = appRow.pkg;
        int i = appRow.uid;
        this.mBackend.getClass();
        boolean notificationAlertsEnabledForPackage =
                NotificationBackend.getNotificationAlertsEnabledForPackage(i, str);
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onPreferenceChange alerts enabled :",
                "PrefControllerMixin",
                notificationAlertsEnabledForPackage);
        boolean z = !notificationAlertsEnabledForPackage;
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i,
                        "setNotificationAlertsEnabledForPackage: pkg=",
                        str,
                        " uid=",
                        " enabled="),
                z,
                "NotificationBackend");
        try {
            NotificationBackend.sINM.setNotificationAlertsEnabledForPackage(str, i, z);
        } catch (Exception e) {
            Log.d("NotificationBackend", "exception in set alerts: ", e);
        }
        this.mDependentFieldListener.onFieldValueChanged();
        if (booleanValue) {
            HashMap hashMap = new HashMap();
            hashMap.put("setting", this.mAppRow.pkg + ";sound");
            SALogging.insertSALog(Integer.toString(36024), "NSTE0024", hashMap, 0);
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        NotificationBackend.AppRow appRow = this.mAppRow;
        if (appRow != null) {
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            boolean notificationAlertsEnabledForPackage =
                    NotificationBackend.getNotificationAlertsEnabledForPackage(i, str);
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "updateState alerts enabled :",
                    "PrefControllerMixin",
                    notificationAlertsEnabledForPackage);
            SwitchPreference switchPreference = (SwitchPreference) preference;
            switchPreference.setChecked(notificationAlertsEnabledForPackage);
            Vibrator vibrator = this.mVibrator;
            if (vibrator == null || vibrator.hasVibrator()) {
                return;
            }
            switchPreference.setTitle(
                    R.string.sec_app_notification_importance_radio_alert_allow_sound_title);
        }
    }
}
