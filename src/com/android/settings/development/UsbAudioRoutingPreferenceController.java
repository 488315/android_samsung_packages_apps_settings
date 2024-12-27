package com.android.settings.development;

import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsbAudioRoutingPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 1;
    public RestrictedSwitchPreference mPreference;

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference("usb_audio");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "usb_audio";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0);
        this.mPreference.setChecked(false);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchEnabled() {
        super.onDeveloperOptionsSwitchEnabled();
        this.mPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId()));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "usb_audio_automatic_routing_disabled",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        this.mPreference.setChecked(
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "usb_audio_automatic_routing_disabled",
                                0)
                        != 0);
        this.mPreference.setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfUsbDataSignalingIsDisabled(
                        this.mContext, UserHandle.myUserId()));
    }
}
