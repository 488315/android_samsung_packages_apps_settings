package com.android.settings.bluetooth;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityHearingAidsFragment;
import com.android.settings.accessibility.ArrowPreference;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsHearingDeviceSettingsController
        extends BluetoothDetailsController implements Preference.OnPreferenceClickListener {
    static final String KEY_HEARING_DEVICE_SETTINGS = "hearing_device_settings";

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_HEARING_DEVICE_SETTINGS;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        if (this.mCachedDevice.isHearingAidDevice()) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) preferenceScreen.findPreference("hearing_device_group");
            Context context = preferenceCategory.getContext();
            ArrowPreference arrowPreference = new ArrowPreference(context);
            arrowPreference.setKey(KEY_HEARING_DEVICE_SETTINGS);
            arrowPreference.setOrder(1);
            arrowPreference.setTitle(
                    context.getString(R.string.bluetooth_hearing_device_settings_title));
            arrowPreference.setSummary(
                    context.getString(R.string.bluetooth_hearing_device_settings_summary));
            arrowPreference.setOnPreferenceClickListener(this);
            preferenceCategory.addPreference(arrowPreference);
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mCachedDevice.isHearingAidDevice();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), KEY_HEARING_DEVICE_SETTINGS)) {
            return false;
        }
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(((BluetoothDetailsController) this).mContext);
        String name = AccessibilityHearingAidsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        ((BluetoothDeviceDetailsFragment) this.mFragment).getClass();
        launchRequest.mSourceMetricsCategory =
                EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {}
}
