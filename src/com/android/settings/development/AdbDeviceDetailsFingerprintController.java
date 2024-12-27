package com.android.settings.development;

import android.debug.PairDevice;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.FooterPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbDeviceDetailsFingerprintController extends AbstractPreferenceController {
    static final String KEY_FINGERPRINT_CATEGORY = "fingerprint_category";
    public PreferenceCategory mFingerprintCategory;
    public FooterPreference mFingerprintPref;
    public PairDevice mPairedDevice;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(KEY_FINGERPRINT_CATEGORY);
        this.mFingerprintCategory = preferenceCategory;
        this.mFingerprintPref = new FooterPreference(preferenceCategory.getContext());
        this.mFingerprintPref.setTitle(
                String.format(
                        this.mContext
                                .getText(R.string.adb_device_fingerprint_title_format)
                                .toString(),
                        this.mPairedDevice.guid));
        this.mFingerprintCategory.addPreference(this.mFingerprintPref);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_FINGERPRINT_CATEGORY;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
