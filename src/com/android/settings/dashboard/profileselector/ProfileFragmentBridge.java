package com.android.settings.dashboard.profileselector;

import android.util.ArrayMap;

import com.android.settings.accounts.AccountDashboardFragment;
import com.android.settings.applications.manageapplications.ManageApplications;
import com.android.settings.deviceinfo.StorageDashboardFragment;
import com.android.settings.inputmethod.AvailableVirtualKeyboardFragment;
import com.android.settings.inputmethod.NewKeyboardLayoutEnabledLocalesFragment;
import com.android.settings.location.LocationServices;

import com.samsung.android.settings.general.AutofillPicker;
import com.samsung.android.settings.usefulfeature.intelligenceservice.IntelligenceServiceSettings;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ProfileFragmentBridge {
    public static final Map FRAGMENT_MAP;

    static {
        ArrayMap arrayMap = new ArrayMap();
        FRAGMENT_MAP = arrayMap;
        arrayMap.put(
                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                        ProfileSelectPhysicalKeyboardFragment.class,
                        arrayMap,
                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                ProfileSelectKeyboardFragment.class,
                                arrayMap,
                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                        ProfileSelectStorageFragment.class,
                                        arrayMap,
                                        ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                ProfileSelectLocationServicesFragment.class,
                                                arrayMap,
                                                ProfileFragmentBridge$$ExternalSyntheticOutline0.m(
                                                        ProfileSelectManageApplications.class,
                                                        arrayMap,
                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                .m(
                                                                        ProfileSelectGalaxyAI.class,
                                                                        arrayMap,
                                                                        ProfileFragmentBridge$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        ProfileSelectAccountFragment
                                                                                                .class,
                                                                                        arrayMap,
                                                                                        AccountDashboardFragment
                                                                                                .class
                                                                                                .getName(),
                                                                                        IntelligenceServiceSettings
                                                                                                .class),
                                                                        ManageApplications.class),
                                                        LocationServices.class),
                                                StorageDashboardFragment.class),
                                        AvailableVirtualKeyboardFragment.class),
                                NewKeyboardLayoutEnabledLocalesFragment.class),
                        AutofillPicker.class),
                ProfileSelectAutofillFragment.class.getName());
    }
}
