package com.android.settings.notification.app;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.settings.widget.SecButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DeepSleepingPreferenceController extends NotificationPreferenceController {
    public Context mContext;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "deep_sleeping";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SecButtonPreference secButtonPreference = (SecButtonPreference) preference;
        secButtonPreference.mOnClickListener =
                new View.OnClickListener() { // from class:
                    // com.android.settings.notification.app.DeepSleepingPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DeepSleepingPreferenceController deepSleepingPreferenceController =
                                DeepSleepingPreferenceController.this;
                        deepSleepingPreferenceController.getClass();
                        Intent intent = new Intent();
                        intent.setAction(
                                "com.samsung.android.sm.ACTION_OPEN_CHECKABLE_LISTACTIVITY");
                        intent.setPackage(
                                SemFloatingFeature.getInstance()
                                        .getString(
                                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                                                "com.samsung.android.lool"));
                        intent.putExtra("activity_type", 1);
                        deepSleepingPreferenceController.mContext.startActivity(intent);
                    }
                };
        secButtonPreference.mButtonTextColor = EmergencyPhoneWidget.BG_COLOR;
        secButtonPreference.mTitle =
                this.mContext.getString(R.string.sec_app_notification_manage_deep_sleeping_apps);
        secButtonPreference.mButtonBackGroundRes = R.drawable.sec_deep_sleeping_bg;
    }
}
