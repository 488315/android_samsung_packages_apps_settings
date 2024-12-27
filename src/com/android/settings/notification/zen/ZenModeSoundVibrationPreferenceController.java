package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeSoundVibrationPreferenceController
        extends AbstractZenModePreferenceController {
    public final String mKey;
    public final ZenModeSettings.SummaryBuilder mSummaryBuilder;

    public ZenModeSoundVibrationPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_sound_vibration_settings", lifecycle);
        this.mKey = "zen_sound_vibration_settings";
        this.mSummaryBuilder = new ZenModeSettings.SummaryBuilder(context);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mKey;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        NotificationManager.Policy notificationPolicy =
                this.mNotificationManager.getNotificationPolicy();
        ZenModeSettings.SummaryBuilder summaryBuilder = this.mSummaryBuilder;
        summaryBuilder.getClass();
        ArrayList arrayList =
                (ArrayList)
                        summaryBuilder.getEnabledCategories(
                                notificationPolicy,
                                new ZenModeSettings$SummaryBuilder$$ExternalSyntheticLambda0(1));
        int size = arrayList.size();
        return size == 0
                ? summaryBuilder.mContext.getString(R.string.sec_zen_people_summery_allow_none)
                : size == 5
                        ? summaryBuilder.mContext.getString(
                                R.string.sec_zen_people_summery_allow_all)
                        : size == 1
                                ? summaryBuilder.mContext.getString(
                                        R.string.sec_zen_items_one_allowed, arrayList.get(0))
                                : size == 2
                                        ? summaryBuilder.mContext.getString(
                                                R.string.sec_zen_items_two_allowed,
                                                arrayList.get(0),
                                                arrayList.get(1))
                                        : size > 2
                                                ? summaryBuilder.mContext.getString(
                                                        R.string.sec_zen_items_more_allowed,
                                                        arrayList.get(0),
                                                        arrayList.get(1),
                                                        Integer.valueOf(size - 2))
                                                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SecPreference secPreference = (SecPreference) preference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
    }
}
