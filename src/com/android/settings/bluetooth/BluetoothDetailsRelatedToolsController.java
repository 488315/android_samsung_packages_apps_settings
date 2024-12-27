package com.android.settings.bluetooth;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsRelatedToolsController extends BluetoothDetailsController {
    public PreferenceCategory mPreferenceCategory;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_related_tools";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        if (this.mCachedDevice.isHearingAidDevice()) {
            this.mPreferenceCategory =
                    (PreferenceCategory) preferenceScreen.findPreference("bluetooth_related_tools");
            Preference findPreference = preferenceScreen.findPreference("live_caption");
            if (!findPreference.isVisible()) {
                this.mPreferenceCategory.removePreference(findPreference);
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getBluetoothFeatureProvider().getClass();
            if (this.mPreferenceCategory.getPreferenceCount() == 0) {
                preferenceScreen.removePreference(this.mPreferenceCategory);
            }
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mCachedDevice.isHearingAidDevice();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {}
}
