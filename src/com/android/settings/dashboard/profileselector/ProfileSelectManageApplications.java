package com.android.settings.dashboard.profileselector;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.settings.applications.manageapplications.ManageApplications;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectManageApplications extends ProfileSelectFragment {
    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        return ProfileSelectFragment.getFragments(
                getContext(),
                getArguments(),
                new ProfileSelectManageApplications$$ExternalSyntheticLambda0(),
                new ProfileSelectManageApplications$$ExternalSyntheticLambda0(),
                new ProfileSelectManageApplications$$ExternalSyntheticLambda0());
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final int getTitleResId() {
        return ManageApplications.getTitleResId(getActivity().getIntent(), getArguments());
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return ManageApplications.getTopLevelPreferenceKey(
                getActivity().getIntent(), getArguments());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        enableAutoFlowLogging(false);
        super.onCreate(bundle);
    }
}
