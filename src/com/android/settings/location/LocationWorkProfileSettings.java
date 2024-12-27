package com.android.settings.location;

import android.content.Context;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationWorkProfileSettings extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LocationWorkProfile";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 11001;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_location_settings_workprofile;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AppLocationPermissionPreferenceController)
                        use(AppLocationPermissionPreferenceController.class))
                .init(this);
        ((LocationForWorkPreferenceController) use(LocationForWorkPreferenceController.class))
                .init(this);
        ((RecentLocationAccessSeeAllButtonPreferenceController)
                        use(RecentLocationAccessSeeAllButtonPreferenceController.class))
                .init(this);
        int i = getArguments().getInt(ImsProfile.SERVICE_PROFILE);
        RecentLocationAccessPreferenceController recentLocationAccessPreferenceController =
                (RecentLocationAccessPreferenceController)
                        use(RecentLocationAccessPreferenceController.class);
        recentLocationAccessPreferenceController.init(this);
        recentLocationAccessPreferenceController.setProfileType(i);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        replaceEnterpriseStringTitle(
                "managed_profile_location_switch",
                "Settings.WORK_PROFILE_LOCATION_SWITCH_TITLE",
                R.string.managed_profile_location_switch_title);
    }
}
