package com.android.settings.bluetooth;

import android.companion.CompanionDeviceManager;
import android.content.pm.PackageManager;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsCompanionAppsController extends BluetoothDetailsController {
    CompanionDeviceManager mCompanionDeviceManager;
    PackageManager mPackageManager;
    PreferenceCategory mProfilesContainer;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "device_companion_apps";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("device_companion_apps");
        this.mProfilesContainer = preferenceCategory;
        preferenceCategory.setLayoutResource(R.layout.preference_companion_app);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {}
}
