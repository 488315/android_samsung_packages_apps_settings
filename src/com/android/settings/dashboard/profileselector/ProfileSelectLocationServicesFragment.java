package com.android.settings.dashboard.profileselector;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.location.LocationServices;
import com.android.settings.location.LocationServicesForPrivateProfile;
import com.android.settings.location.LocationServicesForWork;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ProfileSelectLocationServicesFragment extends ProfileSelectFragment {
    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment
    public final Fragment[] getFragments() {
        final int i = 0;
        final int i2 = 1;
        final int i3 = 2;
        return ProfileSelectFragment.getFragments(
                getContext(),
                null,
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationServicesFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i) {
                            case 0:
                                return new LocationServices();
                            case 1:
                                return new LocationServicesForWork();
                            default:
                                return new LocationServicesForPrivateProfile();
                        }
                    }
                },
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationServicesFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i2) {
                            case 0:
                                return new LocationServices();
                            case 1:
                                return new LocationServicesForWork();
                            default:
                                return new LocationServicesForPrivateProfile();
                        }
                    }
                },
                new ProfileSelectFragment
                        .FragmentConstructor() { // from class:
                                                 // com.android.settings.dashboard.profileselector.ProfileSelectLocationServicesFragment$$ExternalSyntheticLambda0
                    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment.FragmentConstructor
                    public final Fragment constructAndGetFragment() {
                        switch (i3) {
                            case 0:
                                return new LocationServices();
                            case 1:
                                return new LocationServicesForWork();
                            default:
                                return new LocationServicesForPrivateProfile();
                        }
                    }
                });
    }

    @Override // com.android.settings.dashboard.profileselector.ProfileSelectFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.location_services_header;
    }
}
