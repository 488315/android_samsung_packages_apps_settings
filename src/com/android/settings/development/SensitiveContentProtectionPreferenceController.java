package com.android.settings.development;

import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SensitiveContentProtectionPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "disable_screen_share_protections_for_apps_and_notifications";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        Flags.sensitiveNotificationAppProtection();
        return true;
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "disable_screen_share_protections_for_apps_and_notifications",
                0);
        Preference preference = this.mPreference;
        Intrinsics.checkNotNull(
                preference,
                "null cannot be cast to non-null type androidx.preference.TwoStatePreference");
        ((TwoStatePreference) preference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "disable_screen_share_protections_for_apps_and_notifications",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        int i =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "disable_screen_share_protections_for_apps_and_notifications",
                        0);
        Preference preference2 = this.mPreference;
        Intrinsics.checkNotNull(
                preference2,
                "null cannot be cast to non-null type androidx.preference.TwoStatePreference");
        ((TwoStatePreference) preference2).setChecked(i != 0);
    }
}
