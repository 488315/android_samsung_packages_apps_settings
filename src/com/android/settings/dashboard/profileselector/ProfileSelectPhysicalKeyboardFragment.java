package com.android.settings.dashboard.profileselector;

import android.hardware.input.InputDeviceIdentifier;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ProfileSelectPhysicalKeyboardFragment extends ProfileSelectFragment {
    public InputDeviceIdentifier mInputDeviceIdentifier;

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("input_device_identifier", this.mInputDeviceIdentifier);
        return ProfileSelectFragment.getFragments(
                getContext(),
                bundle,
                new ProfileSelectPhysicalKeyboardFragment$$ExternalSyntheticLambda0(),
                new ProfileSelectPhysicalKeyboardFragment$$ExternalSyntheticLambda0(),
                new ProfileSelectPhysicalKeyboardFragment$$ExternalSyntheticLambda0());
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.keyboard_settings_enabled_locales_list;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mInputDeviceIdentifier = getArguments().getParcelable("input_device_identifier");
    }
}
