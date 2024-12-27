package com.android.settings.development;

import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultUsbConfigurationPreferenceController
        extends DeveloperOptionsPreferenceController implements PreferenceControllerMixin {
    public RestrictedPreference mPreference;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedPreference) preferenceScreen.findPreference("default_usb_configuration");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "default_usb_configuration";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        this.mPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId()));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId()));
    }
}
