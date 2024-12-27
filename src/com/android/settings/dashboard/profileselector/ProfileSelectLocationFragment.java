package com.android.settings.dashboard.profileselector;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.location.LocationPersonalSettings;
import com.android.settings.location.LocationSwitchBarController;
import com.android.settings.location.LocationWorkProfileSettings;
import com.android.settings.widget.SettingsMainSwitchBar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectLocationFragment extends ProfileSelectFragment {
    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.location_settings_title;
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        final int i = 0;
        final int i2 = 1;
        final int i3 = 0;
        return ProfileSelectFragment.getFragments(
                getContext(),
                null,
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i) {
                            case 0:
                                return new LocationPersonalSettings();
                            default:
                                return new LocationWorkProfileSettings();
                        }
                    }
                },
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i2) {
                            case 0:
                                return new LocationPersonalSettings();
                            default:
                                return new LocationWorkProfileSettings();
                        }
                    }
                },
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i3) {
                            case 0:
                                return new LocationPersonalSettings();
                            default:
                                return new LocationWorkProfileSettings();
                        }
                    }
                });
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return TopLevelSettings.class.getName();
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.location_settings_header;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_location";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        new LocationSwitchBarController(
                settingsActivity, settingsMainSwitchBar, getSettingsLifecycle());
        settingsMainSwitchBar.show();
    }
}
